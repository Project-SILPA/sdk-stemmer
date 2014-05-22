package org.smc.silpamodules.stemmer;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sujith on 22/5/14.
 */
public class Stemmer {

    private Context mContext;
    private Map<String, String> mRulesDict;

    private static final String mRulesFileName = "stemmer_ml.rules";
    private static final String STEMMER_MODULE_NAME = "Stemmer";
    private static final String STEMMER_MODULE_INFORMATION = "Malayalam Stemmer";

    private static final String LOG_TAG = "Stemmer Module - Stemmer";

    public Stemmer(Context context) {
        this.mContext = context;
        this.mRulesDict = new HashMap<String, String>();
        init();
    }

    private void init() {
        loadRules();
    }

    private void loadRules() {

        BufferedReader br = null;

        try {
            br = new BufferedReader(new InputStreamReader(this.mContext.getResources().
                    getAssets().open(mRulesFileName)));

            String line = "";
            int lineNumber = 0;
            int ruleNumber = 0;

            while (true) {
                String text;
                try {
                    lineNumber = lineNumber + 1;
                    text = new String((br.readLine().getBytes("UTF-8")),
                            "UTF-8");
                    text = text.trim();
                } catch (Exception e) {
                    br.close();
                    break;
                }

                if (text.equals("")) {
                    continue;
                }

                // comment - ignore
                if (text.startsWith("#")) {
                    continue;
                }

                // remove the comment part of the line
                text = text.split("#")[0];
                line = text.trim();

                if (line == null || line.equals("")) {
                    continue;
                }

                if (line.split("=").length != 2) {
                    Log.e(LOG_TAG, "[Error] Syntax Error in the Rules. Line number: " +
                            lineNumber);
                    Log.e(LOG_TAG, "Line:  " + text);
                    continue;
                }

                String[] tokens = line.split("=");
                String lhs = tokens[0].trim();
                String rhs = tokens[1].trim();

                if (lhs.length() > 0) {
                    // string is quoted
                    if (lhs.charAt(0) == '"') {
                        lhs = lhs.substring(1, lhs.length());
                    }
                    // string is quoted
                    if (lhs.charAt(lhs.length() - 1) == '"') {
                        lhs = lhs.substring(0, lhs.length() - 1);
                    }
                }

                if (rhs.length() > 0) {
                    // string is quoted
                    if (rhs.charAt(0) == '"') {
                        rhs = rhs.substring(1, rhs.length());
                    }
                    // string is quoted
                    if (rhs.charAt(rhs.length() - 1) == '"') {
                        rhs = rhs.substring(0, rhs.length() - 1);
                    }
                }

                ruleNumber = ruleNumber + 1;
                this.mRulesDict.put(lhs, rhs);

                //Log.v(LOG_TAG, "[" + ruleNumber + "]" + lhs + " : " + rhs);
            }

        } catch (IOException ioe) {
            Log.e(LOG_TAG, "Error : " + ioe.getMessage());
            return;
        }
    }

    private String trim(String word) {
        char[] punctuations = {'~', '!', '@', '#', '$', '%', '^', '&', '*',
                '(', ')', '-', '+', '_', '=', '{', '}', '|', ':', ';', '<',
                '>', ',', '.', '?'};
        word = word.trim();
        int index = word.length() - 1;
        while (index > 0) {
            boolean flag = false;
            for (int i = 0; i < punctuations.length; i++) {
                if (word.charAt(index) == punctuations[i]) {
                    flag = true;
                }
            }

            if (flag) {
                word = word.substring(0, index);
            } else {
                break;
            }
            index = index - 1;
        }
        return word;
    }

    private Map<String, String> stem(String text) {

        int wordCount;
        int wordIter;
        String word;
        String[] words;
        Map<String, String> resultDict = null;

        try {
            text = new String((text.getBytes("UTF-8")), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e(LOG_TAG, "Encoding of given argument not supported. null returned");
            return null;
        }

        if (this.mRulesDict == null) {
            loadRules();
        }

        resultDict = new HashMap<String, String>();

        wordIter = 0;
        word = "";
        words = text.split(" ");
        wordCount = words.length;


        while (wordIter < wordCount) {
            word = words[wordIter];
            word = this.trim(word);

            // TODO word = word.strip('!,.?:')
            // trim first few characters of regex

            int wordLength = word.length();
            int suffixPosItr = 2;
            String wordStemmed = "";

            while (suffixPosItr < wordLength) {
                String suffix = word.substring(suffixPosItr, wordLength);
                if (this.mRulesDict.containsKey(suffix)) {
                    wordStemmed = word.substring(0, suffixPosItr)
                            + this.mRulesDict.get(suffix);
                    break;
                }
                suffixPosItr = suffixPosItr + 1;
            }
            wordIter = wordIter + 1;

            if (wordStemmed.equals("")) {
                wordStemmed = word;
            }
            resultDict.put(word, wordStemmed);
        }
        return resultDict;
    }

    public Map<String, String> getStemWordsAsMap(String text) {
        return stem(text);
    }

    public String[] getStemWordsAsArray(String text) {
        Map<String, String> stemmedMap = stem(text);

        String[] words = text.split(" ");
        int wordCount = words.length;

        String[] result = new String[wordCount];

        for (int i = 0; i < wordCount; i++) {
            result[i] = stemmedMap.get(words[i]);
        }
        return result;
    }


    public String getModuleName() {
        return STEMMER_MODULE_NAME;
    }

    public String getStemmerModuleInformation() {
        return STEMMER_MODULE_INFORMATION;
    }
}
