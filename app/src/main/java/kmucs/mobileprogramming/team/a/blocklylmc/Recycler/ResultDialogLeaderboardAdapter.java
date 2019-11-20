package kmucs.mobileprogramming.team.a.blocklylmc.Recycler;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.CycleInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import kmucs.mobileprogramming.team.a.blocklylmc.LeaderBoardItem;
import kmucs.mobileprogramming.team.a.blocklylmc.R;

public class ResultDialogLeaderboardAdapter extends RecyclerView.Adapter<ResultDialogLeaderboardAdapter.ViewHolder> implements ResultDialogLeaderboardItemDecoration.ResultDialogLeaderboardStickyRecordInterface {
    final static int MY_RECORD = 1;
    final static int NORMAL_RECORD = 0;

    LeaderBoardItem leaderBoardItem[];
    String username;

    public ResultDialogLeaderboardAdapter(LeaderBoardItem[] data, String username){
        this.username = username;
        leaderBoardItem = data;
    }

    public void setLeaderBoardItem(LeaderBoardItem[] item){
        leaderBoardItem = item;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recycler_leaderboard_cell, parent, false);
        ViewHolder vh = new ViewHolder(view);
        vh.setIsRecyclable(false);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LeaderBoardItem score = leaderBoardItem[position];
        if(position%2 == 1){
            holder.linearLayout.setBackgroundResource(R.color.resultRecordEven);
        } else{
            holder.linearLayout.setBackgroundResource(R.color.resultRecordOdd);
        }
        if(score.getUsername().equals(username)){
            holder.linearLayout.setBackgroundResource(R.color.resultMyRecordBackground);
        }
        holder.rank.setText(Integer.toString(position+1));
        holder.username.setText(score.getUsername());
        holder.cycle.setText(Integer.toString(score.getCycle()));
    }

    @Override
    public int getItemCount() {
        if(leaderBoardItem == null)
            return 0;
        return leaderBoardItem.length;
    }

    public boolean isMyRecored(int position){
        return leaderBoardItem[position].getUsername().equals(username);
    }

    @Override
    public int getHeaderPositionForItem(int itemPosition) {
        int headerPosition = 0;

        do {
            if (this.isHeader(itemPosition)) {
                headerPosition = itemPosition;
                break;
            }
            itemPosition += 1;
        } while (itemPosition < leaderBoardItem.length);
        if(headerPosition == 0 && !this.isHeader(0))
            return -1;
        return headerPosition;
    }

    @Override
    public int getHeaderLayout(int headerPosition) {
        return R.layout.recycler_leaderboard_cell;
    }

    @Override
    public void bindHeaderData(View header, int headerPosition) {
        TextView rank, username, cycle;
        rank = header.findViewById(R.id.recycler_record_rank);
        username = header.findViewById(R.id.recycler_record_username);
        cycle = header.findViewById(R.id.recycler_record_cycle);
        rank.setText(Integer.toString(headerPosition+1));
        username.setText(leaderBoardItem[headerPosition].getUsername());
        cycle.setText(Integer.toString(leaderBoardItem[headerPosition].getCycle()));
    }

    @Override
    public boolean isHeader(int itemPosition) {
        return isMyRecored(itemPosition);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout linearLayout;
        private TextView rank,username, cycle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.recycler_record_layout);
            rank = itemView.findViewById(R.id.recycler_record_rank);
            username = itemView.findViewById(R.id.recycler_record_username);
            cycle = itemView.findViewById(R.id.recycler_record_cycle);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if(isHeader(position))
            return MY_RECORD;
        return NORMAL_RECORD;
    }
}

