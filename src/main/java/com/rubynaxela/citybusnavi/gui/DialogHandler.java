package com.rubynaxela.citybusnavi.gui;

import com.rubynaxela.citybusnavi.CityBusNavi;
import com.rubynaxela.citybusnavi.assets.IconManager;
import com.rubynaxela.citybusnavi.assets.StringManager;
import com.rubynaxela.citybusnavi.data.datatypes.auxiliary.Trilean;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import static java.awt.FileDialog.LOAD;
import static java.awt.FileDialog.SAVE;

/**
 * This class contains dialog boxes derived into functions displaying the window and returning the received data
 */
@SuppressWarnings("unused")
public final class DialogHandler {

    private final StringManager stringManager;
    private final IconManager iconManager;

    private Component anchorFrame = null;

    public DialogHandler(CityBusNavi instance) {
        stringManager = instance.getStringManager();
        iconManager = instance.getIconManager();
    }

    /**
     * Sets the parent {@link java.awt.Component} of the dialogs. Must be called
     * before showing any dialog window, otherwise the window will not be visible
     *
     * @param frame the parent {@link java.awt.Component} of the dialogs
     */
    public void setAnchor(Component frame) {
        anchorFrame = frame;
    }

    /**
     * Shows an info message
     *
     * @param message the message content
     */
    public void showInfo(String message) {
        JOptionPane.showOptionDialog(anchorFrame, message, stringManager.get("lang.dialog.title.default"),
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                iconManager.get("icon.dialog.info"), new String[]{stringManager.get("lang.button.ok")},
                stringManager.get("lang.button.ok"));
    }

    /**
     * Shows a warning message
     *
     * @param message the message content
     */
    public void showWarning(String message) {
        JOptionPane.showOptionDialog(anchorFrame, message, stringManager.get("lang.dialog.title.warning"),
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                iconManager.get("icon.dialog.warning"), new String[]{stringManager.get("lang.button.ok")},
                stringManager.get("lang.button.ok"));
    }

    /**
     * Shows an error message
     *
     * @param message     the message content
     * @param exitOnClose determines whether to close the application after the dialog box is dismissed
     */
    public void showError(String message, boolean exitOnClose) {
        final String okButton = stringManager.get("lang.button.ok", "OK");
        JOptionPane.showOptionDialog(anchorFrame, message, stringManager.get("lang.dialog.title.error", "Error"),
                JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
                iconManager.get("icon.dialog.error"), new Object[]{okButton}, okButton);
        if (exitOnClose) System.exit(0);
    }

    /**
     * Shows an error message
     *
     * @param message the message content
     */
    public void showError(String message) {
        showError(message, false);
    }

    /**
     * Shows a YES-or-NO question dialog
     *
     * @param message       the message content
     * @param defaultAnswer this answer will be highlighted when the window is displayed ({@code true} for Yes and
     *                      {@code false} for No). It will be also used as the result if the window cannot be shown
     * @return <ul>
     * <li>{@code true} if the user answered Yes</li>
     * <li>{@code false} if the user answered No</li>
     * </ul>
     */
    public boolean askYesNoQuestion(String message, boolean defaultAnswer) {
        return JOptionPane.showOptionDialog(anchorFrame, message, stringManager.get("lang.dialog.title.default"),
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                iconManager.get("icon.dialog.question"), new String[]{
                        stringManager.get("lang.button.yes"), stringManager.get("lang.button.no")},
                defaultAnswer ? stringManager.get("lang.button.yes") : stringManager.get("lang.button.no")) == 0;
    }

    /**
     * Shows a YES-NO-CANCEL question dialog. The default answer is the Cancel option ({@link Trilean#NEUTRAL})
     *
     * @param message the message content
     * @return <ul>
     * <li>{@link Trilean#POSITIVE} if the user answered Yes</li>
     * <li>{@link Trilean#NEGATIVE} if the user answered No</li>
     * <li>{@link Trilean#NEUTRAL} if the user answered Cancel</li>
     * </ul>
     */
    public Trilean askYesNoCancelQuestion(String message) {
        int answer = JOptionPane.showOptionDialog(anchorFrame, message, stringManager.get("lang.dialog.title.default"),
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, iconManager.get("icon.dialog.question"),
                new String[]{stringManager.get("lang.button.yes"), stringManager.get("lang.button.no"),
                        stringManager.get("lang.button.cancel")},
                stringManager.get("lang.button.cancel"));
        return Trilean.from012Model(answer);
    }

    /**
     * Opens a file selection dialog in load mode
     *
     * @param extensions list of accepted file extensions. If empty, any extension is accepted
     * @return file chosen by the user
     */
    public File chooseFileToLoad(String... extensions) {
        AWTFileDialog dialog = new AWTFileDialog((Frame) anchorFrame, LOAD,
                stringManager.get("lang.dialog.file_chooser.file_to_open"));
        if (extensions.length > 0) dialog.acceptExtensions(extensions);
        return dialog.getSelectedFile();
    }

    /**
     * Opens a file selection dialog in save mode
     *
     * @param proposedName the default file name
     * @param extensions   list of accepted file extensions. If empty, any extension is accepted
     * @return file chosen by the user
     */
    public File chooseFileToSave(String proposedName, String... extensions) {
        AWTFileDialog dialog = new AWTFileDialog((Frame) anchorFrame, SAVE,
                stringManager.get("lang.dialog.file_chooser.file_to_save"), proposedName);
        if (extensions.length > 0) dialog.acceptExtensions(extensions);
        return dialog.getSelectedFile();
    }

}
