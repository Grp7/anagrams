package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();

    HashSet<String> wordSet = new HashSet<>();
    ArrayList<String> wordList = new ArrayList<>();
    HashMap<String, ArrayList> lettersToWord = new HashMap<>();
    HashMap<Integer, ArrayList> sizeToWords = new HashMap<>();
    int wordLength = DEFAULT_WORD_LENGTH;

    public String Sor(String x)
    {
        char A[] = x.toCharArray();
        Arrays.sort(A);
        String b = new String(A);
        return b;
    }

    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordSet.add(word);
            wordList.add(word);
            String q = Sor(word);
            if(lettersToWord.containsKey(q))
            {
                ArrayList<String> r = lettersToWord.get(q);
                r.add(word);
                lettersToWord.put(q, r);
            }
            else
            {
                ArrayList<String> z = new ArrayList<>();
                z.add(word);
                lettersToWord.put(q, z);
            }
            int len = word.length();
            if(sizeToWords.containsKey(len))
            {
                ArrayList<String> b = sizeToWords.get(len);
                b.add(word);
                sizeToWords.put(len, b);
            }
            else
            {
                ArrayList<String> k = new ArrayList<>();
                k.add(word);
                sizeToWords.put(len, k);
            }
        }

    }

    public boolean isGoodWord(String word, String base) {
        if(wordSet.contains(word))
            if(!(word.contains(base)))
                return true;
            else return false;
        else
            return false;
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<>();
        char[] s = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        for(char c : s){
            String v = Sor(c+word);//G7
            if(lettersToWord.containsKey(v))
                result.addAll(lettersToWord.get(v));
        }
        return result;
    }

    public String pickGoodStarterWord() {
        String n = "";
        ArrayList<String> w = new ArrayList<>(sizeToWords.get(wordLength));
        int s = random.nextInt(w.size()-1);
        while(s<w.size()){
            ArrayList<String> v = new ArrayList<>(getAnagramsWithOneMoreLetter(w.get(s)));
            int len = v.size();
            if(len >= MIN_NUM_ANAGRAMS){
                n = w.get(s);
                if(wordLength< MAX_WORD_LENGTH)
                    wordLength += 1;
                break;
            }
            s += 1;
            if(s==w.size())
                s = 0;
        }
        return n;
    }
}
