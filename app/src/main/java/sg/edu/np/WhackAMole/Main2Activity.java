package sg.edu.np.WhackAMole;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class Main2Activity extends AppCompatActivity {
    private TextView Score;
    private static final String TAG = "Whack-A-Mole 2.0";
    /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 8.
        - The function doCheck() takes in button selected and computes a hit or miss and adjust the score accordingly.
        - The functions readTimer() and placeMoleTimer() are to inform the user X seconds before starting and loading new mole.
        - Feel free to modify the function to suit your program.
    */


    CountDownTimer get_ready;
    private void readyTimer(){
        /*  HINT:
            The "Get Ready" Timer.
            Log.v(TAG, "Ready CountDown!" + millisUntilFinished/ 1000);
            Toast message -"Get Ready In X seconds"
            Log.v(TAG, "Ready CountDown Complete!");
            Toast message - "GO!"
            belongs here.
            This timer countdown from 10 seconds to 0 seconds and stops after "GO!" is shown.
         */
        get_ready = new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                final Toast toast = Toast.makeText(Main2Activity.this,"Get Ready in "+(millisUntilFinished/1000+1)+" seconds",Toast.LENGTH_SHORT);
                toast.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                    }
                }, 1000);
                Log.v(TAG, "Ready CountDown!" + (millisUntilFinished/ 1000 + 1));
            }

            @Override
            public void onFinish() {
                Toast.makeText(Main2Activity.this,"GO!",Toast.LENGTH_SHORT).show();
                placeMoleTimer();
                Log.v(TAG, "Ready CountDown Complete!");
            }
        };
        get_ready.start();
    }
    CountDownTimer moletimer;
    private void placeMoleTimer(){
        /* HINT:
           Creates new mole location each second.
           Log.v(TAG, "New Mole Location!");
           setNewMole();
           belongs here.
           This is an infinite countdown timer.
         */
        moletimer = new CountDownTimer(1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                setNewMole();
                Log.v(TAG, "New Mole Location!");
            }

            @Override
            public void onFinish() {
                moletimer.start();
            }
        };
        moletimer.start();
    }

    private static final int[] BUTTON_IDS = {
            /* HINT:
                Stores the 9 buttons IDs here for those who wishes to use array to create all 9 buttons.
                You may use if you wish to change or remove to suit your codes.*/
            R.id.button1,
            R.id.button2,
            R.id.button3,
            R.id.button4,
            R.id.button5,
            R.id.button6,
            R.id.button7,
            R.id.button8,
            R.id.button9
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*Hint:
            This starts the countdown timers one at a time and prepares the user.
            This also prepares the existing score brought over.
            It also prepares the button listeners to each button.
            You may wish to use the for loop to populate all 9 buttons with listeners.
         */

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent receivingEnd = getIntent();
        String oldScore = receivingEnd.getStringExtra("Score");
        Score = (TextView) findViewById(R.id.textView);
        Score.setText(oldScore);
        readyTimer();
        Log.v(TAG, "Current User Score: " + String.valueOf(Score));

        for(final int id : BUTTON_IDS){
            /*  HINT:
            This creates a for loop to populate all 9 buttons with listeners.
            You may use if you wish to remove or change to suit your codes.
            */
            Button button = findViewById(id);
            final Button clickButton = button;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    doCheck(clickButton);
                }
            });
        }
    }
    @Override
    protected void onStart(){
        super.onStart();
    }
    private void doCheck(Button checkButton)
    {
        /* Hint:
            Checks for hit or miss
            Log.v(TAG, "Hit, score added!");
            Log.v(TAG, "Missed, point deducted!");
            belongs here.
        */
        if (checkButton.getText() == "*") {
            int currentScore = Integer.parseInt(Score.getText().toString());
            Log.v(TAG, "Hit, score added!");
            moletimer.cancel();
            moletimer.start();
            currentScore = currentScore + 1;
            Score.setText(Integer.toString(currentScore));
        } else {
            int currentScore = Integer.parseInt(Score.getText().toString());
            Log.v(TAG, "Missed, score deducted!");
            moletimer.cancel();
            moletimer.start();
            if (currentScore > 0) {
                currentScore = currentScore - 1;
            }
            Score.setText(Integer.toString(currentScore));
        }
    }

    private void setNewMole() {
        Random ran = new Random();
        int randomLocation = ran.nextInt(9);
        for(final int id : BUTTON_IDS){
            Button button = findViewById(id);
            button.setText("O");
        }
        Button mole = findViewById(BUTTON_IDS[randomLocation]);
        mole.setText("*");
    }
}

