# 2021-03-02 ~ 03

Item01. 생성자 대신 정적 팩터리 메서드를 고려하라.
--------------------------------------------

```java
public class Foo {
    public static Boolean valueOf(boolean b) {
        return b ? Boolean.TRUE : Boolean.FALSE;
    }
}
```
클래스는 클라이언트에 public 생성자 대신(혹은 생성자와 함께) 정적 팩터리 메서드를 제공할 수 있다.

### *_정적 팩터리 메서드가 생성자보다 좋은 장점 5가지_*

`1. 이름을 가질 수 있다.`  
 : 생성자는 클래스와 동일한 이름을 사용. 하지만 적정 팩터리 메서드는 이름 정의가 가능.  
   (반환될 객체의 특성을 쉽게 묘사할수 있도록 지을수 있음)
   생성자는 똑같은 타입 파라미터로 받는 생성자 두개를 만들수 없음.   
   -> 정적 팩터리 메서드로 바꾸고 각각의 차이를 잘드러내는 이름을 지어주는것이 가능.
    
`2. 호출될때 마다 인스턴스를 새로 생성하지 않아도 된다.`   
  : 불변 클래스는 인스턴스를 미리 만들어 놓거나 새로 생성한 인스턴스를 캐싱하여 재활용하는 식으로 불필요한 객체생성을 피할 수 있음.

```java
public class Foo {
    //불변객체생성
    private static final Foo GOOD_NIGHT = new Foo();
    
    public static Foo getFoo(){
        return GOOD_NIGHT;
    }
    public static void main(String[] args) {
        //객체를 매번 새로 생성하는게 아니라 getFoo 호출해서 가져옴.
        Foo foo = Foo.getFoo();
    }
}
```

`3. 반환 타입의 하위 타입 객체를 반환할 수 있는 능력이 있다.`  
 : 반환할 객체의 클래스를 자유롭게 선택할 수 있게 하는 유연성을 갖음.  
   API를 만들 때 이 유연성을 응용시 구현 클래스를 공개하지 않고 그 객체를 반환이 가능해 API를 작게 유지 가능.
   Collections 프레임워크가 이에 해당.  
   자바8부터는 인터페이스가 정적 메서드를 가질 수 있기 때문에 인스턴스화 불가한 동반 클래스를 만들 필요가 없음.  
   동반 클래스에 두었던 public 정적 멤버들 상당수를 그냥 인터페이스 자체에 두면됨.  
   **_그러나, 정적 메서드들을 구현하기 위한 코드 중 많은 부분은 여전히 별도의 package-private 클래스에 두어야 할 수 있음.  
   자바8에서도 인터페이스에는 public 정적 메서드까지 허락하지만 정적 필드와 정적 멤버 클래스는 여전히 public 이어야함._**  
```java
public class Foo {

    private static final Foo GOOD_NIGHT = new Foo();
    
    public static Foo getFoo(){
        return GOOD_NIGHT;
    }

    public static Foo getFoo(boolean flag){
        return flag ? new Foo() : new BarFoo();
    }

    public static void main(String[] args) {
        Foo foo = Foo.getFoo(false);
        System.out.println(foo.getClass());
    }
    //상속받음
    static class BarFoo extends Foo {

    }
}
```
`4. 입력 매개변수에 따라 매번 다른 클래스의 객체를 반환할 수 있다.`      
 : EnumSet 클래스는 public 생성자 없이 오직 정적 팩터리만 제공하는데, 
   OpenJDK에서는 원소의 수에 따라 두 가지 하위 클래스(RegularEnumSet, JumboEnumSet) 중 하나의 인스턴스를 반환.
```java
public class Foo {
    public static void main(String[] args) {
        EnumSet<Color> color = EnumSet.allOf(Color.class);
        EnumSet<Color> blueAndWhite = EnumSet.of(Color.BLUE, Color.RED);
    }

    enum Color {
        RED, BLUE, WHITE
    }
}
```

`5. 정적 팩터리 메서드를 작성하는 시점에는 반환할 객체의 클래스가 존재하지 않아도 된다.`    
 : 서비스 제공자 프레임워크를 만드는 근간이 된다. JDBC가 대표적인 예.   
   서비스 제공자 프레임워크에서의 제공자는 서비스의 구현체.  
   이 구현체들은 클라이언트에 제공하는 역할을 프레임워크가 통제하여, 클라이언트를 구현체로 부터 분리.  
   서비스 제공자 프레임워크는 서비스 인터페이스(구현체의 동작을 정의) , 제공자가 구현체를 등록할 때 사용하는 제공자등록,  
   클라이언트가 서비스의 인스턴스를 얻을 때 사용하는 서비스 접근 API   
   4번째 서비스 제공자 인터페이스 : 서비스 인터페이스의 인스턴스를 생성하는 팩터리 객체 설명  
   -> 서비스 제공자가 없으면 각 구현체를 인스턴스로 만들 때 리플렉션을 사용.  
    - 의존성 객체 주입도 강력한 서비스 제공자라고 생각할 수 있음.    
    - 자바6부터 `java.util.ServiceLoader`라는 범용 서비스 제공자가 프레임워크가 제공되어 프레임워크를 직접 만들 필요가 거의 없어짐.

### *_정적 팩터리 메서드가 생성자보다 좋은 단점_*

`1. 상속을 하러면 public이나 protected 생성자가 필요하니 정적 팩터리 메서드만 제공하면 하위 클래스를 만들 수 없다.`     
 : 컬렉션 프레임워크의 유틸리티 구현 클래스들은 상속이 불가.
 
`2. 정적  팩터리 메서드는 프로그래머가 찾기 어렵다.`        
 : from (매개 변수를 하나 받아서 해당 타입의 인스터를 반환하는 형변환 메서드)
    
  `Date d = Date.from(instant);`        
 : of(여러 매겨변수를 받아 적합한 타입의 인스턴스를 반환하는 집게 메서드)
 
  `Set<Rank> faceCards = EnumSet.of(JACK, QUEEN, KING);`        
 : valueof(from과 of의 더 자세한 버전)
 
   `BigInteger prime = BigInteger.valueOf(Integer.MAX_VALUE);`      
 : instance 혹은 getInstance (매개변수를 받는다면 매개변수로 명시된 인스턴스를 반환하지만, 같은 인스턴스임을 보장하지는 않는다.)
 
  `StackWalker luke = StaclWalker.getInstance(option);`
 : create 혹은 new Instance (Instance 혹은 getInstance와 같지만, 매번 새로운 인스턴스를 생성해 반환함을 보장한다.)
 
  `Object newArray = Array.newInstance(classObject, arrayLen);`
 : getType (getInstance와 같으나, 생성할 클래스가 아닌 다른 클래스에 팩터리 메서드를 정의할 대 쓴다. "Type"은 팩터리 메서드가 반환할 객체의 타입이다.)
 
  `FilStore fs = Files.getFilsStore(path);`
  : newType (newInstance와 같으나, 생성할 클래스가 아닌 다른 클래스에 팩터리 메서드를 정의할 대 쓴다. "Type"은 팩터리 메서드가 반환할 객체의 타입이다.)
  
  `BufferedReader br = Files.newBufferedReader(path);`
  : type (getType과 newType의 간결한 버전)
  
   `List<Complant> litany = Collections.list(legacyLitnay);`


