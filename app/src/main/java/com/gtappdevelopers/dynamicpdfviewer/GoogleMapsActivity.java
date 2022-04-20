package com.gtappdevelopers.dynamicpdfviewer;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class GoogleMapsActivity extends AppCompatActivity {

    private TextInputEditText sourceEdt, destEdt;
    private Button drawRouteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);
        sourceEdt = findViewById(R.id.idEdtSourceLocation);
        destEdt = findViewById(R.id.idEdtDestination);
        drawRouteBtn = findViewById(R.id.idBtnDrawRoute);
        drawRouteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String src = sourceEdt.getText().toString().trim();
                String dest = destEdt.getText().toString().trim();
                if (src.isEmpty() || dest.isEmpty()) {
                    Toast.makeText(GoogleMapsActivity.this, "Please enter source and destination location..", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        Uri uri = Uri.parse("https://www.google.co.in/maps/dir/" + src + "/" + dest);
                        Intent i = new Intent(Intent.ACTION_VIEW, uri);
                        i.setPackage("com.google.android.apps.maps");
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);

                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                        Log.e("TAG", "Fail to load" + e);
                        Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
                        Intent i = new Intent(Intent.ACTION_VIEW, uri);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                    }
                }
            }
        });
    }
}