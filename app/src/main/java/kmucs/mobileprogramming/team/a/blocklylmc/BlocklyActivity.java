package kmucs.mobileprogramming.team.a.blocklylmc;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.xml.transform.Result;

import kmucs.mobileprogramming.team.a.blocklylmc.Dialog.ResultDialog;

public class BlocklyActivity extends AppCompatActivity implements Dialog.OnClickListener, View.OnClickListener {
    private final Handler handler = new Handler();

    WebView webView;
    WebSettings webSettings;

    Button btn_run;
    Button btn_submit;

    FloatingActionButton fab;
    int lv;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
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
                        Toast.makeText(getApplicationContext(), String.valueOf(lv), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(BlocklyActivity.this, ProblemViewerActivity.class);
                        intent.putExtra("lv", lv);
                        startActivity(intent);
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

        btn_submit = findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);

        SharedPreferences sharedPreferences = getSharedPreferences("Tutorial_Info", MODE_PRIVATE);
        boolean isFirst = sharedPreferences.getBoolean("BlocklyActivity", false);
        if(!isFirst) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("BlocklyActivity", true);
            editor.apply();
            Intent intent = new Intent(this, TutorialActivity.class);
            intent.putExtra("layout_idx", 1);
            startActivity(intent);
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == -1)
            finish();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch(which){
            case Dialog.BUTTON_NEUTRAL:
                finish();
                break;
            case Dialog.BUTTON_POSITIVE:
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if(viewId == R.id.btn_submit){
            Bundle dialogBundle = new Bundle();
            dialogBundle.putString("username", "v");
            dialogBundle.putInt("lv",lv);

            ResultDialog resultdialog = new ResultDialog();
            resultdialog.setOnClickListener(this);
            resultdialog.setTargetFragment(getSupportFragmentManager().getFragment(new Bundle(),"blocklyActivity"),0);
            resultdialog.setArguments(dialogBundle);
            resultdialog.show(getSupportFragmentManager(), "result dialog");

        }
    }
}
