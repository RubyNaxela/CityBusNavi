package com.rubynaxela.citybusnavi.gui.components.std;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

import static com.rubynaxela.citybusnavi.util.Utils.gridElementSettings;

public class ScrollableJFrame extends DefaultJFrame {

    private final DefaultJPanel container = new DefaultJPanel();
    private final JScrollPane scrollPane = new JScrollPane(container);

    public ScrollableJFrame(boolean exitOnClose) {
        super(exitOnClose);
        super.register(scrollPane, gridElementSettings(0, 0));
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    @Override
    public void register(@NotNull Component component, Object constraints) {
        this.components.add(component);
        this.container.register(component, constraints);
    }
}
