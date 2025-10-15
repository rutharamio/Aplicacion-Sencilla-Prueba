package com.example.aplicacionsencillaprueba;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class ContactDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        // Obtener las vistas de texto y botones
        TextView nameTV = findViewById(R.id.nameTV);
        TextView numberTV = findViewById(R.id.numberTV);
        Button callButton = findViewById(R.id.callButton);
        Button smsButton = findViewById(R.id.smsButton);

        // Obtener datos del Intent
        String name = getIntent().getStringExtra("name");
        String contact = getIntent().getStringExtra("contact");

        // Mostrar los datos del contacto
        nameTV.setText(name);
        numberTV.setText(contact);

        // Acción al hacer clic en "Llamar"
        callButton.setOnClickListener(v -> {
            String phone = numberTV.getText().toString();

            // Verificar si se tiene el permiso para hacer llamadas
            if (ContextCompat.checkSelfPermission(ContactDetailActivity.this, Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                // Realizar la llamada
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phone));
                startActivity(callIntent);
            } else {
                // Pedir permiso si no se tiene
                ActivityCompat.requestPermissions(ContactDetailActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE}, 1);
            }
        });

        // Acción al hacer clic en "Enviar SMS"
        smsButton.setOnClickListener(v -> {
            String phone = numberTV.getText().toString();
            // Abrir la app de SMS con el número prellenado
            Intent smsIntent = new Intent(Intent.ACTION_VIEW);
            smsIntent.setData(Uri.parse("smsto:" + phone));
            smsIntent.putExtra("sms_body", "Hola, te estoy enviando un mensaje desde mi app de contactos.");
            startActivity(smsIntent);
        });
    }

    // Callback para los permisos de llamada
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Si el permiso es concedido, realizar la llamada
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            String phone = getIntent().getStringExtra("contact");
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + phone));
            startActivity(callIntent);
        } else {
            Toast.makeText(this, "Permiso de llamada no concedido", Toast.LENGTH_SHORT).show();
        }
    }
}
