package com.example.tugas1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText txtUsername;
    private EditText txtPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtUsername.getText().toString().equals("admin") && txtPassword.getText().toString().equals("admin")){
                    onClickberhasil();
                }else if(txtUsername.getText().toString().equals("") || txtPassword.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Username atau Password tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getApplicationContext(), "Username atau Password Anda tidak benar!", Toast.LENGTH_SHORT).show();
                }
            }
            private void onClickberhasil(){
                setContentView(R.layout.home_screen_activity);
            }
        });
    }
}