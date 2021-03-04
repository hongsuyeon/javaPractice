package Day03.effectiveJava02;

public class PizzaClient {
    public static void main(String[] args) {

        MyPizza myPizza = new MyPizza.Builder(MyPizza.Size.MEDIUM).addTopping(Pizza.Topping.HAM).addTopping(Pizza.Topping.MUSHROOM).build();
        Calzone calzone = new Calzone.Builder().addTopping(Pizza.Topping.PEPPER).sauceInside().build();

    }
}
