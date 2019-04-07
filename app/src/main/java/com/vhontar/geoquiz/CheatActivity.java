package com.vhontar.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_IS_TRUE = "answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "answer_shown";
    private static final String KEY_ANSWER_SHOWN = "key_answer_shown";

    private TextView mAnswerTextView;
    private Button mShowAnswerButton;
    private TextView mAppLevelTextView;

    private boolean mIsAnswerTrue;
    private boolean mIsAnswerShown;

    public static Intent newIntent(Context context, boolean isAnswerTrue) {
        Intent i = new Intent(context, CheatActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE, isAnswerTrue);
        return i;
    }

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        if (savedInstanceState != null) {
            mIsAnswerShown = savedInstanceState.getBoolean(KEY_ANSWER_SHOWN, false);
        }

        mIsAnswerTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mAnswerTextView = findViewById(R.id.tv_cheat_answer);
        mShowAnswerButton = findViewById(R.id.btn_cheat_show_answer);
        mAppLevelTextView = findViewById(R.id.tv_cheat_app_level);

        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnswer();
                mIsAnswerShown = true;
                setAnswerShownResult();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    int cx = mShowAnswerButton.getWidth() / 2;
                    int cy = mShowAnswerButton.getHeight() / 2;
                    float radius = mShowAnswerButton.getWidth();
                    Animator anim = ViewAnimationUtils
                            .createCircularReveal(mShowAnswerButton, cx, cy, radius, 0);
                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mAnswerTextView.setVisibility(View.VISIBLE);
                            mShowAnswerButton.setVisibility(View.INVISIBLE);
                        } });
                    anim.start();
                } else {
                    mAnswerTextView.setVisibility(View.VISIBLE);
                    mShowAnswerButton.setVisibility(View.INVISIBLE);
                }
            }
        });

        mAppLevelTextView.setText(getString(R.string.app_level, Build.VERSION.SDK_INT));

        if (mIsAnswerShown) {
            showAnswer();
            setAnswerShownResult();
        }
    }

    private void showAnswer() {
        int answerId = R.string.false_button;
        if (mIsAnswerTrue) answerId = R.string.true_button;
        mAnswerTextView.setText(answerId);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_ANSWER_SHOWN, mIsAnswerShown);
    }

    private void setAnswerShownResult() {
        Intent i = new Intent();
        i.putExtra(EXTRA_ANSWER_SHOWN, mIsAnswerShown);
        setResult(RESULT_OK, i);
    }
}
