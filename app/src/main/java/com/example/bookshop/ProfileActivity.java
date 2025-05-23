package com.example.bookshop;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private EditText username, address, nic, gender, contactNumber;
    private Button btnSaveProfile, btnViewReviews, btnAddReview;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize Firebase
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = auth.getCurrentUser();

        // Bind views
        username = findViewById(R.id.username);
        address = findViewById(R.id.address);
        nic = findViewById(R.id.nic);
        gender = findViewById(R.id.gender);
        contactNumber = findViewById(R.id.contactNumber);
        btnSaveProfile = findViewById(R.id.btnSaveProfile);
        btnViewReviews = findViewById(R.id.btnViewReviews);
        btnAddReview = findViewById(R.id.btnAddReview);

        // Load user details
        loadUserProfile();

        // Save button functionality
        btnSaveProfile.setOnClickListener(v -> saveUserProfile());

        // View/Add Review buttons
        btnViewReviews.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, ViewReviewActivity.class)));
        btnAddReview.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, AddReviewActivity.class)));
    }

    private void loadUserProfile() {
        if (user != null) {
            DocumentReference userDoc = db.collection("users").document(user.getUid());
            userDoc.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    username.setText(documentSnapshot.getString("username"));
                    address.setText(documentSnapshot.getString("address"));
                    nic.setText(documentSnapshot.getString("nic"));
                    gender.setText(documentSnapshot.getString("gender"));
                    contactNumber.setText(documentSnapshot.getString("contactNumber"));
                }
            }).addOnFailureListener(e -> Toast.makeText(ProfileActivity.this, "Error loading profile data", Toast.LENGTH_SHORT).show());
        }
    }

    private void saveUserProfile() {
        String usernameText = username.getText().toString().trim();
        String addressText = address.getText().toString().trim();
        String nicText = nic.getText().toString().trim();
        String genderText = gender.getText().toString().trim();
        String contactText = contactNumber.getText().toString().trim();

        if (TextUtils.isEmpty(usernameText) || TextUtils.isEmpty(contactText) || TextUtils.isEmpty(genderText) || TextUtils.isEmpty(nicText)) {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (user != null) {
            DocumentReference userDoc = db.collection("users").document(user.getUid());
            Map<String, Object> userProfile = new HashMap<>();
            userProfile.put("username", usernameText);
            userProfile.put("address", addressText);
            userProfile.put("nic", nicText);
            userProfile.put("gender", genderText);
            userProfile.put("contactNumber", contactText);

            userDoc.set(userProfile).addOnSuccessListener(aVoid ->
                    Toast.makeText(ProfileActivity.this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show()
            ).addOnFailureListener(e ->
                    Toast.makeText(ProfileActivity.this, "Failed to Update Profile", Toast.LENGTH_SHORT).show()
            );
        }
    }
}
