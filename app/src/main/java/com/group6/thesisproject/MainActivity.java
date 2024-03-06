package com.group6.thesisproject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



public class MainActivity extends AppCompatActivity {

    private TextView textViewResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResponse = findViewById(R.id.textViewResponse);
        Button buttonRequest = findViewById(R.id.buttonRequest);

        buttonRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Replace the IP with your ESP32's IP
                new HttpRequestTask().execute("http://192.168.113.222/");
            }
        });
    }

    private class HttpRequestTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            StringBuilder response = new StringBuilder();
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader input = new BufferedReader(new InputStreamReader(conn.getInputStream()), 8192);
                    String strLine;
                    while ((strLine = input.readLine()) != null) {
                        response.append(strLine);
                    }
                    input.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response.toString();
        }

        protected void onPostExecute(String result) {
            textViewResponse.setText(result);
        }
    }
}
