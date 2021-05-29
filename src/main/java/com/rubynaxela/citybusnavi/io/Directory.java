package com.rubynaxela.citybusnavi.io;

import java.io.File;

/**
 * This class serves the only purpose of improving code readability.
 * Should be used instead of {@link java.io.File} to store directory objects.
 */
public class Directory extends File {

    public Directory(String pathname) {
        super(pathname);
    }
}
