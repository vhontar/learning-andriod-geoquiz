package com.vhontar.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.vhontar.geoquiz.models.Question;

public class MainActivity extends AppCompatActivity {

    private TextView mQuestionTextView;
    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPreviousButton;

    private Question[] mQuestions = new Question[] {
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQuestionTextView = findViewById(R.id.tv_question);
        mTrueButton = findViewById(R.id.btn_true);
        mFalseButton = findViewById(R.id.btn_false);
        mNextButton = findViewById(R.id.next_button);
        mPreviousButton = findViewById(R.id.previous_button);

        // set first question
        updateQuestion();

        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectNextQuestion();
            }
        });

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectNextQuestion();
            }
        });

        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPreviousQuestion();
            }
        });
    }

    private void selectNextQuestion() {
        mCurrentIndex = (mCurrentIndex + 1) % mQuestions.length;
        updateQuestion();
    }

    private void selectPreviousQuestion() {
        mCurrentIndex--;
        if (mCurrentIndex == -1) mCurrentIndex = mQuestions.length - 1;
        updateQuestion();
    }

    /**
     * Method is used for checking user answer for question
     * */
    private void checkAnswer(boolean isUserAnswerTrue) {
        int answerId = R.string.toast_incorrect;
        if (mQuestions[mCurrentIndex].isAnswerTrue() == isUserAnswerTrue) {
            answerId = R.string.toast_correct;
        }
        Toast.makeText(MainActivity.this, answerId, Toast.LENGTH_SHORT).show();
    }

    /**
     * Method is used for updating question text view after next button clicking
     * */
    private void updateQuestion() {
        mQuestionTextView.setText(mQuestions[mCurrentIndex].getTextResId());
    }
}
