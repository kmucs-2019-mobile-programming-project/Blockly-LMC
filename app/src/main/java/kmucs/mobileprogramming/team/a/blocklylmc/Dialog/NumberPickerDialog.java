/*
    작성자 : 20181619 박종흠
 */

package kmucs.mobileprogramming.team.a.blocklylmc.Dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class NumberPickerDialog extends DialogFragment {
    private NumberPicker.OnValueChangeListener valueChangeListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        final NumberPicker numberPicker = new NumberPicker(getActivity());

        numberPicker.setValue(0);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(999);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Input")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        valueChangeListener.onValueChange(numberPicker, numberPicker.getValue(), numberPicker.getValue());
                    }
                });
        builder.setView(numberPicker);
        return builder.create();
    }

    public NumberPicker.OnValueChangeListener getValueChangeListener() {
        return valueChangeListener;
    }

    public void setValueChangeListener(NumberPicker.OnValueChangeListener valueChangeListener) {
        this.valueChangeListener = valueChangeListener;
    }
}
