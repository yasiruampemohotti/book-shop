package com.example.bookshop;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddReviewActivity extends AppCompatActivity {

    private EditText username, bookName, isbn, review, whereToBuy;
    private RatingBar ratingBar;
    private Button btnSubmitReview;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        db = FirebaseFirestore.getInstance();

        username = findViewById(R.id.username);
        bookName = findViewById(R.id.bookName);
        isbn = findViewById(R.id.isbn);
        review = findViewById(R.id.review);
        whereToBuy = findViewById(R.id.whereToBuy);
        ratingBar = findViewById(R.id.ratingBar);
        btnSubmitReview = findViewById(R.id.btnSubmitReview);

        btnSubmitReview.setOnClickListener(v -> {
            String usernameText = username.getText().toString().trim();
            String bookNameText = bookName.getText().toString().trim();
            String isbnText = isbn.getText().toString().trim();
            String reviewText = review.getText().toString().trim();
            float ratingValue = ratingBar.getRating();
            String whereToBuyText = whereToBuy.getText().toString().trim();

            Map<String, Object> reviewData = new HashMap<>();
            reviewData.put("username", usernameText);
            reviewData.put("bookName", bookNameText);
            reviewData.put("isbn", isbnText);
            reviewData.put("review", reviewText);
            reviewData.put("rating", ratingValue);
            reviewData.put("whereToBuy", whereToBuyText);

            db.collection("reviews").add(reviewData)
                    .addOnSuccessListener(documentReference -> Toast.makeText(AddReviewActivity.this, "Review Submitted!", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(AddReviewActivity.this, "Error submitting review!", Toast.LENGTH_SHORT).show());
        });
    }
}
