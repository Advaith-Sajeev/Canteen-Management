package main;

import java.util.ArrayList;
import java.util.List;

public class TimeSlotManager {
    private static final List<TimeSlot> availableTimeSlots = generateAvailableTimeSlots();

    private static List<TimeSlot> generateAvailableTimeSlots() {
        List<TimeSlot> timeSlots = new ArrayList<>();

        // Add time slots to the list
        timeSlots.add(new TimeSlot(1, "9:00 AM", "10:00 AM"));
        timeSlots.add(new TimeSlot(2, "10:00 AM", "11:00 AM"));
        timeSlots.add(new TimeSlot(3, "11:00 AM", "12:00 PM"));
        timeSlots.add(new TimeSlot(4, "12:00 PM", "1:00 PM"));
        timeSlots.add(new TimeSlot(5, "1:00 PM", "2:00 PM"));
        timeSlots.add(new TimeSlot(6, "2:00 PM", "3:00 PM"));
        timeSlots.add(new TimeSlot(7, "3:00 PM", "4:00 PM"));
        timeSlots.add(new TimeSlot(8, "4:00 PM", "5:00 PM"));
        timeSlots.add(new TimeSlot(9, "5:00 PM", "6:00 PM"));
        timeSlots.add(new TimeSlot(10, "6:00 PM", "7:00 PM"));
        timeSlots.add(new TimeSlot(11, "7:00 PM", "8:00 PM"));

        return timeSlots;
    }

    public static List<TimeSlot> getAvailableTimeSlots() {
        return availableTimeSlots;
    }

    public void addTimeSlot(String startTime, String endTime) {
        int newSlotNumber = availableTimeSlots.size() + 1;
        TimeSlot newTimeSlot = new TimeSlot(newSlotNumber, startTime, endTime);
        availableTimeSlots.add(newTimeSlot);
    }

    public boolean deleteTimeSlot(int slotNumber) {
        for (TimeSlot timeSlot : availableTimeSlots) {
            if (timeSlot.getSlotNumber() == slotNumber) {
                availableTimeSlots.remove(timeSlot);
                return true;
            }
        }
        return false;
    }
    public void displayTimeSlots() {
        for (TimeSlot timeSlot : availableTimeSlots) {
            System.out.println("Slot " + timeSlot.getSlotNumber() + ": " +
                    timeSlot.getStartTime() + " - " + timeSlot.getEndTime());
        }
    }
    public static void reorderSlotNumbers() {
        for (int i = 0; i < availableTimeSlots.size(); i++) {
            availableTimeSlots.get(i).slotNumber = i + 1;
        }
    }

}
