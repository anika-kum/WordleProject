package edu.willamette.cs1.wordle;
import java.util.Arrays;
import java.util.Date; //for some animation!!
//import edu.willamette.cs1.wordle.WordleDictionary;
//import williamette.WordleDictionary.*;

/*
 * File: Wordle.java
 * -----------------
 * This module is the starter file for the Wordle assignment.
 * BE SURE TO UPDATE THIS COMMENT WHEN YOU COMPLETE THE CODE.
*/


public class Wordle{
	/* Private instance variables */
    private WordleGWindow gw;
    private int curRow = 0;
	private String secretWord =  //"audio";// <--- good test word.
			WordleDictionary.FIVE_LETTER_WORDS[(int) (Math.random() * WordleDictionary.FIVE_LETTER_WORDS.length)];
	//The word that the user is trying to guess
	
    public void run() {
        gw = new WordleGWindow();
        gw.addEnterListener((s) -> enterAction(s));
    }

    /*
     * The enterAction method is called when the user hits the RETURN key or clicks the ENTER button,
     * passing in the string of characters on the current row.
     */
    public void enterAction(String s) {
    	boolean gameOver = false;
    	s = s.toLowerCase(); //the string passed in is all upper case, but dictionary is lower case.
    	boolean valid = true; //only used for the for loop, check to see if word is in dictionary.
    	String adjustableSecret = secretWord; //you'll see what this is for, to adjust the character of the found index
    	if (gameOver == false) { //ONLY DO ALL OF THESE THINGS IF THE GAME ISN'T OVER.
    		for (int i=0; i<WordleDictionary.FIVE_LETTER_WORDS.length; i++) {
    			if (WordleDictionary.FIVE_LETTER_WORDS[i].equals(s)) {
    				valid = true;
    				break;
    			}
    			else {
    				valid = false;
    			}
    		}
    		if (valid == true){ // if the word is valid, do all of this stuff!
        	
    			gw.showMessage("Nice try.");
    			
    			if (s.equals(secretWord)) {//if they got the word
    				/* the following was the beginning of some extension that I was trying to do. Unfinished, but regular Wordle still works!
    				try{
				        // Delay for 1 second
    					for (int i=0; i<WordleGWindow.N_COLS; i++) {
    						gw.setSquareColor(gw.getCurrentRow(), i, WordleGWindow.RAINBOW_COLORS[i]);
    						//SOURCE: https://javahungry.blogspot.com/2020/10/java-delay.html & https://stackoverflow.com/questions/8586137/java-delay-wait
    						Thread.sleep(1000);
    					}
    				}
    				catch(InterruptedException ex){
				          
				     }
				     */
    				gw.showMessage("You got it!");
    				gameOver = true;
    				adjustableSecret = "!!!!!";
    				for (int i=0; i<WordleGWindow.N_COLS; i++) {
    					gw.setSquareColor(gw.getCurrentRow(), i, WordleGWindow.CORRECT_COLOR);
    				}
    			}
    			else { //if they didn't get the word
    				boolean []done = new boolean[WordleGWindow.N_COLS]; // keeps track of whether a character's type is determined.
    				if (gw.getCurrentRow() <= WordleGWindow.N_ROWS - 1) { //if we aren't out of turns yet! (well, not out of turns, per se.
    					// more if we still want to color the letters that are inputted.)
    					for (int i=0; i<WordleGWindow.N_COLS; i++) {
    						if (s.charAt(i) == secretWord.charAt(i)) {
    							gw.setSquareColor(gw.getCurrentRow(), i, WordleGWindow.CORRECT_COLOR);
    							gw.setKeyColor(s.substring(i, i+1).toUpperCase(), WordleGWindow.CORRECT_COLOR); //set the key to green;
    							done[i] = true; //the character is at the right spot.
    							adjustableSecret = adjustableSecret.substring(0, i) + "!" + adjustableSecret.substring(i+1); //making sure that the same index
    							//won't be confused with later.
    						}
    					}
    					for (int i=0; i<WordleGWindow.N_COLS; i++) {
    						if (done[i] == false) { //if the character's spot is not already determined
    							if (adjustableSecret.indexOf(s.charAt(i)) != -1) { //later in the second to last comment is this!
    								adjustableSecret = adjustableSecret.substring(0, adjustableSecret.indexOf(s.charAt(i))) 
            										+ "!" + adjustableSecret.substring(adjustableSecret.indexOf(s.charAt(i))+1);
    								done[i] = true;
    								gw.setSquareColor(gw.getCurrentRow(), i, WordleGWindow.PRESENT_COLOR);
    								if (gw.getKeyColor(s.substring(i, i+1).toUpperCase()) != WordleGWindow.CORRECT_COLOR) {
    									gw.setKeyColor(s.substring(i, i+1).toUpperCase(), WordleGWindow.PRESENT_COLOR);
    								}
    							}
    							else {
    								gw.setSquareColor(gw.getCurrentRow(), i, WordleGWindow.MISSING_COLOR);
    								if (gw.getKeyColor(s.substring(i, i+1).toUpperCase()) != WordleGWindow.CORRECT_COLOR ||
            							gw.getKeyColor(s.substring(i, i+1).toUpperCase()) != WordleGWindow.PRESENT_COLOR) {
    									gw.setKeyColor(s.substring(i, i+1).toUpperCase(), WordleGWindow.MISSING_COLOR);
    								}
    							}
    						}
    					}
    					if (gw.getCurrentRow() == WordleGWindow.N_ROWS - 1) { //this is if we're out of turns, and we know that the word isn't correct
    						gw.showMessage("Game over! The correct word was: " + secretWord.toUpperCase());
    						gameOver = true;
    					}
    					
    				} //end if we aren't out of turns yet.
    			} //end if they didn't get the word (else).
    			//AT THE END OF THIS ENTIRE IF (if they got the word)... WE WANT TO CHANGE THE CURRENT ROW WE ARE AT AND MOVE ON TO THE NEXT ROW...
    			// BUT ONLY IF THE GAME OVER IS STILL FALSE
    			if (gameOver == false) { gw.setCurrentRow(gw.getCurrentRow() + 1); }
    		} //end if the word is valid.
    		else { //if the word is not valid, no need to do anything.
    			gw.showMessage("Not in word list.");
    		}
    	} //end if game is over or not.
    } //end method
    
    
    //Just trying to see if I can get milestone 1 to work (below:)
    /*
    public void addWord(String s, int row) {
    	for (int i=0; i < WordleGWindow.N_COLS; i++) {
    		gw.setSquareLetter(row-1, i, s.substring(i, i+1));
    	}
    }
    */
    
/* Startup code */

    public static void main(String[] args) {
    	Wordle wordle = new Wordle();
    	wordle.run();
    	//the below is also a test for milestone #1!
    	//wordle.addWord(WordleDictionary.FIVE_LETTER_WORDS[(int) (Math.random() * WordleDictionary.FIVE_LETTER_WORDS.length)], 1);
    }

}
