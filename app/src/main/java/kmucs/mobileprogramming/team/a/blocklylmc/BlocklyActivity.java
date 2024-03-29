package kmucs.mobileprogramming.team.a.blocklylmc;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.xml.transform.Result;

import kmucs.mobileprogramming.team.a.blocklylmc.Dialog.ResultDialog;
import kmucs.mobileprogramming.team.a.blocklylmc.materialshowcaseview.MaterialShowcaseSequence;
import kmucs.mobileprogramming.team.a.blocklylmc.materialshowcaseview.MaterialShowcaseView;
import kmucs.mobileprogramming.team.a.blocklylmc.materialshowcaseview.ShowcaseConfig;

/**
 * 작성자 : 20181617 박정현
 */

public class BlocklyActivity extends AppCompatActivity implements Dialog.OnClickListener, View.OnClickListener {
    private final Handler handler = new Handler();

    WebView webView;
    WebSettings webSettings;

    ImageButton btn_run;
    ImageButton btn_submit;

    FloatingActionButton fab;
    int lv;

    int[] mailBoxes;
    boolean isSubmit;

    boolean isPractice;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blockly);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setHomeAsUpIndicator(R.drawable.ic_blockly);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle("");

        fab = findViewById(R.id.floatingActionButton);
        fab.setVisibility(View.GONE);
        isPractice = true;

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            lv = bundle.getInt("lv", -1);
            if(lv != -1) {
                isPractice = false;
                fab.setVisibility(View.VISIBLE);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
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
                isSubmit=false;
                webView.loadUrl("javascript:generateCode()");
            }
        });

        btn_submit = findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);

        int delay = 50;

        if(isPractice){
            ShowcaseConfig config = new ShowcaseConfig();
            config.setDelay(delay); // half second between each showcase view

            MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(BlocklyActivity.this, getResources().getText(R.string.tutorial_blockly_challenge).toString());
            sequence.setConfig(config);
            sequence.addSequenceItem(btn_run, getResources().getText(R.string.tutorial_blockly_run).toString(), getResources().getText(R.string.tutorial_agree).toString());
            sequence.addSequenceItem(btn_submit, getResources().getText(R.string.tutorial_blockly_submit_practice).toString(), getResources().getText(R.string.tutorial_agree).toString());
//        sequence.addSequenceItem(btn_submit, getResources().getText(R.string.tutorial_blockly_submit).toString(), getResources().getText(R.string.tutorial_agree).toString());
            sequence.start();
        }
        else{
            ShowcaseConfig config = new ShowcaseConfig();
            config.setDelay(delay); // half second between each showcase view

            MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(BlocklyActivity.this, getResources().getText(R.string.tutorial_blockly_practice).toString());
            sequence.setConfig(config);
            sequence.addSequenceItem(btn_run, getResources().getText(R.string.tutorial_blockly_run).toString(), getResources().getText(R.string.tutorial_agree).toString());
            sequence.addSequenceItem(btn_submit, getResources().getText(R.string.tutorial_blockly_submit).toString(), getResources().getText(R.string.tutorial_agree).toString());
            sequence.addSequenceItem(fab, getResources().getText(R.string.tutorial_blockly_fab).toString(), getResources().getText(R.string.tutorial_agree).toString());
//        sequence.addSequenceItem(btn_submit, getResources().getText(R.string.tutorial_blockly_submit).toString(), getResources().getText(R.string.tutorial_agree).toString());
            sequence.start();
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
        this.mailBoxes = mailBoxes;
        if(isSubmit){
            submit();
        } else {
            Intent intent = new Intent(getApplicationContext(), EmulatorActivity.class);
            intent.putExtra("mailBoxes", mailBoxes);
            startActivity(intent);
        }
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

    public void submit(){
        Bundle dialogBundle = new Bundle();
        dialogBundle.putString("username", getSharedPreferences("User_Info", MODE_PRIVATE).getString("username", ""));
        dialogBundle.putInt("lv", lv);
        dialogBundle.putIntArray("mailBoxes", mailBoxes);
        ResultDialog resultdialog = new ResultDialog();
        resultdialog.setOnClickListener(this);
        resultdialog.setTargetFragment(getSupportFragmentManager().getFragment(new Bundle(), "blocklyActivity"), 0);
        resultdialog.setArguments(dialogBundle);
        resultdialog.show(getSupportFragmentManager(), "result dialog");
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if(viewId == R.id.btn_submit){
            if(isPractice) Toast.makeText(getApplicationContext(), "도전 모드에서만 제출할 수 있습니다", Toast.LENGTH_LONG).show();
            else {
                isSubmit = true;
                webView.loadUrl("javascript:generateCode()");
            }
        }
    }
}
