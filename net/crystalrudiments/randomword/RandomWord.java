package net.crystalrudiments.randomword;
/**
 * Creates random words using probability and ordering
 * of vowels and constanants in the English language.
 * 
 * Created on August 24, 2000, 10:19 PM
 *
 * @author  Adam Davis
 * @version 1.0.0000
 */
public class RandomWord {

    private java.lang.String word;
    private char[] letter = new char[26];
    private int[] perc = new int[26];
    private boolean[] is_vowel = new boolean[26];
    private int total_perc=0;
    private int current_letter=0;
    private int PERC_INIT=2; // constant: initial value of "perc" for each letter.
    private int num_of_vow=6; // number of vowels.
    private int num_of_con=20; // number of constanents.
    
    /** Creates new form RandomWord */
    public RandomWord() {
        
        word = "Random Word";
        initLetters ();
        initVowels ();
        initPercs ();
    }

    private void initLetters () {
        /* purpose: to assign the letters a-z to the "letter" array using the 
        procedure "Add_Letter" -- and to assign if a letter is a vowel to the
        "is_vowel" array.*/
        addLetter ('a'); addLetter ('b'); addLetter ('c'); addLetter ('d');
        addLetter ('e'); addLetter ('f'); addLetter ('g'); addLetter ('h');
        addLetter ('i'); addLetter ('j'); addLetter ('k'); addLetter ('l');
        addLetter ('m'); addLetter ('n'); addLetter ('o'); addLetter ('p');
        addLetter ('q'); addLetter ('r'); addLetter ('s'); addLetter ('t');
        addLetter ('u'); addLetter ('v'); addLetter ('w'); addLetter ('x');
        addLetter ('y'); addLetter ('z');
    }
    
    private void initVowels () {
        for (int i=0; i <= 25; i++) {
            is_vowel[i] = false;
        }
        is_vowel[0] = true; is_vowel[4] = true;
        is_vowel[8] = true; is_vowel[14] = true;
        is_vowel[20] = true; is_vowel[24] = true;
    }
    
    private void initPercs () {
        /* purpose: first sets all 'percs' to default value then sets the 
        special cases, (i.e. letters that are more or less probable than the 
        average) */
        for (int i=0; i <= 25; i++) {
            addPerc (i, PERC_INIT);
        }
        /* Note: the "Add_Perc" subs DO NOT have to be in any particular order,
        but it looks nicer...*/
        addPerc(0,9); addPerc(2,5);
        addPerc(4,10); addPerc(8, 7);
        addPerc(12,6); addPerc(14, 8);
        addPerc(18,6); addPerc(19, 6);
        addPerc(20,7); addPerc(25, 1);
        fixPercs();
    }
    
    private void addPerc (int a, int b) {
        /* purpose: simply assigns the relative probability of each letter 
        to appear -- to the "perc" array (roughly 5 -17) */
        if (perc[a] == PERC_INIT) total_perc -= perc[a];
        total_perc += b;
        perc[a] = b;
    }
    
    private void fixPercs() {
        // purpose: adds all previous letter values to each letter; from z to a.
        for (int a = 25; a >= 0; a--) {
            for (int i = (a - 1); i >= 0; i--) {
                perc[a] += perc[i];
            }
        }
    }
   
    private void addLetter(char abc){
        /* purpose: adds letter to "letter" array */
        letter[current_letter] = abc;
        current_letter++;
    }
    
    /**
     * Creates random word of a certain length.
     * @param length
     * @return
     */
    public String makeRandomWord(int length) {
        
        word = "";
        int ran = 0;
        int num = 0;
        for (int i = 0; i < length; i++){
            ran = (int)(java.lang.Math.random() * total_perc);
            for (int j = 0; j < 26; j++) {
                if (ran <= perc[j]) {
                num = j;
                break;
                }
            }
            word += letter[num];
        }
        return word;
    }
    
    

}