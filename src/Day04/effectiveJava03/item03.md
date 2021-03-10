# 2021-03-09
Item03. private 생성자나 열거 타입으로 싱글턴임을 보증하라.
--------------------------------------------

1. 싱글턴을 만드는 방식
   
  1) 유일한 인스턴스에 접근 할 수 있는 수단으로 public static 맴버 생성
     
     : private 생성자는 SingleTon1.instance를 초기화 시에 한 번만 호출
      (public이나 protected 생성자가 없으므로 클래스 초기화시 만들어진 인스턴스가 전체 시스템에서 하나뿐임을 보장)
     
     =>  리플렉션 API인 AccessibleObject.setAccessible을 사용해 private 생성자 호출 가능  *_====> 생성자를 수정하여 두번쨰 객체가 생성되려 하면 예외를 던지도록 처리 필요._*
  
    (장점)
      - public static 필드가 final으로 선언되어 다른객체를 참조 불가능
      - 소스 코드의 간결함. 
      - public 필드방식의 장점은 해당 클래스가 싱글턴임이 API에 명시적으로 나타남. 

     
```java
//1)에 대한 예시
public class SingleTon1 {
    //public static 필드가 final 이라서 다른 객체를 참조가 불가능, 간결함
    public static final SingleTon1 instance = new SingleTon1();

    private SingleTon1(){

    }
}
```

2) 정적 팩토리 메서드를 public static멤버로 제공
  
  (장점)
  - getInstance 메서드는 항상 같은 객체의 참조를 반환하므로, 다른 인스턴스는 생성불가
  - API를 바꾸지 않고도 싱글턴이 아니게 처리 가능 (getInstance 내부 구현만 변경하면 됨.)    
  - 정적 팩터리의 메서드 참조를 공급자(supplier)로 사용할 수 있다는 점이다.

 ** 싱글턴 클래스를 직렬화하려면 단순히 Serializable을 구현한다고 선언하는 것만으로는 부족하다.
    모든 인스턴스 필드를 일시적이라고 선언시, readResolve 메서드를 제공해야한다.
    
```java
//2)에 대한 예시
public class SingleTon2 {

  private static final SingleTon2 instance = new SingleTon2();

  private SingleTon2(){

  }
  public static SingleTon2 getInstance(){
    return instance;
  }
}
```
```java
public class SingleTest1 {
    public static void main(String[] args) throws NoSuchMethodException {
        // SingleTon1 singleTon1 = new SingleTon1();
        SingleTon1 singleTon1 = SingleTon1.instance; //초기화시 한번만 호출처리됨.
        SingleTon1 singleTon2 = SingleTon1.instance;

        SingleTon2 singleTon2_1 = SingleTon2.getInstance();
        SingleTon2 singleTon2_2 = SingleTon2.getInstance();
        
        //정적 팩터리를 제네릭 싱글턴 패턴 팩터리로 만들수 있다.
        Supplier<SingleTon2> suppler2 = SingleTon2::getInstance;
    }
}
```
  3) 열거 타입을 선언하는 것
 : public 필드 방식과 비슷하지만, 더 간결하고 추가 노력없이 직렬화 가능, 리플렉션 공격에도 제2의 인스턴스가 생성을 막는다.
     대부분의 상황에서는 원소가 하나뿐인 열거 타입이 싱글턴을 만드는 가장 좋은 방법!..
     
     -> 하지만 상속해서 사용해야 할 경우는 불가능, 열거 타입이 다른 인터페이스를 구현하도록 선언할 수는 있다.
```java
public enum SingleTOn3 {
  INSTANCE;

  public String getName(){
    return "hongsu";
  }
}
```