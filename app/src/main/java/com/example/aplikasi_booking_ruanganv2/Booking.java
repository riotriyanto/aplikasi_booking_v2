package com.example.aplikasi_booking_ruanganv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.aplikasi_booking_ruanganv2.Adapter.AdapterData;
import com.example.aplikasi_booking_ruanganv2.Model.ModelData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Booking extends AppCompatActivity {
    List<ModelData> mItem;
    RecyclerView.Adapter mAdapter;
    RecyclerView mRecyclerview;
    RecyclerView.LayoutManager mManager;
    Button tambah;
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Booking.this, MainActivity.class);
        startActivity(intent);
        Log.d("button", "BACK");
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        mItem = new ArrayList<>();

        LoadJson();
        mRecyclerview = (RecyclerView) findViewById(R.id.viewe);
        mAdapter = new AdapterData(Booking.this, mItem);
//        mAdapter = new AdapterData(MainActivity.this, mItem);
//
        mManager = new LinearLayoutManager(Booking.this, LinearLayoutManager.VERTICAL,false);
        mRecyclerview.setLayoutManager(mManager);

        mRecyclerview.setAdapter(mAdapter);


        tambah = findViewById(R.id.tambah);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Booking.this, Tambah.class);
                startActivity(intent);
            }
        });

    }

    private void LoadJson(){
//        pd.setMessage("Mengambil Data");
//        pd.setCancelable(false);
//        pd.show();
        final Session session = new Session();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Server.URL+"booking?id_user="+session.getRegisteredPass(getBaseContext()), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        pd.cancel();
                        Log.d("volley", "response" + response.toString());
                        JSONArray mess = null;
                        try {
                            mess = response.getJSONArray("data");
                            Log.d("voley","mes"+mess.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for (int i=0; i < mess.length(); i++){
                            try {
                                JSONObject data = mess.getJSONObject(i);
                                ModelData md = new ModelData();
                                md.setRuangan(data.getString("urai_ruangan"));
                                md.setId(data.getString("id_booking"));
                                md.setJam(data.getString("urai_jam"));
                                md.setTgl(data.getString("tgl"));
                                Log.d("p","pes"+data.getString("urai_ruangan"));
                                mItem.add(md);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                pd.cancel();
                Log.d("voley", "error"+error.getMessage());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);
    }
}
