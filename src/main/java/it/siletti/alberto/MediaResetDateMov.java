package it.siletti.alberto;

import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.mov.QuickTimeDirectory;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class MediaResetDateMov extends MediaResetDate {
    public MediaResetDateMov(File file) throws ImageProcessingException, IOException {
        super(file);
    }

    @Override
    void changeDate() throws IOException {

        // obtain the Exif directory
        QuickTimeDirectory directory
                = metadata.getFirstDirectoryOfType(QuickTimeDirectory.class);

        // query the tag's value
        if (directory != null) {
            Date date = directory.getDate(QuickTimeDirectory.TAG_CREATION_TIME);
            if (date != null) {
                file.setLastModified(date.getTime());
                LocalDate localDate = date.toInstant().atZone(ZoneId.of("Europe/Rome")).toLocalDate();
                int year = localDate.getYear();
                int month = localDate.getMonthValue();
                int day = localDate.getDayOfMonth();
                String new_name = year + "-" + month + "-" + day + "-" + file.getName();

                renameFile(file, new_name);
            }
        }

    }
}
