package main;

public class TimeSlot {
    public int slotNumber;
    private String startTime;
    private String endTime;

    public TimeSlot(int slotNumber, String startTime, String endTime) {
        this.slotNumber = slotNumber;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}
