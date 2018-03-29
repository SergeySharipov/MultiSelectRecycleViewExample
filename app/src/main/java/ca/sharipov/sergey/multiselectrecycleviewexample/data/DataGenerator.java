package ca.sharipov.sergey.multiselectrecycleviewexample.data;

import java.util.ArrayList;

public class DataGenerator {

    static public ArrayList<Item> generateItems(int n) {
        ArrayList<Item> items = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Item item = new Item(String.valueOf(i), "Item-" + i);
            items.add(item);
        }
        return items;
    }
}
