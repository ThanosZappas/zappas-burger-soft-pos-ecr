package gr.android.cineflix.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Helper {

    public static String formatDate(String date) {
        if (date == null || date.isEmpty()) {
            return "Unknown Date";
        }

        // Check if the date is already in the correct format (d MMMM yyyy)
        try {
            SimpleDateFormat desiredFormat = new SimpleDateFormat("d MMMM yyyy", Locale.US);
            desiredFormat.setLenient(false);
            Date testDate = desiredFormat.parse(date);
            // If we can parse it and it matches the original string, it's already in correct format
            if (desiredFormat.format(testDate).equals(date)) {
                return date;
            }
        } catch (ParseException e) {
            // Not in the desired format, continue with other formats
        }

        // Try different date formats
        SimpleDateFormat[] possibleFormats = {
            new SimpleDateFormat("yyyy-MM-dd", Locale.US),
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US),
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
        };

        Date parsedDate = null;
        for (SimpleDateFormat format : possibleFormats) {
            try {
                format.setLenient(false);
                parsedDate = format.parse(date);
                if (parsedDate != null) {
                    break;
                }
            } catch (ParseException e) {
                // Continue trying other formats
            }
        }

        if (parsedDate == null) {
            return "Invalid Date";
        }

        // Format the date in the desired format
        SimpleDateFormat outputFormat = new SimpleDateFormat("d MMMM yyyy", Locale.US);
        return outputFormat.format(parsedDate);
    }

    public static String formatRuntime(int runtime) {
        if (runtime <= 0) {
            return "Unknown Runtime";
        }
        int hours = runtime / 60;
        int minutes = runtime % 60;
        return String.format(Locale.US, "%d hr %d min", hours, minutes);
    }
}
