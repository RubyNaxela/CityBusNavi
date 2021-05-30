package com.rubynaxela.citybusnavi.gui.components;

import com.rubynaxela.citybusnavi.gui.components.std.DefaultJButton;
import com.rubynaxela.citybusnavi.gui.components.std.DefaultJFrame;

/**
 * This class represents the structure of the application main window GUI
 */
public final class MainWindow extends DefaultJFrame {

    public final DefaultJButton updateButton = new DefaultJButton();
    public final DefaultJButton demoMapButton = new DefaultJButton();
    public final DefaultJButton openLineScheduleButton = new DefaultJButton();
    public final DefaultJButton openStopScheduleButton = new DefaultJButton();

    public MainWindow() {
        super(true);
    }
}
