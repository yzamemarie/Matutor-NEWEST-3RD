package com.example.matutor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditProfile extends AppCompatActivity {

    Button saveChanges, deleteAccount;
    Spinner ageSpinner, topic1Spinner, topic2Spinner, topic3Spinner, topic4Spinner, topic5Spinner;
    EditText editDate;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // removes status bar
        setContentView(R.layout.activity_edit_profile);

        //assignment
        saveChanges = findViewById(R.id.saveChangesButton);
        deleteAccount = findViewById(R.id.deleteAccountButton);
        ageSpinner = findViewById(R.id.editAgeSpinner);
        editDate = findViewById(R.id.editDateText);
        topic1Spinner = findViewById(R.id.editTopicSpinner1);
        topic2Spinner = findViewById(R.id.editTopicSpinner2);
        topic3Spinner = findViewById(R.id.editTopicSpinner3);
        topic4Spinner = findViewById(R.id.editTopicSpinner4);
        topic5Spinner = findViewById(R.id.editTopicSpinner5);
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.profile);

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

        //save changes button
        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveConfirmation();
            }
        });

        //delete account button
        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DeleteAccount.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
                finish();
            }
        });

        //navbar navigation
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.posting) {
                    startActivity(new Intent(getApplicationContext(), Posting.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                else if (itemId == R.id.dashboard) {
                    startActivity(new Intent(getApplicationContext(), Dashboard.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                else if (itemId == R.id.content) {
                    startActivity(new Intent(getApplicationContext(), Content.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                else if (itemId == R.id.profile) {
                    return true;
                }
                else if (itemId == R.id.notif) {
                    startActivity(new Intent(getApplicationContext(), Notification.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), Profile.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
        finish();
    }

    //confirmation dialogues
    private void saveConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm");
        builder.setMessage("Save changes?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(), Profile.class));
                overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
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