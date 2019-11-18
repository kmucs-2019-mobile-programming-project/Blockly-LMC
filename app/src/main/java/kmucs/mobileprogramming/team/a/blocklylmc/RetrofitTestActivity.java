package kmucs.mobileprogramming.team.a.blocklylmc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitTestActivity extends AppCompatActivity {
    EditText et_username, et_level, et_cycle;
    Button btn_record, btn_leaderboard;
    TextView tv_leaderboard;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofittest);

        btn_record = findViewById(R.id.btn_record);
        btn_leaderboard = findViewById(R.id.btn_leaderboard);

        et_username = findViewById(R.id.et_username);
        et_level = findViewById(R.id.et_level);
        et_cycle = findViewById(R.id.et_cycle);

        tv_leaderboard = findViewById(R.id.tv_leaderboard);

        btn_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(RetrofitService.requestBaseURL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    RetrofitService retrofitService = retrofit.create(RetrofitService.class);

                    Call<ResponseData> call = retrofitService.record(et_username.getText().toString(), Integer.parseInt(et_level.getText().toString()), Integer.parseInt(et_cycle.getText().toString()));
                    call.enqueue(new Callback<ResponseData>() {
                        @Override
                        public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                            ResponseData responseData = response.body();
                            Toast.makeText(getApplicationContext(), responseData.getStatus().toString() + " " + responseData.getMessage(), Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<ResponseData> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Request Failure", Toast.LENGTH_LONG).show();
                        }
                    });
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Exception", Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(RetrofitService.requestBaseURL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    RetrofitService retrofitService = retrofit.create(RetrofitService.class);

                    Call<ResponseData> call = retrofitService.leaderboard(Integer.parseInt(et_level.getText().toString()));
                    call.enqueue(new Callback<ResponseData>() {
                        @Override
                        public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                            ResponseData responseData = response.body();
                            LeaderBoardItem[] leaderboard = responseData.getLeaderboard();
                            String s = "";
                            for (int i = 0; i < leaderboard.length; i++) {
                                s += leaderboard[i].toString() + "\n";
                            }
                            tv_leaderboard.setText(s);
                        }

                        @Override
                        public void onFailure(Call<ResponseData> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Request Failure", Toast.LENGTH_LONG).show();
                        }
                    });
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Exception", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
