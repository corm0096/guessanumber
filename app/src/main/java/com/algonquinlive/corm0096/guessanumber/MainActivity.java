/*********************************************
 * Hilo, a guessing game.
 * Code by Daniel Cormier (corm0096)
 *
 * VARIABLES:
 * Button submitButton (for 'submit guess'), short click to clickGuess()
 * Button resetButton (for 'reset'), short click to clickReset(),
 *      long click to Toast message, then clickReset().
 * EditText theGuessBox contains the user's guesses.
 *
 * int theNumber: randomly-selected number from 1-1000 the user is trying to guess
 * int numGuess: How many guesses the user has taken.
 * int playerGuess: the guess the user has entered.
 *
 * string message: contains messages to be passed to Toast system for user messaging.
 *
 * METHODS:
 * void gameStart(void): Resets button functionality, guess count and selects a
 *      new number for the user to guess.
 * View.OnClickListener DCClickListener: Personal click manager code to manage user input
 *      in a cleaner manner.
 * View.OnLongClickListener DCLongClickListener: Personal long click manager code.
 * void clickGuess(void): Manages the user's input, returns messages regarding game state
 *      to the user, and can lock out the "guess" button.
 * void clickReset(void): Manages short clicks on the reset button, which calls gameStart().
 * boolean onCreateOptionsMenu(Menu): Inflates the "about" functionality.
 * boolean onOptionsItemSelected(MenuItem): Manages clicks to the action bar and intercepts
 *      clicks to the "about" button.
 *
 */

package com.algonquinlive.corm0096.guessanumber;

import android.app.DialogFragment;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity
{
    Button submitButton, resetButton;
    EditText theGuessBox;
    int theNumber, numGuess, playerGuess;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(DCClickListener);
        resetButton = (Button) findViewById(R.id.resetButton);
        resetButton.setOnClickListener(DCClickListener);
        resetButton.setOnLongClickListener(DCLongClickListener);
        theGuessBox = (EditText) findViewById(R.id.theGuess);
        theNumber = 0;
        numGuess = 0;
        playerGuess=0;

        gameStart();
    }

    protected View.OnClickListener DCClickListener = new View.OnClickListener()
    {
        public void onClick(View v)
        {
            Log.i("BUTTON CLICK","A button was clicked.");
            if (v.getId()==R.id.submitButton)
            {
                clickGuess();
                Log.i("BUTTON CLICK", "SUBMIT button");
            }
            else if (v.getId()==R.id.resetButton)
            {
                Log.i("BUTTON CLICK", "Reset button");
                clickReset();
            }
        }
    };

    protected View.OnLongClickListener DCLongClickListener = new View.OnLongClickListener()
    {
        public boolean onLongClick(View v)
        {
            if (v.getId() == R.id.resetButton)
            {
                Log.i("LONG CLICK", "reset button");
                Toast.makeText(getApplicationContext(), "The number was " + theNumber, Toast.LENGTH_LONG).show();
                clickReset();
            }
            return true;
        }
    };


    public void gameStart()
    {
        submitButton.setEnabled(true);
        theGuessBox.setText("");
        theNumber = (int) (Math.random() * 1000) + 1;
        Log.i("The Number","Num "+theNumber);
        numGuess=0;
        return;
    }

    public void clickGuess()
    {
        String message, sanitationString;

        sanitationString= theGuessBox.getText().toString();
        if (sanitationString.isEmpty())
        {
            theGuessBox.setError("Enter a guess.");
            theGuessBox.requestFocus();
            return;
        }
        playerGuess=(int) Integer.parseInt(sanitationString);

        if(playerGuess<1||playerGuess>1000)
        {
            theGuessBox.setError("Guesses must be between 1 and 1000.");
            theGuessBox.requestFocus();
            return;

        }
        numGuess++;
        if(playerGuess<theNumber)
        {
            message="Your guess is too low.";
        }
        else if(playerGuess>theNumber)
        {
            message="Your guess is too high.";
        }
        else
        {
            message="You've got it! ";
            if(numGuess<6)
            {
                message+="Superior win!";
            }
            else
            {
                message+="Excellent win!";
            }
            message+="\nIt took you "+numGuess+" guesses.";
            submitButton.setEnabled(false);
        }
        if (numGuess==10&&playerGuess!=theNumber)
        {
            message+="\nYou have exhausted your number of guesses.\nPlease reset.";
            submitButton.setEnabled(false);
        }

        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }

    public void clickReset()
    {
        gameStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id== R.id.action_about)
        {
            DialogFragment newFragment = new AboutDialogFragment();
            newFragment.show(getFragmentManager(), "About Dialog");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}