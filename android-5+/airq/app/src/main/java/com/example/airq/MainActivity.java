package com.example.airq;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.Vibrator;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;
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
import org.w3c.dom.Text;

import static android.app.Service.START_STICKY;

public class MainActivity extends AppCompatActivity {


    private TextView textView;
    private TextView temperature;
    private TextView humidity;
    private TextView co2ppm_text;
    private TextView airquality_text;
    private TextView lst_update;
    private ProgressBar co2ppmpg;
    private ProgressBar airquality;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean alreadyExecuted = false;
        getData();
        if(!alreadyExecuted) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                data();
                dooze();
            }
            alreadyExecuted = true;
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("Alarm","Alarm", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        TextView tempIcon, humIcon;
        Typeface typeface;

        tempIcon = (TextView) findViewById(R.id.tempIcon);
        humIcon = (TextView) findViewById(R.id.humIcon);

        typeface = Typeface.createFromAsset(getAssets(), "fonts/fontawesome-webfont.ttf");

        tempIcon.setTypeface(typeface);
        humIcon.setTypeface(typeface);
    }

    private void dooze() {
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if (!pm.isIgnoringBatteryOptimizations(getPackageName())) {
            Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS, Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        }
    }
    private void data(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connMgr.getRestrictBackgroundStatus()==3 || connMgr.getRestrictBackgroundStatus()==2) {
            //Toast.makeText(MainActivity.this,String.valueOf(connMgr.getRestrictBackgroundStatus()), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_IGNORE_BACKGROUND_DATA_RESTRICTIONS_SETTINGS, Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.item2){
            Intent intent1 = new Intent(MainActivity.this, com.example.airq.MainActivity2.class);
            startActivity(intent1);
        }
        else if(id == R.id.item3){
            Intent intent2 = new Intent(MainActivity.this, com.example.airq.ActivityMain3.class);
            startActivity(intent2);
        }
        else if(id == R.id.item4){
            Intent intent3 = new Intent(MainActivity.this, com.example.airq.Main4Activity.class);
            startActivity(intent3);
        }

        return super.onOptionsItemSelected(item);
    }

    private void getData() {
        String webServer = getString(R.string.webserver);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = webServer+"/get-data.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String temp = "";
                String hum = "";
                String co2ppm = "";
                String last_update = "";
                String sensor_data = "";
                String pm2_5 = "";
                try {
                    JSONObject data = response.getJSONObject(0);
                    temp = data.getString("value1");
                    hum =  data.getString("value2");
                    co2ppm = data.getString("value3");
                    pm2_5 = data.getString("value4");

                    last_update = data.getString("reading_time");
                } catch (JSONException e) {
                    e.printStackTrace();
                }



                Toast.makeText(MainActivity.this,last_update, Toast.LENGTH_LONG).show();
                humidity = (TextView) findViewById(R.id.hum);
                humidity.setText(hum+ "%");
                temperature = (TextView) findViewById(R.id.temp);
                temperature.setText(temp+ "??C");
                co2ppm_text = (TextView) findViewById(R.id.co2ppm_text);
                co2ppm_text.setText(co2ppm+ " ppm");
                co2ppmpg = (ProgressBar) findViewById(R.id.co2ppm);
                co2ppmpg.setMax(2000);
                int ppm_co2 = Integer.parseInt(co2ppm);
                co2ppmpg.setProgress(ppm_co2);
                airquality = (ProgressBar) findViewById(R.id.airquality);
                airquality.setMax(500);
                int airq = Integer.parseInt(pm2_5);
                airquality.setProgress(airq);
                airquality_text = (TextView) findViewById(R.id.airquality_text);
                airquality_text.setText(pm2_5+ " ug/m3");
                lst_update = (TextView) findViewById(R.id.lst_update);
                lst_update.setText("???????????????? ????????????????????: " + last_update);
                if(ppm_co2 > 1000 || airq>100){
                    final MediaPlayer alarm = MediaPlayer.create(MainActivity.this, R.raw.alarm);
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(400);
                    alarm.start();
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "Alarm");
                    builder.setContentTitle("Alarm");
                    builder.setContentText("?????? ???????????? ???????????????????? ???? ????????????????????????");
                    builder.setSmallIcon(R.drawable.ic_stat_new_releases);
                    Context context = getApplicationContext();
                    Intent notIntent = new Intent(context, MainActivity.class);
                    PendingIntent contentIntent = PendingIntent.getActivity(MainActivity.this,0, notIntent,0);
                    builder.setContentIntent(contentIntent);
                    builder.setAutoCancel(true);
                    NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity.this);
                    managerCompat.notify(1, builder.build());
                    context.startForegroundService(notIntent);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "greska", Toast.LENGTH_LONG).show();
            }
        });
        queue.add(jsonArrayRequest);
        refresh(55000);
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

