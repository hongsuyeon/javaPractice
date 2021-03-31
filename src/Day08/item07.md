# 2021-03-30

#Item07. 다 쓴 객체 참조를 해제하라
=======================================================

## 1. 스택(stack)

```java
public class Stack {
    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public Stack(){
        elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public void push(Object e){
        ensureCapacity();
        elements[size++] = e;
    }

    public Object pop(){
        if(size == 0)
            throw new EmptyStackException();
        return elements[--size];
    }

    /*
     * 원소를 위한 공간을 적어도 하나 이상 확보한다.
     * 배열 크기를 늘려야 할 때마다 대략 두 배씩 늘린다.
     * */
    private void ensureCapacity(){
        if(elements.length == size)
            elements = Arrays.copyOf(elements, 2 * size+1);
    }
}
```
> 코드의 문제점 : "메모리 누수"로, 이 스택을 사용하는 프로그램을 오래 실행하다 보면 점차 가비지 컬렉션 활동과 메모리 사용량이 늘어나 결국 성능이 저하 (심할 경우 디스크 페이징, OutOfMemoryError 발생으로 프로그램이 예기치 않게 종료)

 ### 위코드에서 메모리 누수가 발생되는 부분?
```java
    public Object pop(){
        if(size == 0)
            throw new EmptyStackException();
        //********************* 이부분 *******************//
        return elements[--size];
        //****************************************//
    }
```
 * 스택에서 꺼내진 객체들을 프로그램에서 사용하지 않더라도 가비지 컬렉터가 회수하지 않음
 * 스택은 다 쓴 참조를 가지고 있다. -> 다 쓴 참조 : elements 배열의 '활성 영역'밖의 참조들이 모두 해당
 * 가비지 컬렉션 언어에서는 메모리 누수를 찾기가 까다로움.
 * 객체 참조 하나를 살려두면 가비지 컬렉터는 그객체가 아니라 그 객체가 참조하는 모든 객체(그 객체를 참조하는 모든 객체)들을 회수해가지 못한다.
 ### 해결책 :: "null처리(참조 해제)를 해준다."에서 좀 더 좋은 해결책은 "참조를 담은 변수를 유효 범위 밖으로 밀어내는 것!!" 이다.
```java
    public Object pop(){
        if(size == 0)
            throw new EmptyStackException();
        //***************
        Object result = elements[--size];
        elements[size] = null;
        //***************
        return result;
    }
```
 * 장점 : null처리한 참조를 실수로 사용하려면 프로그램은 NullPointException을 던지며 종료.(이상한 소스 동작을 방지)
 -> 객체 참조를 null처리하는 일은 예외적인 경우여야 한다.(항상 null 처리 할 필요는 없음.)
 * 스택이 자기 메모르를 직접관리하기 때문에 메모리 누수에 취약!       
 * 스택은 (객체 자체가 아니라 객체 참조를 담는) elements 배열로 저장소 풀을 만ㄷ르어 원소들을 관리      
    :: 활성 영역은 사용 / 비활성 영역은 비사용(가비지 컬렉터는 사용여부를 알 방법이 없고 null처리로 해당 객체를 더 이상 사용하지 않는다고 가비지컬렉터에게 알려줘야 한다.)
## 2. 캐시
 * 객체 참조를 캐시에 두고나서, 객체를 다 쓴 뒤로도 함참을 그냥 놔두는 일을 접할 수 있다.       
  ::: 운 좋게 캐시 외부에서 키를 참조하는 동안만 엔트리가 살아 있는 캐시가 필요한 상황인 경우 *_WeakHashMap을 사용해 캐시를 생성(다 쓴 엔트리는 그 즉시 자동 제거)_*
## 3. 리스너 혹은 콜백
 * 클라이언트가 콜백을 등록만 하고 명확히 해지하지 않는다면, 콜백은 계속 쌓여간다.        
 ::: 콜백을 약한 참조(week reference)로 저장하면 가비지 컬렉터가 즉시 수거 ex) WeakHashMap

####GC와 Reachability
- java GC는 객체가 가비지인지 판별하기 위해 reachability라는 개념을 사용.
- 어떤 객체의 유효한 참조가 있는 경우 'reachable', 없으면 'unreacheable'로 구별, unreachable객체는 가비지로 간주해 GC를 수행
- 한 객체는 여러 다른 객체를 참조하고, 참조된 다른 객체들도 마찬가지도 다른객체들을 참조할 수 있으므로 객체는 참조 사슬을 이룬다.
- 런타임시 데이터 영역은 Thread영역, Heap영역(객체를 생성 및 보관), 메서드 영역(클래스 정보)        
  - 힙에 있는 객체들의 대한 참조의 종류              
    -  힙 내의 다른 객체에 의한 참조      
    - Java 스택, 즉 Java 메서드 실행 시에 사용하는 지역 변수와 파라미터들에 의한 참조     
    - 네이티브 스택, 즉 JNI에 의해  생성된 객체에 대한 참조      
    - 메서드 영역의 정적 변수에 의한 참조       

> **_약한 참조(Weak reference) / 강한참조 (Strong delivery)_**       
> 강한 참조 : new로 객체 생성을 하여 해당 객체를 참조하는 방식을 말함.
> 강한 참조로 생성된 객체는 GC의 수집대상에서 제외 처리됨.     
> 
> 약한 참조 : WeakReference를 이용하여 new 할당된 객체를 참조하는 방식
> 할당된 객체를 해지하기 위해 null처리시 GC의 수집 대상이 되어 회수처리됨.


> 핵심 정리     
> 메모리 누수는 겉으로 잘 들어나지 않아 시스템에 수년간 잠복하는 사례도 있다.       
> 이런 누수는 철저한 코드 리뷰나 힙 프로파일러 같은 디버깅 도구를 동원해야만 발견되기도 한다.  
> 그래서 이런 종류의 문제는 예방법을 익혀두는것이 매우 중요하다.