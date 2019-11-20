package kmucs.mobileprogramming.team.a.blocklylmc.Dialog;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import kmucs.mobileprogramming.team.a.blocklylmc.LeaderBoardItem;
import kmucs.mobileprogramming.team.a.blocklylmc.R;
import kmucs.mobileprogramming.team.a.blocklylmc.Recycler.ResultDialogLeaderboardAdapter;
import kmucs.mobileprogramming.team.a.blocklylmc.Recycler.ResultDialogLeaderboardItemDecoration;
import kmucs.mobileprogramming.team.a.blocklylmc.ResponseData;
import kmucs.mobileprogramming.team.a.blocklylmc.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResultDialog extends DialogFragment implements Callback<ResponseData> {

    private RecyclerView leaderboardRecycler;
    private TextView result;

    private int level;
    private String username;

    private LeaderBoardItem[] leaderboard;

    private Dialog.OnClickListener onClickListener;

    public void setOnClickListener(Dialog.OnClickListener listener){
        onClickListener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        Bundle bundle = getArguments();

        level = 1;
        leaderboard = null;
        username = "";

        if (bundle != null) {
            level = bundle.getInt("lv", 1);
            username = bundle.getString("username", "");
        }

        View resultLayout = getActivity().getLayoutInflater().inflate(R.layout.dialog_result, null);
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(RetrofitService.requestBaseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            RetrofitService retrofitService = retrofit.create(RetrofitService.class);

            Call<ResponseData> call = retrofitService.leaderboard(level);
            call.enqueue(this);
            //call.execute();
        } catch (Exception e) {
            Toast.makeText(getContext(), "leaderboard error", Toast.LENGTH_SHORT).show();
        }

        result = resultLayout.findViewById(R.id.result_output);
        result.setText("Success");
        result.setTextAppearance(R.style.ResultDialog_TextAppearance_Result_Success);

        leaderboardRecycler = resultLayout.findViewById(R.id.result_recycler_leaderboard);
        ViewGroup.LayoutParams layout = leaderboardRecycler.getLayoutParams();
        layout.height = (int)(256 * getContext().getResources().getDisplayMetrics().density);
        leaderboardRecycler.setLayoutParams(layout);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Result")
                .setView(resultLayout)
                .setCancelable(false)
                .setPositiveButton("계속 풀기",onClickListener)
                .setNeutralButton("문제 목록", onClickListener);

        ResultDialogLeaderboardAdapter adapter = new ResultDialogLeaderboardAdapter(leaderboard, username);
        leaderboardRecycler.setAdapter(adapter);
        leaderboardRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        leaderboardRecycler.addItemDecoration(new ResultDialogLeaderboardItemDecoration(adapter));

        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }

    @Override
    public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
        ResponseData responseData = response.body();
        leaderboard = responseData.getLeaderboard();
        ((ResultDialogLeaderboardAdapter)leaderboardRecycler.getAdapter()).setLeaderBoardItem(leaderboard);
        leaderboardRecycler.getAdapter().notifyDataSetChanged();
}

    @Override
    public void onFailure(Call<ResponseData> call, Throwable t) {

    }
}
