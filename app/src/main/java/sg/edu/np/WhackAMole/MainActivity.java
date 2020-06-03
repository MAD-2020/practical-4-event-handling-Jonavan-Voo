package sg.edu.np.WhackAMole;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ArrayList<Button> buttonList = new ArrayList<>();
    private Button Button1;
    private Button Button2;
    private Button Button3;
    private TextView Score;
    private static final String TAG = "Whack-A-Mole 1.0";
    /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 2.
        - The function doCheck() takes in button selected and computes a hit or miss and adjust the score accordingly.
        - The function doCheck() also decides if the user qualifies for the advance level and triggers for a dialog box to ask for user to decide.
        - The function nextLevelQuery() builds the dialog box and shows. It also triggers the nextLevel() if user selects Yes or return to normal state if user select No.
        - The function nextLevel() launches the new advanced page.
        - Feel free to modify the function to suit your program.
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button1 = (Button) findViewById(R.id.Button1);
        Button2 = (Button) findViewById(R.id.Button2);
        Button3 = (Button) findViewById(R.id.Button3);
        buttonList.add(Button1);
        buttonList.add(Button2);
        buttonList.add(Button3);
        Score = (TextView) findViewById(R.id.Score);
        Log.v(TAG, "Finished Pre-Initialisation!");


    }
    @Override
    protected void onStart(){
        super.onStart();
        setNewMole();

        Button1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                Log.v(TAG, "Button Left Clicked!");
                doCheck(Button1);
                setNewMole();
            }
        });

        Button2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                Log.v(TAG, "Button Middle Clicked!");
                doCheck(Button2);
                setNewMole();
            }
        });

        Button3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                Log.v(TAG, "Button Right Clicked!");
                doCheck(Button3);
                setNewMole();
            }
        });
        Log.v(TAG, "Starting GUI!");
    }
    public void addScore() {
        int currentScore = Integer.parseInt(Score.getText().toString());
        Log.v(TAG, "Hit, score added!");
        currentScore = currentScore + 1;
        Score.setText(Integer.toString(currentScore));
    }
    public void deductScore() {
        int currentScore = Integer.parseInt(Score.getText().toString());
        Log.v(TAG, "Missed, score deducted!");
        if (currentScore > 0) {
            currentScore = currentScore - 1;
        }
        Score.setText(Integer.toString(currentScore));
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.v(TAG, "Paused Whack-A-Mole!");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.v(TAG, "Stopped Whack-A-Mole!");
        finish();
    }

    private void doCheck(Button checkButton) {
        /* Checks for hit or miss and if user qualify for advanced page.
            Triggers nextLevelQuery().
         */
        if (checkButton.getText() == "*") {
            addScore();
        } else {
            deductScore();
        }
        if (Integer.parseInt(Score.getText().toString())%10 == 0 && Integer.parseInt(Score.getText().toString()) != 0) {
            nextLevelQuery();
        }
    }

    private void nextLevelQuery(){
        /*
        Builds dialog box here.
        Log.v(TAG, "User accepts!");
        Log.v(TAG, "User decline!");
        Log.v(TAG, "Advance option given to user!");
        belongs here*/
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Warning! Insane Whack-A-Mole incoming!");
        builder.setMessage("Would you like to advance to advanced mode?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                Log.v(TAG, "User accepts!");
                nextLevel();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                Log.v(TAG, "User decline!");
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
        Log.v(TAG, "Advance option given to user!");
    }

    private void nextLevel(){
        /* Launch advanced page */
        Intent advanced = new Intent(MainActivity.this,Main2Activity.class);
        advanced.putExtra("Score",Score.getText().toString());
        startActivity(advanced);
    }

    private void setNewMole() {
        Random ran = new Random();
        int randomLocation = ran.nextInt(3);
        for (int i = 0;i<buttonList.size();i++){
            buttonList.get(i).setText("O");
        }
        buttonList.get(randomLocation).setText("*");
    }
}