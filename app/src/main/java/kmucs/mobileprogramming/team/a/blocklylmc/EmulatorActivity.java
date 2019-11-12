package kmucs.mobileprogramming.team.a.blocklylmc;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class EmulatorActivity extends AppCompatActivity {

    private GridLayout lmcRegisterView;
    private Spinner lmcMemorySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emulator);

        lmcRegisterView = findViewById(R.id.lmc_register_view);
        lmcMemorySpinner = findViewById(R.id.lmc_memory_spinner);

        // TODO: 11/12/2019 스피너 레이아웃 수정 
//        ArrayList<String> lmcSpinnerItem = new ArrayList<>();
//        for(int i=0;i<99;i+=10){
//            lmcSpinnerItem.add(Integer.toString(i));
//        }
//        ArrayAdapter<String> lmcMemSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, lmcSpinnerItem);
//        lmcMemorySpinner.setAdapter(lmcMemSpinnerAdapter);
    }
}
