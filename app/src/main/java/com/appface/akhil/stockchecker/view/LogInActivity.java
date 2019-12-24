package com.appface.akhil.stockchecker.view;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.appface.akhil.stockchecker.R;
import com.appface.akhil.stockchecker.view.MainActivity;

public class LogInActivity extends AppCompatActivity {

    @BindView(R.id.etusername)
    EditText etusername;

    @BindView(R.id.etpassword)
    EditText etpassword;

    @BindView(R.id.btnlogin)
    Button btnlogin;

    @BindView(R.id.btncancel)
    Button btncancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final Context baseContext = getBaseContext();

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etusername.setText("");
                etpassword.setText("");
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(baseContext, MainActivity.class);
                startActivity(intent);            }
        });

    }
}
