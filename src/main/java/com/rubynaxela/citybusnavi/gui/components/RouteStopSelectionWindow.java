package com.rubynaxela.citybusnavi.gui.components;

import com.rubynaxela.citybusnavi.data.datatypes.gtfs.Route;
import com.rubynaxela.citybusnavi.gui.components.renderers.RouteLabel;
import com.rubynaxela.citybusnavi.gui.components.std.DefaultJFrame;
import com.rubynaxela.citybusnavi.gui.components.std.DefaultJLabel;
import com.rubynaxela.citybusnavi.gui.components.std.DefaultJPanel;

import javax.swing.border.EmptyBorder;
import java.awt.*;

public final class RouteStopSelectionWindow extends DefaultJFrame {

    public final RouteLabel routeNumberLabel;
    public final DefaultJLabel routeNameLabel = new DefaultJLabel();
    public final DefaultJPanel stopLists = new DefaultJPanel();

    public RouteStopSelectionWindow(Route route) {
        super(false);
        routeNumberLabel = new RouteLabel(route, 24);
        routeNameLabel.setFontSizeAndStyle(16, Font.BOLD);
        routeNameLabel.setBorder(new EmptyBorder(0, 5, 0, 0));
    }
}
