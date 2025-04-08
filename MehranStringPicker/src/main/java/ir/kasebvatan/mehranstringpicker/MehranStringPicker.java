package ir.kasebvatan.mehranstringpicker;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MehranStringPicker {

    public interface OnItemSelectedListener {
        void onItemSelected(MehranModel model);
    }

    public static class Builder {
        private final Context context;
        private int toolbarColor = 0xFF537188;
        private int iconColor = 0xFF537188;
        private String title;
        private List<MehranModel> list;
        private OnItemSelectedListener listener;
        private boolean cancelable = true;

        public Builder(@NonNull Context context) {
            this.context = context;
        }

        public Builder setToolbarColor(int toolbarColor) {
            this.toolbarColor = toolbarColor;
            return this;
        }

        public Builder setIconColor(int iconColor) {
            this.iconColor = iconColor;
            return this;
        }

        public Builder setTitle(@NonNull String title) {
            this.title = title;
            return this;
        }

        public Builder setList(@NonNull List<MehranModel> list) {
            this.list = list;
            return this;
        }

        public Builder setOnItemSelectedListener(OnItemSelectedListener listener) {
            this.listener = listener;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Dialog build() {
            if (title == null || list == null) {
                throw new IllegalArgumentException("Title and list must be set.");
            }

            Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.dialog_picker);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setGravity(Gravity.BOTTOM);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
            dialog.setCancelable(cancelable);

            dialog.setOnKeyListener((dialogInterface, keyCode, event) -> {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    dialog.dismiss();
                    return true;
                }
                return false;
            });

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

            List<MehranModel> originalList = new ArrayList<>(list);

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
                        adapter.search(originalList);
                    } else {
                        List<MehranModel> filtered = new ArrayList<>();
                        for (MehranModel item : originalList) {
                            if (item.getStrTitle().contains(searchText)) {
                                filtered.add(item);
                            }
                        }
                        adapter.search(filtered);
                    }
                }
            });

            return dialog;
        }

        public void show() {
            build().show();
        }
    }
}