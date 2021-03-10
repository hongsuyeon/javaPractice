package Day04.effectiveJava03;

import java.util.function.Supplier;

public class SingleTest1 {
    public static void main(String[] args) throws NoSuchMethodException {
       // SingleTon1 singleTon1 = new SingleTon1();
        SingleTon1 singleTon1 = SingleTon1.instance;
        SingleTon1 singleTon2 = SingleTon1.instance;

        SingleTon2 singleTon2_1 = SingleTon2.getInstance();
        SingleTon2 singleTon2_2 = SingleTon2.getInstance();
        SingleTon2 singleTon2_3 = SingleTon2.getInstance();

        Supplier<SingleTon2> suppler2 = SingleTon2::getInstance;

        String name = SingleTOn3.INSTANCE.getName();
    }
}
