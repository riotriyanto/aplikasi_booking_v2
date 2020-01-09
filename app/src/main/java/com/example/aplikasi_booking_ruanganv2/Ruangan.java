package com.example.aplikasi_booking_ruanganv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.aplikasi_booking_ruanganv2.Adapter.AdapterData;
import com.example.aplikasi_booking_ruanganv2.Adapter.AdapterDataDua;
import com.example.aplikasi_booking_ruanganv2.Model.ModelData;
import com.example.aplikasi_booking_ruanganv2.Model.ModelDataDua;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Ruangan extends AppCompatActivity {
    List<ModelDataDua> mItem;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mManager;
    RecyclerView mRecyclerview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruangan);
        mItem = new ArrayList<>();
        mRecyclerview = (RecyclerView) findViewById(R.id.viewe);
        mAdapter = new AdapterDataDua(Ruangan.this, mItem);

        mManager = new LinearLayoutManager(Ruangan.this, LinearLayoutManager.VERTICAL,false);
        mRecyclerview.setLayoutManager(mManager);

        mRecyclerview.setAdapter(mAdapter);

        LoadJson();
    }

    private void LoadJson(){
//        pd.setMessage("Mengambil Data");
//        pd.setCancelable(false);
//        pd.show();
        final Session session = new Session();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Server.URL+"ruangan?id_user="+session.getRegisteredPass(getBaseContext()), null,
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
                                ModelDataDua md = new ModelDataDua();
                                md.setUrai_ruangan(data.getString("urai_ruangan"));
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
