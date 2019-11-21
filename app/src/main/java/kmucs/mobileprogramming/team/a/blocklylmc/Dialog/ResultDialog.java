package kmucs.mobileprogramming.team.a.blocklylmc.Dialog;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import kmucs.mobileprogramming.team.a.blocklylmc.JudgeSystem;
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

public class ResultDialog extends DialogFragment implements Callback<ResponseData>, JudgeSystem.OnJudgeFinishedListener {

    private RecyclerView leaderboardRecycler;
    private TextView result;
    private ProgressBar loadingSpinner;

    private int level;
    private String username;
    private int[] mailBoxes;

    private JudgeSystem judgeSystem;

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
        mailBoxes = null;

        if (bundle != null) {
            level = bundle.getInt("lv", 1);
            username = bundle.getString("username", "");
            mailBoxes = bundle.getIntArray("mailBoxes");
        }

        judgeSystem = new JudgeSystem(level, mailBoxes);
        judgeSystem.setOnJudgeFinishedListener(this);

        View resultLayout = getActivity().getLayoutInflater().inflate(R.layout.dialog_result, null);

        result = resultLayout.findViewById(R.id.result_output);
        loadingSpinner = resultLayout.findViewById(R.id.result_loading_spinner);
        leaderboardRecycler = resultLayout.findViewById(R.id.result_recycler_leaderboard);

        showProgress();

        judgeSystem.start();

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

    void showProgress(){
        result.setVisibility(View.GONE);
        leaderboardRecycler.setVisibility(View.GONE);
        loadingSpinner.setVisibility(View.VISIBLE);
    }

    void hideProgress(){
        result.setVisibility(View.VISIBLE);
        leaderboardRecycler.setVisibility(View.VISIBLE);
        loadingSpinner.setVisibility(View.GONE);
    }

    @Override
    public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
        ResponseData responseData = response.body();
        leaderboard = responseData.getLeaderboard();
        ((ResultDialogLeaderboardAdapter)leaderboardRecycler.getAdapter()).setLeaderBoardItem(leaderboard);
        leaderboardRecycler.getAdapter().notifyDataSetChanged();
        hideProgress();
}

    @Override
    public void onFailure(Call<ResponseData> call, Throwable t) {

    }

    @Override
    public void onJudgeFinished(int level, int cycle, boolean success) {
        @StringRes int resultStringId = (success?R.string.dialog_result_success:R.string.dialog_result_failed);
        @StyleRes int resultStyleId = (success?R.style.ResultDialog_TextAppearance_Result_Success:R.style.ResultDialog_TextAppearance_Result_Failed);
        result.setText(resultStringId);
        result.setTextAppearance(resultStyleId);
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(RetrofitService.requestBaseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            RetrofitService retrofitService = retrofit.create(RetrofitService.class);

            Call<ResponseData> call = retrofitService.record(username, level, cycle);
            call.enqueue(this);
            //call.execute();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
