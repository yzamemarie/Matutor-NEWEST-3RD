package com.example.matutor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class ScheduleSession extends AppCompatActivity {

    Button close, schedule;
    Spinner modeSpinner;
    EditText editDate, editSessionRate, editTimeStart, editTimeEnd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // removes status bar
        setContentView(R.layout.activity_schedule_session);

        close = findViewById(R.id.closeButton);
        schedule = findViewById(R.id.scheduleButton);
        modeSpinner = findViewById(R.id.modeOfTutorSpinner);
        editDate = findViewById(R.id.editDateText);
        editTimeStart = findViewById(R.id.editTimeStartText);
        editTimeEnd = findViewById(R.id.editTimeEndText);

        // Populate the Spinner with an array of items
        String[] items = {"Face-to-Face (F2F)", "Virtual (via Google Meet, Zoom, etc.)"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modeSpinner.setAdapter(adapter);

        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        editTimeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimeStartPickerDialog();
            }
        });

        editTimeEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimeEndPickerDialog();
            }
        });

        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Schedule set!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Bookings.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeConfirmation();
            }
        });

    }

    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), TutorProfilePreview1.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
        finish();
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

    private void showTimeStartPickerDialog() {
        final Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String selectedTime = String.format("%02d:%02d", hourOfDay, minute);
                        editTimeStart.setText(selectedTime);
                    }
                }, hour, minute, true);

        timePickerDialog.show();
    }

    private void showTimeEndPickerDialog() {
        final Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String selectedTime = String.format("%02d:%02d", hourOfDay, minute);
                        editTimeEnd.setText(selectedTime);
                    }
                }, hour, minute, true);

        timePickerDialog.show();
    }

    private void closeConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm?");
        builder.setMessage("Cancel review?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), Notification.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
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