package main;

import java.util.List;
import java.util.Scanner;

class SlotAllotmentModule {
    private static final double PENALTY_AMOUNT = 10.0;

    public static void reserveTimeSlot(Scanner scanner) {
        List<TimeSlot> availableTimeSlots = TimeSlotManager.getAvailableTimeSlots();
        displayAvailableTimeSlots(availableTimeSlots, scanner);
    }

    private static void displayAvailableTimeSlots(List<TimeSlot> timeSlots, Scanner scanner) {
        if (timeSlots.isEmpty()) {
            System.out.println("No available time slots at the moment. Please try again later.");
            return;
        }

        System.out.println("Available Time Slots for Order Collection:");
        for (int i = 0; i < timeSlots.size(); i++) {
            System.out.println((i + 1) + ". " + timeSlots.get(i).getStartTime() + " to " + timeSlots.get(i).getEndTime());
        }

        System.out.print("Choose a time slot (1-" + timeSlots.size() + "): ");
        int selectedSlotIndex = getValidSlotIndex(scanner, timeSlots.size());

        if (selectedSlotIndex != -1) {
            TimeSlot selectedTimeSlot = timeSlots.get(selectedSlotIndex);
            System.out.println("You have successfully reserved the time slot:");
            System.out.println("Slot Number: " + selectedTimeSlot.getSlotNumber());
            System.out.println("Time: " + selectedTimeSlot.getStartTime() + " to " + selectedTimeSlot.getEndTime());


            checkLateArrival(selectedTimeSlot, scanner);
        } else {
            System.out.println("Invalid selection. Please choose a valid time slot.");
        }
    }

    private static int getValidSlotIndex(Scanner scanner, int size) {
        while (true) {
            try {
                int selectedSlotIndex = Integer.parseInt(scanner.nextLine());
                if (selectedSlotIndex >= 1 && selectedSlotIndex <= size) {
                    return selectedSlotIndex - 1;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.print("Invalid input. Please choose a valid time slot (1-" + size + "): ");
        }
    }

    private static void checkLateArrival(TimeSlot selectedTimeSlot, Scanner scanner) {
        System.out.print("Enter the actual arrival time (HH:mm AM/PM): ");
        String arrivalTime = scanner.nextLine();

        if (isLate(selectedTimeSlot.getEndTime(), arrivalTime)) {
            double penalty = PENALTY_AMOUNT;
            System.out.println("You arrived late! A penalty of Rs. " + penalty + " will be charged.");
        } else {
            System.out.println("Thank you for arriving on time!");
        }
    }

    private static boolean isLate(String expectedEndTime, String arrivalTime) {
        try {
            String[] expectedEndTimeParts = expectedEndTime.split(" ");
            String[] arrivalTimeParts = arrivalTime.split(" ");

            String expectedTime = expectedEndTimeParts[0];
            String expectedPeriod = expectedEndTimeParts[1];
            String arrivalHour = arrivalTimeParts[0].split(":")[0];
            String arrivalMinute = arrivalTimeParts[0].split(":")[1];
            String arrivalPeriod = arrivalTimeParts[1];

            int expectedHour = Integer.parseInt(expectedTime.split(":")[0]);
            int expectedMinute = Integer.parseInt(expectedTime.split(":")[1]);

            int arrivalHourInt = Integer.parseInt(arrivalHour);
            int arrivalMinuteInt = Integer.parseInt(arrivalMinute);

            if (arrivalPeriod.equals("PM") && !expectedPeriod.equals("PM")) {
                arrivalHourInt += 12;
            }

            if (arrivalPeriod.equals("AM") && expectedPeriod.equals("PM")) {
                arrivalHourInt += 12;
            }

            if (arrivalHourInt > expectedHour || (arrivalHourInt == expectedHour && arrivalMinuteInt > expectedMinute)) {
                return true; // Arrived late
            }

        } catch (Exception e) {
            // Handle parsing or other exceptions
            e.printStackTrace();
        }
        return false; // Arrived on time or early
    }

}


public class CanteenSlotAllotmentSystem {
    public static void start() {

        Scanner scanner = new Scanner(System.in);


        SlotAllotmentModule.reserveTimeSlot(scanner);


        scanner.close();
    }
}
