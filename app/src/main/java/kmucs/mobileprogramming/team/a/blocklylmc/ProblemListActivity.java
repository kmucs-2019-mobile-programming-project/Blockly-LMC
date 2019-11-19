package kmucs.mobileprogramming.team.a.blocklylmc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ProblemListActivity extends AppCompatActivity implements View.OnClickListener {
    int[] btn_problem = {R.id.btn_lv1, R.id.btn_lv2};
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problemlist);

        for(int i=0; i<btn_problem.length; i++){
            findViewById(btn_problem[i]).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, BlocklyActivity.class);
        intent.putExtra("id", view.getId());
        startActivity(intent);
    }
}