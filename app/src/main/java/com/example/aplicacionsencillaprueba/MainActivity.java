package com.example.aplicacionsencillaprueba;

import android.Manifest;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import android.content.Intent;


public class MainActivity extends AppCompatActivity {

    private ArrayList<Contacts> contactsArrayList = new ArrayList<>();
    private RecyclerView contactRV;
    private Adapter adapter;
    private final ActivityResultLauncher<Intent> createContactLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    contactsArrayList.clear();
                    getContacts();   // refresca lista después de crear un contacto
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactRV = findViewById(R.id.rv);
        FloatingActionButton addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateNewContactActivity.class);
            createContactLauncher.launch(intent);
        });



        ArrayList<Adapter.Contacts> contactsList = new ArrayList<>();
        adapter = new Adapter(this, contactsList);
        contactRV.setLayoutManager(new LinearLayoutManager(this));
        contactRV.setAdapter(adapter);

        requestPermissions();
    }

    private void requestPermissions() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.SEND_SMS,
                        Manifest.permission.WRITE_CONTACTS)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(MainActivity.this, "Permisos concedidos", Toast.LENGTH_SHORT).show();
                            getContacts();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .check();
    }

    private void getContacts() {
        contactsArrayList.clear();
        Cursor cursor = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String number = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contactsArrayList.add(new Contacts(name, number));
            }
            cursor.close();
        }

        adapter.notifyItemRangeChanged(0, contactsArrayList.size());
    }
}
