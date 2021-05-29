package com.rubynaxela.citybusnavi.gui.components;

import com.rubynaxela.citybusnavi.gui.components.std.DefaultJPanel;

import javax.swing.*;

public final class RouteStopsListPanel extends DefaultJPanel {
    public final JLabel titleLabel = new JLabel();
    public final JList<String> stopsList = new JList<>();
}
