package oongaliegabangalieBot.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {

    // DateTime formatter for displaying dates
    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("MMM d yyyy, hh:mm a");

    // DateTime formatter for parsing and storing dates
    private static final DateTimeFormatter STORAGE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // new variables - start and end time as LocalDateTime
    protected LocalDateTime fromDateTime;
    protected LocalDateTime toDateTime;

    // original string versions (kept for compatibility)
    protected String from;
    protected String to;

    // constructs a new event with description, start and end times as strings
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;

        // Try to parse the date/time strings
        try {
            this.fromDateTime = parseDateTime(from);
        } catch (Exception e) {
            // If parsing fails, keep as null
            this.fromDateTime = null;
        }

        try {
            this.toDateTime = parseDateTime(to);
        } catch (Exception e) {
            // If parsing fails, keep as null
            this.toDateTime = null;
        }
    }

    // Constructs a new event with description and LocalDateTime objects
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.fromDateTime = from;
        this.toDateTime = to;
        this.from = from.format(STORAGE_FORMATTER);
        this.to = to.format(STORAGE_FORMATTER);
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

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public LocalDateTime getFromDateTime() {
        return fromDateTime;
    }

    public LocalDateTime getToDateTime() {
        return toDateTime;
    }

    // For storage purposes, return the standardized date format
    public String getFromForStorage() {
        return fromDateTime != null ? fromDateTime.format(STORAGE_FORMATTER) : from;
    }

    public String getToForStorage() {
        return toDateTime != null ? toDateTime.format(STORAGE_FORMATTER) : to;
    }

    @Override
    public String toString() {
        if (fromDateTime != null && toDateTime != null) {
            // Display formatted dates if available
            return "[E]" + super.toString() + " (from: " + fromDateTime.format(DISPLAY_FORMATTER) +
                    " to: " + toDateTime.format(DISPLAY_FORMATTER) + ")";
        } else {
            // Fallback to original strings if date parsing failed
            return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
        }
    }
}