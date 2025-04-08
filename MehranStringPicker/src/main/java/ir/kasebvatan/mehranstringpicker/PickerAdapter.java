package ir.kasebvatan.mehranstringpicker;


import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.kasebvatan.mehranstringpicker.databinding.ItemPickerBinding;

public class PickerAdapter extends RecyclerView.Adapter<PickerAdapter.VH> {

    private List<MehranModel> list;
    private int color;
    private OnItemClickListener callback;

    public interface OnItemClickListener {
        void onItemClick(MehranModel model);
    }

    public PickerAdapter(List<MehranModel> list, int color, OnItemClickListener callback) {
        this.list = list;
        this.callback = callback;
        this.color = color;
    }

    public static class VH extends RecyclerView.ViewHolder {
        ItemPickerBinding binding;

        public VH(ItemPickerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemPickerBinding binding = ItemPickerBinding.inflate(inflater, parent, false);
        return new VH(binding);
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        MehranModel data = list.get(position);

        holder.binding.txtTitle.setText(data.getStrTitle());

        try {
            holder.binding.img.setImageTintList(ContextCompat.getColorStateList(
                    holder.itemView.getContext(),
                    color
            ));
        } catch (Exception e) {
            holder.binding.img.setImageTintList(ContextCompat.getColorStateList(
                    holder.itemView.getContext(),
                    R.color.colorPrimary
            ));
        }


        try {
            holder.binding.img.setImageDrawable(
                    ContextCompat.getDrawable(
                            holder.itemView.getContext(),
                            data.image
                    )
            );
            holder.binding.img.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            holder.binding.img.setVisibility(View.INVISIBLE);
            Log.d("MehranStringPicker: ", e.toString());
        }


        holder.itemView.setOnClickListener(v -> {
            if (callback != null) {
                callback.onItemClick(data);
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    public void search(List<MehranModel> newList) {
        this.list = newList;
        notifyDataSetChanged();
    }
}


