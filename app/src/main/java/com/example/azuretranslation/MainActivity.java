package com.example.azuretranslation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.net.ResponseCache;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    EditText editTextTextPersonName;
    TextView textView;
    Spinner spinner;
    String[] data = {"russian", "english"};
    Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(AzureTranslationAPI.API_URL)
            .build();

    AzureTranslationAPI api = retrofit.create(AzureTranslationAPI.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Call<Translation> call = api.getLanguages();
        call.enqueue(new LanguagesCallback());
        editTextTextPersonName = findViewById(R.id.editTextTextPersonName);
        textView = findViewById(R.id.textView);
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setPrompt("Languages");
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String in = editTextTextPersonName.getText().toString();
                textView.setText(in);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    static class LanguagesCallback implements Callback<Translation> {

        @Override
        public void onResponse(Call<Translation> call, Response<Translation> response) {
            if (response.isSuccessful()) {
                Log.d("mytag", "response: " + response.body());
            } else
                Log.d("mytag", "error" + response.code());

        }

        @Override
        public void onFailure(Call<Translation> call, Throwable t) {
            Log.d("mytag", "error" + t.getLocalizedMessage());
        }
    }
}