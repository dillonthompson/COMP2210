import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.Queue;

import java.util.stream.Collectors;

/**
 * Provides an implementation of the WordLadderGame interface. 
 *
 * @author Your Name (you@auburn.edu)
 * @author Dean Hendrix (dh@auburn.edu)
 * @version 2019-03-29
 */
public class Doublets implements WordLadderGame {

    // The word list used to validate words.
    // Must be instantiated and populated in the constructor.
    /////////////////////////////////////////////////////////////////////////////
    // DECLARE A FIELD NAMED lexicon HERE. THIS FIELD IS USED TO STORE ALL THE //
    // WORDS IN THE WORD LIST. YOU CAN CREATE YOUR OWN COLLECTION FOR THIS     //
    // PURPOSE OF YOU CAN USE ONE OF THE JCF COLLECTIONS. SUGGESTED CHOICES    //
    // ARE TreeSet (a red-black tree) OR HashSet (a closed addressed hash      //
    // table with chaining).
    /////////////////////////////////////////////////////////////////////////////

    private HashSet<String> lexicon;
    private HashSet<String> visited;

    /**
     * Instantiates a new instance of Doublets with the lexicon populated with
     * the strings in the provided InputStream. The InputStream can be formatted
     * in different ways as long as the first string on each line is a word to be
     * stored in the lexicon.
     */
    public Doublets(InputStream in) {
        try {
            //////////////////////////////////////
            // INSTANTIATE lexicon OBJECT HERE  //
            //////////////////////////////////////
            lexicon = new HashSet<String>();
            Scanner s = new Scanner(new BufferedReader(new InputStreamReader(in)));
            while (s.hasNext()) {
                String str = s.next();
                /////////////////////////////////////////////////////////////
                // INSERT CODE HERE TO APPROPRIATELY STORE str IN lexicon. //
                /////////////////////////////////////////////////////////////
                lexicon.add(str.toUpperCase());
                s.nextLine();
            }
            in.close();
        }
        catch (java.io.IOException e) {
            System.err.println("Error reading from InputStream.");
            System.exit(1);
        }
    }


    //////////////////////////////////////////////////////////////
    // ADD IMPLEMENTATIONS FOR ALL WordLadderGame METHODS HERE  //
    //////////////////////////////////////////////////////////////
    /**
     * Returns the Hamming distance between two strings, str1 and str2. The
     * Hamming distance between two strings of equal length is defined as the
     * number of positions at which the corresponding symbols are different. The
     * Hamming distance is undefined if the strings have different length, and
     * this method returns -1 in that case. See the following link for
     * reference: https://en.wikipedia.org/wiki/Hamming_distance
     *
     * @param  str1 the first string
     * @param  str2 the second string
     * @return      the Hamming distance between str1 and str2 if they are the
     *                  same length, -1 otherwise
     */
    public int getHammingDistance(String str1, String str2) {
        //check if str1 and str2 are null
        if (str1 == null || str2 == null) {
            return -1;
        }
        //check if equal length
        if (str1.length() != str2.length()) {
            return -1;
        }
        //check for hamming distance
        int count = 0;
        for (int i = 0; i < str1.length(); i++) {
            // System.out.println(str1.charAt(i));
            if (str1.charAt(i) != str2.charAt(i)) {
                // System.out.println(str1.charAt(i));
                count++;
            }
        }
        return count;
    }


   /**
    * Returns a minimum-length word ladder from start to end. If multiple
    * minimum-length word ladders exist, no guarantee is made regarding which
    * one is returned. If no word ladder exists, this method returns an empty
    * list.
    *
    * Breadth-first search must be used in all implementing classes.
    *
    * @param  start  the starting word
    * @param  end    the ending word
    * @return        a minimum length word ladder from start to end
    */
    public List<String> getMinLadder(String start, String end) {
        return bfs(start, end);
    }

    public List<String> bfs(String start, String end) {
        visited = new HashSet<String>();
        Queue<Node> q = new LinkedList<Node>();
        List<String> nbrs = new ArrayList<String>();
        q.add(new Node(start, null));
        while (!q.isEmpty()) {
            Node temp = q.remove();
            if (temp.word.equals(end)) {
                while (temp.n != null) {
                    nbrs.add(temp.word);
                    temp = temp.n;
                }
                nbrs.add(start);
                Collections.reverse(nbrs);
                return nbrs;
            }
            for (String i : getNeighbors(temp.word)) {
                if (!visited.contains(i)) {
                    visited.add(i);
                }
                q.add(new Node(i, temp));
            }
        }
        return nbrs;
    }


    /**
     * Returns all the words that have a Hamming distance of one relative to the
     * given word.
     *
     * @param  word the given word
     * @return      the neighbors of the given word
     */
    public List<String> getNeighbors(String word){
        //instantiate nbrs
        List<String> nbrs = new ArrayList<String>();
        //check if word is null
        if (word == null) {
            throw new IllegalArgumentException("word cannot be null");
        }
        //get the nbrs
        for (int i = 0; i < word.length(); i++) {
            for (char j = 'a'; j <= 'z'; j++) {
                String cop = word.replace(word.charAt(i), j);
                if (isWord(cop) && getHammingDistance(word, cop) == 1) {
                    nbrs.add(cop);
                }
            }
        }
        return nbrs;
    }


    /**
     * Returns the total number of words in the current lexicon.
     *
     * @return number of words in the lexicon
     */
    public int getWordCount() {
        return lexicon.size();
    }


    /**
     * Checks to see if the given string is a word.
     *
     * @param  str the string to check
     * @return     true if str is a word, false otherwise
     */
    public boolean isWord(String str) {
        //check for str to be null
        if (str == null) {
            return false;
        }
        //check if lexicon is empty
        if (lexicon.isEmpty()) {
            return false;
        }
        //look for word
        if (lexicon.contains(str.toUpperCase())) {
            return true;
        }
        return false;
    }


    /**
     * Checks to see if the given sequence of strings is a valid word ladder.
     *
     * @param  sequence the given sequence of strings
     * @return          true if the given sequence is a valid word ladder,
     *                       false otherwise
     */
    public boolean isWordLadder(List<String> sequence) {
        for (int i = 0; i < sequence.size() - 1; i++) {
            String curr = sequence.get(i);
            String nxt = sequence.get(i + 1);
            if (!isWord(curr) || !isWord(nxt) || getHammingDistance(curr, nxt) > 1) {
                return false;
            }
        }        
        return true;
    }

    public class Node {
        String word;
        Node n;
        public Node(String str, Node node) {
            word = str;
            n = node;
        }
    }

}

