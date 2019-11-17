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

public class BlocklyActivity extends AppCompatActivity {
    private final Handler handler = new Handler();

    WebView webView;
    WebSettings webSettings;

    Button btn_run;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blockly);

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
        startActivity(new Intent(this, EmulatorActivity.class));
    }
}
