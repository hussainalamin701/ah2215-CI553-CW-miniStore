package catalogue;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class BetterBasketTest {

    @Test
    void testAddProduct() {
        ArrayList<Product> items = new ArrayList<>();
        Product p1 = new Product("0001","toaster", 12.3, 1);
        Product p2 = new Product("0002","kettle", 12.7, 1);

        items.add(p1);
        items.add(p2);
        items.add(p2);
        items.add(p2);


    }
}