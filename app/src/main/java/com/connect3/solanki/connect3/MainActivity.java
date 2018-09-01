package com.connect3.solanki.connect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // 0: circle 1: cross 2: empty
    int activeplayer = 0;
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningPositions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    boolean gameActive = true;

    public void dropIn(View view) {
        ImageView counter = (ImageView) view;

        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if (gameState[tappedCounter] == 2 && gameActive) {
            gameState[tappedCounter] = activeplayer;
            counter.setTranslationY(-1500);

            if (activeplayer == 0) {
                counter.setImageResource(R.drawable.circle);
                activeplayer = 1;
            }
            else {
                counter.setImageResource(R.drawable.cross);
                activeplayer = 0;
            }

            counter.animate().translationYBy(1500).rotation(1800).setDuration(400);

            for (int[] winningPosition : winningPositions) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2) {
                    String winner = "";
                    // someone has won
                    gameActive = false;

                    if (activeplayer == 1) {
                        winner = "Circle";
                    } else {
                        winner = "Cross";
                    }

                    TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);
                    Button playAgainButton = (Button) findViewById(R.id.playAgainButton);

                    winnerTextView.setText(winner + " has Won!");
                    winnerTextView.setVisibility(View.VISIBLE);
                    winnerTextView.animate().alpha(1).rotation(720).scaleX(1.5f).scaleY(1.5f).setStartDelay(500).setDuration(1500);
                    playAgainButton.setVisibility(View.VISIBLE);
                }
            }

            boolean emptySquare = false;
            for (int squareState : gameState) {
                if (squareState == 2) {
                    emptySquare = true;
                    break;
                }
            }

            if (!emptySquare && gameActive) {
                // Game is a draw
                TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);
                Button playAgainButton = (Button) findViewById(R.id.playAgainButton);

                winnerTextView.setText("Game Draw!");
                winnerTextView.setVisibility(View.VISIBLE);

                winnerTextView.animate().scaleX(2.25f).scaleY(2.25f).setStartDelay(500).setDuration(3000);
                playAgainButton.setVisibility(View.VISIBLE);

                gameActive = false;
            }
        }
    }

    public void playAgain(View view) {
        TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);
        Button playAgainButton = (Button) findViewById(R.id.playAgainButton);

        winnerTextView.setVisibility(View.INVISIBLE);
        playAgainButton.setVisibility(View.INVISIBLE);

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for (int i=0;i<gridLayout.getChildCount();i++) {
            ImageView counter = (ImageView) gridLayout.getChildAt(i);

            counter.setImageDrawable(null);
        }

        for (int i=0;i<gameState.length;i++) {
            gameState[i] = 2;
        }
        activeplayer = 0;
        gameActive = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
