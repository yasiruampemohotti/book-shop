package com.example.bookshop;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private List<Review> reviewList;

    public ReviewAdapter(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = reviewList.get(position);
        holder.username.setText(review.getusername());
        holder.bookName.setText(review.getBookName());
        holder.isbn.setText(review.getIsbn());
        holder.review.setText(review.getReview());
        holder.whereToBuy.setText(review.getWhereToBuy());

        // Set the rating using RatingBar
        holder.ratingBar.setRating(review.getRating());
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public void updateList(List<Review> newList) {
        reviewList = newList;
        notifyDataSetChanged();
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {

        TextView username, bookName, isbn, review, whereToBuy;
        RatingBar ratingBar;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            bookName = itemView.findViewById(R.id.bookName);
            isbn = itemView.findViewById(R.id.isbn);
            review = itemView.findViewById(R.id.review);
            whereToBuy = itemView.findViewById(R.id.whereToBuy);
            ratingBar = itemView.findViewById(R.id.ratingBar);  // Added RatingBar reference
        }
    }
}
