package com.rubynaxela.citybusnavi.gui.components.renderers;

import com.rubynaxela.citybusnavi.data.datatypes.gtfs.Route;
import com.rubynaxela.citybusnavi.gui.components.std.DefaultJLabel;

import javax.swing.*;
import java.awt.*;

public class RouteLabel extends DefaultJLabel {

    private final Route route;

    public RouteLabel(Route route, int fontSize) {
        super(route.routeShortName, SwingConstants.CENTER);
        this.route = route;
        setFontSizeAndStyle(fontSize, Font.BOLD);
        setForeground(route.routeTextColor);
        setBackground(route.routeColor);
        setPreferredSize(new Dimension((int) (2.75 * fontSize), (int) (2.75 * fontSize)));
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        if (route.routeType == Route.RouteType.TRAM)
            g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);
        else
            g.fillRect(0, 0, getSize().width - 1, getSize().height - 1);
        super.paintComponent(g);
    }
}
