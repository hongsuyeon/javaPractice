package Day04.effectiveJava03;

public class SingleTon2 {

    private static final SingleTon2 instance = new SingleTon2();

    private SingleTon2(){

    }
    public static SingleTon2 getInstance(){
        return instance;
    }
}
