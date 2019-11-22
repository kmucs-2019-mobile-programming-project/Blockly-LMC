/*
    작성자 : 20181619 박종흠
 */

package kmucs.mobileprogramming.team.a.blocklylmc.Recycler;

import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import kmucs.mobileprogramming.team.a.blocklylmc.R;

public class ResultDialogLeaderboardItemDecoration extends RecyclerView.ItemDecoration {

    private ResultDialogLeaderboardStickyRecordInterface mListener;
    private int recordHeight;
    private int recyclerHeight;

    public ResultDialogLeaderboardItemDecoration(@NonNull ResultDialogLeaderboardStickyRecordInterface listener){
        mListener = listener;
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        recyclerHeight = parent.getHeight();

        View topChild = parent.getChildAt(0);
        // topChil = ((LinearLayoutManager)parent.getLayoutManager()).findLastVisibleItemPosition()
        if(topChild == null){
            return;
        }

        // int topChildPosition = parent.getChildAdapterPosition(topChild);
        int topChildPosition = ((LinearLayoutManager)parent.getLayoutManager()).findLastVisibleItemPosition();
        if(topChildPosition == RecyclerView.NO_POSITION){
            return;
        }

        int headerPos = mListener.getHeaderPositionForItem(topChildPosition);
        if(headerPos == -1){
            return;
        }
        View currentHeader = getHeaderViewForItem(headerPos, parent);
        fixLayoutSize(parent, currentHeader);
        int contactPoint = currentHeader.getTop();
        View childInContact = getChildInContact(parent, contactPoint, headerPos);

        if(childInContact != null && mListener.isHeader(parent.getChildAdapterPosition(childInContact))){
            moveHeader(c, currentHeader, childInContact);
            return;
        }

        drawHeader(c, currentHeader);
    }

    private View getHeaderViewForItem(int headerPosition, RecyclerView parent){
        int layoutResId = mListener.getHeaderLayout(headerPosition);
        View header = LayoutInflater.from(parent.getContext()).inflate(layoutResId, parent, false);
        header.setBackgroundResource(R.color.resultMyRecordBackground);
        int padding_start_end = (int)(16 * parent.getContext().getResources().getDisplayMetrics().density);
        int padding_top = (int)(16 * parent.getContext().getResources().getDisplayMetrics().density);
        // header.setPadding(padding_start_end, padding_top, padding_start_end,0);
        mListener.bindHeaderData(header, headerPosition);
        return header;
    }

    private void drawHeader(Canvas c, View header){
        c.save();
        c.translate(0,recyclerHeight - header.getHeight());
        header.draw(c);
        c.restore();
    }

    private void moveHeader(Canvas c, View currentHeader, View nextHeader){
        c.save();
        c.translate(0, nextHeader.getTop() - currentHeader.getHeight());
        currentHeader.draw(c);
        c.restore();
    }

    private View getChildInContact(RecyclerView parent, int contactPoint, int currentHeaderPos){
        View childInContact = null;
        for (int i=parent.getChildCount() - 1;i>=0;i--) {
            int heightTolerance = 0;
            View child = parent.getChildAt(i);
            if (currentHeaderPos != i) {
                boolean isChildHeader = mListener.isHeader(parent.getChildAdapterPosition(child));
                if (isChildHeader) {
                    heightTolerance = recordHeight - child.getHeight();
                }
            }
            int childTopPosition;
            if (child.getTop() > recyclerHeight) {
                childTopPosition = child.getTop() + heightTolerance;
            } else {
                childTopPosition = child.getTop();
            }
            if(childTopPosition < contactPoint){
                if(child.getBottom() >= contactPoint){
                    childInContact = child;
                    break;
                }
            }
        }
        return childInContact;
    }

    private void fixLayoutSize(ViewGroup parent, View view){
        int widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(), View.MeasureSpec.EXACTLY);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight(), View.MeasureSpec.UNSPECIFIED);

        int childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec,parent.getPaddingStart(),view.getLayoutParams().width);
        int childHeightspec = ViewGroup.getChildMeasureSpec(heightSpec, parent.getPaddingEnd(), view.getLayoutParams().height);

        view.measure(childWidthSpec, childHeightspec);

        view.layout(0,0,view.getMeasuredWidth(),  recordHeight = view.getMeasuredHeight());
    }

    public interface ResultDialogLeaderboardStickyRecordInterface {
        int getHeaderPositionForItem(int itemPosition);
        int getHeaderLayout(int headerPosition);
        void bindHeaderData(View header, int headerPosition);
        boolean isHeader(int itemPosition);
    }

}
