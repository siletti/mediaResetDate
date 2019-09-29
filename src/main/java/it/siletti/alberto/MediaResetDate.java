package it.siletti.alberto;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;

import java.io.File;
import java.io.IOException;

abstract class MediaResetDate {

    Metadata metadata;
    File file;

    public MediaResetDate(File myFile) throws ImageProcessingException, IOException {
        this.file = myFile;
        this.metadata = ImageMetadataReader.readMetadata(myFile);
    }


    static void renameFile(File toBeRenamed, String new_name)
            throws IOException {

        Boolean success = false;

        //need to be in the same path
        File fileWithNewName = new File(toBeRenamed.getParent(), new_name);
        if (fileWithNewName.exists()) {
            throw new IOException("file exists: " + toBeRenamed.getName());
        }

        // Rename file (or directory) ... trick for windows
        for (int i = 0; i < 20; i++) {
            success = toBeRenamed.renameTo(fileWithNewName);
            if (success)
                break;
            System.gc();
            Thread.yield();
        }

        if (!success) {
            // File was not successfully renamed
            throw new IOException("cannot rename file: " + toBeRenamed.getName());
        }
    }

    abstract void changeDate() throws IOException;
}
