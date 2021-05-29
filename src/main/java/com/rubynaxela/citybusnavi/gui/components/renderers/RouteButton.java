package com.rubynaxela.citybusnavi.gui.components.renderers;

import com.rubynaxela.citybusnavi.data.datatypes.gtfs.Route;
import com.rubynaxela.citybusnavi.gui.components.std.DefaultJButton;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class RouteButton extends DefaultJButton {

    private final Route route;
    private Shape shape;

    public RouteButton(Route route, int fontSize) {
        super(route.routeShortName);
        this.route = route;
        setFontSizeAndStyle(fontSize, Font.BOLD);
        setForeground(route.routeTextColor);
        setBackground(route.routeColor);
        setPreferredSize(new Dimension((int) (2.75 * fontSize), (int) (2.75 * fontSize)));
        setMargin(new Insets(0, 0, 0, 0));
        if (route.routeType == Route.RouteType.TRAM) setContentAreaFilled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) g.setColor(getBackground().brighter());
        else g.setColor(getBackground());
        if (route.routeType == Route.RouteType.TRAM)
            g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);
        else
            g.fillRect(0, 0, getSize().width - 1, getSize().height - 1);
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(UIManager.getColor("Component.borderColor"));
        if (route.routeType == Route.RouteType.TRAM)
            g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
        else
            g.drawRect(0, 0, getSize().width - 1, getSize().height - 1);
    }

    @Override
    public boolean contains(int x, int y) {
        if (route.routeType == Route.RouteType.TRAM) {
            if (shape == null || !shape.getBounds().equals(getBounds()))
                shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
            return shape.contains(x, y);
        } else {
            return super.contains(x, y);
        }
    }
}