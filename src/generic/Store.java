package generic;

import java.util.List;

public class Store {
    public static void main(String[] args) {

        AppleDao appleDao = new AppleDao();
        appleDao.save(Apple.of(1));
        appleDao.save(Apple.of(2));

        List<Apple> all = appleDao.findAll();
        all.forEach(System.out::println);
    }
}
