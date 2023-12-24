package main;

import java.util.Scanner;

public class StaffAccess {
    public static void start(MenuManager dailyMenu, TimeSlotManager timeSlotManager, Scanner scanner) {
        System.out.println("Hello, Canteen Staff!");

        int choice;
        do {
            System.out.println("Menu:");
            System.out.println("1. View Menu");
            System.out.println("2. Add to Menu");
            System.out.println("3. Delete from Menu");
            System.out.println("4. View Time Slots");
            System.out.println("5. Add Time Slot");
            System.out.println("6. Delete Time Slot");
            System.out.println("0. Exit");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewMenu(dailyMenu);
                    break;
                case 2:
                    addMenuItem(dailyMenu, scanner);
                    break;
                case 3:
                    deleteMenuItem(dailyMenu, scanner);
                    break;
                case 4:
                    viewTimeSlots(timeSlotManager);
                    break;
                case 5:
                    addTimeSlot(timeSlotManager, scanner);
                    break;
                case 6:
                    deleteTimeSlot(timeSlotManager, scanner);
                    break;
                case 0:
                    System.out.println("Exiting Staff Access.");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        } while (choice != 0);
    }

    private static void viewMenu(MenuManager dailyMenu) {
        System.out.println("Current Menu Items:");
        dailyMenu.displayMenu();
    }

    private static void viewTimeSlots(TimeSlotManager timeSlotManager) {
        System.out.println("Available Time Slots:");
        timeSlotManager.displayTimeSlots();
    }

    private static void addMenuItem(MenuManager dailyMenu, Scanner scanner) {
        System.out.println("Enter the details of the new menu item:");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Description: ");
        String description = scanner.nextLine();
        System.out.print("Price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();


        dailyMenu.addMenuItem(new MenuItem(name, description, price));
        System.out.println("Menu item added successfully!");
    }

    private static void deleteMenuItem(MenuManager dailyMenu, Scanner scanner) {
        System.out.println("Current Menu Items:");
        dailyMenu.displayMenu();

        System.out.print("Enter the number of the item to delete: ");
        int itemNumber = scanner.nextInt();
        scanner.nextLine();


        if (dailyMenu.deleteMenuItem(itemNumber)) {
            System.out.println("Menu item deleted successfully!");
        } else {
            System.out.println("Invalid item number. Deletion failed.");
        }
    }

    private static void addTimeSlot(TimeSlotManager timeSlotManager, Scanner scanner) {
        System.out.print("Enter the start time of the new time slot (HH:mm AM/PM): ");
        String startTime = scanner.nextLine();
        System.out.print("Enter the end time of the new time slot (HH:mm AM/PM): ");
        String endTime = scanner.nextLine();

        timeSlotManager.addTimeSlot(startTime, endTime);
        System.out.println("Time slot added successfully!");
    }

    private static void deleteTimeSlot(TimeSlotManager timeSlotManager, Scanner scanner) {
        System.out.println("Available Time Slots:");
        timeSlotManager.displayTimeSlots();

        System.out.print("Enter the number of the time slot to delete: ");
        int slotNumber = scanner.nextInt();
        scanner.nextLine();


        if (timeSlotManager.deleteTimeSlot(slotNumber)) {
            System.out.println("Time slot deleted successfully!");
        } else {
            System.out.println("Invalid time slot number. Deletion failed.");
        }
    }
}
