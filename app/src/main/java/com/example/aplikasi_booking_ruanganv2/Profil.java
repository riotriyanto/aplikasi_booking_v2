package com.example.aplikasi_booking_ruanganv2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Profil extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    EditText username, password, nama;
    Button simpan;
    ConnectivityManager conMgr;
    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        final Session session = new Session();

        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);

        username = findViewById(R.id.username);
        nama = findViewById(R.id.nama);
        password = findViewById(R.id.password);
        simpan = findViewById(R.id.simpan);
//        username = getIntent().getStringExtra(TAG_USERNAME);
        username.setText( session.getRegisteredUser(getBaseContext()));
        nama.setText(session.getLoggedInUser(getBaseContext()));

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String n = nama.getText().toString();
                String id = session.getRegisteredPass(getBaseContext());
                updateData(user,pass, n);

            }
        });

        showD();
    }

    private void updateData(String user, String pass, String n) {

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
//        final TextView textView = (TextView) findViewById(R.id.textView);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        final JSONObject jsonObject = new JSONObject();
        final Session session = new Session();
        String id_user = session.getRegisteredPass(getBaseContext());
        try {
            if (pass.length()>0){
                jsonObject.put("id", id_user);
                jsonObject.put("username",user);
                jsonObject.put("nama_user", n);
                jsonObject.put("password",pass);
                jsonObject.put("level","2");
            }else {
                jsonObject.put("id", id_user);
                jsonObject.put("username",user);
                jsonObject.put("nama_user", n);
                jsonObject.put("level","2");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("vol", "dat : "+jsonObject.toString());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, Server.URL+"users", jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        boolean res = false;
                        String mess = null;
                        try {
                            res = response.getBoolean("success");
                            mess = response.getString("message");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        textView.append(response.getString("message"));

                        if (res){
                            Toast.makeText(getApplicationContext() ,mess, Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext() ,mess, Toast.LENGTH_LONG).show();
                        }
                        Intent intent = new Intent(Profil.this, MainActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("voley", "err"+error.toString());
//                textView.append("Error getting response"+error);
                Toast.makeText(getApplicationContext() ,"Gagal memperbaharui profil", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void showD() {
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
                            username.setText(mess.getJSONObject(0).getString("username"));
                            nama.setText(mess.getJSONObject(0).getString("nama_user"));
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
