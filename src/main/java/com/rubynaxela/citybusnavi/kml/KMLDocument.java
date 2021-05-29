package com.rubynaxela.citybusnavi.kml;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class KMLDocument {

    private final Document xmlDom = DocumentHelper.createDocument();
    private final Element contentRoot;

    public KMLDocument(String name) {
        getDocument().addElement("kml").addNamespace("", "http://earth.google.com/kml/2.0");
        contentRoot = getWrapper().addElement("Document");
        getRoot().addElement("name").addText(name);
    }

    public Document getDocument() {
        return xmlDom;
    }

    public Element getWrapper() {
        return xmlDom.getRootElement();
    }

    public Element getRoot() {
        return contentRoot;
    }
}
