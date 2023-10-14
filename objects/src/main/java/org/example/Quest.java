package org.example;

import java.time.LocalDate;

public class Quest {
    private String name;
    private String description;
    private int deadline;
    private boolean isCompleted;

    public Quest(String name, String description, int deadline) {
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.isCompleted = false;
    }

    public Quest () {}

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getDeadline() {
        return deadline;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    @Override
    public String toString() {
        String status = isCompleted ? "Completed" : "Not Completed";
        String deadlineStr = deadline == -1 ? "         No Deadline" : "            Deadline: " + deadline;
        return "Quest: " + name + "\n               Description: " + description + "\n" +
                deadlineStr + "\n             Status: " + status;
    }
}
