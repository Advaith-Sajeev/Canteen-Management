package main;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

class AdminCanteenManager {
    private List<Canteen> canteenList;

    public AdminCanteenManager() {
        canteenList = new ArrayList<>();
    }

    public List<Canteen> getCanteenList() {
        return canteenList;
    }

    public void addCanteen(Canteen canteen) {
        canteenList.add(canteen);
    }

    public boolean deleteCanteen(int index) {
        if (index >= 0 && index < canteenList.size()) {
            canteenList.remove(index);
            return true;
        }
        return false;
    }
}

public class AdminAccess {
    public static void start(AdminCanteenManager adminCanteenManager, Scanner scanner) {
        System.out.println("Hello, Admin!");

        int choice;
        do {
            System.out.println("Admin Menu:");
            System.out.println("1. Add Canteen");
            System.out.println("2. Remove Canteen");
            System.out.println("3. View Canteens");
            System.out.println("0. Exit");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addCanteen(adminCanteenManager, scanner);
                    break;
                case 2:
                    removeCanteen(adminCanteenManager, scanner);
                    break;
                case 3:
                    viewCanteens(adminCanteenManager);
                    break;
                case 0:
                    System.out.println("Exiting Admin Access.");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        } while (choice != 0);
    }

    private static void addCanteen(AdminCanteenManager adminCanteenManager, Scanner scanner) {
        System.out.println("Enter the details of the new canteen:");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Location: ");
        String location = scanner.nextLine();
        System.out.print("Operational Hours: ");
        String operationalHours = scanner.nextLine();

        adminCanteenManager.addCanteen(new Canteen(name, location, operationalHours));
        System.out.println("Canteen added successfully!");
    }

    private static void removeCanteen(AdminCanteenManager adminCanteenManager, Scanner scanner) {
        List<Canteen> canteenList = adminCanteenManager.getCanteenList();

        if (!canteenList.isEmpty()) {
            System.out.println("Available Canteens:");
            for (int i = 0; i < canteenList.size(); i++) {
                System.out.println((i + 1) + ". " + canteenList.get(i).getName());
            }

            System.out.print("Enter the number of the canteen to remove: ");
            int canteenNumber = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (canteenNumber >= 1 && canteenNumber <= canteenList.size()) {
                if (adminCanteenManager.deleteCanteen(canteenNumber - 1)) {
                    System.out.println("Canteen removed successfully!");
                } else {
                    System.out.println("Failed to remove the canteen.");
                }
            } else {
                System.out.println("Invalid canteen number. Please choose a valid canteen.");
            }
        } else {
            System.out.println("No canteens available to remove.");
        }
    }

    private static void viewCanteens(AdminCanteenManager adminCanteenManager) {
        List<Canteen> canteenList = adminCanteenManager.getCanteenList();

        if (!canteenList.isEmpty()) {
            System.out.println("Available Canteens:");
            for (int i = 0; i < canteenList.size(); i++) {
                System.out.println((i + 1) + ". " + canteenList.get(i).getName());
            }
        } else {
            System.out.println("No canteens available.");
        }
    }
}
