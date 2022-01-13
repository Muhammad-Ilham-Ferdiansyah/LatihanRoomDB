package com.ilhamferdx.latihanroomdb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ilhamferdx.latihanroomdb.database.AppDatabase;
import com.ilhamferdx.latihanroomdb.database.entity.User;

public class TambahActivity extends AppCompatActivity {
    private EditText editName, editEmail;
    private Button btnSave;
    private AppDatabase database;
    private int uid = 0;
    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        editName = findViewById(R.id.full_name);
        editEmail = findViewById(R.id.email);
        btnSave = findViewById(R.id.btn_save);

        database = AppDatabase.getInstance(getApplicationContext());

        Intent intent = getIntent();
        uid = intent.getIntExtra("uid",0);
        if (uid > 0){
            isEdit = true;
            User user = database.userDao().get(uid);
            editName.setText(user.fullName);
            editEmail.setText(user.email);
        } else {
            isEdit = false;
        }
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEdit){
                    database.userDao().update(uid,editName.getText().toString(),editEmail.getText().toString());
                } else {
                    database.userDao().insertAll(editName.getText().toString(),editEmail.getText().toString());
                }
                finish();
            }
        });
    }
}