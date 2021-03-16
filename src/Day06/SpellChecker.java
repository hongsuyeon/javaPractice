package Day06;

import java.util.List;

public class SpellChecker {

    private static final Lexicon dictionay= new KoreanDictory();

    private SpellChecker(){

    }

    public static boolean isValid(String word){
        throw new UnsupportedOperationException();
    }

    public static List<String> suggestion(String type){
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {
        SpellChecker.isValid("hello");
    }

}
interface  Lexicon{ }

class KoreanDictory implements  Lexicon { }