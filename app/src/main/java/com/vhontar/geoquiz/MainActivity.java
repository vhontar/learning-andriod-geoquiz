package com.vhontar.geoquiz;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.vhontar.geoquiz.models.Question;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_QUESTIONS = "questions";
    private static final int REQUEST_CHEAT_CODE = 2200;

    private TextView mQuestionTextView;
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;
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
    private boolean mIsCheater = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle)");
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mQuestions = (Question[]) savedInstanceState.getParcelableArray(KEY_QUESTIONS);
        }

        mQuestionTextView = findViewById(R.id.tv_question);
        mTrueButton = findViewById(R.id.btn_true);
        mFalseButton = findViewById(R.id.btn_false);
        mCheatButton = findViewById(R.id.btn_cheat);
        mNextButton = findViewById(R.id.next_button);
        mPreviousButton = findViewById(R.id.previous_button);

        // set first question
        updateQuestion();
        mIsCheater = mQuestions[mCurrentIndex].isUserCheater();

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

        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isAnswerTrue = mQuestions[mCurrentIndex].isAnswerTrue();
                Intent i = CheatActivity.newIntent(MainActivity.this, isAnswerTrue);
                startActivityForResult(i, REQUEST_CHEAT_CODE);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;

        if (requestCode == REQUEST_CHEAT_CODE) {
            if (data == null) return;
            mIsCheater = CheatActivity.wasAnswerShown(data);
            mQuestions[mCurrentIndex].makeUserCheater(mIsCheater);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState()");
        outState.putInt(KEY_INDEX, mCurrentIndex);
        outState.putParcelableArray(KEY_QUESTIONS, mQuestions);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }

    private void selectNextQuestion() {
        mCurrentIndex = (mCurrentIndex + 1) % mQuestions.length;
        mIsCheater = mQuestions[mCurrentIndex].isUserCheater();
        updateQuestion();
    }

    private void selectPreviousQuestion() {
        mCurrentIndex--;
        if (mCurrentIndex == -1) mCurrentIndex = mQuestions.length - 1;
        mIsCheater = mQuestions[mCurrentIndex].isUserCheater();
        updateQuestion();
    }

    /**
     * Method is used for checking user answer for question
     * */
    private void checkAnswer(boolean isUserAnswerTrue) {
        int answerId = R.string.toast_incorrect;
        if (mIsCheater) {
            answerId = R.string.judgment_toast;
        } else if (mQuestions[mCurrentIndex].isAnswerTrue() == isUserAnswerTrue) {
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
