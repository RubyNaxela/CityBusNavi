package com.rubynaxela.citybusnavi.gui.html;

public class HTMLTags {

    /**
     * Surrounds the argument string with a {@code &lt;html&gt;} HTML tag
     *
     * @param content a string
     * @return {@code "&lt;html&gt;content&lt;/html&gt;"} where {@code content} is the argument string
     */
    public static String html(String content) {
        return "<html>" + content + "</html>";
    }

    /**
     * Surrounds the argument string with a {@code &lt;u&gt;} HTML tag
     *
     * @param content a string
     * @return {@code "&lt;u&gt;content&lt;/u&gt;"} where {@code content} is the argument string
     */
    public static String u(String content) {
        return "<u>" + content + "</u>";
    }
}
