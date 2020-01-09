package com.example.aplikasi_booking_ruanganv2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.aplikasi_booking_ruanganv2.Model.ModelSpinner;
import com.example.aplikasi_booking_ruanganv2.Model.ModelSpinnerDua;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static android.R.layout.simple_spinner_item;

public class Tambah extends AppCompatActivity {

    Spinner spinner, spinnerDua;
    Button tambah, tombol2;
    private ArrayList<ModelSpinner> goodModelArrayList;
    private ArrayList<ModelSpinnerDua> goodModelArrayListDua;
    private ArrayList<String> urairuangan = new ArrayList<String>();
    private ArrayList<String> uraijam = new ArrayList<String>();
    private ArrayList<String> id = new ArrayList<String>();
    private ArrayList<String> id_jam = new ArrayList<String>();
    HashMap<Integer,String> spinnerMap = new HashMap<Integer, String>();

    TextView mDisplayData;
    DatePickerDialog.OnDateSetListener mDataSetListenner;
    ConnectivityManager conMgr;
    ProgressDialog pDialog;
    private static final String TAG = "Tambah";
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Tambah.this, Booking.class);
        startActivity(intent);
        Log.d("button", "BACK");
    }
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        spinner = findViewById(R.id.selLayanan);
        spinnerDua = findViewById(R.id.spinner);

        tambah = findViewById(R.id.tambah);




        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ruangan = spinnerMap.get(spinner.getSelectedItemPosition());
                String jam = spinnerMap.get(spinnerDua.getSelectedItemPosition());
                String tanggal = mDisplayData.getText().toString();
                tambahbooking(ruangan, tanggal, jam);

//                Toast.makeText(getApplicationContext(), ruangan, Toast.LENGTH_LONG).show();
            }
        });

        JSONObject jsonObject=null;
        loadData(jsonObject);
        loadJam(jsonObject);

        mDisplayData = findViewById(R.id.tgl);

        mDisplayData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(Tambah.this,android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth,
                        mDataSetListenner, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDataSetListenner = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                Log.d(TAG, "onset :"+year+"-"+month+"-"+dayOfMonth);
//                Toast.makeText(getApplicationContext(),"tahun"+year, Toast.LENGTH_LONG).show();
                month = month+1;
                String bulan = String.valueOf(month);
                String tanggal = String.valueOf(dayOfMonth);
                if (month<10){
                    bulan = "0"+month;
                }
                if(dayOfMonth <10){
                    tanggal = "0"+dayOfMonth;
                }
                mDisplayData.setText(year+"-"+bulan+"-"+tanggal);
            }
        };

    }


    private void loadData(JSONObject jsonObject) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://103.100.27.19/api_fp_pam/api/ruangan", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        Log.d("res","data"+response);
                        JSONArray mess = null;
                        try {
                            mess = response.getJSONArray("data");
//                            Log.d("voley","mes"+mess.toString());
                            goodModelArrayList = new ArrayList<>();
                            for (int i = 0; i < mess.length(); i++) {

                                ModelSpinner playerModel = new ModelSpinner();
                                JSONObject dataobj = mess.getJSONObject(i);

                                playerModel.setId(dataobj.getString("id_ruangan"));
                                playerModel.setUrairuangan(dataobj.getString("urai_ruangan"));

                                goodModelArrayList.add(playerModel);
//                                Log.d("vol", "res"+dataobj.getString("id_ruangan"));

                            }

                            String[] spinnerArray = new String[goodModelArrayList.size()];
//                            HashMap<Integer,String> spinnerMap = new HashMap<Integer, String>();
                            for (int i = 0; i < goodModelArrayList.size(); i++){
                                urairuangan.add(goodModelArrayList.get(i).getUrairuangan().toString());
                                id.add(goodModelArrayList.get(i).getId().toString());

                                spinnerMap.put(i,goodModelArrayList.get(i).getId().toString());
                                spinnerArray[i] = goodModelArrayList.get(i).getUrairuangan().toString();
                            }


                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(Tambah.this, simple_spinner_item, spinnerArray);
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                            spinner.setAdapter(spinnerArrayAdapter);

//                            int selectionPosition= spinnerArrayAdapter.getPosition("ruangan 4.2.3");
//                            spinner.setSelection(selectionPosition);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                textView.append("Error getting response"+error);
                Log.d("voley","error"+error);
                Toast.makeText(Tambah.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);
    }

    private void loadJam(JSONObject jsonObject) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://103.100.27.19/api_fp_pam/api/jam", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        Log.d("res","data"+response);
                        JSONArray mess = null;
                        try {
                            mess = response.getJSONArray("data");
////                            Log.d("voley","mes"+mess.toString());
                            goodModelArrayListDua = new ArrayList<>();
                            for (int i = 0; i < mess.length(); i++) {

                                ModelSpinnerDua playerModelDua = new ModelSpinnerDua();
                                JSONObject dataobj = mess.getJSONObject(i);

                                playerModelDua.setId(dataobj.getString("id_jam"));
                                playerModelDua.setUrai_jam(dataobj.getString("urai_jam"));

                                goodModelArrayListDua.add(playerModelDua);
//                                Log.d("vol", "res"+dataobj.getString("id_ruangan"));

                            }
//
                            String[] spinnerArray = new String[goodModelArrayListDua.size()];
//                            HashMap<Integer,String> spinnerMap = new HashMap<Integer, String>();
                            for (int i = 0; i < goodModelArrayListDua.size(); i++){
                                uraijam.add(goodModelArrayListDua.get(i).getUrai_jam().toString());
                                id_jam.add(goodModelArrayListDua.get(i).getId().toString());

                                spinnerMap.put(i,goodModelArrayListDua.get(i).getId().toString());
                                spinnerArray[i] = goodModelArrayListDua.get(i).getUrai_jam().toString();
                            }


                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(Tambah.this, simple_spinner_item, spinnerArray);
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                            spinnerDua.setAdapter(spinnerArrayAdapter);

//                            int selectionPosition= spinnerArrayAdapter.getPosition("ruangan 4.2.3");
//                            spinner.setSelection(selectionPosition);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                textView.append("Error getting response"+error);
                Log.d("voley","error"+error);
                Toast.makeText(Tambah.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);
    }

    private void tambahbooking(final String druangan, final String dtgl, final String djam) {
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
            int id_ru = Integer.parseInt(druangan);
            int id_j = Integer.parseInt(djam);
            jsonObject.put("id_user", id_user);
            jsonObject.put("id_ruangan",id_ru);
            jsonObject.put("id_jam", id_j);
            jsonObject.put("tgl",dtgl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("vol", "dat : "+jsonObject.toString());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Server.URL+"booking", jsonObject,
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
                        Intent intent = new Intent(Tambah.this, Booking.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("voley", "err"+error.toString());
//                textView.append("Error getting response"+error);
                String urai_ruangan = spinner.getSelectedItem().toString();
                Toast.makeText(getApplicationContext() ,"Anda tidak bisa membooking "+urai_ruangan+" pada tanggal dan waktu tersebut", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonObjectRequest);

    }
}
