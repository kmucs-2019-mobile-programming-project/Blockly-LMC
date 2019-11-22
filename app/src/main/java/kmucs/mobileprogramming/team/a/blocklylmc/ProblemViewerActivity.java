/*
    작성자 : 20181619 박종흠
 */

package kmucs.mobileprogramming.team.a.blocklylmc;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class ProblemViewerActivity extends AppCompatActivity {

    private WebView problemView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_viewer);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setHomeAsUpIndicator(R.drawable.ic_blockly);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle("");

        Intent args = getIntent();
        int level = args.getExtras().getInt("lv", 1);

        problemView = findViewById(R.id.problem_webview);
        problemView.setWebChromeClient(new WebChromeClient());
        WebSettings webSettings = problemView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        problemView.loadUrl("file:///android_asset/problem/problem_"+level+".html");

    }
}
