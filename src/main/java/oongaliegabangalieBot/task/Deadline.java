package oongaliegabangalieBot.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {

    // DateTime formatter for displaying the deadline
    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("MMM d yyyy, hh:mm a");

    // DateTime formatter for parsing and storing dates
    private static final DateTimeFormatter STORAGE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // new variable - due date as LocalDateTime
    protected LocalDateTime by;

    // original by string (kept for compatibility)
    protected String byString;

    // constructs a new deadline with description and due date (string format)
    public Deadline(String description, String by) {
        super(description);
        this.byString = by;
        this.by = null; // Will be set if parsed successfully
        try {
            this.by = parseDateTime(by);
        } catch (Exception e) {
            // If parsing fails, keep the original string but don't set the LocalDateTime
            System.out.println("Warning: Date format not recognized for: " + by);
        }
    }

    // Constructs a new deadline with description and LocalDateTime
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
        this.byString = by.format(STORAGE_FORMATTER);
    }

    // Try to parse various date formats
    private LocalDateTime parseDateTime(String dateTimeStr) {
        try {
            // Try to parse using standard formats like:
            // yyyy-MM-dd HH:mm
            return LocalDateTime.parse(dateTimeStr, STORAGE_FORMATTER);
        } catch (Exception e1) {
            try {
                // Try to parse formats like d/M/yyyy HHmm (e.g., 2/12/2019 1800)
                DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
                return LocalDateTime.parse(dateTimeStr, customFormatter);
            } catch (Exception e2) {
                throw new IllegalArgumentException("Could not parse date: " + dateTimeStr);
            }
        }
    }

    public String getBy() {
        return byString;
    }

    public LocalDateTime getByDateTime() {
        return by;
    }

    // For storage purposes, return the standardized date format
    public String getByForStorage() {
        return by != null ? by.format(STORAGE_FORMATTER) : byString;
    }

    @Override
    public String toString() {
        if (by != null) {
            // Display formatted date if available
            return "[D]" + super.toString() + " (by: " + by.format(DISPLAY_FORMATTER) + ")";
        } else {
            // Fallback to original string if date parsing failed
            return "[D]" + super.toString() + " (by: " + byString + ")";
        }
    }
}