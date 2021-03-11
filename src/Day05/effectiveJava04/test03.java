package Day05.effectiveJava04;

public class test03 extends test02 {
    public static void main(String[] args) {
        test02 test = new test03();
        System.out.println(test.protectedVal);
        System.out.println(test.publicVal);
        System.out.println(test.defaultVal);
    }
}
