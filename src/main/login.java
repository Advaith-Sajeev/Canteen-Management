package main;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

abstract class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

class Staff extends User {
    public Staff(String username, String password) {
        super(username, password);
    }
}

class Student extends User {
    public Student(String username, String password) {
        super(username, password);
    }
}

class CanteenStaff extends User {
    public CanteenStaff(String username, String password) {
        super(username, password);
    }
}

class Admin extends User {
    public Admin(String username, String password) {
        super(username, password);
    }
}

class LoginManager {
    private Map<String, User> userDatabase;

    public LoginManager() {
        userDatabase = new HashMap<>();

        userDatabase.put("mohan", new Staff("mohan", "password"));
        userDatabase.put("ehtesham", new Student("ehtesham", "qwerty"));
        userDatabase.put("Shaji", new CanteenStaff("Shaji", "hariislove"));
        userDatabase.put("admin", new Admin("admin", "adminpassword"));
    }

    public User loginUser(String username, String password) {
        User user = userDatabase.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}


public class login {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LoginManager loginManager = new LoginManager();
        MenuManager dailyMenu = new MenuManager(); // Initialize DailyMenu
        TimeSlotManager timeSlotManager = new TimeSlotManager(); // Initialize TimeSlotManager
        AdminCanteenManager adminCanteenManager = new AdminCanteenManager(); // Initialize AdminCanteenManager

        System.out.println("Welcome to the Canteen Management System!");

        User loggedInUser = null;

        while (loggedInUser == null) {
            try {
                System.out.print("Select user type (1. Staff, 2. Student, 3. Canteen Staff, 4. Admin): ");
                int userType = scanner.nextInt();
                scanner.nextLine();

                System.out.print("Enter username: ");
                String username = scanner.nextLine();

                System.out.print("Enter password: ");
                String password = scanner.nextLine();

                // Attempt to log in
                loggedInUser = loginManager.loginUser(username, password);

                if (loggedInUser != null) {
                    int userRole = -1;
                    if (loggedInUser instanceof Staff) {
                        userRole = 1;
                    } else if (loggedInUser instanceof Student) {
                        userRole = 2;
                    } else if (loggedInUser instanceof CanteenStaff) {
                        userRole = 3;
                    } else if (loggedInUser instanceof Admin) {
                        userRole = 4;
                    }

                    if (userRole == -1 || userRole != userType) {
                        System.out.println("Invalid user type or mismatched user type. Please login with the correct user type.");
                        loggedInUser = null;
                    } else {
                        System.out.println("Login successful. Welcome, " + loggedInUser.getUsername() + "!");

                        if (userType == 1 || userType == 2) {
                            displaycanteen.start();
                        } else if (userType == 3) {
                            StaffAccess.start(dailyMenu, timeSlotManager, scanner);
                        } else if (userType == 4) {
                            AdminAccess.start(adminCanteenManager, scanner);
                        }
                    }
                } else {
                    System.out.println("Invalid username or password. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer for user type.");
                scanner.nextLine();
            }
        }

        scanner.close();
    }
}

