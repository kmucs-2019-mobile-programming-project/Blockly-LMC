package kmucs.mobileprogramming.team.a.blocklylmc.Recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import kmucs.mobileprogramming.team.a.blocklylmc.InstructionSet;
import kmucs.mobileprogramming.team.a.blocklylmc.R;

public class MemoryRecyclerAdapter extends RecyclerView.Adapter<MemoryRecyclerAdapter.ViewHolder> {

    private int[] lmcData;

    private int currentRow;

    public MemoryRecyclerAdapter(int[] data){
        lmcData = data;
    }

    public void setLmcData(int[] lmcData) {
        this.lmcData = lmcData;
        currentRow = 0;
    }

    public void setCurrentRow(int row){
        currentRow=row;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recycler_memory_cell, parent, false);
        ViewHolder vh = new ViewHolder(view);
        vh.setIsRecyclable(false);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String code = Integer.toString(lmcData[position]);
        if(position == currentRow)
            holder.linearLayout.setBackgroundResource(R.color.memoryCellCurrent);
        holder.memory_loc.setText(Integer.toString(position));
        holder.memory_disassemble.setText(InstructionSet.disassemble(lmcData[position]));
        holder.memory_code.setText(code);
    }

    @Override
    public int getItemCount() {
        if(lmcData == null)
            return 0;
        return lmcData.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView memory_loc, memory_disassemble, memory_code;
        public LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.recycler_layout);
            memory_loc = itemView.findViewById(R.id.recycler_loc);
            memory_disassemble = itemView.findViewById(R.id.recycler_disassemble);
            memory_code = itemView.findViewById(R.id.recycler_code);
        }
    }
}
