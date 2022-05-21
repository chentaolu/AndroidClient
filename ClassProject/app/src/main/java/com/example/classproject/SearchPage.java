package com.example.classproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SearchPage extends AppCompatActivity {
    EditText schoolname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        schoolname = (EditText)findViewById(R.id.edt1);

        Button submit = (Button)findViewById(R.id.button1);
        submit.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });
    }
}
