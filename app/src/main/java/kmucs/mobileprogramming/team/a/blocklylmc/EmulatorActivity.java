package kmucs.mobileprogramming.team.a.blocklylmc;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import kmucs.mobileprogramming.team.a.blocklylmc.Dialog.NumberPickerDialog;
import kmucs.mobileprogramming.team.a.blocklylmc.Recycler.MemoryRecyclerAdapter;

public class EmulatorActivity extends AppCompatActivity implements View.OnClickListener, NumberPicker.OnValueChangeListener {

    private GridLayout lmcRegisterView;
    private Spinner lmcMemorySpinner;

    private RecyclerView memoryRecyclerView;

    private int[] lmcMailbox;

    private LMC machine;

    private ImageButton buttonStep, buttonReset;

    private TextView lmcPC, lmcACC, lmcIR, lmcPSR;

    private int numberPickerValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emulator);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayHomeAsUpEnabled(true);
        // actionBar.setIcon(R.drawable.back);
        actionBar.setTitle("Back to the code");

        lmcMailbox = getIntent().getIntArrayExtra("mailBoxes");

        lmcRegisterView = findViewById(R.id.lmc_register_view);
        lmcMemorySpinner = findViewById(R.id.lmc_memory_spinner);
        memoryRecyclerView = findViewById(R.id.lmc_memory_recycler);
        memoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        MemoryRecyclerAdapter adapter = new MemoryRecyclerAdapter(lmcMailbox);
        memoryRecyclerView.setAdapter(adapter);

        machine = new LMC();
        machine.setMailBoxes(lmcMailbox);

        buttonStep = findViewById(R.id.lmc_step);
        buttonStep.setOnClickListener(this);

        buttonReset = findViewById(R.id.lmc_reset);
        buttonReset.setOnClickListener(this);

        lmcPC = findViewById(R.id.lmc_pc);
        lmcACC = findViewById(R.id.lmc_acc);
        lmcIR = findViewById(R.id.lmc_ir);
        lmcPSR = findViewById(R.id.lmc_psr);
        // TODO: 11/12/2019 스피너 레이아웃 수정 
//        ArrayList<String> lmcSpinnerItem = new ArrayList<>();
//        for(int i=0;i<99;i+=10){
//            lmcSpinnerItem.add(Integer.toString(i));
//        }
//        ArrayAdapter<String> lmcMemSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, lmcSpinnerItem);
//        lmcMemorySpinner.setAdapter(lmcMemSpinnerAdapter);
    }
    
    void refreshRecycler(int position){
        ((MemoryRecyclerAdapter)memoryRecyclerView.getAdapter()).setLmcData(machine.getMailBoxes());
        memoryRecyclerView.getAdapter().notifyDataSetChanged();
        ((MemoryRecyclerAdapter) memoryRecyclerView.getAdapter()).setCurrentRow(position);
        ((LinearLayoutManager) memoryRecyclerView.getLayoutManager()).scrollToPositionWithOffset(position,memoryRecyclerView.getChildAt(0).getHeight());
        // memoryRecyclerView.scrollToPosition(position);

    }

    int lmcINP(){
        // input dialog
        NumberPickerDialog numberPickerDialog = new NumberPickerDialog();
        numberPickerDialog.setValueChangeListener(this);
        numberPickerDialog.show(this.getSupportFragmentManager(),"number picker");
        return numberPickerValue;
    }

    void lmcOUT(int value){
        // output dialog
        AlertDialog.Builder outputDialog = new AlertDialog.Builder(this);
        outputDialog.setTitle("Output")
                .setMessage(Integer.toString(value))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        outputDialog.show();

    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch(viewId) {
            case R.id.lmc_step:
                try {
                    machine.step();
                } catch (OutOfMailboxException e){
                    // error dialog
                    onClick(findViewById(R.id.lmc_reset));
                    return;
                }
                if (machine.getWaitINP()) {
                    // input dialog
                    Toast.makeText(this, "wait inp", Toast.LENGTH_SHORT).show();
                    //machine.setIO(lmcINP());
                    lmcINP();
                    Log.d("emu","io" + numberPickerValue);
                } else if (machine.getWaitOUT()) {
                    // output dialog
                    Toast.makeText(this, "wait out", Toast.LENGTH_SHORT).show();
                    lmcOUT(machine.getIO());
                    Log.d("emu","io" + machine.getA());
                }
                int current = machine.getPC();
                refreshRecycler(current);
                updateRegister();
                break;
            case R.id.lmc_reset:
                machine.reset();
                refreshRecycler(0);
                updateRegister();
                break;
            default:
                break;
        }
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        numberPickerValue = picker.getValue();
        machine.setIO(picker.getValue());
        updateRegister();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void updateRegister(){
        lmcPC.setText(Integer.toString(machine.getPC()));
        lmcACC.setText(Integer.toString(machine.getA()));
        lmcIR.setText(Integer.toString(machine.getIR()));
        lmcPSR.setText(Integer.toString(machine.getPSR()));
    }
}
