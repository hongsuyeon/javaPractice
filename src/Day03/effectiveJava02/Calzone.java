package Day03.effectiveJava02;

import java.util.Objects;

public class Calzone extends  Pizza {

    private final boolean sauceInside;

    public static class Builder extends Pizza.Builder<Builder> {
        private boolean sauceInside = false;

        public Builder sauceInside(){
            sauceInside = true;
            return this;
        }


        @Override
        public Calzone build() {
            return new Calzone(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    public Calzone(Builder builder) {
        super(builder);
        sauceInside = builder.sauceInside;
    }

}
