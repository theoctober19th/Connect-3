package com.theoctober19th.connect3;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.v7.widget.GridLayout;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static final int BLUE_TURN = 0;
    static final int RED_TURN = 1;
    private boolean blueWins = false;
    private boolean redWins = false;
    private boolean isGameActive = true;

    private int turn = BLUE_TURN;

    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    int[][] winningPosition = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    GridLayout gridLayout = null;
    TextView resultTextView = null;
    Button restartButton = null;

    public boolean isGameDrawn(){
        boolean notAllFull = false;
        for(int i=0; i<gameState.length; i++){
            if(gameState[i] == 2) notAllFull = true;
        }
        if(!notAllFull && !blueWins && !redWins && isGameActive) return true;
        else return false;
    }

    public void onClick(View view){
        ImageView button = (ImageView) view;
        if(isGameActive) {
            int tappedCounterID = Integer.parseInt(button.getTag().toString()) - 1;
            gameState[tappedCounterID] = turn;

            button.setScaleX(0.1f);
            button.setScaleY(0.1f);
            if (turn == BLUE_TURN) {
                button.setImageResource(R.drawable.blue_round_button);
                turn = RED_TURN;
            } else {
                button.setImageResource(R.drawable.red_round_button);
                turn = BLUE_TURN;
            }
            button.setEnabled(false);
            button.animate().scaleX(1).scaleY(1).setDuration(500);

            for (int[] position : winningPosition) {
                if (gameState[position[0]] == BLUE_TURN && gameState[position[1]] == BLUE_TURN && gameState[position[2]] == BLUE_TURN) {
                    blueWins = true;
                }
                if (gameState[position[0]] == RED_TURN && gameState[position[1]] == RED_TURN && gameState[position[2]] == RED_TURN) {
                    redWins = true;
                }
            }

            if (redWins) {
                resultTextView.setText("RED WINS");
                isGameActive = false;
                resultTextView.setTextColor(Color.RED);
                resultTextView.setVisibility(View.VISIBLE);
                restartButton.setVisibility(View.VISIBLE);

            }
            if (blueWins) {
                resultTextView.setText("BLUE WINS");
                isGameActive = false;
                resultTextView.setTextColor(Color.BLUE);
                resultTextView.setVisibility(View.VISIBLE);
                restartButton.setVisibility(View.VISIBLE);
            }
            if(isGameDrawn()){
                resultTextView.setText("DRAW !!");
                isGameActive = false;
                resultTextView.setTextColor(Color.GREEN);
                resultTextView.setVisibility(View.VISIBLE);
                restartButton.setVisibility(View.VISIBLE);
            }
        }

    }

    public void checkGame(int viewID){

    }

    public void restartGame(View view){
        resultTextView.setVisibility(View.INVISIBLE);
        restartButton.setVisibility(View.INVISIBLE);

        for(int i=0; i<gridLayout.getChildCount(); i++){
            ImageView child = (ImageView) gridLayout.getChildAt(i);
            child.setImageDrawable(null);
            child.setEnabled(true);
            isGameActive = true;
            redWins = false;
            blueWins = false;
            for(int j=0; j<gameState.length; j++){
                gameState[j] = 2;
            }
        }
    }

    /*
    public void onClick(View view){
        ImageView imageView = (ImageView) findViewById(view.getId());
        if(turn == BLUE_TURN) {
            imageView.setImageResource(R.drawable.blue_round_button);
            turn = RED_TURN;
            checkGame(view.getId());
        }else{
            imageView.setImageResource(R.drawable.red_round_button);
            turn = BLUE_TURN;
            checkGame(view.getId());
        }
    }
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        restartButton = (Button) findViewById(R.id.restartButton);
    }
}
