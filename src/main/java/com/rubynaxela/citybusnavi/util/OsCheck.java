package com.rubynaxela.citybusnavi.util;

import java.util.Locale;

/**
 * This class is a tool for checking the operating system the program is
 * running on. Uses the {@code os.name} Java property to determine the system
 */
public final class OsCheck {

    public static OSType getOperatingSystemType() {
        String os = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
        if ((os.contains("mac")) || (os.contains("darwin"))) {
            return OSType.MAC_OS;
        } else if (os.contains("win")) {
            return OSType.WINDOWS;
        } else if (os.contains("nux")) {
            return OSType.LINUX;
        } else {
            return OSType.OTHER;
        }
    }

    public enum OSType {
        WINDOWS, MAC_OS, LINUX, OTHER
    }
}
