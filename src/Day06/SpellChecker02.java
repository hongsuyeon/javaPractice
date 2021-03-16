package Day06;

import java.util.List;

public class SpellChecker02 {
    private static final Lexicon dictionay= new KoreanDictory();

    private SpellChecker02(){

    }

    public static final SpellChecker02 INSTANCE = new SpellChecker02(){

    };

    public boolean isValid(String word){
        throw new UnsupportedOperationException();
    }

    public List<String> suggestions(String type){
        throw new UnsupportedOperationException();
    }
}
