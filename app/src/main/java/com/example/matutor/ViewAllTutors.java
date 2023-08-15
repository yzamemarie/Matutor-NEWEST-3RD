package com.example.matutor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

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

public class ViewAllTutors extends AppCompatActivity {

    Button learnerSwitch;
    SearchView searchBar;

    ExtendedFloatingActionButton menuFabBtn;
    FloatingActionButton viewAllPosts, createPost, viewCreatedPost, viewAllUsers;
    Boolean allFabVisible; //checks for visibility of sub fabs
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // removes status bar
        setContentView(R.layout.activity_view_all_learners);

        searchBar = findViewById(R.id.searchView);
        learnerSwitch = findViewById(R.id.switchButton);
        menuFabBtn = findViewById(R.id.menuFab);
        viewAllPosts = findViewById(R.id.viewAllFab);
        createPost = findViewById(R.id.createPostFab);
        viewCreatedPost =findViewById(R.id.viewCreatedPostFab);
        viewAllUsers = findViewById(R.id.viewAllUsersFab);
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.posting);

        viewAllPosts.setVisibility(View.GONE);
        createPost.setVisibility(View.GONE);
        viewCreatedPost.setVisibility(View.GONE);
        viewAllUsers.setVisibility(View.GONE);

        learnerSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ViewAllLearners.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
                finish();
            }
        });

        //search bar
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle search query submission
                performSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Handle search query text change
                performSearch(newText);
                return true;
            }
        });

        allFabVisible = false;

        //shrink extended fab + click listener
        menuFabBtn.shrink();
        menuFabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!allFabVisible) {
                    //extend extended fab and set sub fab as visible
                    menuFabBtn.extend();
                    viewAllPosts.show();
                    createPost.show();
                    viewCreatedPost.show();
                    viewAllUsers.show();
                    allFabVisible = true;

                } else {
                    //hid sub fabs
                    menuFabBtn.shrink();
                    viewAllPosts.hide();
                    createPost.hide();
                    viewCreatedPost.hide();
                    viewAllUsers.hide();
                    allFabVisible = false;
                }
            }
        });

        //sub fab click listeners
        viewAllPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Posting.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
                finish();
            }
        });

        createPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreatePosting.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
                finish();
            }
        });

        viewCreatedPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ViewCreatedPostsTutor.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
                finish();
            }
        });

        viewAllUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Current page!", Toast.LENGTH_SHORT).show();
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.posting) {
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
                    startActivity(new Intent(getApplicationContext(), Profile.class));
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

    private void performSearch(String query) {
        // Here, you can implement your search logic
        // Update the UI or perform any actions based on the search query
    }

    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), Posting.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
        finish();
    }
}