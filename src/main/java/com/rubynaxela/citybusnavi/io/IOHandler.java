package com.rubynaxela.citybusnavi.io;

import com.rubynaxela.citybusnavi.CityBusNavi;
import com.rubynaxela.citybusnavi.assets.StringManager;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;

@SuppressWarnings("FieldCanBeLocal")
public final class IOHandler {

    private static final int BUFFER_SIZE = 1024;
    private static final int END_OF_STREAM = -1;

    private final StringManager stringManager;

    public IOHandler(CityBusNavi instance) {
        stringManager = instance.getStringManager();
    }

    /**
     * Downloads a file from an URL to the specified file
     *
     * @param source      the source URL
     * @param destination the destination file
     * @return whether the download succeeded
     */
    public boolean downloadFile(@NotNull URL source, @NotNull File destination) {

        try (BufferedInputStream in = new BufferedInputStream(source.openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(destination)) {
            byte[] dataBuffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, BUFFER_SIZE)) != END_OF_STREAM)
                fileOutputStream.write(dataBuffer, 0, bytesRead);
        } catch (UnknownHostException e) {
            System.err.println(stringManager.get("lang.error.server_connection"));
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * @param destination the destination directory
     * @param zipEntry    a {@link java.util.zip.ZipEntry} object from a {@link java.util.zip.ZipInputStream}
     * @return a reference to the newly created file in the target directory
     * @throws java.lang.SecurityException        if a required system property value cannot be accessed, or if a security
     *                                            manager exists and its {@link java.lang.SecurityManager#checkRead}
     *                                            method denies read access to the file
     * @throws java.io.IOException                if an I/O error occurs, which is possible because the construction
     *                                            of the canonical pathname may require filesystem queries
     * @throws java.lang.IllegalArgumentException if the zip entry is outside of the target directory
     */
    @NotNull
    private File newExtractedFile(@NotNull Directory destination, @NotNull ZipEntry zipEntry)
            throws SecurityException, IOException, IllegalArgumentException {

        File destFile = new File(destination, zipEntry.getName());
        String destDirPath = destination.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator))
            throw new IllegalArgumentException("Entry is outside of the target dir: " + zipEntry.getName());
        return destFile;
    }

    /**
     * Extracts files from a ZIP archive to the indicated directory
     *
     * @param sourceFile  the archive file
     * @param destination the destination directory
     * @throws java.io.FileNotFoundException if the file does not exist, is a directory rather than a regular
     *                                       file, or for some other reason cannot be opened for reading
     * @throws java.lang.SecurityException   if a security manager exists and its {@link java.lang.SecurityManager#checkRead}
     *                                       method denies read access to the file
     * @throws java.util.zip.ZipException    if a ZIP file error has occurred
     * @throws java.io.IOException           if an I/O error has occurred
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void unzipFile(@NotNull File sourceFile, @NotNull Directory destination)
            throws FileNotFoundException, SecurityException, ZipException, IOException {

        ZipInputStream zis = new ZipInputStream(new FileInputStream(sourceFile));
        ZipEntry zipEntry = zis.getNextEntry();

        while (zipEntry != null) {
            File extracted = newExtractedFile(destination, zipEntry);
            if (!zipEntry.isDirectory()) {
                // Extracting next file from ZIP
                File parentDir = extracted.getParentFile();
                if (!parentDir.isDirectory() && !parentDir.mkdirs())
                    throw new IOException("Failed to create directory " + parentDir);
                FileOutputStream fos = new FileOutputStream(extracted);
                int len;
                byte[] buffer = new byte[1024];
                while ((len = zis.read(buffer)) > 0)
                    fos.write(buffer, 0, len);
                fos.close();
            } else {
                // Extracting next directory from ZIP
                if (!extracted.isDirectory() && !extracted.mkdirs())
                    throw new IOException("Failed to create directory " + extracted);
            }
            zipEntry = zis.getNextEntry();
        }

        zis.closeEntry();
        zis.close();

        // Changing extensions from .txt to .csv
        for (final File dataFile : Objects.requireNonNull(destination.listFiles()))
            dataFile.renameTo(new File(destination, dataFile.getName().replace(".txt", ".csv")));
    }

    /**
     * Downloads and unpacks up-to-date GTFS data from the server
     *
     * @param source              the source URL
     * @param downloadDestination the destination file
     */
    public void retrieveGTFSData(@NotNull URL source, @NotNull File downloadDestination) {
        if (downloadFile(source, downloadDestination)) try {
            unzipFile(downloadDestination, new Directory(downloadDestination.getParent()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
