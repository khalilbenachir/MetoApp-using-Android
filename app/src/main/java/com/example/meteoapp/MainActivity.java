package com.example.meteoapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextVille;
    private ListView listViewMeteo;


    List<MeteoItem> data=new ArrayList<>();

    private MeteoListModel model;
    private ImageButton ButtonOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextVille=findViewById(R.id.editTextVille);
        listViewMeteo=findViewById(R.id.listViewMeteo);
        ButtonOk=findViewById(R.id.buttonOk);
        model=new MeteoListModel(getApplicationContext(),R.layout.list_item_layout,data);
        listViewMeteo.setAdapter(model);


        ButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MyLog",".......");
                data.clear();
                model.notifyDataSetChanged();
                RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
                String ville=editTextVille.getText().toString();
                Log.i("MyLog",ville);
                //
                String url="http://api.openweathermap.org/data/2.5/forecast?q="+ville+"&APPID=2c3c00201b8e5f35bb95e8eb99c185b4\n";
                StringRequest stringRequest=new StringRequest(Request.Method.GET,
                        url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i("MyLog", "............");
                            Log.i("MyLog", response);
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("list");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                MeteoItem meteoItem = new MeteoItem();
                                JSONObject d = jsonArray.getJSONObject(i);
                                JSONObject main = d.getJSONObject("main");
                                JSONArray weather = d.getJSONArray("weather");
                                int tempMin = (int) (main.getDouble("temp_min") - 273.15);
                                int tempMax = (int) (main.getDouble("temp_max") - 273.15);
                                int pression = main.getInt("pression");
                                int humidity = main.getInt("humidity");


                                meteoItem.tempMax = tempMax;
                                meteoItem.tempMin = tempMin;
                                meteoItem.humidite = humidity;
                                meteoItem.pression = pression;
                                meteoItem.image = weather.getJSONObject(0).getString("main");
                                data.add(meteoItem);


                            }
                            model.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("MyLog","Connection Problem");
                            }
                        });
                queue.add(stringRequest);

            }
        });
    }





}
