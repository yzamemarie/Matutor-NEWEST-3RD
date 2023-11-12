package com.example.matutor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.matutor.databinding.ActivityContentBinding;
import com.example.matutor.databinding.ActivitySidebarBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

public class Sidebar extends AppCompatActivity {

    ActivitySidebarBinding binding;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // removes status bar
        binding = ActivitySidebarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //fetch user first name and last name
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
                                    binding.userLastName.setText(lastname);
                                }

                                String firstname = documentSnapshot.getString("learnerFirstname");
                                if (!firstname.isEmpty()) {
                                    binding.userFirstName.setText(firstname);
                                }
                            }
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }
}