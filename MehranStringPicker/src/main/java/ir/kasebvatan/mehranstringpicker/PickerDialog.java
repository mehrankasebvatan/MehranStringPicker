package ir.kasebvatan.mehranstringpicker;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PickerDialog {

    public interface OnItemSelectedListener {
        void onItemSelected(MehranModel model);
    }

    public static void show(Context context, int toolbarColor, int iconColor, String title, List<MehranModel> list, OnItemSelectedListener listener) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_picker);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        dialog.setCancelable(true);

        RelativeLayout toolbar = dialog.findViewById(R.id.toolbar);
        try {
            toolbar.setBackgroundColor(ContextCompat.getColor(context, toolbarColor));
        } catch (Exception e) {
            toolbar.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
            Log.d("MehranStringPicker: ", e.toString());
        }

        ImageView imgClose = dialog.findViewById(R.id.imgClose);
        imgClose.setOnClickListener(view -> dialog.dismiss());

        EditText etSearch = dialog.findViewById(R.id.etSearch);

        RecyclerView rvData = dialog.findViewById(R.id.rvData);

        TextView txtTitle = dialog.findViewById(R.id.txtTitle);
        txtTitle.setText(title);

        PickerAdapter adapter = new PickerAdapter(list, iconColor, item -> {
            if (listener != null) {
                listener.onItemSelected(item);
            }
            dialog.dismiss();
        });
        rvData.setAdapter(adapter);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Nothing
            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchText = s.toString();
                if (searchText.isEmpty()) {
                    adapter.search(list);
                } else {
                    List<MehranModel> filtered = new ArrayList<>();
                    for (MehranModel item : list) {
                        if (item.getStrTitle().contains(searchText)) {
                            filtered.add(item);
                        }
                    }
                    adapter.search(filtered);
                }
            }
        });

        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



