package com.example.matutor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class RegisterNew extends AppCompatActivity {

    Button register, returnLogin;
    TextInputEditText regFullname, regEmail, regPassword, regBdate, regAddress;
    String fullname, email, password, bdate, address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //removes status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register_new);

        register = findViewById(R.id.registerButton);
        returnLogin = findViewById(R.id.loginHereButton);

        //click register button to proceed to dashboard
       register.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               //get text fields
               regFullname = findViewById(R.id.regFullnameInput);
               regEmail = findViewById(R.id.regEmailInput);
               regPassword = findViewById(R.id.regPasswordInput);
               regBdate = findViewById(R.id.regBdateInput);
               regAddress = findViewById(R.id.regAddressInput);

               //get text from text fields
               fullname = regFullname.getText().toString().trim();
               email = regEmail.getText().toString().trim();
               password = regPassword.getText().toString().trim();
               bdate = regBdate.getText().toString().trim();
               address = regAddress.getText().toString().trim();

               //checks if text fields are empty and displays toast prompt if true
               if (fullname.isEmpty()) {
                   Toast.makeText(getApplicationContext(), "[!] Enter full name", Toast.LENGTH_SHORT).show();
               } else if (email.isEmpty()) {
                   Toast.makeText(getApplicationContext(), "[!] Enter email", Toast.LENGTH_SHORT).show();
               } else if (password.isEmpty()) {
                   Toast.makeText(getApplicationContext(), "[!] Enter password", Toast.LENGTH_SHORT).show();
               }else if (bdate.isEmpty()) {
                   Toast.makeText(getApplicationContext(), "[!] Enter birthdate", Toast.LENGTH_SHORT).show();
               }else if (address.isEmpty()) {
                   Toast.makeText(getApplicationContext(), "[!] Enter address", Toast.LENGTH_SHORT).show();
               } else {
                   //if all text field is not empty, proceed to dashboard
                   startActivity(new Intent(getApplicationContext(),Dashboard.class));
                   overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                   finish();
               }
           }
       });

        //return to login button
        returnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        back();
    }

    private void back() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("[?] Return");
        builder.setMessage("Cancel registration?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(), Login.class));
                overridePendingTransition(0, 0);
                finishAffinity();
                System.exit(0);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}