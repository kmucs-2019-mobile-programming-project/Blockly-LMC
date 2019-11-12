package kmucs.mobileprogramming.team.a.blocklylmc;

import android.os.Bundle;
import android.widget.GridLayout;

import androidx.appcompat.app.AppCompatActivity;

public class EmulatorActivity extends AppCompatActivity {

    private GridLayout lmcRegisterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emulator);

        lmcRegisterView = findViewById(R.id.lmc_register_view);
    }
}
