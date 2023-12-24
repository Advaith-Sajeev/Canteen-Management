package main;
import java.util.*;

class MenuItem {
    private String name;
    private String description;
    private double price;

    public MenuItem(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }
}

class DailyMenu {
    private Map<Integer, MenuItem> menuItems;

    public DailyMenu(MenuManager menuManager) {
        menuItems = new HashMap<>();
        initializeMenu(menuManager);
    }

    private void initializeMenu(MenuManager menuManager) {
        menuItems = menuManager.getMenuItems();
    }

    public Map<Integer, MenuItem> getMenuItems() {
        return menuItems;
    }
}

class ShoppingCart {
    private Map<MenuItem, Integer> items;

    public ShoppingCart() {
        items = new HashMap<>();
    }

    public void addItem(MenuItem item) {
        items.put(item, items.getOrDefault(item, 0) + 1);
    }

    public Map<MenuItem, Integer> getItems() {
        return items;
    }

    public double calculateTotalCost() {
        return items.entrySet().stream().mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue()).sum();
    }
}

class CanteenCustomer {
    public static void displayMenu(DailyMenu dailyMenu) {
        System.out.println("Today's Menu:");
        for (Map.Entry<Integer, MenuItem> entry : dailyMenu.getMenuItems().entrySet()) {
            System.out.println(entry.getKey() + ". " + entry.getValue().getName() + " - " +
                    entry.getValue().getDescription() + " - Rs. " + entry.getValue().getPrice());
        }
    }

    public void orderFood(DailyMenu dailyMenu, ShoppingCart cart, Scanner scanner) {
        System.out.println("Enter the number of the item you want to order (item number, quantity) or type 'done' to finish:");

        boolean continueOrdering = true;

        while (continueOrdering) {
            String userInput = scanner.nextLine();

            if (userInput.equalsIgnoreCase("done")) {
                continueOrdering = false;
            } else {
                String[] inputTokens = userInput.split(",");
                if (inputTokens.length != 2) {
                    System.out.println("Invalid input format. Please provide item number and quantity separated by a comma.");
                    continue;
                }

                try {
                    int itemNumber = Integer.parseInt(inputTokens[0].trim());
                    int quantity = Integer.parseInt(inputTokens[1].trim());

                    MenuItem selectedMenuItem = dailyMenu.getMenuItems().get(itemNumber);

                    if (selectedMenuItem != null) {
                        for (int i = 0; i < quantity; i++) {
                            cart.addItem(selectedMenuItem);
                        }
                        System.out.println(quantity + "x " + selectedMenuItem.getName() + " added to the cart successfully!");
                    } else {
                        System.out.println("Invalid menu item number. Please choose a valid item.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter valid numbers for item number and quantity.");
                }
            }
        }
    }

    public void displayTotalCost(ShoppingCart cart) {
        System.out.println("Items in the cart:");
        for (Map.Entry<MenuItem, Integer> entry : cart.getItems().entrySet()) {
            System.out.println(entry.getValue() + "x " + entry.getKey().getName() + " - Rs ." +
                    entry.getKey().getPrice() + " each");
        }
        System.out.println("Total cost: Rs. " + cart.calculateTotalCost());
    }
}

public class CanteenOrderingSystem {
    public static void start() {
        // Initialization
        MenuManager menuManager = new MenuManager();
        DailyMenu dailyMenu = new DailyMenu(menuManager);
        ShoppingCart cart = new ShoppingCart();
        CanteenCustomer canteenCustomer = new CanteenCustomer();
        Scanner scanner = new Scanner(System.in);

        // Display menu
        canteenCustomer.displayMenu(dailyMenu);

        // User orders items
        canteenCustomer.orderFood(dailyMenu, cart, scanner);

        // Display total cost
        canteenCustomer.displayTotalCost(cart);

        // Close scanner
        CanteenPaymentSystem.start();
        scanner.close();
    }
}
