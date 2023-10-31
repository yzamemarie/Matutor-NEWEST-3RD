package com.example.matutor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.matutor.databinding.ActivityRegisterInfoBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterInfo extends AppCompatActivity {

    ActivityRegisterInfoBinding binding;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore fire = FirebaseFirestore.getInstance();

    private static final int PICK_IMAGE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityRegisterInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Get text from Intent
        Intent intent = getIntent();
        String getEmail = intent.getStringExtra("Email");
        String getPassword = intent.getStringExtra("Password");
        String getConfirm = intent.getStringExtra("Confirm Password");

        //Set Text
        binding.regEmailInput.setText(getEmail);
        binding.regPasswordInput.setText(getPassword);
        binding.regConfirmPasswordInput.setText(getConfirm);

        // Populate the Spinner using a loop
        List<String> items = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            items.add(String.valueOf(i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.regAgeSpinner.setAdapter(adapter);

        binding.regEditDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        binding.addTagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Topic added.", Toast.LENGTH_SHORT).show();
            }
        });

        binding.regIdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Select ID image.", Toast.LENGTH_SHORT).show();
            }
        });

        binding.regSelfieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Select selfie image.", Toast.LENGTH_SHORT).show();
            }
        });

        //click register button to proceed to dashboard
       binding.registerButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               //get text from text fields
               String learnerFirstname = binding.regFirstnameInput.getText().toString().trim();
               String learnerLastname = binding.regLastnameInput.getText().toString().trim();
               String learnerEmail = binding.regEmailInput.getText().toString().trim();
               String learnerPassword = binding.regPasswordInput.getText().toString().trim();
               String confirmPass = binding.regConfirmPasswordInput.getText().toString().trim();
               String learnerBdate = binding.regEditDate.getText().toString().trim();
               String learnerAddress = binding.regAddressInput.getText().toString().trim();
               String learnerGuardianName = binding.regGuardianNameInput.getText().toString().trim();
               String learnerGuardianEmail = binding.regGuardianEmailInput.getText().toString().trim();

               //checks if text fields are empty and displays toast prompt if true
               if (learnerFirstname.isEmpty()) {
                   Toast.makeText(getApplicationContext(), "Please enter your first name.", Toast.LENGTH_SHORT).show();
               } else if (learnerLastname.isEmpty()) {
                   Toast.makeText(getApplicationContext(), "Please enter your last name.", Toast.LENGTH_SHORT).show();
               } else if (learnerEmail.isEmpty()) {
                   Toast.makeText(getApplicationContext(), "Please enter your email.", Toast.LENGTH_SHORT).show();
               } else if (learnerPassword.isEmpty()) {
                   Toast.makeText(getApplicationContext(), "Please enter your password.", Toast.LENGTH_SHORT).show();
               } else if (confirmPass.isEmpty()) {
                   Toast.makeText(getApplicationContext(), "Please confirm your password.", Toast.LENGTH_SHORT).show();
               } else if (learnerBdate.isEmpty()) {
                   Toast.makeText(getApplicationContext(), "Please select your birthdate.", Toast.LENGTH_SHORT).show();
               } else if (learnerAddress.isEmpty()) {
                   Toast.makeText(getApplicationContext(), "PLease enter your address.", Toast.LENGTH_SHORT).show();
               } else if (learnerGuardianName.isEmpty()) {
                   Toast.makeText(getApplicationContext(), "Please enter your parent's or guardian's name.", Toast.LENGTH_SHORT).show();
               } else if (learnerGuardianEmail.isEmpty()) {
                   Toast.makeText(getApplicationContext(), "Please enter your parent's or guardian's email.", Toast.LENGTH_SHORT).show();
               } else if (!confirmPass.equals(learnerPassword)) {
                   Toast.makeText(getApplicationContext(), "Passwords do not match. Please enter again.", Toast.LENGTH_SHORT).show();
               } else {
                   auth.createUserWithEmailAndPassword(learnerEmail, learnerPassword)
                           .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                               @Override
                               public void onComplete(@NonNull Task<AuthResult> task) {
                                   if (task.isSuccessful()) {
                                       //registration is successful
                                       FirebaseUser user = auth.getCurrentUser();
                                       String uid = user.getUid();

                                       //create user document in firestore
                                       Map<String, Object> learnerMap = new HashMap<>();
                                       learnerMap.put("learnerFirstame", learnerFirstname);
                                       learnerMap.put("learnerLastname", learnerLastname);
                                       learnerMap.put("learnerEmail", learnerEmail);
                                       learnerMap.put("learnerPassword", learnerPassword);
                                       learnerMap.put("learnerBdate", learnerBdate);
                                       learnerMap.put("learnerAddress", learnerAddress);
                                       learnerMap.put("learnerGuardianName", learnerGuardianName);
                                       learnerMap.put("learnerGuardianEmail", learnerGuardianEmail);

                                       //checks if uid is not empty
                                       if (uid != null) {
                                           fire.collection("userLearner")
                                                   .document(uid)
                                                   .set(learnerMap)
                                                   .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                       @Override
                                                       public void onSuccess(Void unused) {
                                                           Toast.makeText(RegisterInfo.this, "Learner has successfully registered!", Toast.LENGTH_SHORT).show();
                                                           clearAll();
                                                       }
                                                   })
                                                   .addOnFailureListener(new OnFailureListener() {
                                                       @Override
                                                       public void onFailure(@NonNull Exception e) {
                                                           Toast.makeText(RegisterInfo.this, "Registration failed! Please recheck your information.", Toast.LENGTH_SHORT).show();
                                                       }
                                                   });
                                           }
                                   } else {
                                       Toast.makeText(RegisterInfo.this, "Registration failed! Please recheck your information.", Toast.LENGTH_SHORT).show();
                                   }
                               }
                           });

                   //if all text field is not empty, proceed to dashboard
                   startActivity(new Intent(getApplicationContext(),Login.class));
                   overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                   finish();
               }
           }
       });

        //return to login button
        binding.loginHereButton.setOnClickListener(new View.OnClickListener() {
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
        builder.setMessage("Go back?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(), RegisterEmailPass.class));
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

    private void clearAll() {
        binding.regFirstnameInput.getText().clear();
        binding.regLastnameInput.getText().clear();
        binding.regEmailInput.getText().clear();
        binding.regPasswordInput.getText().clear();
        binding.regConfirmPasswordInput.getText().clear();
        binding.regEditDate.getText().clear();
        binding.regAddressInput.getText().clear();
        binding.regGuardianNameInput.getText().clear();
        binding.regGuardianEmailInput.getText().clear();
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
                    binding.regEditDate.setText(selectedDate);
                },
                year, month, day);

        datePickerDialog.show();
    }

}