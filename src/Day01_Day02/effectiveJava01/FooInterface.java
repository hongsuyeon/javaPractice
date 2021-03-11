package Day01_Day02.effectiveJava01;

public interface FooInterface {

    static final Foo GOOD_NIGHT = new Foo();

    public static Foo getFoo(){
        return new Foo();
    }
}
