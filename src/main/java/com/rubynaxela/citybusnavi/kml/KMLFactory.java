package com.rubynaxela.citybusnavi.kml;

import com.rubynaxela.citybusnavi.data.datatypes.auxiliary.GeoPoint;
import com.rubynaxela.citybusnavi.data.datatypes.auxiliary.Pair;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;

public final class KMLFactory {

    @Contract(pure = true)
    @SafeVarargs
    public static Element createStyleMap(String id, Pair<String, String>... entries) {

        Element styleMap = DocumentFactory.getInstance().createElement("StyleMap").addAttribute("id", id);
        for (Pair<String, String> entry : entries) {
            Element pair = styleMap.addElement("Pair");
            pair.addElement("key").addText(entry.getKey());
            pair.addElement("styleUrl").addText(entry.getValue());
        }
        return styleMap;
    }

    @Contract(pure = true)
    public static Element createPaddlePinStyle(String id, double scale, String imageName) {

        Element style = DocumentFactory.getInstance().createElement("Style").addAttribute("id", id);
        {
            Element iconStyle = style.addElement("IconStyle");
            {
                iconStyle.addElement("scale").addText("" + scale);
                iconStyle.addElement("Icon").addElement("href")
                        .addText("http://maps.google.com/mapfiles/kml/paddle/" + imageName + ".png");
                iconStyle.addElement("hotSpot").addAttribute("x", "32").addAttribute("y", "1")
                        .addAttribute("xunits", "pixels").addAttribute("yunits", "pixels");
            }
            style.addElement("ListStyle").addElement("ItemIcon").addElement("href")
                    .addText("http://maps.google.com/mapfiles/kml/paddle/" + imageName + "-lv.png");
        }
        return style;
    }

    @Contract(pure = true)
    public static Element createLineStyle(String id, double scale, String color) {

        Element style = DocumentFactory.getInstance().createElement("Style").addAttribute("id", id);
        {
            Element iconStyle = style.addElement("IconStyle");
            {
                iconStyle.addElement("scale").addText("" + scale);
                iconStyle.addElement("Icon").addElement("href")
                        .addText("http://maps.google.com/mapfiles/kml/pushpin/ylw-pushpin.png");
                iconStyle.addElement("hotSpot").addAttribute("x", "20").addAttribute("y", "2")
                        .addAttribute("xunits", "pixels").addAttribute("yunits", "pixels");
            }
            Element lineStyle = style.addElement("LineStyle");
            {
                lineStyle.addElement("color").addText(color);
                lineStyle.addElement("width").addText("3");
            }
        }
        return style;
    }

    @Contract(pure = true)
    public static Element createFolder(String name, boolean opened) {
        Element folder = DocumentFactory.getInstance().createElement("Folder");
        folder.addElement("name").addText(name);
        folder.addElement("open").addText(opened ? "1" : "0");
        return folder;
    }

    @Contract(pure = true)
    public static Element createPin(String name, double lat, double lon, String style) {

        Element pin = DocumentFactory.getInstance().createElement("Placemark");
        {
            pin.addElement("name").setText(name);
            Element viewPoint = pin.addElement("LookAt");
            {
                viewPoint.addElement("longitude").addText("" + lon);
                viewPoint.addElement("latitude").addText("" + lat);
                viewPoint.addElement("range").addText("250");
            }
            pin.addElement("styleUrl").setText("#msn_" + style);
            pin.addElement("Point").addElement("coordinates").addText(lon + "," + lat + ",0");
        }
        return pin;
    }

    @Contract(pure = true)
    public static Element createPath(String name, ArrayList<GeoPoint> points, String style) {

        StringBuilder coordinatesString = new StringBuilder();
        for (GeoPoint point : points)
            coordinatesString.append(point.longitude).append(",").append(point.latitude).append(",0 ");

        Element path = DocumentFactory.getInstance().createElement("Placemark");
        {
            path.addElement("name").addText(name);
            path.addElement("styleUrl").addText("#msn_" + style);
            Element lineString = path.addElement("LineString");
            {
                lineString.addElement("tessellate").addText("1");
                lineString.addElement("coordinates").addText(coordinatesString.toString().trim());
            }
        }
        return path;
    }
}
