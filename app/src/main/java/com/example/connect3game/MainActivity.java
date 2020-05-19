package com.example.connect3game;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.GridLayout;

public class MainActivity extends AppCompatActivity {
    //1 = player red, 2 = player 2(yellow), 0 = none
    int active_player=1;
    int[][] board_state = new int [3][3];
    boolean gameActive = true;

    public void dropIn(View view){
        if(gameActive) {
            //Button press check
            Log.i("Info", "pressed");

            //finding box1 image view
            ImageView img = (ImageView) view;

            //getting position of view
            String val = img.getTag().toString();
            String[] tag = val.split(" ", 2);
            int posX = Integer.parseInt(tag[0]);
            int posY = Integer.parseInt(tag[1]);

            //select tile
            if (active_player == 1) {
                img.setImageResource(R.drawable.red);
                board_state[posX][posY] = 1;
                active_player = 2;
            }
            else if (active_player == 2) {
                img.setImageResource(R.drawable.yellow);
                board_state[posX][posY] = 2;
                active_player = 1;
            }

            //animation
            img.setY(-500);
            img.animate().translationY(0).rotation(360).setDuration(1000);
            //Disable tile overwrite
            img.setEnabled(false);

            //check state of board
            int k = 0, l = 2;
            int count_crossL_r = 0, count_crossL_y = 0, count_crossR_r = 0, count_crossR_y = 0;
            for (int i = 0; i < board_state.length; i++) {
                int count_horizon_r = 0, count_horizon_y = 0;
                int count_ver_r = 0, count_ver_y = 0;
                for (int j = 0; j < board_state[0].length; j++) {
                    //Horizontal match counting
                    if (board_state[i][j] == 1)
                        count_horizon_r++;
                    else if (board_state[i][j] == 2)
                        count_horizon_y++;

                    //Vertical match
                    if (board_state[j][i] == 1)
                        count_ver_r++;
                    else if (board_state[j][i] == 2)
                        count_ver_y++;
                }

                //Cross match starting from left
                if (board_state[i][k] == 1)
                    count_crossL_r++;
                else if (board_state[i][k] == 2)
                    count_crossL_y++;
                k++;

                //Cross match starting from right
                if (board_state[i][l] == 1)
                    count_crossR_r++;
                else if (board_state[i][l] == 2)
                    count_crossR_y++;
                l--;

                //Display winner text
                TextView win_text = (TextView) findViewById(R.id.win_text);
                Button play_again = (Button) findViewById(R.id.play_again);
                String win_red = "Red has won";
                String win_yellow = "Yellow has won";

                //match conditions
                if (count_horizon_r == 3 || count_ver_r == 3 || count_crossL_r == 3 || count_crossR_r == 3) {
                    win_text.setText(win_red);
                    win_text.setVisibility(View.VISIBLE);
                    play_again.setVisibility(View.VISIBLE);
                    gameActive = false;
                }
                else if (count_horizon_y == 3 || count_ver_y == 3 || count_crossL_y == 3 || count_crossR_y == 3) {
                    win_text.setText(win_yellow);
                    win_text.setVisibility(View.VISIBLE);
                    play_again.setVisibility(View.VISIBLE);
                    gameActive = false;
                }
            }
        }
    }

    public void play_again(View view){
        //Reset textview and button
        TextView win_text = (TextView) findViewById(R.id.win_text);
        Button play_again = (Button) findViewById(R.id.play_again);
        win_text.setVisibility(View.INVISIBLE);
        play_again.setVisibility(View.INVISIBLE);

        //reset images
        androidx.gridlayout.widget.GridLayout grid=(androidx.gridlayout.widget.GridLayout)findViewById(R.id.gridLayout);
        for (int i = 0; i< grid.getChildCount(); i++) {
            ImageView img = (ImageView) grid.getChildAt(i);
            img.setImageDrawable(null);
            View vi = (View) grid.getChildAt(i);
            vi.setEnabled(true);

        }

        //Reset board state
        for(int i=0; i<board_state.length;i++) {
            for (int j = 0; j < board_state[0].length; j++) {
                board_state[i][j] = 0;
            }
        }
        gameActive = true;
        active_player=1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
