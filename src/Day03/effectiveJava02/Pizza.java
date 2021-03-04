package Day03.effectiveJava02;

import java.util.EnumSet;
import java.util.Objects;

public abstract class Pizza {

    public enum Topping {
        HAM, MUSHROOM, ORION, PEPPER, SAUSAGE
    }

    final EnumSet<Topping> toppings;

    abstract static class Builder<T extends Builder<T>> { //자신의 하위타입 매개변수를 받음 - 재귀적인 타입 매개변수
        EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);

        public T addTopping(Topping topping){
            toppings.add(Objects.requireNonNull(topping));
            return self();
        }

        abstract Pizza build(); // Convariant 리턴 타입을 위한 준비작업

        protected abstract T self(); // self-type 개념을 사용해서 메소드 체이닝이 가능하게 함.
    }

    public Pizza(Builder<?> builder) {
        toppings = builder.toppings;
    }
}
