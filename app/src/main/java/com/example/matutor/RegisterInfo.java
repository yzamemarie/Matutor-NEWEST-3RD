package com.example.matutor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.matutor.databinding.ActivityRegisterInfoBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.lang.annotation.Native;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterInfo extends AppCompatActivity {

    //private static final int PERMISSION_CAMERA = 1;
    //private static final int PERMISSION_GALLERY = 2;
    private static final int SELECT_ID_FRONT = 3;
    private static final int SELECT_ID_BACK = 4;
    private static final int SELECT_SELFIE = 5;

    ActivityRegisterInfoBinding binding;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    String idFrontFileName, idBackFileName, selfieFileName;
    Intent imageData = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityRegisterInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String getEmail = intent.getStringExtra("Email");
        String getPassword = intent.getStringExtra("Password");
        String getConfirm = intent.getStringExtra("Confirm Password");

        binding.regEmailInput.setText(getEmail);
        binding.regPasswordInput.setText(getPassword);
        binding.regConfirmPasswordInput.setText(getConfirm);

        List<String> items = new ArrayList<>();
        for (int i = 0; i <= 100; i++) {
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

        binding.regIdFrontButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPickerDialog(SELECT_ID_FRONT);
            }
        });

        binding.regIdBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPickerDialog(SELECT_ID_BACK);
            }
        });

        binding.regSelfieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPickerDialog(SELECT_SELFIE);
            }
        });

        //click register button to proceed to login
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
                String learnerAge = binding.regAgeSpinner.getSelectedItem().toString().trim();
                String learnerAddress = binding.regAddressInput.getText().toString().trim();
                String learnerContact = binding.regContactInput.getText().toString().trim();
                String learnerGuardianName = binding.regGuardianNameInput.getText().toString().trim();
                String learnerGuardianEmail = binding.regGuardianEmailInput.getText().toString().trim();

                //checks if front and back ID images and selfie are selected
                if (imageData == null || imageData.getParcelableExtra("data") == null) {
                    Toast.makeText(getApplicationContext(), "No image has been selected.", Toast.LENGTH_SHORT).show();
                    return;
                }

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
                } else if (binding.regAgeSpinner.getSelectedItem().equals("0")) {
                    Toast.makeText(getApplicationContext(), "Please select your age.", Toast.LENGTH_SHORT).show();
                } else if (learnerAddress.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter your address.", Toast.LENGTH_SHORT).show();
                } else if (learnerContact.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter your contact number.", Toast.LENGTH_SHORT).show();
                } else if (learnerGuardianName.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter your parent's or guardian's name.", Toast.LENGTH_SHORT).show();
                } else if (learnerGuardianEmail.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter your parent's or guardian's email.", Toast.LENGTH_SHORT).show();
                } else if (!confirmPass.equals(learnerPassword)) {
                    Toast.makeText(getApplicationContext(), "Passwords do not match. Please enter again.", Toast.LENGTH_SHORT).show();
                } else {
                    auth.createUserWithEmailAndPassword(learnerEmail, learnerPassword)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    String uid = auth.getCurrentUser().getUid();
                                    if (uid != null) {
                                        //create user document for firestore
                                        Map<String, Object> learnerMap = new HashMap<>();
                                        learnerMap.put("learnerUid", uid);
                                        learnerMap.put("learnerFirstname", learnerFirstname);
                                        learnerMap.put("learnerLastname", learnerLastname);
                                        learnerMap.put("learnerEmail", learnerEmail);
                                        learnerMap.put("learnerPassword", learnerPassword);
                                        learnerMap.put("learnerBdate", learnerBdate);
                                        learnerMap.put("learnerAge", learnerAge);
                                        learnerMap.put("learnerContact", learnerContact);
                                        learnerMap.put("learnerAddress", learnerAddress);
                                        learnerMap.put("learnerGuardianName", learnerGuardianName);
                                        learnerMap.put("learnerGuardianEmail", learnerGuardianEmail);

                                        firestore.collection("user_learner")
                                                .document(learnerEmail)  // Use learnerEmail as the document ID
                                                .set(learnerMap)
                                                .addOnSuccessListener(aVoid -> {
                                                    binding.regFirstnameInput.getText().clear();
                                                    binding.regLastnameInput.getText().clear();
                                                    binding.regEmailInput.getText().clear();
                                                    binding.regPasswordInput.getText().clear();
                                                    binding.regEditDate.getText().clear();
                                                    binding.regAgeSpinner.setSelection(0);
                                                    binding.regAddressInput.getText().clear();
                                                    binding.regContactInput.getText().clear();
                                                    binding.regGuardianNameInput.getText().clear();
                                                    binding.regGuardianEmailInput.getText().clear();

                                                    // Upload images to Firestore Storage
                                                    uploadDefaultProfile(learnerEmail, firestore);
                                                    uploadDefaultProfile(learnerEmail, firestore);
                                                    uploadImageToFirestore(learnerEmail, SELECT_ID_FRONT, imageData, idFrontFileName);
                                                    uploadImageToFirestore(learnerEmail, SELECT_ID_BACK, imageData, idBackFileName);
                                                    uploadImageToFirestore(learnerEmail, SELECT_SELFIE, imageData, selfieFileName);

                                                    Toast.makeText(getApplicationContext(), "Learner has successfully registered!", Toast.LENGTH_SHORT).show();
                                                })
                                                .addOnFailureListener(e -> {
                                                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                });

                                    }
                                    startActivity(new Intent(getApplicationContext(), Login.class));
                                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                    finish();

                                }  else {
                                    Toast.makeText(getApplicationContext(), "Registration failed!", Toast.LENGTH_SHORT).show();
                                }

                            });

                    //add code that stores user info for admin approval
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

    private void uploadDefaultProfile(String learnerEmail, FirebaseFirestore firestore) {
        int defaultProfilePictureResourceId = R.drawable.user_pp;
        Uri defaultProfilePictureUri = Uri.parse("android.resource://" + getPackageName() + "/" + defaultProfilePictureResourceId);
        StorageReference storageRef = storage.getReference().child("profile_pictures/" + learnerEmail + "/user_pp.png");
        UploadTask uploadTask = storageRef.putFile(defaultProfilePictureUri);

        uploadTask.addOnSuccessListener(taskSnapshot -> updateProfilePictureInFirestore(learnerEmail, firestore))
                .addOnFailureListener(e -> {
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void updateProfilePictureInFirestore(String learnerEmail, FirebaseFirestore firestore) {
        StorageReference storageRef = storage.getReference().child("profile_pictures/" + learnerEmail + "/user_pp.png");
        storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
            String downloadUrl = uri.toString();
            DocumentReference userRef = firestore.collection("learner").document(learnerEmail);
            userRef.update("learnerProfilePicture", downloadUrl)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(getApplicationContext(), "Successfully updated to Firestore ", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });
    }

    private void openPickerDialog(int perm) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Source");
        builder.setItems(new CharSequence[]{"Camera", "Gallery"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int choice) {
                switch (choice) {
                    case 0:
                        openCamera(perm);
                        break;
                    case 1:
                        openGallery(perm);
                        break;
                }
            }
        });
        builder.show();
    }

    private void openCamera(int perm) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, perm);
        }
    }

    private void openGallery(int perm) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, perm);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_ID_FRONT || requestCode == SELECT_ID_BACK || requestCode == SELECT_SELFIE) {
                // Save the data for later use
                imageData = data;

                // Get the file name and update the corresponding EditText
                String fileName = getImageFileName(requestCode);
                if (fileName != null && !fileName.isEmpty()) {
                    displayImageFileName(requestCode, fileName);
                } else {
                    Toast.makeText(getApplicationContext(), "Error: Unable to get the file name.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void displayImageFileName(int perm, String fileName) {
        switch (perm) {
            case SELECT_ID_FRONT:
                idFrontFileName = fileName;
                binding.idFrontPathTextView.setText(fileName);
                break;
            case SELECT_ID_BACK:
                idBackFileName = fileName;
                binding.idBackPathTextView.setText(fileName);
                break;
            case SELECT_SELFIE:
                selfieFileName = fileName;
                binding.selfiePathTextView.setText(fileName);
                break;
        }
    }

    private void uploadImageToFirestore(String learnerEmail, int perm, Intent data, String fileName) {
        StorageReference storageRef = storage.getReference().child("approval_id");
        UploadTask uploadTask = storageRef.putFile(getImageUri(perm, data, fileName));

        uploadTask.addOnSuccessListener(taskSnapshot -> {
            storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                String imageUri = uri.toString();

                String updateFieldImageUri;
                switch (perm) {
                    case SELECT_ID_FRONT:
                        updateFieldImageUri = "learnerIdFrontImage";
                        break;
                    case SELECT_ID_BACK:
                        updateFieldImageUri = "learnerIdBackImage";
                        break;
                    case SELECT_SELFIE:
                        updateFieldImageUri = "learnerSelfieImage";
                        break;
                    default:
                        updateFieldImageUri = "";
                }

                if (!updateFieldImageUri.isEmpty()) {
                    DocumentReference userRef = firestore.collection("user_learner").document(learnerEmail);
                    userRef.update(updateFieldImageUri, imageUri)
                            .addOnSuccessListener(aVoid -> {
                                // Image URL updated successfully in Firestore
                            })
                            .addOnFailureListener(e -> {
                                // Handle the case where the image URL update fails
                            });
                }
            });
        }).addOnFailureListener(e -> {
            Toast.makeText(getApplicationContext(), "Error uploading image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    private Uri getImageUri(int perm, Intent data, String fileName) {
        if (perm == SELECT_ID_FRONT || perm == SELECT_ID_BACK || perm == SELECT_SELFIE) {
            if (data != null && data.getData() != null) {
                // Return the URI of the selected image
                return data.getData();
            } else if (data != null && data.getExtras() != null && data.getExtras().get("data") != null) {
                // If the image is captured with the camera, return the URI of the captured image
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                String path = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), imageBitmap, fileName, null);
                return Uri.parse(path);
            }
        }
        return null; // Return null if URI retrieval fails or if perm is not recognized
    }

    private String getImageFileName(int perm) {
        switch (perm) {
            case SELECT_ID_FRONT:
                return "id_front";
            case SELECT_ID_BACK:
                return "id_back";
            case SELECT_SELFIE:
                return "selfie";
            default:
                return "";
        }
    }

}