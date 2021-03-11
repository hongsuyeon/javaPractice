package Day05.effectiveJava04;

import javax.rmi.CORBA.Util;

/*public abstract class UtilClass {*/
public class UtilClass {
    public static String getName(){
        return "hongsu";
    }
    //유틸 클래스라 인스턴스를 만들지 못하게 막았습니
    private UtilClass(){
        throw new AssertionError();
    }

    static class AnotherClass extends  UtilClass {

    }

    public static void main(String[] args) {
       //1차적으로 방어
        // UtilClass utilClass = new UtilClass();
        AnotherClass anotherClass = new AnotherClass();
        UtilClass.getName();

    }
}
