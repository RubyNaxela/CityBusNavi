package com.rubynaxela.citybusnavi.gui;

import com.rubynaxela.citybusnavi.io.Directory;
import com.rubynaxela.citybusnavi.util.OsCheck;

import java.awt.*;
import java.io.File;

import static com.rubynaxela.citybusnavi.util.OsCheck.OSType.WINDOWS;

/**
 * This class is an implementation of {@link java.awt.FileDialog}.
 * Displays a dialog window from which the user can select a file.
 */
public final class AWTFileDialog extends FileDialog {

    /**
     * Creates a new file dialog. The default directory is user's home directory
     *
     * @param mode         {@link java.awt.FileDialog#LOAD} or {@link java.awt.FileDialog#SAVE}
     * @param anchor       the parent {@link java.awt.Frame} of the dialog
     * @param title        the title of the dialog window
     * @param proposedName the default file name
     */
    public AWTFileDialog(Frame anchor, int mode, String title, String proposedName) {
        super(anchor, title);
        if (proposedName != null) setFile(proposedName);
        openDirectory(new Directory(System.getProperty("user.home")));
        setMode(mode);
        setVisible(true);
    }

    /**
     * Creates a new file dialog. The default directory is user's home directory
     *
     * @param mode   {@link java.awt.FileDialog#LOAD} or {@link java.awt.FileDialog#SAVE}
     * @param anchor the parent {@link Frame} of the dialog
     * @param title  the title of the dialog window
     */
    public AWTFileDialog(Frame anchor, int mode, String title) {
        this(anchor, mode, title, null);
    }

    /**
     * Sets the default directory of the dialog. If not present, sets the default directory to user's home directory
     *
     * @param directory the default directory
     */
    public void openDirectory(Directory directory) {
        if (directory.exists()) setDirectory(directory.getAbsolutePath());
        else setDirectory(System.getProperty("user.home"));
    }

    /**
     * Sets an extensions filter of the dialog
     *
     * @param extensions accepted extensions, without leading dots
     */
    public void acceptExtensions(String... extensions) {
        if (OsCheck.getOperatingSystemType() != WINDOWS) {
            for (final String ext : extensions) setFilenameFilter((dir, name) -> name.endsWith("." + ext));
        } else {
            StringBuilder extFilter = new StringBuilder();
            for (final String ext : extensions) extFilter.append("*.").append(ext).append(";");
            setFile(extFilter.substring(0, extFilter.length() - 1));
            System.out.println("Filter: " + extFilter.substring(0, extFilter.length() - 1));
        }
    }

    public File getSelectedFile() {
        try {
            return new File(this.getDirectory(), this.getFile());
        } catch (NullPointerException ignored) {
            return null;
        }
    }
}
