package com.example.HelloWorld;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    SharedPrefManager sharedPrefManager;
    DatabaseHelper databaseHelper;
    private EditText txtUsername;
    private EditText txtPassword;
    private Button btnLogin;
//    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: dipanggil");
        Log.i(TAG, "onCreate: percobaan");
        setContentView(R.layout.activity_main);

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
//        btnLogout = findViewById(R.id.btnLogout);
        sharedPrefManager = new SharedPrefManager(this);
        databaseHelper = new DatabaseHelper(this);
        TextView createAccount = findViewById(R.id.buatAkun);
        createAccount.setText(fromHTML("Belum punya akun? " + "</font><font color='#FFFFFF'>Buat Akun</font>"));
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, Register.class));
            }
        });

        if(sharedPrefManager.getSPSudahLogin()){
            startActivity(new Intent(LoginActivity.this, HomeScreenActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = txtUsername.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();

                Boolean cekUser = databaseHelper.checkUser(username, password);
                if(cekUser == true) {

                    Toast.makeText(LoginActivity.this, "Selamat Datang di Aplikasiku", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, HomeScreenActivity.class));
                    sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);
                    finish();
                }
                else {
                    Toast.makeText(LoginActivity.this, "Silahkan cek E-mail dan Password anda", Toast.LENGTH_SHORT).show();
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