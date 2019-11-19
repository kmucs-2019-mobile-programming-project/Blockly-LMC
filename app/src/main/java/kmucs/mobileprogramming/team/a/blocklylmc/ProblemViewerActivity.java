package kmucs.mobileprogramming.team.a.blocklylmc;

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

        Intent args = getIntent();
        String url = args.getExtras().getString("problemUrl", "");

        problemView = findViewById(R.id.problem_webview);
        problemView.setWebChromeClient(new WebChromeClient());
        WebSettings webSettings = problemView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        problemView.loadUrl(url);

    }
}
