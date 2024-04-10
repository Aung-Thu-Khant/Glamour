package com.scet.saloonspot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.scet.saloonspot.adapters.ShowSalonsAdapter;
import com.scet.saloonspot.models.MySaloon;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class AdminDashBoardActivity extends AppCompatActivity {

    FloatingActionButton floatingAdd;
    RecyclerView rvsalons;

    ShowSalonsAdapter showSalonsAdapter;

    ArrayList<MySaloon> saloonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_admin_dash_board);
        getInit();
        setEvents();



    }

    private void setEvents() {
        floatingAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashBoardActivity.this,AddSaloonActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getInit() {
        floatingAdd = findViewById(R.id.floatingAdd);
        rvsalons = findViewById(R.id.rvsalons);
        saloonList = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvsalons.setLayoutManager(manager);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Saloons");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    String name = data.child("SaloonName").getValue().toString();
                    String phone = data.child("Mobile").getValue().toString();
                    String mail = data.child("Email").getValue().toString();
                    String aera = data.child("Area").getValue().toString();
                    String address = data.child("SaloonAddress").getValue().toString();
                    String pass = data.child("Password").getValue().toString();
                    MySaloon mySaloon = new MySaloon(name,address,phone,aera,mail,pass);
                    saloonList.add(mySaloon);
                }
                showSalonsAdapter = new ShowSalonsAdapter(AdminDashBoardActivity.this,saloonList);
                rvsalons.setAdapter(showSalonsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
