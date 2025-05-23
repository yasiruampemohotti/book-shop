package com.example.bookshop;

import android.os.Bundle;
import android.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewReviewActivity extends AppCompatActivity {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private ReviewAdapter reviewAdapter;
    private List<Review> reviewList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_review);

        searchView = findViewById(R.id.searchView);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reviewList = new ArrayList<>();
        reviewAdapter = new ReviewAdapter(reviewList);
        recyclerView.setAdapter(reviewAdapter);

        db = FirebaseFirestore.getInstance();

        // Retrieve and listen for reviews from Firestore
        db.collection("reviews").addSnapshotListener((EventListener<QuerySnapshot>) (value, error) -> {
            if (value != null) {
                reviewList.clear();
                for (DocumentSnapshot document : value.getDocuments()) {
                    Review review = document.toObject(Review.class);
                    reviewList.add(review);
                }
                reviewAdapter.notifyDataSetChanged();
            }
        });

        // Implement search functionality
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchReviews(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchReviews(newText);
                return true;
            }
        });
    }

    private void searchReviews(String query) {
        List<Review> filteredList = new ArrayList<>();
        for (Review review : reviewList) {
            if (review.getBookName().toLowerCase().contains(query.toLowerCase()) ||
                    review.getIsbn().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(review);
            }
        }
        reviewAdapter.updateList(filteredList);
    }
}
