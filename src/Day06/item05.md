# 2021-03-16

#Item05. 자원을 직접 명시하지 말고 의존  객체 주입을 사용하라.
=======================================================

### *_<h3>1. 정적 유틸리티를 잘못 사용하는 예 - 유연하지 않고 테스트가 어렵다.</h3>_*
```java
public class SpellChecker {
    private static final Lexicon dictionary = new SpellChecker();
    
    private SpellChecker() {
        
    }
    
    public static boolean isValid(String word){ }
    public static List<String> suggestions(String type){ }
        
}
```

### *_<h3>2. 싱글턴을 잘못 사용한 예 - 유연하지 않고 테스트 하기 어렵다.</h3>_*
```java
public class SpellChecker {
    private static final Lexicon dictionary = new SpellChecker();
    
    private SpellChecker() {
        
    }
    
    public static boolean isValid(String word){ }
    public static List<String> suggestions(String type){ }
        
}
```

 ** 사용하는 자원에 따라 동작이 달라지는 클래스에는 정적  유틸리티 클래스나 싱글턴 방식이 적합하지 않다.
> 인스턴스를 생성할 때 생성자에 필요한 자원을 넘겨주는 방식!! -> 의존객체인 사전을 주입해주면 된다.
```java
public class SpellChecker03 {

    private final Lexicon01 dictionary;

    public SpellChecker03(Lexicon01 dictionary){
        this.dictionary = Objects.requireNonNull(dictionary);
    }

    public boolean isValid(String word){
        throw new UnsupportedOperationException();
    }

    public List<String> suggestions(String type){
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {
        Lexicon01 lexicon01 = new Lexicon01();
        SpellChecker03 spellChecker = new SpellChecker03(lexicon01);
        spellChecker.isValid("hihihi");
    }
}

class Lexicon01 { }
```
* 의존 객체 주입은 생성자, 정적 팩터리, 빌더 모두에 똑같이 적용가능!
* 생성자에 자원 팩터리를 넘겨주는 방식이 있다.(Suppler<T>인터페이스)  
 ::: Supplier<T> 인터페이스가 팩터리를 표현한 완벽한 예.  
     Supplier<T>를 입력으로 받는 메서드는 일반적으로 한정적 와일드 카드 카입을 사용해 팩터리의 타입 매개변수를 제한해야 한다.
     이 방식을 사용해 클라이언트는 자신이 명시한 하위 타입이라면 무엇이든 생성할 수 있는 팩터리를 넘길 수 있다.
```java
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
        spellChecker.isValid("hello");
    }
}

interface Lexicon01 { }

class KoreanDictionary implements Lexicon01 { }
```

> *__핵심정리__*
> 클래스가 내부적으로 하나 이상의 자원에 의존하고, 자원이 클래스 동작에 영향을 준다면 싱글턴과 정적 유틸리티 클래스는 사용하지 않는 것이 좋다.  
> 이 자원들을 클래스를 직접 만들게 해서도 안 된다. 대신 필요한 자원을(혹은 그자원을 만들어주는 팩터리를) 생성자에 (혹은 정적 팩터리나 빌더에) 넘겨 주자.  
> *__의존 객체 주입은 클래스의 유연성, 재사용성, 테스트 용이성을 개선해준다.__*
