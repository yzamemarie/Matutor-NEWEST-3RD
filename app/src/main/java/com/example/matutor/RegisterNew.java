package com.example.matutor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RegisterNew extends AppCompatActivity {

    Button register, returnLogin, addTag, selectIdImage, selectSelfieImage;
    Spinner ageSpinner;
    EditText editDate, editTag;
    TextInputEditText regFullname, regEmail, regPassword, regAddress, regGuardianName, regGuardianEmail;
    String fullname, email, password, bdate, address, guardianName, guardianEmail;
    //Uri imageUri;
    private static final int PICK_IMAGE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register_new);

        register = findViewById(R.id.registerButton);
        returnLogin = findViewById(R.id.loginHereButton);
        selectIdImage = findViewById(R.id.regIdButton);
        selectSelfieImage = findViewById(R.id.regSelfieButton);
        addTag = findViewById(R.id.addTagButton);
        ageSpinner = findViewById(R.id.regAgeSpinner);
        editDate = findViewById(R.id.editDateText);
        editTag = findViewById(R.id.editTagText);


        // Populate the Spinner using a loop
        List<String> items = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            items.add(String.valueOf(i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageSpinner.setAdapter(adapter);

        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        addTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Topic added.", Toast.LENGTH_SHORT).show();
            }
        });

        selectIdImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Select ID image.", Toast.LENGTH_SHORT).show();
            }
        });

        selectSelfieImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Select selfie image.", Toast.LENGTH_SHORT).show();
            }
        });

        //click register button to proceed to dashboard
       register.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               //get text fields
               regFullname = findViewById(R.id.regFullnameInput);
               regEmail = findViewById(R.id.regEmailInput);
               regPassword = findViewById(R.id.regPasswordInput);
               editDate = findViewById(R.id.editDateText);
               regAddress = findViewById(R.id.regAddressInput);
               regGuardianName = findViewById(R.id.regGuardianNameInput);
               regGuardianEmail = findViewById(R.id.regGuardianEmailInput);

               //get text from text fields
               fullname = regFullname.getText().toString().trim();
               email = regEmail.getText().toString().trim();
               password = regPassword.getText().toString().trim();
               bdate = editDate.getText().toString().trim();
               address = regAddress.getText().toString().trim();
               guardianName = regGuardianName.getText().toString().trim();
               guardianEmail = regGuardianEmail.getText().toString().trim();

               //checks if text fields are empty and displays toast prompt if true
               if (fullname.isEmpty()) {
                   Toast.makeText(getApplicationContext(), "Enter full name.", Toast.LENGTH_SHORT).show();
               } else if (email.isEmpty()) {
                   Toast.makeText(getApplicationContext(), "Enter email.", Toast.LENGTH_SHORT).show();
               } else if (password.isEmpty()) {
                   Toast.makeText(getApplicationContext(), "Enter password.", Toast.LENGTH_SHORT).show();
               } else if (bdate.isEmpty()) {
                   Toast.makeText(getApplicationContext(), "Enter birthdate.", Toast.LENGTH_SHORT).show();
               } else if (address.isEmpty()) {
                   Toast.makeText(getApplicationContext(), "Enter address.", Toast.LENGTH_SHORT).show();
               } else if (guardianName.isEmpty()) {
                   Toast.makeText(getApplicationContext(), "Enter parent's or guardian's name.", Toast.LENGTH_SHORT).show();
               } else if (guardianEmail.isEmpty()) {
                   Toast.makeText(getApplicationContext(), "Enter parent's or guardian's email.", Toast.LENGTH_SHORT).show();
               } else {
                   //if all text field is not empty, proceed to dashboard
                   Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_SHORT).show();
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

    private void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, yearSelected, monthOfYear, dayOfMonth) -> {
                    String selectedDate = (monthOfYear + 1) + "/" + dayOfMonth + "/" + yearSelected;
                    editDate.setText(selectedDate);
                },
                year, month, day);

        datePickerDialog.show();
    }
}