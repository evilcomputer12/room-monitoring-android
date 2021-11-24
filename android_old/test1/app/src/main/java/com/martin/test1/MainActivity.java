package com.martin.test1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getData();
    }

    private void getData() {
        RequestQueue queue = Volley.newRequestQueue(this);

        String url ="https://marvelroommonitor.000webhostapp.com/get-data.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String temp = "";
                String hum = "";
                String co2ppm = "";
                String last_update = "";
                String sensor_data = "";
                try {
                    JSONObject data = response.getJSONObject(0);
                    temp = data.getString("value1");
                    hum =  data.getString("value2");
                    co2ppm = data.getString("value3");
                    last_update = data.getString("reading_time");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Toast.makeText(MainActivity.this,last_update, Toast.LENGTH_LONG).show();
                textView = (TextView) findViewById(R.id.textView);
                textView.setText(temp + "  " + hum + "  " + co2ppm + "  " + last_update);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "greska", Toast.LENGTH_LONG).show();
            }
        });
        queue.add(jsonArrayRequest);
        refresh(60000);
    }

    private void refresh(int milliseconds) {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                getData();
            }
        };
        handler.postDelayed(runnable, milliseconds);
    }

}

