package main;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

class Canteen {
    private String name;
    private String location;
    private String operationalHours;

    public Canteen(String name, String location, String operationalHours) {
        this.name = name;
        this.location = location;
        this.operationalHours = operationalHours;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getOperationalHours() {
        return operationalHours;
    }
}

class CanteenManager {
    private List<Canteen> canteenList;

    public CanteenManager() {
        canteenList = new ArrayList<>();
        canteenList.add(new Canteen("Canteen A", "Location A", "9:00 AM - 5:00 PM"));
        canteenList.add(new Canteen("Canteen B", "Location B", "8:00 AM - 4:00 PM"));
        canteenList.add(new Canteen("Canteen C", "Location C", "10:00 AM - 6:00 PM"));
        // Add more canteens as needed
    }

    public List<Canteen> getCanteenList() {
        return canteenList;
    }

    public List<Canteen> getCanteenList(String location) {
        List<Canteen> filteredCanteens = new ArrayList<>();
        for (Canteen canteen : canteenList) {
            if (canteen.getLocation().equalsIgnoreCase(location)) {
                filteredCanteens.add(canteen);
            }
        }
        return filteredCanteens;
    }

    public List<Canteen> getCanteenList(int startTime, int endTime) {
        List<Canteen> filteredCanteens = new ArrayList<>();
        for (Canteen canteen : canteenList) {
            String[] hours = canteen.getOperationalHours().split("-");
            String[] start = hours[0].split(":");
            String[] end = hours[1].split(":");
            int startHour = Integer.parseInt(start[0].trim());
            int endHour = Integer.parseInt(end[0].trim());

            if (startHour >= startTime && endHour <= endTime) {
                filteredCanteens.add(canteen);
            }
        }
        return filteredCanteens;
    }

}

class displaycanteen {
    public static void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Canteen Display System!");

        System.out.println("How do you want to search the canteen?");
        System.out.println("1. Get canteen by index");
        System.out.println("2. Get canteen by name");
        System.out.print("Enter your choice (1-2): ");

        int choice = -1;
        boolean validChoice = false;

        do {
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice >= 1 && choice <= 3) {
                    validChoice = true;
                } else {
                    System.out.println("Invalid choice. Please enter a valid option (1-2).");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer (1-2).");
                scanner.nextLine();
            }
        } while (!validChoice);

        List<Canteen> canteenList = null;

        switch (choice) {
            case 1:
                canteenList = getCanteenList();
                break;
            case 2:
                canteenList = getCanteenList(scanner);
                break;
        }

        try {
            if (canteenList != null && !canteenList.isEmpty()) {
                System.out.println("Available Canteens:");
                for (int i = 0; i < canteenList.size(); i++) {
                    System.out.println((i + 1) + ". " + canteenList.get(i).getName());
                }

                int selectedCanteenIndex = -1;
                boolean validSelection = false;

                do {
                    System.out.print("Select a canteen (1-" + canteenList.size() + "): ");

                    try {
                        selectedCanteenIndex = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        if (selectedCanteenIndex >= 1 && selectedCanteenIndex <= canteenList.size()) {
                            validSelection = true;
                        } else {
                            System.out.println("Invalid selection. Please choose a valid canteen.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a valid integer for canteen selection.");
                        scanner.nextLine();
                    }
                } while (!validSelection);

                Canteen selectedCanteen = canteenList.get(selectedCanteenIndex - 1);
                System.out.println("You selected: " + selectedCanteen.getName());

                CanteenOrderingSystem.start();
            } else {
                System.out.println("No canteens available based on your search criteria.");
            }
        } finally {
            scanner.close();
        }
    }

    private static List<Canteen> getCanteenList() {
        CanteenManager canteenManager = new CanteenManager();
        return canteenManager.getCanteenList();
    }



    private static List<Canteen> getCanteenList(Scanner scanner) {
        List<Canteen> filteredCanteens = new ArrayList<>();
        boolean validInput = false;

        do {
            System.out.print("Enter the name of the canteen: ");
            String canteenName = scanner.nextLine();

            CanteenFilterByName filterByName = new CanteenFilterByName(canteenName);
            Thread filterByNameThread = new Thread(filterByName);
            filterByNameThread.start();

            try {
                filterByNameThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            filteredCanteens = filterByName.getFilteredCanteens();

            if (filteredCanteens.isEmpty()) {
                System.out.println("No canteen found with that name. Please try again.");
            } else {
                validInput = true;
            }
        } while (!validInput);

        return filteredCanteens;
    }



    static class CanteenFilterByHours implements Runnable {
        private final int startHour;
        private final int endHour;
        private List<Canteen> filteredCanteens;

        public CanteenFilterByHours(int startHour, int endHour) {
            this.startHour = startHour;
            this.endHour = endHour;
        }

        @Override
        public void run() {
            CanteenManager canteenManager = new CanteenManager();
            filteredCanteens = canteenManager.getCanteenList(startHour, endHour);
        }

        public List<Canteen> getFilteredCanteens() {
            return filteredCanteens;
        }
    }

    static class CanteenFilterByName implements Runnable {
        private final String canteenName;
        private List<Canteen> filteredCanteens;

        public CanteenFilterByName(String canteenName) {
            this.canteenName = canteenName;
        }

        @Override
        public void run() {
            CanteenManager canteenManager = new CanteenManager();
            List<Canteen> canteenList = canteenManager.getCanteenList();
            filteredCanteens = new ArrayList<>();

            for (Canteen canteen : canteenList) {
                if (canteen.getName().equalsIgnoreCase(canteenName)) {
                    filteredCanteens.add(canteen);
                }
            }
        }

        public List<Canteen> getFilteredCanteens() {
            return filteredCanteens;
        }
    }
}
