import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Defines the methods needed to play a word search game.
 *
 * @author Dillon Thompson (djt0026@auburn.edu)
 * @version 2018-03-22
 * 
 */

public class TheWords implements WordSearchGame {
    private TreeSet<String> lexicon;
    private String[][] board;
    private boolean[][] visited;
    private int length;
    private int minLength;
    private boolean loaded;
    private int rows;
    private int cols;
    private int order;
    int score;


    public TheWords() {
        lexicon = new TreeSet<String>();
        score = 0;
     }
     /**
    * Loads the lexicon into a data structure for later use. 
    * 
    * @param fileName A string containing the name of the file to be opened.
    * @throws IllegalArgumentException if fileName is null
    * @throws IllegalArgumentException if fileName cannot be opened.
    */
   public void loadLexicon(String fileName) {
       //check if file is null
       if (fileName == null) {
           throw new IllegalArgumentException("file name cannot be null");
       }

       //create file, bufferedreader, and scanner

       File lex = new File(fileName);
       BufferedReader br = new BufferedReader(lex);
       Scanner sc = new Scanner(br);

       //creates a string for words to be stored and added to tree
       String word;

       //read file into treeset
       try {
           //while word is not null add the word into the lexicon
           while ((word = br.readLine()) != null) {
               lexicon.add(word.toLowerCase());
           }
       }
       //if file doesn't open
       catch(Exception err) {
           throw new IllegalArgumentException("file could not be opened");
       }
       loaded = true;
   }
   
   /**
    * Stores the incoming array of Strings in a data structure that will make
    * it convenient to find words.
    * 
    * @param letterArray This array of length N^2 stores the contents of the
    *     game board in row-major order. Thus, index 0 stores the contents of board
    *     position (0,0) and index length-1 stores the contents of board position
    *     (N-1,N-1). Note that the board must be square and that the strings inside
    *     may be longer than one character.
    * @throws IllegalArgumentException if letterArray is null, or is  not
    *     square.
    */
   public void setBoard(String[] letterArray) {
       //variable for the square root of the length
       double rt = Math.sqrt(letterArray.length);
       
       //check if it is null or a square
       if (letterArray == null && rt % 1 != 0) {
           throw new IllegalArgumentException("array is either null or not square");
       }

       //setting rows and cols for 2d array
       rows = (int) rt;
       cols = (int) rt;

       //setting board dimensions
       board = new String[rows][cols];
       visited = new boolean[rows][cols];

       //add letter array to the board
       int x = 0;
       int y = 0;

       for (int i = 0; i < letterArray.length - 1; i++) {
           //if x isn't at the last column then add it to that row
           while (x <= cols) {
               //check if x is at the last column. 
               //if it is, set it back to 0 and increment the row
               if (x == cols) {
                   x = 0;
                   y++;
               }
               //add the current letter to the current index of the board
               board[y][x] = letterArray[i];
               x++;
           }
           visited[y][x] = false;
       }
   }
   
   /**
    * Creates a String representation of the board, suitable for printing to
    *   standard out. Note that this method can always be called since
    *   implementing classes should have a default board.
    */
   public String getBoard() {
       
   }
   
   /**
    * Retrieves all valid words on the game board, according to the stated game
    * rules.
    * 
    * @param minimumWordLength The minimum allowed length (i.e., number of
    *     characters) for any word found on the board.
    * @return java.util.SortedSet which contains all the words of minimum length
    *     found on the game board and in the lexicon.
    * @throws IllegalArgumentException if minimumWordLength < 1
    * @throws IllegalStateException if loadLexicon has not been called.
    */
   public SortedSet<String> getAllValidWords(int minimumWordLength) {
       return minimumWordLength;
   }
   
  /**
   * Computes the cummulative score for the scorable words in the given set.
   * To be scorable, a word must (1) have at least the minimum number of characters,
   * (2) be in the lexicon, and (3) be on the board. Each scorable word is
   * awarded one point for the minimum number of characters, and one point for 
   * each character beyond the minimum number.
   *
   * @param words The set of words that are to be scored.
   * @param minimumWordLength The minimum number of characters required per word
   * @return the cummulative score of all scorable words in the set
   * @throws IllegalArgumentException if minimumWordLength < 1
   * @throws IllegalStateException if loadLexicon has not been called.
   */  
   public int getScoreForWords(SortedSet<String> words, int minimumWordLength) {
       return 0;
   }
   
   /**
    * Determines if the given word is in the lexicon.
    * 
    * @param wordToCheck The word to validate
    * @return true if wordToCheck appears in lexicon, false otherwise.
    * @throws IllegalArgumentException if wordToCheck is null.
    * @throws IllegalStateException if loadLexicon has not been called.
    */
   public boolean isValidWord(String wordToCheck) {
       return false;
   }
   
   /**
    * Determines if there is at least one word in the lexicon with the 
    * given prefix.
    * 
    * @param prefixToCheck The prefix to validate
    * @return true if prefixToCheck appears in lexicon, false otherwise.
    * @throws IllegalArgumentException if prefixToCheck is null.
    * @throws IllegalStateException if loadLexicon has not been called.
    */
   public boolean isValidPrefix(String prefixToCheck) {
       return false;
   }
      
   /**
    * Determines if the given word is in on the game board. If so, it returns
    * the path that makes up the word.
    * @param wordToCheck The word to validate
    * @return java.util.List containing java.lang.Integer objects with  the path
    *     that makes up the word on the game board. If word is not on the game
    *     board, return an empty list. Positions on the board are numbered from zero
    *     top to bottom, left to right (i.e., in row-major order). Thus, on an NxN
    *     board, the upper left position is numbered 0 and the lower right position
    *     is numbered N^2 - 1.
    * @throws IllegalArgumentException if wordToCheck is null.
    * @throws IllegalStateException if loadLexicon has not been called.
    */
   public List<Integer> isOnBoard(String wordToCheck) {
       List copy = new List();
       return copy;
   }
}