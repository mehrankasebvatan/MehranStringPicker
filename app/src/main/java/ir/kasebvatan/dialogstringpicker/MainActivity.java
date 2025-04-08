package ir.kasebvatan.dialogstringpicker;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import ir.kasebvatan.dialogstringpicker.databinding.ActivityMainBinding;
import ir.kasebvatan.mehranstringpicker.MehranModel;
import ir.kasebvatan.mehranstringpicker.MehranStringPicker;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.txtClick.setOnClickListener(v -> {

            ArrayList<MehranModel> options = new ArrayList<>();

            MehranModel n = new MehranModel();
            n.setStrTitle("Options Serach");
            n.setStrID("");
            n.setIntID(-1);
            n.setImage(R.drawable.ic_search);
            options.add(n);

            for (int i = 0; i < 10; i++) {
                MehranModel m = new MehranModel();
                m.setStrTitle("Options " + i);
                m.setStrID("");
                m.setIntID(i);
                m.setImage(-1);
                options.add(m);
            }
            new MehranStringPicker.Builder(this)
                    .setTitle("Choose 1 option")
                    .setList(options)
                    .setOnItemSelectedListener(new MehranStringPicker.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(MehranModel model) {
                            // انجام عملیات بعد از انتخاب
                            binding.txt.setText(model.getStrTitle());
                        }
                    })
                    .setToolbarColor(R.color.red) // تغییر رنگ تولبار (اختیاری)
                    .setIconColor(R.color.green)   // تغییر رنگ آیکون (اختیاری)
                    .setCancelable(false)       // غیرفعال کردن قابلیت لغو با لمس خارج از دیالوگ (اختیاری)
                    .show(); // برای نمایش مستقیم دیالوگ


        });
    }
}