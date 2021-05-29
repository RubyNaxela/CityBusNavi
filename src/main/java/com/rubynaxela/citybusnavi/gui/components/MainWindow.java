package com.rubynaxela.citybusnavi.gui.components;

import com.rubynaxela.citybusnavi.gui.components.std.DefaultJButton;
import com.rubynaxela.citybusnavi.gui.components.std.DefaultJFrame;

/**
 * This class represents the structure of the application main window GUI
 */
public final class MainWindow extends DefaultJFrame {

    public final DefaultJButton updateButton = new DefaultJButton();
    public final DefaultJButton demoButton = new DefaultJButton();
    public final DefaultJButton openScheduleButton = new DefaultJButton();

    public MainWindow() {
        super(true);
    }
}
