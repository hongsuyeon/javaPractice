# 2021-03-17

#Item06. 불필요한 객체 생성을 피하라.
=======================================================

> String s = new String("bikini");  

 : 생성자로 만들어내려는 String과 기능적으로 완전히 동일.

> String s = "bikini";  

 : 하나의 String 인스턴스를 사용, 생성자 대신 정적 팩터리  메서드를 제공하는 불편 클래스에서는 정적 팩터리 메서드를 사용해 
불필요한 객체 생성을 피할 수 있다.  
  *__Boolean(String) 생성자 대신 Boolean.valueOf(String) 팩터리 메서드를 사용하는것이 좋다.     
  팩터리 메서드는 불변/가변 객체에 상관없이 사용 중에 변경되지 않음을 알수 있으면 재사용 가능!__*

```java
public class test {
  static boolean isRomanNumeral(String s){
      return s.matches("^(?=.)M*(C[MD]|D?C{0,3}"+"(X|[CL]|L?X{0,3})(I[XV]|V?I{0,3}$");
  }
}
```  
###1. 정적 초기화
- 문제점 - Stirng.matches 메서드를 사용 -> 정규표현식으로 문자열 형태를 확인하는 가장 쉬운방법이나, 성능이 중요한 상황에서는 반복해 사용하기 적합하지 않음.
          이 메서드 내부에서 만드는 Pattern인스턴스는, 한 번 쓰고 버려져서 곧바로 가비지 컬렉션 대상이 된다.
          Pattern은 입력받은 정규표현식에 해당하는 유한 상태 머신을 만들기 때문에 생성 비용이 높음
- 성능개선 - 필요한 정규표현식을 표현하는 Pattern인스턴스를 클래스 초기화(정적 초기화) 과정에서 직접 생성해 캐싱해두고, 나중에 isRomanNumeral 메서드가 호출될때마다 이 인스턴스를 재사용.
- isRomanNumeral 방식의 클래스가 초기화된 후 이메서드를 한번도 호출하지 않으면, ROMAN필드는 쓸데없이 초기화 한 꼴이 된다.  
  개선된 isRomanNumeral가 처음 호출될 때 필드를 초기화하는 지연 초기화로 불필요한 초기화를 없앨수 있지만, 권장하지 않음(성능개선에 크게 도움이 되지 않는다.)
  
```java
import java.util.regex.Pattern;

public class test {
  public static final Pattern ROMAN = Pattern.compile("^(?=.)M*(C[MD]|D?C{0,3}" + "(X|[CL]|L?X{0,3})(I[XV]|V?I{0,3}$");
  
  static boolean isRomanNumeral(String s) {
    return ROMAN.matcher(s).matches();
  }
}
```

###2. 어뎁터
- 객체가 불변이라면 재사용해도 안전함이 명백함.   
  ex) 어뎁터 - 어뎁터는 실제 작업은 뒷단 객체에 위임하고 자신은 제 2의 인터페이스 역할을 해주는 객체이다.
      뒷단 객체 외에는 관리할 상태가 없으므로 뒷단 객체 하나당 어댑터 하나씩만 만들어지면 충분하다.  
  Map 인터페이스의 KeySet 메서드는 Map 객체 안의 전부를 담은 Set 뷰를 반환. -> 반환된 Set인스턴스가 일반적으로 가변이더라도 반환된 인스턴스들은 기능적으로 모두 동일하다.
  반환된 객체 중 하나를 수정하면 다른 모든 객체가 따라서 바뀐다.    

###3. 오토박싱
- 오토박싱 : 기본타입과 그에 대응하는 박싱된 기본 타입의 구분을 흐려주지만, 완전히 없애주는 것은 아니다.
```java
import java.util.regex.Pattern;

public class test {
  private static long sum(){
      Long sum = 0L;
      for(int i=0;i<=Integer.MAX_VALUE;i++){
          sum+=i;
          return sum;
      }
  }
}
```
> 박싱된 기본 타입보다는 기본 타입을 사용하고, 의도치 않은 오토박싱이 숨어들지 않도록 주의해라.






     


