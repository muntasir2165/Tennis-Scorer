package com.example.android.tennisscorer;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //An array of all possible Tennis scores
    //Note:
    //50 is a stand in for my ad / win (not to be displayed on the screen)
    //60 is a stand in for win after deuce (not to be displayed on the screen)
    int[] tennisScores = new int[]{0, 15, 30, 40, 50, 60};

    //A hashmap of all possible combinations of tennis scores and the corresponding commentary
    HashMap<int[], String> scoresComments = new HashMap<int[], String>();

    //Player 1 score metrics
    int player1score = 0;
    int player1gamesWon = 0;
    int player1setsWon = 0;

    //Player 2 score metrics
    int player2score = 0;
    int player2gamesWon = 0;
    int player2setsWon = 0;

    //Tennis scores to display
    int player1TennisScore;
    int player2TennisScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //scoresComments hashman initializtion with all possible score combinations and the corresponding commentary
        scoresComments.put(new int[]{0, 0}, "Love All");
        scoresComments.put(new int[]{0, 15}, "Love-15");
        scoresComments.put(new int[]{0, 30}, "Love-30");
        scoresComments.put(new int[]{0, 40}, "Love-40; Game Point for Player 2");
        scoresComments.put(new int[]{0, 50}, "Player 2 Wins the Game!");
        scoresComments.put(new int[]{15, 0}, "Love-15");
        scoresComments.put(new int[]{15, 0}, "Love-15");
        scoresComments.put(new int[]{15, 15}, "15-All");
        scoresComments.put(new int[]{15, 30}, "");
        scoresComments.put(new int[]{15, 40}, "Game Point for Player 2");
        scoresComments.put(new int[]{15, 50}, "Player 2 Wins the Game!");
        scoresComments.put(new int[]{30, 0}, "Love-30");
        scoresComments.put(new int[]{30, 15}, "");
        scoresComments.put(new int[]{30, 30}, "30-All");
        scoresComments.put(new int[]{30, 40}, "Game Point for Player 2");
        scoresComments.put(new int[]{30, 50}, "Player 2 Wins the Game!");
        scoresComments.put(new int[]{40, 0}, "Love-40; Game Point for Player 1");
        scoresComments.put(new int[]{40, 15}, "Game Point for Player 1");
        scoresComments.put(new int[]{40, 30}, "Game Point for Player 1");
        scoresComments.put(new int[]{40, 40}, "Duece!");
        scoresComments.put(new int[]{40, 50}, "Player 2's My Advantage");
        scoresComments.put(new int[]{40, 60}, "Player 2 Wins the Game!");
        scoresComments.put(new int[]{50, 0}, "Player 1 Wins the Game!");
        scoresComments.put(new int[]{50, 15}, "Player 1 Wins the Game!");
        scoresComments.put(new int[]{50, 30}, "Player 1 Wins the Game!");
        scoresComments.put(new int[]{50, 40}, "Player 1's My Advantage");
        scoresComments.put(new int[]{50, 50}, "Deuce again!");
        scoresComments.put(new int[]{60, 40}, "Player 1 Wins the Game!");
    }

    /**
     * Increase the score for Player 1.
     */
    public void player1pointScore(View v) {
        player1score += 1;
        //Code 1 implies that player 1 scored
        computeAndDisplayInformation(1);
    }

    /**
     * Increase the score for Player 2.
     */
    public void player2pointScore(View v) {
        player2score += 1;
        //Code 2 implies that player 2 scored
        computeAndDisplayInformation(2);
    }

    /**
     * Displays the score metrics for Player 1.
     */
    public void displayForPlayer1(String score, int gamesWon, int setsWon) {
        TextView scoreView = (TextView) findViewById(R.id.player_1_score);
        scoreView.setText(String.valueOf(score));
        TextView gameView = (TextView) findViewById(R.id.player_1_game);
        gameView.setText(String.valueOf(gamesWon));
        TextView setView = (TextView) findViewById(R.id.player_1_set);
        setView.setText(String.valueOf(setsWon));
    }

    /**
     * Displays the score metrics for Player 2.
     */
    public void displayForPlayer2(String score, int gamesWon, int setsWon) {
        TextView scoreView = (TextView) findViewById(R.id.player_2_score);
        scoreView.setText(String.valueOf(score));
        TextView gameView = (TextView) findViewById(R.id.player_2_game);
        gameView.setText(String.valueOf(gamesWon));
        TextView setView = (TextView) findViewById(R.id.player_2_set);
        setView.setText(String.valueOf(setsWon));
    }

    /**
     * Displays comments in the commentary box.
     */
    public void displayCommentary(String comments) {
        TextView commentaryView = (TextView) findViewById(R.id.commentary);
        commentaryView.setText(String.valueOf(comments));
    }

    /**
     * Restart the game by resetting all scoring metrics for both players back to 0.
     */
    public void restart(View v) {
        //Reset player 1 score metrics
        player1score = 0;
        player1gamesWon = 0;
        player1setsWon = 0;

        //Reset player 2 score metrics
        player2score = 0;
        player2gamesWon = 0;
        player2setsWon = 0;

        //Code 0 implies restart / reset of all (or some) scoring metrics
        computeAndDisplayInformation(0);
    }

    /**
     * Reset the scoring metrics for both teams back to 0 after a set win.
     */
    public void resetScoreAfterSetWin() {
        //Reset player 1 score metrics
        player1score = 0;
        player1gamesWon = 0;

        //Reset player 2 score metrics
        player2score = 0;
        player2gamesWon = 0;

        //Code 0 implies restart / reset of some (or all) scoring metrics
        computeAndDisplayInformation(0);
    }

    /**
     * Convert internal scores for player 1 and 2 into tennis scores to display on the screen.
     */
    public void computeTennisScore() {
        player1TennisScore = tennisScores[player1score % 6];
        player2TennisScore = tennisScores[player2score % 6];
    }

    /**
     * Compute the scores, feedback, game wins and sets wins for players 1 and 2 and display information on the screen
     * <p>
     * code: An integer in the range 0-2 that indicates the following events:
     * 0: Restart / reset of some (or all) scoring metrics
     * 1: Player 1 scores
     * 2: Player 2 scores
     */
    public void computeAndDisplayInformation(int code) {
        computeTennisScore();
        String comments = "";
        for (Map.Entry m : scoresComments.entrySet()) {
            int[] scores = (int[]) m.getKey();
            comments = (String) m.getValue();
            if (player1TennisScore == scores[0] && player2TennisScore == scores[1]) {
                if ((scores[1] == 50 && ((scores[0] == 0) || (scores[0] == 15) || (scores[0] == 30))) ||
                        (scores[0] == 40 && scores[1] == 60)) {
                    //Player 2 wins
                    player2gamesWon += 1;
                    player1score = 0;
                    player2score = 0;
                    computeTennisScore();
                    if (player2gamesWon - player1gamesWon >= 2) {
                        player2setsWon += 1;
                        comments += " And the Set!";
                        displayCommentary(comments);
                        //Display the game win information on the screen for 2 seconds
                        SystemClock.sleep(2000);
                        resetScoreAfterSetWin();
                    }
                    if (player2setsWon - player1setsWon >= 2) {
                        comments = "Player 2 Wins the Entire Game!!!";
                        displayCommentary(comments);
                        //Display the set win information on the screen for 2 seconds
                        SystemClock.sleep(2000);
                        //Programmatically press the restart button to restart the game
                        Button btn = (Button) findViewById(R.id.restart_button);
                        btn.performClick();
                    }
                } else if ((scores[0] == 50 && ((scores[1] == 0) || (scores[1] == 15) || (scores[1] == 30))) ||
                        (scores[0] == 60 && scores[1] == 40)) {
                    //Player 1 wins
                    player1gamesWon += 1;
                    player1score = 0;
                    player2score = 0;
                    computeTennisScore();
                    if (player1gamesWon - player2gamesWon >= 2) {
                        player1setsWon += 1;
                        comments += " And the Set!";
                        displayCommentary(comments);
                        //Display the game win information on the screen for 2 seconds
                        SystemClock.sleep(2000);
                        resetScoreAfterSetWin();
                    }
                    if (player1setsWon - player2setsWon >= 2) {
                        comments = "Player 1 Wins the Entire Game!!!";
                        displayCommentary(comments);
                        //Display the set win information on the screen for 2 seconds
                        SystemClock.sleep(2000);
                        //Programmatically press the restart button to restart the game
                        Button btn = (Button) findViewById(R.id.restart_button);
                        btn.performClick();
                    }
                } else if (scores[0] == 50 && scores[1] == 50) {
                    // Deuce
                    player1score = 3;
                    player2score = 3;
                    computeTennisScore();
                }
                break;
            }
        }
        if (code == 1) {
            comments = "Player 1 scores! " + comments;
        } else if (code == 2) {
            comments = "Player 2 scores! " + comments;
        }

        //If the score of any player is 50, then display 'My ad' for that player's score
        displayForPlayer1((player1TennisScore == 50) ? "My ad" : String.valueOf(player1TennisScore), player1gamesWon, player1setsWon);
        displayForPlayer2((player2TennisScore == 50) ? "My ad" : String.valueOf(player2TennisScore), player2gamesWon, player2setsWon);
        displayCommentary(comments);
    }
}
