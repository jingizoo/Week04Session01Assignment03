package com.jalaj.firstapp.autosearchproject;

import android.graphics.Bitmap;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    SearchDBHelper searchDBHelper;
    TextView txtVwEmpName, txtVwEmpAge;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtVwEmpName = (TextView) findViewById(R.id.txtVwDataName);
        txtVwEmpAge = (TextView) findViewById(R.id.txtVwDataName);
        imageView = (ImageView) findViewById(R.id.imgEmployeeImage);

        searchDBHelper = new SearchDBHelper(this);
       /* try {
          //  searchDBHelper.buildDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        String[] results = searchDBHelper.getEmployeeData(6);
        Log.i(results.toString(),results[0]);
        Bitmap bitmap = searchDBHelper.getEmployeeImage(6);
        imageView.setImageBitmap(bitmap);
        txtVwEmpAge.setText(results[1]);
        txtVwEmpName.setText(results[0]);

    }


    public void onDestroy() {
        super.onDestroy();
        searchDBHelper.close();
    }
}
