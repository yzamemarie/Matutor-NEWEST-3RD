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

public class Login extends AppCompatActivity {

    Button regHere, forgotPass, login;
    TextInputEditText emailText, passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //removes status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        regHere = findViewById(R.id.regHereButton);
        //forgotPass = findViewById(R.id.forgotPasswordLink);
        login = findViewById(R.id.loginButton);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the email and password text fields
                emailText = findViewById(R.id.emailInput);
                passwordText = findViewById(R.id.passwordInput);

                // Get the text entered by the user
                String email = emailText.getText().toString().trim();
                String password = passwordText.getText().toString().trim();

                //checks if text fields are empty and displays toast prompt if true
                if (email.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "[!] Enter email address.", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "[!] Enter password.", Toast.LENGTH_SHORT).show();
                    } else {
                        //if all text field is not empty, proceed to dashboard
                        startActivity(new Intent(getApplicationContext(),Dashboard.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        finish();
                    }
            }
        });

        //register here button
        regHere.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterNew.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });

    }

    //Exit Message Prompt Validation
    @Override
    public void onBackPressed() {
        back();
    }

    private void back() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("[?] Exit");
        builder.setMessage("Exit application?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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
