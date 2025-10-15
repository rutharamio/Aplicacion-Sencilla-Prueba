package com.example.aplicacionsencillaprueba;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.PermissionListener;

public class ContactDetailActivity extends AppCompatActivity {

    private TextView nameTV, phoneTV;
    private Button callBtn, smsBtn;
    private String name, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        nameTV = findViewById(R.id.nameTV);
        phoneTV = findViewById(R.id.phoneTV);
        callBtn = findViewById(R.id.callBtn);
        smsBtn = findViewById(R.id.smsBtn);


        // Recibir datos del intent
        name = getIntent().getStringExtra("name");
        phone = getIntent().getStringExtra("contact");

        if (name == null) name = "Sin nombre";
        if (phone == null) phone = "";

        nameTV.setText(name);
        phoneTV.setText(phone);

        callBtn.setOnClickListener(v -> requestCallPermissionAndCall());

        smsBtn.setOnClickListener(v -> {
            if (phone.isEmpty()) {
                Toast.makeText(this, "Número no disponible", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent i = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phone));
            i.putExtra("sms_body", "Hola " + name + "!");
            startActivity(i);
        });
    }

    private void requestCallPermissionAndCall() {
        if (phone.isEmpty()) {
            Toast.makeText(this, "Número no disponible", Toast.LENGTH_SHORT).show();
            return;
        }

        Dexter.withContext(this)
                .withPermission(Manifest.permission.CALL_PHONE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        callNumber();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // fallback: abrir dialer si no dio permiso
                        openDialer();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(com.karumi.dexter.listener.PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void callNumber() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        try {
            startActivity(intent);
        } catch (SecurityException ex) {
            // si por alguna razón falta permiso
            openDialer();
        }
    }

    private void openDialer() {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        startActivity(intent);
    }
}

