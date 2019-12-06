package com.example.rozgaar;

import android.content.Intent;
import android.speech.RecognitionService;
import android.speech.RecognizerIntent;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.widget.Toolbar;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Intent_sample extends AppCompatActivity {

    private TextView text_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_sample);
        text_view = (TextView) findViewById(R.id.text_view);







//        final ListView listview=findViewById(R.id.list_view);
//
//
//        String[] Jobs=new String[]{
//                "Carpenter",
//                "Plumber",
//                "Civil Engineer",
//                "Doctor"
//        };
//
//
//        final List<String> jobs_list=new ArrayList<String>(Arrays.asList(Jobs));
//
//        final ArrayAdapter<String> arrayadapter=new ArrayAdapter<String>(this,R.layout.simple_list_item1,jobs_list);
//
//        listview.setAdapter(arrayadapter);



    }

    public void getSpeechInput(View view) {
        Intent intent_speech = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent_speech.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent_speech.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if(intent_speech.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent_speech,10);

        }else{
            Toast.makeText(this,"Your Device Don't support speech Input",Toast.LENGTH_SHORT).show();

        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 10:
                if(resultCode == RESULT_OK && data != null){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    text_view.setText(result.get(0));


                }

        }
    }



}
