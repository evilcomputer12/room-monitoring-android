package com.example.airq;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ActivityMain3 extends AppCompatActivity {
    private WebView webView;
    SwipeRefreshLayout swipeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        webView = (WebView) findViewById(R.id.wview);
        webView.setWebViewClient(new WebViewClient());
        final String webServer = getString(R.string.webserver);
        final String url = "/history-table.php";
        String url1 = "/chart-data.php";
        webView.loadUrl(webServer+url);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);

        swipeLayout = findViewById(R.id.swipe_container);
        // Adding Listener
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code here
                // To keep animation for 4 seconds
                webView.loadUrl(webServer+url);
                WebSettings webSettings = webView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webSettings.setDomStorageEnabled(true);
                webSettings.setLoadWithOverviewMode(true);
                webSettings.setUseWideViewPort(true);
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        // Stop animation (This will be after 3 seconds)
                        swipeLayout.setRefreshing(false);
                    }
                }, 4000); // Delay in millis
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1, menu);

        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.item0){
            webView = (WebView) findViewById(R.id.wview);
            webView.setWebViewClient(new WebViewClient());
            final String webServer = getString(R.string.webserver);
            final String url = "/history-table.php";
            final String url1 = "/chart-data.php";
            webView.loadUrl(webServer+url);
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setDomStorageEnabled(true);
            webSettings.setLoadWithOverviewMode(true);
            webSettings.setUseWideViewPort(true);

            swipeLayout = findViewById(R.id.swipe_container);
            // Adding Listener
            swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    // Your code here
                    // To keep animation for 4 seconds
                    webView.loadUrl(webServer+url);
                    WebSettings webSettings = webView.getSettings();
                    webSettings.setJavaScriptEnabled(true);
                    webSettings.setDomStorageEnabled(true);
                    webSettings.setLoadWithOverviewMode(true);
                    webSettings.setUseWideViewPort(true);
                    new Handler().postDelayed(new Runnable() {
                        @Override public void run() {
                            // Stop animation (This will be after 3 seconds)
                            swipeLayout.setRefreshing(false);
                        }
                    }, 4000); // Delay in millis
                }
            });
        }
        else if(id == R.id.item1){
            webView = (WebView) findViewById(R.id.wview);
            webView.setWebViewClient(new WebViewClient());
            final String webServer = getString(R.string.webserver);
            final String url = "/history-table.php";
            final String url1 = "/chart-data.php";
            swipeLayout = findViewById(R.id.swipe_container);
            webView.loadUrl(webServer+url);
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setDomStorageEnabled(true);
            webSettings.setLoadWithOverviewMode(true);
            webSettings.setUseWideViewPort(true);
            // Adding Listener
            swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    // Your code here
                    // To keep animation for 4 seconds
                    webView.loadUrl(webServer+url1);
                    WebSettings webSettings = webView.getSettings();
                    webSettings.setJavaScriptEnabled(true);
                    webSettings.setDomStorageEnabled(true);
                    webSettings.setLoadWithOverviewMode(true);
                    webSettings.setUseWideViewPort(true);
                    new Handler().postDelayed(new Runnable() {
                        @Override public void run() {
                            // Stop animation (This will be after 3 seconds)
                            swipeLayout.setRefreshing(false);
                        }
                    }, 4000); // Delay in millis
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    private void createWebPrintJob(WebView webView) {

        //create object of print manager in your device
        PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);

        //create object of print adapter
        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter();

        //provide name to your newly generated pdf file
        String jobName = getString(R.string.app_name) + " Print Test";

        //open print dialog
        printManager.print(jobName, printAdapter, new PrintAttributes.Builder().build());
    }

    public void printPDF(View view) {
        createWebPrintJob(webView);
    }

}
