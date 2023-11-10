package com.example.matutor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.matutor.databinding.ActivityProfileBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class Profile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ActivityProfileBinding binding;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // removes status bar
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavigator.setSelectedItemId(R.id.dashboard);

        //FOR DRAWER SIDE MENU
        setSupportActionBar(binding.toolbar);
        //NAV MENU
        binding.navView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar, R.string.drawer_open, R.string.drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        binding.navView.setNavigationItemSelectedListener(this);

        //fetch and display user's info
        String uid = auth.getCurrentUser().getUid();
        if (!uid.isEmpty()) {
            firestore.collection("learner")
                    .document(uid)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                Picasso picasso = new Picasso.Builder(getApplicationContext()).build();
                                if (documentSnapshot.contains("learnerProfilePicture")) {
                                    String profilePicUrl = documentSnapshot.getString("learnerProfilePicture");
                                    if (!profilePicUrl.isEmpty() && profilePicUrl != null) {
                                        picasso.load(profilePicUrl)
                                                .into(binding.userProfilePic);
                                    }
                                }

                                String lastname = documentSnapshot.getString("learnerLastname");
                                if (!lastname.isEmpty()) {
                                    binding.lastnameTextView.setText(lastname);
                                }

                                String firstname = documentSnapshot.getString("learnerFirstname");
                                if (!firstname.isEmpty()) {
                                    binding.firstnameTextView.setText(firstname);
                                }

                                String email = documentSnapshot.getString("learnerEmail");
                                if (!email.isEmpty()) {
                                    binding.emailDetails.setText(email);
                                }

                                String bdate = documentSnapshot.getString("learnerBdate");
                                if (!bdate.isEmpty()) {
                                    binding.birthdateDetails.setText(bdate);
                                }

                                String age = documentSnapshot.getString("learnerAge");
                                if (!age.isEmpty()) {
                                    binding.ageDetails.setText(age);
                                }

                                String address = documentSnapshot.getString("learnerAddress");
                                if (!address.isEmpty()) {
                                    binding.addressDetails.setText(address);
                                }

                                String contact = documentSnapshot.getString("learnerContact");
                                if (!contact.isEmpty()) {
                                    binding.contactDetails.setText(contact);
                                }

                                String guardianName = documentSnapshot.getString("learnerGuardianName");
                                if (!guardianName.isEmpty() && guardianName != null) {
                                    binding.guardianNameDetails.setText(guardianName);
                                }

                                String guardianEmail = documentSnapshot.getString("learnerGuardianEmail");
                                if (!guardianEmail.isEmpty() && guardianEmail != null) {
                                    binding.guardianEmailDetails.setText(guardianEmail);
                                }
                            }
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }

        //click to edit user profile
        binding.editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditProfile.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
                finish();
            }
        });

        //navbar navigation
        binding.bottomNavigator.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.posting) {
                    startActivity(new Intent(getApplicationContext(), Posting.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                else if (itemId == R.id.dashboard) {
                    return true;
                }
                else if (itemId == R.id.content) {
                    startActivity(new Intent(getApplicationContext(), Content.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                else if (itemId == R.id.create) {
                    startActivity(new Intent(getApplicationContext(), CreatePosting.class));
                    overridePendingTransition(0, 0);
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
        //to avoid closing the application on back press
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(getApplicationContext(), Dashboard.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
            finish();
        }

    }

    //sidemenu
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.side_dashboard) {
            startActivity(new Intent(getApplicationContext(), Dashboard.class));
            return true;
        }
        else if (itemId == R.id.side_profile) {
            startActivity(new Intent(getApplicationContext(), Profile.class));
            return true;
        }
        else if (itemId == R.id.side_progReports) {
            startActivity(new Intent(getApplicationContext(), ViewProgressReportsLearner.class));
            return true;
        }
        else if (itemId == R.id.side_yourPostings) {
            startActivity(new Intent(getApplicationContext(), ViewCreatedPosts.class));
            return true;
        }
        else if (itemId == R.id.side_yourBookings) {
            startActivity(new Intent(getApplicationContext(), Bookings.class));
            return true;
        }
        else if (itemId == R.id.side_yourReviews) {
            startActivity(new Intent(getApplicationContext(), ReviewsHistory.class));
            return true;
        }
        else if (itemId == R.id.side_yourHistory) {
            startActivity(new Intent(getApplicationContext(), BookingsHistory.class));
            return true;
        }
        else if (itemId == R.id.side_help) {
            //create help smth
            return true;
        }
        else if (itemId == R.id.side_logout) {
            logoutConfirmation();
            return true;
        }
        return false;
    }

    private void logoutConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout Session");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            auth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }
}
