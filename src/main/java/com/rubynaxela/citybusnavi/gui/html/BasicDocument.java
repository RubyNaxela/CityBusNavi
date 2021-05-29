package com.rubynaxela.citybusnavi.gui.html;

import com.hp.gagawa.java.elements.Body;
import com.hp.gagawa.java.elements.Head;
import com.hp.gagawa.java.elements.Html;
import com.hp.gagawa.java.elements.Style;

/**
 * This is a basic HTML document class
 */
public class BasicDocument extends Html {

    private final Body body;
    private final Head head;
    private final Style style;

    public BasicDocument() {

        body = new Body();
        head = new Head();
        style = new Style("text/css");

        this.appendChild(head);
        this.appendChild(body);
        head.appendChild(style);
    }

    public Head getHead() {
        return head;
    }

    public Body getBody() {
        return body;
    }

    public void addGlobalStyle(String css) {
        style.appendText(css);
    }
}

