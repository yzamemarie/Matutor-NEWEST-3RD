package com.example.matutor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BookingsTutor extends AppCompatActivity {

    Button learnerSwitch, finishSession, cancelSession;
    ExtendedFloatingActionButton menuFabBtn;
    FloatingActionButton viewProfile, viewBookings, viewHistory;
    Boolean allFabVisible; //checks for visibility of sub fabs
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // removes status bar
        setContentView(R.layout.activity_bookings_tutor);

        //assignment
        learnerSwitch = findViewById(R.id.switchButton);
        finishSession = findViewById(R.id.finishLearnerSessionButton);
        cancelSession = findViewById(R.id.cancelLearnerSessionButton);
        menuFabBtn = findViewById(R.id.menuFab);
        viewProfile = findViewById(R.id.viewProfileFab);
        viewBookings = findViewById(R.id.viewBookingsFab);
        viewHistory = findViewById(R.id.viewHistoryFab);
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.profile);

        viewProfile.setVisibility(View.GONE);
        viewBookings.setVisibility(View.GONE);
        viewHistory.setVisibility(View.GONE);

        //set boolean variable as false
        allFabVisible = false;

        //shrink extended fab + click listener
        menuFabBtn.shrink();
        menuFabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!allFabVisible) {
                    //extend extended fab and set sub fab as visible
                    menuFabBtn.extend();
                    viewProfile.show();
                    viewBookings.show();
                    viewHistory.show();
                    allFabVisible = true;

                } else {
                    //hid sub fabs
                    menuFabBtn.shrink();
                    viewProfile.hide();
                    viewBookings.hide();
                    viewHistory.hide();
                    allFabVisible = false;
                }
            }
        });

        //sub fab click listeners
        viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProfileTutor.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
                finish();
            }
        });

        viewBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Current page!", Toast.LENGTH_SHORT).show();
            }
        });

        viewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BookingsHistoryTutor.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
                finish();
            }
        });

        //switch user type
        learnerSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Bookings.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
                finish();
            }
        });

        //finish tutor button
        finishSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmFinishSession();
            }
        });

        //cancel session
        cancelSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelSessionConfirmation();
            }
        });

        //navbar navigation
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.posting) {
                    return true;
                } else if (itemId == R.id.dashboard) {
                    startActivity(new Intent(getApplicationContext(), Dashboard.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemId == R.id.content) {
                    startActivity(new Intent(getApplicationContext(), Content.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemId == R.id.profile) {

                    startActivity(new Intent(getApplicationContext(), Profile.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemId == R.id.notif) {
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
        Intent intent = new Intent(getApplicationContext(), DashboardTutor.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }

    private void confirmFinishSession() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm?");
        builder.setMessage("Confirm completion of session?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), ReviewLearner.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
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

    private void cancelSessionConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cancel?");
        builder.setMessage("Do you really want to cancel this session?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Session cancelled!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), PostingsTutor.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
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