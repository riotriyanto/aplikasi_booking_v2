package com.example.aplikasi_booking_ruanganv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.aplikasi_booking_ruanganv2.Login.TAG_ID;
import static com.example.aplikasi_booking_ruanganv2.Login.TAG_USERNAME;

public class MainActivity extends AppCompatActivity {
    ConnectivityManager conMgr;
    ProgressDialog pDialog;
    CardView logout, booking, ruangan, profil;
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
        showD(id);
//        txt_username.setText( session.getRegisteredUser(getBaseContext()));
//        txt_nama.setText(session.getLoggedInUser(getBaseContext()));

        logout = findViewById(R.id.logout);
        booking = findViewById(R.id.booking);
        ruangan = findViewById(R.id.ruangan);
        profil = findViewById(R.id.profil);

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

        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(MainActivity.this, Profil.class);
                startActivity(inten);
            }
        });
    }

    private void showD(String id) {
        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection",
                        Toast.LENGTH_LONG).show();
            }
        }
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Logging in ...");
//        showDialog();
        final TextView textView = (TextView) findViewById(R.id.textView);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());


        final Session session = new Session();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Server.URL+"users?id="+session.getRegisteredPass(getBaseContext()), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        boolean res = false;
                        JSONArray mess;
                        try {
//                            da = response.getJSONObject("data");
                            mess = response.getJSONArray("data");
//                            textView.append(mess.getJSONObject(0).getString("id_user"));
                            // menyimpan login ke session
                            Log.d("err", "p : "+mess.toString());
                            Session session = new Session();
                            txt_username.setText(mess.getJSONObject(0).getString("username"));
                            txt_nama.setText(mess.getJSONObject(0).getString("nama_user"));
//                            session.setRegisteredPass(getBaseContext(), mess.getJSONObject(0).getString("id_user"));
//                            session.setRegisteredUser(getBaseContext(), mess.getJSONObject(0).getString("username"));
//                            session.setLoggedInUser(getBaseContext(), mess.getJSONObject(0).getString("nama_user"));
//                            session.setLoggedInStatus(getBaseContext(), true);
//                            res = response.getBoolean("success");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
