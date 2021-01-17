package com.erin.dotdotdot;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private DotsGame mGame;
    private DotsGrid mDotsGrid;
    private TextView mMovesRemaining;
    private TextView mScore;
    private SoundEffects mSoundEffects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMovesRemaining = findViewById(R.id.movesRemaining);
        mScore = findViewById(R.id.score);
        mDotsGrid = findViewById(R.id.gameGrid);
        mDotsGrid.setGridListener(mGridListener);

        mGame = DotsGame.getInstance();
        mSoundEffects = SoundEffects.getInstance(getApplicationContext());
        newGame();
    }

    private DotsGrid.DotsGridListener mGridListener = new DotsGrid.DotsGridListener() {
        @Override
        public void onAnimationFinished() {
            mGame.finishMove();
            mDotsGrid.invalidate();
            updateMovesAndScore();
            if (mGame.isGameOver()) {
                mSoundEffects.playGameOver();
            }
        }

        @Override
        public void onDotSelected(Dot dot, DotsGrid.DotSelectionStatus status) {
            // Ignore selections when game is over
            if (mGame.isGameOver()) return;
            // Add to list of selected dots
            mGame.addSelectedDot(dot);

            // Play first tone when first dot is selected
            if (status == DotsGrid.DotSelectionStatus.First) {
                mSoundEffects.resetTones();
            }
            // Select the dot and play the right tone
            DotsGame.AddDotStatus addStatus = mGame.addSelectedDot(dot);
            if (addStatus == DotsGame.AddDotStatus.Added) {
                mSoundEffects.playTone(true);
            }
            else if (addStatus == DotsGame.AddDotStatus.Removed) {
                mSoundEffects.playTone(false);
            }


            // If done selecting dots then replace selected dots and display new moves and
            if (status == DotsGrid.DotSelectionStatus.Last) {
                if (mGame.getSelectedDots().size() > 1) {
                    mDotsGrid.animateDots();
                    // These methods must be called AFTER the animation completes
//                    mGame.finishMove();
//                    updateMovesAndScore();
                } else {
                   mGame.clearSelectedDots();
                }
            }

            // Display changes to the game
            mDotsGrid.invalidate();
        }
    };

    public void newGameClick(View view) {
        newGame();
    }
    private void newGame() {
        mGame.newGame();
        mDotsGrid.invalidate();
        updateMovesAndScore();
    }

    private void updateMovesAndScore() {
        mMovesRemaining.setText(Integer.toString(mGame.getMovesLeft()));
        mScore.setText(Integer.toString(mGame.getScore()));
    }
}