package com.ilhamferdx.latihanroomdb;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ilhamferdx.latihanroomdb.adapter.UserAdapter;
import com.ilhamferdx.latihanroomdb.database.AppDatabase;
import com.ilhamferdx.latihanroomdb.database.entity.User;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button btnTambah;
    private AppDatabase database;
    private UserAdapter userAdapter;
    private List<User> list = new ArrayList<>();
    private AlertDialog.Builder dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycle_view);
        btnTambah = findViewById(R.id.btn_tambah);

        database = AppDatabase.getInstance(getApplicationContext());
        list.clear();
        list.addAll(database.userDao().getAll());
        userAdapter = new UserAdapter(getApplicationContext(), list);
        userAdapter.setDialog(new UserAdapter.Dialog() {
            @Override
            public void onClick(int position) {
                final CharSequence[] dialogItem = {"Edit", "Hapus"};
                dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which){
                            case 0:
                                Intent intent = new Intent(MainActivity.this, TambahActivity.class);
                                intent.putExtra("uid", list.get(position).uid);
                                startActivity(intent);
                                break;
                            case 1:
                                User user = list.get(position);
                                database.userDao().delete(user);
                                onStart();
                                break;
                        }
                    }
                });
                dialog.show();
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(userAdapter);

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TambahActivity.class));
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        list.clear();
        list.addAll(database.userDao().getAll());
        userAdapter.notifyDataSetChanged();
    }
}