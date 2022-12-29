package hu.home.qrdolgozat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.time.LocalDateTime;

import hu.home.qrdolgozat.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.scanBtn.setOnClickListener((e) -> {

            IntentIntegrator intentIntegrator = new IntentIntegrator(this);
            intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
            intentIntegrator.setPrompt("Szkennelj egy QR kódot");
            intentIntegrator.setCameraId(0);
            intentIntegrator.setBeepEnabled(false);
            intentIntegrator.setBarcodeImageEnabled(false);
            intentIntegrator.initiateScan();
        });
        binding.saveBtn.setOnClickListener((e) -> {
            if (binding.getResultString().isEmpty()){
                Toast.makeText(this, "Mentéshez szkenneljen be egy QR kódot!", Toast.LENGTH_SHORT).show();
                return;
            }
            db = new DBHelper(MainActivity.this);
            if (db.insertData(binding.getResultString(), "1111", "now")){
                Toast.makeText(this, "QR kód tartalma sikeresen mentve!", Toast.LENGTH_SHORT).show();
                binding.resultTextView.setText("");
            }else {
                Toast.makeText(this, "Mentés sikertelen, próbáld meg később!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Megszakítva", Toast.LENGTH_LONG).show();
            } else {
                binding.setResultString(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}