package com.example.aplikasi_booking_ruanganv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.aplikasi_booking_ruanganv2.Login.TAG_ID;
import static com.example.aplikasi_booking_ruanganv2.Login.TAG_USERNAME;

public class MainActivity extends AppCompatActivity {
    CardView logout, booking, ruangan;
    SharedPreferences sharedpreferences;
    String id, username;
    TextView txt_username, txt_nama;
    private Boolean exit = false;
    @Override
    public void onBackPressed(){
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }
        Log.d("button", "BACK");
    }
    protected void onCreate(Bundle savedInstanceState) {
        final Session session = new Session();


        if (session.getLoggedInStatus(getBaseContext()) == false){
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);

        txt_username = findViewById(R.id.username);
        txt_nama = findViewById(R.id.name);
        id = getIntent().getStringExtra(TAG_ID);
        username = getIntent().getStringExtra(TAG_USERNAME);
        txt_username.setText( session.getRegisteredUser(getBaseContext()));
        txt_nama.setText(session.getLoggedInUser(getBaseContext()));

        logout = findViewById(R.id.logout);
        booking = findViewById(R.id.booking);
        ruangan = findViewById(R.id.ruangan);




        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean(Login.session_status, false);
                editor.putString(TAG_ID, null);
                editor.putString(TAG_USERNAME, null);
                editor.commit();
                session.clearLoggedInUser(getBaseContext());
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });

        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Booking.class);
                startActivity(intent);
            }
        });

        ruangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Ruangan.class);
                startActivity(intent);
            }
        });
    }
}
