package com.example.autocart;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.util.Log;

public class AC_FeedbackPage extends AppCompatActivity {

    /* Variable Initialization (feedback options) */
    TextView tvFeedback;
    RatingBar ratingStars;
    Button feedbackButton;
    EditText feedbackContent;

    int stars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autocart_feedbackpage);

        tvFeedback = findViewById(R.id.tvFeedback);
        ratingStars = findViewById(R.id.ratingBar);
        feedbackButton = findViewById(R.id.feedbackSubmit);
        feedbackContent = findViewById(R.id.textMessage);

        ratingStars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() { // star bar options (used to record user satisfaction)
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) { // rating options (1-5)
                if (rating == 0) { // if statement: if rating == 0: user is "very dissatisfied"
                    tvFeedback.setText("Very Dissatisfied"); // rating confirmation
                }
                else if (rating == 1) { // else if statement: if rating == 1: user is "dissatisfied"
                    tvFeedback.setText("Dissatisfied"); // rating confirmation
                }
                else if (rating == 2) { // else if statement: if rating == 2: user is "ok"
                    tvFeedback.setText("Ok"); // rating confirmation
                }
                else if (rating == 3) { // else if statement: if rating == 3: user is "Decent"
                    tvFeedback.setText("Decent"); // rating confirmation
                }
                else if (rating == 4) { // else if statement: if rating == 4: user is "Satisfied"
                    tvFeedback.setText("Satisfied"); // rating confirmation
                }
                else if (rating == 5) { // else if statement: if rating == 5: user is "Very Satisfied"
                    tvFeedback.setText("Very Satisfied"); // rating confirmation
                }
                else{ // else statement: else if rating is not available, wait for user input
                    // DO NOTHING //
                }

                stars = (int)Math.ceil(rating);
            }
        });

        /* Submit Feedback Button: submit feedback entry for product maintenance */
        //Referencing solution from https://stackoverflow.com/questions/8994488/android-button-onclick-submit-to-email
        feedbackButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"autocart.assistance@gmail.com"});
                email.putExtra(Intent.EXTRA_SUBJECT, "AutoCart Feedback: " + stars + "/5 star rating");
                email.putExtra(Intent.EXTRA_TEXT, feedbackContent.getText().toString());

                //need this to prompts email client only
                email.setType("message/rfc822");

                //Should launch the user's email app allowing the user to choose which app to send the email.
                startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }
        });
    }
}

