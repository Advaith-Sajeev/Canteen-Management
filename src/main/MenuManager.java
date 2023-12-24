package main;

import java.util.HashMap;
import java.util.Map;

public class MenuManager {
    private Map<Integer, MenuItem> menuItems;

    public MenuManager() {
        menuItems = new HashMap<>();
        initializeMenu();
    }

    private void initializeMenu() {
        menuItems.put(1, new MenuItem("ChickenBurger", "Delicious chicken burger", 6.99));
        menuItems.put(2, new MenuItem("VegetablePizza", "Veggie delight pizza", 9.99));
        menuItems.put(3, new MenuItem("SpaghettiBolognese", "Classic pasta dish", 8.49));
        menuItems.put(4, new MenuItem("CaesarSalad", "Fresh salad with Caesar dressing", 5.99));
        menuItems.put(5, new MenuItem("ChocolateCake", "Rich chocolate dessert", 4.99));
    }

    public Map<Integer, MenuItem> getMenuItems() {
        return menuItems;
    }

    public void addMenuItem(MenuItem menuItem) {
        int newItemNumber = menuItems.size() + 1;
        menuItems.put(newItemNumber, menuItem);
    }

    public boolean deleteMenuItem(int itemNumber) {
        if (menuItems.containsKey(itemNumber)) {
            menuItems.remove(itemNumber);
            return true;
        }
        return false;
    }

    public void displayMenu() {
        int index = 1;
        for (Map.Entry<Integer, MenuItem> entry : menuItems.entrySet()) {
            MenuItem menuItem = entry.getValue();
            System.out.println(index + ". " + menuItem.getName() + " - " +
                    menuItem.getDescription() + " - Rs. " + menuItem.getPrice());
            index++;
        }
    }
}
