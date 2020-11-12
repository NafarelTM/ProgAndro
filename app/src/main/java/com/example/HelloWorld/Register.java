package com.example.HelloWorld;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Build;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {

    EditText txtUsername, txtPassword, txtConPassword;
    Button btnRegister;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        databaseHelper = new DatabaseHelper(this);

        txtUsername = (EditText)findViewById(R.id.txtUsernameReg);
        txtPassword = (EditText)findViewById(R.id.txtPasswordReg);
        txtConPassword = (EditText)findViewById(R.id.txtConPassword);
        btnRegister = (Button)findViewById(R.id.btnRegister);

        TextView register = (TextView)findViewById(R.id.register);

        register.setText(fromHTML("Anda sudah memiliki akun" +
                "</font><font color='#FFFFFF'> Kembali ke Halaman Login</font>"));

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, LoginActivity.class));
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txtUsername.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();
                String conPassword = txtConPassword.getText().toString().trim();

                ContentValues values = new ContentValues();


                if (!password.equals(conPassword)){
                    Toast.makeText(Register.this, "Password Belum Sama. Mohon Cek kembali!", Toast.LENGTH_SHORT).show();
                }else if (password.equals("") || username.equals("")){
                    Toast.makeText(Register.this, "Silahkan Isi Username dan Password!", Toast.LENGTH_SHORT).show();
                }else {
                    values.put(DatabaseHelper.row_username, username);
                    values.put(DatabaseHelper.row_password, password);
                    databaseHelper.insertData(values);

                    Toast.makeText(Register.this, "Akun Anda Berhasil Terdaftar", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });


    }

    public static Spanned fromHTML(String html){
        Spanned hasil;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            hasil = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        }else {
            hasil = Html.fromHtml(html);
        }
        return hasil;
    }
}
