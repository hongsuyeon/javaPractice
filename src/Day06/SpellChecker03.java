package Day06;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class SpellChecker03 {

    private final Lexicon01 dictionary;

    public SpellChecker03(Supplier<Lexicon01> dictionary){
        this.dictionary = Objects.requireNonNull(dictionary).get();
    }

    public boolean isValid(String word){
        throw new UnsupportedOperationException();
    }

    public List<String> suggestions(String type){
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {
        Lexicon01 lexicon01 = new KoreanDictionary();
        SpellChecker03 spellChecker = new SpellChecker03(() -> lexicon01);
        spellChecker.isValid("hjello");
        /*SpellChecker03 spellChecker = new SpellChecker03(lexicon01);
        spellChecker.isValid("hihihi");*/
    }
}

//class Lexicon01 { }
interface Lexicon01 { }

class KoreanDictionary implements Lexicon01 { }
