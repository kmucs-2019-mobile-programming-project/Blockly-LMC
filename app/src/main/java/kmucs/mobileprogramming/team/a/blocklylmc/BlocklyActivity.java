package kmucs.mobileprogramming.team.a.blocklylmc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BlocklyActivity extends AppCompatActivity {
    private final Handler handler = new Handler();

    WebView webView;
    WebSettings webSettings;

    Button btn_run;

    FloatingActionButton fab;
    int lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blockly);

        fab = findViewById(R.id.floatingActionButton);
        fab.setVisibility(View.GONE);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            lv = bundle.getInt("lv", -1);
            if(lv != -1) {
                fab.setVisibility(View.VISIBLE);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // TODO: Problem Viewer Activity에 lv값 넣어서 호출하기
                        Toast.makeText(getApplicationContext(), String.valueOf(lv), Toast.LENGTH_LONG).show();
                    }
                });
            }
        }

        webView = findViewById(R.id.webview);
//        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/blockly/webview.html");
        webView.addJavascriptInterface(this, "AndroidAPP");

        btn_run = findViewById(R.id.btn_run);
        btn_run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.loadUrl("javascript:generateCode()");
            }
        });
    }

    @JavascriptInterface
    public void getCode(String code){
        String[] block = code.split("\n");
        String[] token = block[0].split(" ");
        int[] mailBoxes = new int[100];
        for(int i = 0; i < token.length; i++){
            if(i >= 100) break;
            mailBoxes[i] = Integer.parseInt(token[i]);
        }
        Intent intent = new Intent(this, EmulatorActivity.class);
        intent.putExtra("mailBoxes", mailBoxes);
        startActivity(intent);
    }
}
