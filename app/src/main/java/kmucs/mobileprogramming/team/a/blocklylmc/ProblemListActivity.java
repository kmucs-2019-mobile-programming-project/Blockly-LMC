package kmucs.mobileprogramming.team.a.blocklylmc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ProblemListActivity extends AppCompatActivity implements View.OnClickListener {
    int[] btn_problem = {R.id.btn_lv1, R.id.btn_lv2};
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problemlist);

        for(int i=0; i<btn_problem.length; i++){
            findViewById(btn_problem[i]).setOnClickListener(this);
        }

        SharedPreferences sharedPreferences = getSharedPreferences("Tutorial_Info", MODE_PRIVATE);
        boolean isFirst = sharedPreferences.getBoolean("ProblemListActivity", false);
        if(!isFirst) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("ProblemListActivity", true);
            editor.apply();
            Intent intent = new Intent(this, TutorialActivity.class);
            intent.putExtra("layout_idx", 0);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View view) {
        int lv = -1;
        switch (view.getId()){
            case R.id.btn_lv1:
                lv = 1;
                break;
            case R.id.btn_lv2:
                lv = 2;
                break;
        }

        Intent intent = new Intent(this, BlocklyActivity.class);
        intent.putExtra("lv", lv);
        startActivity(intent);
    }
}