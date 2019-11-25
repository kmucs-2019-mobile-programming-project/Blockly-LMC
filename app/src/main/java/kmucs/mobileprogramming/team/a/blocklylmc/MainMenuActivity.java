package kmucs.mobileprogramming.team.a.blocklylmc;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import android.app.AlertDialog;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import kmucs.mobileprogramming.team.a.blocklylmc.materialshowcaseview.MaterialShowcaseSequence;
import kmucs.mobileprogramming.team.a.blocklylmc.materialshowcaseview.MaterialShowcaseView;
import kmucs.mobileprogramming.team.a.blocklylmc.materialshowcaseview.ShowcaseConfig;

/**
 * 작성자 : 20181617 박정현
 */

public class MainMenuActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);
        ImageButton btnChallenge = findViewById(R.id.btn_challenge);
        ImageButton btnPractice = findViewById(R.id.btn_practice);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setHomeAsUpIndicator(R.drawable.ic_blockly);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle("");

        TextView tv_license = findViewById(R.id.tv_license);
        SpannableString content = new SpannableString(tv_license.getText().toString());
        content.setSpan(new UnderlineSpan(), 0, content.length(),0);
        tv_license.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebView webView = new WebView(MainMenuActivity.this);
                webView.loadUrl("file:///android_asset/license/license.html");
                webView.setInitialScale(140);

                // display the WebView in an AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(MainMenuActivity.this);
                builder.setTitle("오픈소스 라이센스")
                        .setView(webView)
                        .setPositiveButton("닫기", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
            }
        });

        btnChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainMenuActivity.this);
                final EditText editText = new EditText(MainMenuActivity.this);
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setTitle("도전모드 입장")
                        .setMessage("사용할 닉네임을 입력하세요")
                        .setView(editText)
                        .setCancelable(true)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String username = editText.getText().toString();
                                if(username.length() > 0) {
                                    SharedPreferences sharedPreferences = getSharedPreferences("User_Info", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("username", username);
                                    editor.apply();
                                    Intent intent = new Intent(MainMenuActivity.this, ProblemListActivity.class);
                                    startActivity(intent);
                                }
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                builder.show();
            }
        });

        btnPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new MaterialShowcaseView.Builder(MainMenuActivity.this)
//                        .setTarget(new Point(539, 1504), 274)
//                        .setDismissText("확인")
//                        .setContentText("이 버튼은 버튼입니다.")
//                        .setDelay(100) // optional but starting animations immediately in onCreate can make them choppy
//                        .singleUse("2Shown ID2") // provide a unique ID used to ensure it is only shown once
//                        .show();
//
//
//                ShowcaseConfig config = new ShowcaseConfig();
//                config.setDelay(500); // half second between each showcase view
//
//                MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(MainMenuActivity.this, "TEST");
//
//                sequence.setConfig(config);
//
//                sequence.addSequenceItem(new Point(539, 1504), 274,
//                        "This is button one", "GOT IT");
//
//                sequence.addSequenceItem(new Point(539, 1504), 274,
//                        "This is button two", "GOT IT");
//
//                sequence.addSequenceItem(new Point(539, 1504), 274,
//                        "This is button three", "GOT IT");
//
//                sequence.start();

                Intent intent = new Intent(MainMenuActivity.this, BlocklyActivity.class);
                startActivity(intent);
            }
        });
    }
}
