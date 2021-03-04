package Day03.effectiveJava02;

public class NutritionFacts {
    //@Builder.Default , @Singular
    private int servinfSize;
    private int sodium;
    private int servings;
    private int carbohydrate;

    public void setServinfSize(int servinfSize) {
        this.servinfSize = servinfSize;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public void setCarbohydrate(int carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public NutritionFacts() {
    }

    public NutritionFacts(int servinfSize) {
        this.servinfSize = servinfSize;
    }

    public NutritionFacts(int servinfSize, int sodium) {
        this.servinfSize = servinfSize;
        this.sodium = sodium;
    }

    public NutritionFacts(int servinfSize, int sodium, int servings) {
        this.servinfSize = servinfSize;
        this.sodium = sodium;
        this.servings = servings;
    }

    public NutritionFacts(int servinfSize, int sodium, int servings, int carbohydrate) {
        this.servinfSize = servinfSize;
        this.sodium = sodium;
        this.servings = servings;
        this.carbohydrate = carbohydrate;
    }


    public static void main(String[] args) {
        NutritionFacts nutritionFacts0 = new NutritionFacts(1);
        NutritionFacts nutritionFacts1 = new NutritionFacts(1,6);
        NutritionFacts nutritionFacts2 = new NutritionFacts(1,6,10);
        NutritionFacts nutritionFacts3 = new NutritionFacts(1,6,10);
        NutritionFacts nutritionFacts = new NutritionFacts();
        nutritionFacts.setCarbohydrate(1);
        nutritionFacts.setServinfSize(20);
        nutritionFacts.setSodium(6);
        nutritionFacts.setServinfSize(8);
      //  NutritionFacts nutritionFacts = new NutritionFacts(1,6,10,3);
       // NutritionFacts.builder().servings(10).servinfSize(1).build();
    }
}
