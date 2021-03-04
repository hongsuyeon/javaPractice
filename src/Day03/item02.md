# 2021-03-04

Item02. 생성자에 매개변수가 많다면 빌더패턴을 고려하라.
--------------------------------------------

1. 점층적 생성자 패턴을 사용 -> *_매개변수의 개수가 많아지면 클라이언트 코드를 작성하거나 읽기 어려워진다._*
```java
public class NutritionFacts {
    private int servinfSize;
    private int sodium;
    private int servings;
    private int carbohydrate;

    public NutritionFacts() {
    }

    public NutritionFacts(int servinfSize) {
        this.servinfSize = servinfSize;
    }

    public NutritionFacts(int servinfSize, int sodium) {
        this.servinfSize = servinfSize;
        this.sodium = sodium;
    }

    public NutritionFacts(int servinfSize, int sodium, int servings) {
        this.servinfSize = servinfSize;
        this.sodium = sodium;
        this.servings = servings;
    }

    public NutritionFacts(int servinfSize, int sodium, int servings, int carbohydrate) {
        this.servinfSize = servinfSize;
        this.sodium = sodium;
        this.servings = servings;
        this.carbohydrate = carbohydrate;
    }


    public static void main(String[] args) {
        NutritionFacts nutritionFacts0 = new NutritionFacts(1);
        NutritionFacts nutritionFacts1 = new NutritionFacts(1,6);
        NutritionFacts nutritionFacts2 = new NutritionFacts(1,6,10);
        NutritionFacts nutritionFacts3 = new NutritionFacts(1,6,10);
    }
}
```
2. 매개변수의 개수가 많을 경우 활용할 수 있는 대안 -> *_자바빈즈 패턴_*
 : 매개 변수가 없는 생성자로 객체를 만든 후, 세터 메서드들을 호출해 원하는 매개변수의 값을 설정하는 방식
   ==> 메서드를 여러개 호출해야하고, 객체가 완전히 생성되기전까지는 일관성이 무너진 상태에 놓임..
       클래스를 불변상태로 만들 수 없고, 스레드 안정성을 위해서는 추가적인 작업 필요. 
```java
public class NutritionFacts {
    private int servinfSize;
    private int sodium;
    private int servings;
    private int carbohydrate;

    public NutritionFacts() {
    }

    public void setServinfSize(int servinfSize) {
        this.servinfSize = servinfSize;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }
    
    public void setServings(int servings) {
        this.servings = servings;
    }

    public void setCarbohydrate(int carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public static void main(String[] args) {
        NutritionFacts nutritionFacts = new NutritionFacts();
        NutritionFacts nutritionFacts = new NutritionFacts();
        nutritionFacts.setCarbohydrate(1);
        nutritionFacts.setServinfSize(20);
        nutritionFacts.setSodium(6);
        nutritionFacts.setServinfSize(8);
        
    }
}
```   

`대안책 ::::: 빌더 패턴(점층적 생성자 패턴의 안정성과 자바 빈즈 패턴의 가독성을 겸비)`
