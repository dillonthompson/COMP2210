import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;






/**
 * Creates a word search game based on definitions from WordSearchGame.
 *
 * @author Dillon Thompson (djt0026@auburn.edu)
 * @version 2019-03-26
 * 
 */
 
public class TheWords implements WordSearchGame {
    private TreeSet<String> lexicon;
    private String[][] board;
    private boolean[][] visited;
    private List<Integer> thePath;
    private int length;
    private boolean loaded;
    private int score;
    private final int MAX_NEIGHBORS = 8;
    private String wordSoFar;
    private SortedSet<String> allValidWords;
    private ArrayList<Position> positionPath;
   
   /**
   * Constructor. Initializes default board.
   */
   public TheWords() {
      lexicon = null;
   }
       
    /**
    * Loads the lexicon into a data structure for later use. 
    * 
    * @param fileName A string containing the name of the file to be opened.
    * @throws IllegalArgumentException if fileName is null
    * @throws IllegalArgumentException if fileName cannot be opened.
    */
   public void loadLexicon(String fileName) {
      lexicon = new TreeSet<String>(); 
      if (fileName == null) {
          throw new IllegalArgumentException("file name cannot be null");
      }

    //creates a string for words to be stored and added to tree
    String word;

    //read file into treeset
    try {
        //create file, bufferedreader, and filereader
         File lex = new File(fileName);
         FileReader fr = new FileReader(lex);
         BufferedReader br = new BufferedReader(fr);

        //while word is not null add the word into the lexicon
        while ((word = br.readLine()) != null) {
            lexicon.add(word.toUpperCase());
        }
    }
    //if file doesn't open
    catch(FileNotFoundException err) {
        throw new IllegalArgumentException("could not find file");
    }
    //other errors
    catch(Exception err) {
        throw new IllegalArgumentException("something is wrong with your file");
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
       if (letterArray == null) {
           throw new IllegalArgumentException("array is either null or not square");
        }
        //variable for sqrt of length
        double rt = Math.sqrt(letterArray.length);
        if (rt % 1 != 0) {
            throw new IllegalArgumentException("array size must be square");
        }
        if (loaded == false) {
            throw new IllegalStateException("lexicon not loaded");
        }
    
        length = (int) rt;
        board = new String[length][length];
        visited = new boolean[length][length];
        int curLetter = 0;
        //set board
        for (int x = 0; x < length; x++) {
            for (int y = 0; y < length; y++) {
                board[x][y] = letterArray[curLetter].toUpperCase();
                curLetter++;
            }
        }
   }
   
   /**
    * Creates a String representation of the board, suitable for printing to
    *   standard out. Note that this method can always be called since
    *   implementing classes should have a default board.
    */
   public String getBoard() {
      String strBoard = "";
      return strBoard;
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
      //check if word is null
      if (wordToCheck == null) {
         throw new IllegalArgumentException("word cannot be null");
      }
      //check if loaded
      if (loaded == false) {
         throw new IllegalStateException("lexicon is not loaded");
      }

    //set the input string to uppercase
    wordToCheck = wordToCheck.toUpperCase();

    //look for the word.
    return lexicon.contains(wordToCheck);
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
      //check if prefixToCheck is null
      if (prefixToCheck == null) {
        throw new IllegalArgumentException("prefix cannot be null");
      }
      //check if loaded
      if (loaded == false) {
         throw new IllegalStateException("lexicon not loaded");
      }
      //variable to hold the lexicon ceiling
      String preFix = lexicon.ceiling(prefixToCheck);

      //check if equal
      if (preFix == prefixToCheck) {
         return true;
      }
      return false;
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
      //check if word length is valid
      if (minimumWordLength < 1) {
         throw new IllegalArgumentException("invalid word length");
      }
      //check if loaded
      if (loaded == false) {
         throw new IllegalStateException("lexicon not loaded");
      }
      //create new list and sets for positionPath and allValidWords
      positionPath = new ArrayList<Position>(); 
      allValidWords = new TreeSet<String>();
      wordSoFar = "";
      //look for words on board
      for (int i = 0; i < length; i++) {
         for (int j = 0; j < length; j ++) {
            wordSoFar = board[i][j];
            if (isValidWord(wordSoFar) && wordSoFar.length() >= minimumWordLength) {
               allValidWords.add(wordSoFar);
            }
            if (isValidPrefix(wordSoFar)) {
               Position p = new Position(i,j);
               positionPath.add(p);
               dfsAllValidWords(i, j, minimumWordLength); 
               positionPath.remove(p);
            }
         }
      }
      return allValidWords;
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
      if (minimumWordLength < 1) {
         throw new IllegalArgumentException();
      }
      if (loaded == false) {
         throw new IllegalStateException();
      }
      score = 0;
      Iterator<String> i = words.iterator();
      while (i.hasNext()) {
         String word = i.next();
         if (word.length() >= minimumWordLength && isValidWord(word)
             && !isOnBoard(word).isEmpty()) {
            score += (word.length() - minimumWordLength) + 1;
         }
      }
      return score;
   }

      
   /**
    * Determines if the given word is in on the game board. If so, it returns
    * the thePath that makes up the word.
    * @param wordToCheck The word to validate
    * @return java.util.List containing java.lang.Integer objects with  the thePath
    *     that makes up the word on the game board. If word is not on the game
    *     board, return an empty list. Positions on the board are numbered from zero
    *     top to bottom, left to right (i.e., in row-major order). Thus, on an NxN
    *     board, the upper left position is numbered 0 and the lower right position
    *     is numbered N^2 - 1.
    * @throws IllegalArgumentException if wordToCheck is null.
    * @throws IllegalStateException if loadLexicon has not been called.
    */
   public List<Integer> isOnBoard(String wordToCheck) {
      if (wordToCheck == null) {
         throw new IllegalArgumentException("word cannot be null");
      }
      if (loaded == false) {
         throw new IllegalStateException("lexicon not loaded");
      }
      positionPath = new ArrayList<Position>();
      wordToCheck = wordToCheck.toUpperCase();
      wordSoFar = "";
      thePath = new ArrayList<Integer>();
      //search for the word
      for (int i = 0; i < length; i++) {
         for (int j = 0; j < length; j++) {
            if (wordToCheck.equals(board[i][j])) {
               thePath.add(i * length + j); 
               return thePath;
            }
            if (wordToCheck.startsWith(board[i][j])) {
               Position pos = new Position(i, j);
               positionPath.add(pos); 
               wordSoFar = board[i][j]; 
               dfsOneWord(i, j, wordToCheck); 
               if (!wordToCheck.equals(wordSoFar)) {
                  positionPath.remove(pos);
               }
               else {
                  for (Position p: positionPath) {
                     thePath.add((p.x * length) + p.y);
                  } 
                  return thePath;
               }
            }
         }
      }
      return thePath;
   }
   
   /**
   * DFS implementation
   * @param x the x value on the board
   * @param y the y value on the board
   * @param wordToCheck the word we lookin for
   */
   private void dfsOneWord(int x, int y, String wordToCheck) {
      Position pos = new Position(x, y);
      unvisitAll(); 
      visitPath(); 
      //implement DFS recursively
      for (Position p: pos.neighbors()) {
         if (!isVisited(p)) {
            visit(p);
            if (wordToCheck.startsWith(wordSoFar + board[p.x][p.y])) {
               wordSoFar += board[p.x][p.y];
               positionPath.add(p);
               dfsOneWord(p.x, p.y, wordToCheck);
               if (wordToCheck.equals(wordSoFar)) {
                  return;
               }
               else {
                  //backtrack
                  positionPath.remove(p);
                  int last = wordSoFar.length() - board[p.x][p.y].length();
                  wordSoFar = wordSoFar.substring(0, last);
               }
            }
         }
      }
      unvisitAll(); 
      visitPath(); 
   }

   /**
    * dfs for getAllValidWords method
    */
   public void dfsAllValidWords(int x, int y, int min) {
      Position pos = new Position(x, y);
      unvisitAll();
      visitPath(); 
      for (Position p : pos.neighbors()) {
         if (!isVisited(p)) {
            visit(p);
            if (isValidPrefix(wordSoFar + board[p.x][p.y])) {
               wordSoFar += board[p.x][p.y];
               positionPath.add(p);
               if (isValidWord(wordSoFar) && wordSoFar.length() >= min) {
                  allValidWords.add(wordSoFar);
               }
               //backtrack
               dfsAllValidWords(p.x, p.y, min);
               positionPath.remove(p);
               int last = wordSoFar.length() - board[p.x][p.y].length();
               wordSoFar = wordSoFar.substring(0, last);
            }
         }
      }
      unvisitAll(); 
      visitPath(); 
   }

   /**
   * sets everything to unvisited
   */
   private void unvisitAll() {
      visited = new boolean[length][length];
      for (boolean[] row : visited) {
         Arrays.fill(row, false);
      }
   }
   
   /**
   * marks the path for the word as visited
   */
   private void visitPath() {
      for (int i = 0; i < positionPath.size(); i ++) {
         visit(positionPath.get(i));
      }
   }
   

   ///////////////////////////////////////////
   // Position class and associated methods //
   ///////////////////////////////////////////

   /**
    * Models an (x,y) position in the grid.
    */
   private class Position {
      int x;
      int y;
   
      /** Constructs a Position with coordinates (x,y). */
      public Position(int x, int y) {
         this.x = x;
         this.y = y;
      }
   
      /** Returns a string representation of this Position. */
      @Override
      public String toString() {
         return "(" + x + ", " + y + ")";
      }
   
      /** Returns all the neighbors of this Position. */
      public Position[] neighbors() {
         Position[] nbrs = new Position[MAX_NEIGHBORS];
         int count = 0;
         Position p;
         // generate all eight neighbor positions
         // add to return value if valid
         for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
               if (!((i == 0) && (j == 0))) {
                  p = new Position(x + i, y + j);
                  if (isValid(p)) {
                     nbrs[count++] = p;
                  }
               }
            }
         }
         return Arrays.copyOf(nbrs, count);
      }
   }

   /**
    * checks if a position is valid.
    * @param p the position
    */
   private boolean isValid(Position p) {
      return (p.x >= 0) && (p.x < length) && (p.y >= 0) && (p.y < length);
   }

   /**
    * Checks if a position has been visited.
    * @param p the position
    */
   private boolean isVisited(Position p) {
      return visited[p.x][p.y];
   }

   /**
    * Mark this valid position as having been visited.
    */
   private void visit(Position p) {
      visited[p.x][p.y] = true;
   }

}
