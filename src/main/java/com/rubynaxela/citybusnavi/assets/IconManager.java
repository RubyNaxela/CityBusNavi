package com.rubynaxela.citybusnavi.assets;

import com.formdev.flatlaf.icons.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

public final class IconManager {

    @Contract(pure = true)
    @Nullable
    public Icon get(String key) {
        switch (key) {
            case "icon.dialog.info":
                return new FlatOptionPaneInformationIcon();
            case "icon.dialog.warning":
                return new FlatOptionPaneWarningIcon();
            case "icon.dialog.error":
                return new FlatOptionPaneErrorIcon();
            case "icon.dialog.question":
                return new FlatOptionPaneQuestionIcon();
            case "icon.dialog.data":
                return new FlatOptionPaneAbstractIcon("Actions.Green", "Actions.Green") {
                    @Override
                    protected Shape createOutside() {
                        return new Ellipse2D.Float(2, 2, 28, 28);
                    }

                    @Override
                    protected Shape createInside() {
                        Path2D q = new Path2D.Float(Path2D.WIND_EVEN_ODD);
                        q.append(new Rectangle2D.Float(8, 8, 6, 16), false);
                        q.append(new Rectangle2D.Float(18, 8, 6, 6), false);
                        q.append(new Rectangle2D.Float(18, 18, 6, 6), false);
                        return q;
                    }
                };
            default:
                return null;
        }
    }
}
