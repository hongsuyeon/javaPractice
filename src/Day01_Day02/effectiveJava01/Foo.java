package Day01_Day02.effectiveJava01;

import java.util.EnumSet;

public class Foo {

    String name;
    String address;

    public Foo() {
    }

    private static final Foo GOOD_NIGHT = new Foo();

    public Foo(String name){
        this.name = name;
    }

    public static Foo withAddress(String address){
        Foo foo = new Foo();
        foo.address = address;
        return foo;
    }

    public static Foo getFoo(){
        return GOOD_NIGHT;
    }

    public static Foo getFoo(boolean flag){
        return flag ? new Foo() : new BarFoo();
    }

    public static void main(String[] args) {
        Foo foo = new Foo("hongsu");
        Foo foo1 = Foo.withAddress("suwon");
        Foo foo2 = Foo.getFoo();
        Foo foo3 = Foo.getFoo(false);

        EnumSet<Color> color = EnumSet.allOf(Color.class);

        EnumSet<Color> blueAndWhite = EnumSet.of(Color.BLUE, Color.RED);

        Object a = Foo.getFoo(false);
        System.out.println(a.getClass());
    }

    static class BarFoo extends Foo {

    }

    enum Color {
        RED, BLUE, WHITE
    }
    //private static method가 필요한 이유
    public static void doSomething(){
        게임을하고잔다();
    }

    public static void doSomethingTomorrow(){
        게임을하고잔다();
    }

    private static void 게임을하고잔다(){
        // TODO 게임하기.
        // TODO 잔다.
    }
}

