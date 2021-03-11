package Day05.effectiveJava04;

public class test01 {

    private static String privateVal = "private 입니다.";

    private static String publicVal = "public 입니다.";

    protected static String protectedVal = "protected 입니다.";

    static String defaultVal = "default 입니다.";

    public static void main(String[] args) {
        System.out.println(privateVal);
        System.out.println(protectedVal);
        System.out.println(defaultVal);
        System.out.println(publicVal);
    }

}
