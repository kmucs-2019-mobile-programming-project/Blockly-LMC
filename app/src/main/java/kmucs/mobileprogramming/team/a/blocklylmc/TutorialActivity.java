package kmucs.mobileprogramming.team.a.blocklylmc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * 작성자 : 20181617 박정현
 */

public class TutorialActivity extends Activity {
    int[] layout = {R.layout.activity_tutorial_problemlist, R.layout.activity_tutorial_blockly};
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            int layout_idx = bundle.getInt("layout_idx", -1);
            if(layout_idx == -1) finish();
            setContentView(layout[layout_idx]);
        }
        else finish();
    }
}
