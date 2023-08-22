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

    Button register, returnLogin, selectIdImage, selectSelfieImage;
    Spinner ageSpinner, topic1Spinner, topic2Spinner, topic3Spinner, topic4Spinner, topic5Spinner;
    EditText editDate; //selectIdPath, selectSelfiePath;
    TextInputEditText regFullname, regEmail, regPassword, regBdate, regAddress;
    String fullname, email, password, bdate, address;
    Uri imageUri;
    private static final int PICK_IMAGE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register_new);

        register = findViewById(R.id.registerButton);
        returnLogin = findViewById(R.id.loginHereButton);
        ageSpinner = findViewById(R.id.regAgeSpinner);
        editDate = findViewById(R.id.editDateText);
        selectIdImage = findViewById(R.id.regIdButton);
        //selectIdPath = findViewById(R.id.idPathTextView);
        selectSelfieImage = findViewById(R.id.regSelfieButton);
        //selectSelfiePath = findViewById(R.id.selfiePathTextView);
        topic1Spinner = findViewById(R.id.regTopicSpinner1);
        topic2Spinner = findViewById(R.id.regTopicSpinner2);
        topic3Spinner = findViewById(R.id.regTopicSpinner3);
        topic4Spinner = findViewById(R.id.regTopicSpinner4);
        topic5Spinner = findViewById(R.id.regTopicSpinner5);

        // Populate the Spinner using a loop
        List<String> items = new ArrayList<>();
        for (int i = 12; i <= 100; i++) {
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

        // Populate the Spinner for topic 1
        String[] t1 = {"SELECT TOPIC 1"};
        ArrayAdapter<String> a1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, t1);
        a1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        topic1Spinner.setAdapter(a1);

        // Populate the Spinner for topic 2
        String[] t2 = {"SELECT TOPIC 2"};
        ArrayAdapter<String> a2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, t2);
        a2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        topic2Spinner.setAdapter(a2);

        // Populate the Spinner for topic 3
        String[] t3 = {"SELECT TOPIC 3"};
        ArrayAdapter<String> a3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, t3);
        a3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        topic3Spinner.setAdapter(a3);

        // Populate the Spinner for topic 4
        String[] t4 = {"SELECT TOPIC 4"};
        ArrayAdapter<String> a4 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, t4);
        a4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        topic4Spinner.setAdapter(a4);

        // Populate the Spinner for topic 5
        String[] t5 = {"SELECT TOPIC 5"};
        ArrayAdapter<String> a5 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, t5);
        a5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        topic5Spinner.setAdapter(a5);

        selectIdImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Select ID image!", Toast.LENGTH_SHORT).show();
            }
        });

        selectSelfieImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Select selfie image!", Toast.LENGTH_SHORT).show();
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

               //get text from text fields
               fullname = regFullname.getText().toString().trim();
               email = regEmail.getText().toString().trim();
               password = regPassword.getText().toString().trim();
               bdate = editDate.getText().toString().trim();
               address = regAddress.getText().toString().trim();

               //checks if text fields are empty and displays toast prompt if true
               if (fullname.isEmpty()) {
                   Toast.makeText(getApplicationContext(), "Enter full name", Toast.LENGTH_SHORT).show();
               } else if (email.isEmpty()) {
                   Toast.makeText(getApplicationContext(), "Enter email", Toast.LENGTH_SHORT).show();
               } else if (password.isEmpty()) {
                   Toast.makeText(getApplicationContext(), "Enter password", Toast.LENGTH_SHORT).show();
               }else if (bdate.isEmpty()) {
                   Toast.makeText(getApplicationContext(), "Enter birthdate", Toast.LENGTH_SHORT).show();
               }else if (address.isEmpty()) {
                   Toast.makeText(getApplicationContext(), "Enter address", Toast.LENGTH_SHORT).show();
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