package com.example.airq;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    RecyclerView recyclerView;
    List<lista> listi;
    private static String JSON_URL = "https://marvelroommonitor.000webhostapp.com/history.php";
    Adapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        recyclerView = findViewById(R.id.lista);
        listi = new ArrayList<>();
        extractListi();
    }

    private void extractListi() {
        RequestQueue queue = Volley.newRequestQueue(MainActivity2.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject listaObject = response.getJSONObject(i);

                        lista lista = new lista();
                        lista.setId(listaObject.getString("id"));
                        lista.setTemp(listaObject.getString("value1"));
                        lista.setHum(listaObject.getString("value2"));
                        lista.setCo2(listaObject.getString("value3"));
                        lista.setPm25(listaObject.getString("value4"));
                        lista.setVreme(listaObject.getString("reading_time"));
                        listi.add(lista);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new Adapter(getApplicationContext(),listi);
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });

        queue.add(jsonArrayRequest);

    }

}