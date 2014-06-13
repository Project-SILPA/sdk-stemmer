Usage
=====

### Note :
This module is still under development and this document presently shows only currently available utilities.

#### Get all stemmed words in array 
```
        Stemmer obj = new Stemmer(getContext());
        String[] arr = obj.getStemWordsAsArray("ഇടുക്കി: മഴ കുറഞ്ഞ പശ്ചാത്തലത്തില്‍ ഇടുക്കി അണക്കെട്ട് മൂന്ന് ദിവസത്തേക്ക് തുറക്കേണ്ട");
```
The above function `obj.getStemWordsAsArray(String);` accepts unicode string and outputs stemmed words as an array in respective order. 


#### Get all stemmed words in map 
```
        Stemmer obj = new Stemmer(getContext());
        Map<String, String> map = obj.getStemWordsAsMap("ഇടുക്കി: മഴ കുറഞ്ഞ പശ്ചാത്തലത്തില്‍ ഇടുക്കി അണക്കെട്ട് മൂന്ന് ദിവസത്തേക്ക് തുറക്കേണ്ട");
```
The above function `obj.getStemWordsAsMap(String);` accepts unicode string and outputs stemmed words as (key, value) pairs where key - word, value - stemmed word.


#### StemmerEditText and  StemmerTextView

EditText
```

        <org.silpa.stemmer.StemmerEditText
                android:id="@+id/edtStemmer"
                android:layout_width="match_parent"
                android:layout_height="wrap_content" />                
                 
```

TextView
```

        <org.silpa.stemmer.StemmerTextView
                android:id="@+id/tvStemmer"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="ഇടുക്കി മഴ കുറഞ്ഞ പശ്ചാത്തലത്തില്‍ ഇടുക്കി അണക്കെട്ട് മൂന്ന് ദിവസത്തേക്ക് തുറക്കേണ്ട" />
                
                 
```

Stemmed words can be extracted by :

```
        StemmerTextView tvStemmer = (StemmerTextView)
                        view.findViewById(R.id.tvStemmer);        
        String[] arr = tvStemmer.getStemWordsAsArray();
        Map<String, String> map = tvStemmer.getStemWordsAsMap();
```

Similarly for StemmerEditText.


#### Get module name and information
```
        String moduleName = obj.getModuleName();
        String moduleInforamtion =  obj.getModuleInformation();
```

#### To run tests

  - Select test to run
  - Right Click -> Run Test -> Run as Android Test

