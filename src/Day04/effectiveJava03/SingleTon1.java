package Day04.effectiveJava03;

public class SingleTon1 {

    public static final SingleTon1 instance = new SingleTon1();

    int count;

    private SingleTon1(){
        count++;
        System.out.println(count);
        if(count != 1){
            throw new IllegalStateException("this object should be singleTon");
        }
    }
}
