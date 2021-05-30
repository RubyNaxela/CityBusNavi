package com.rubynaxela.citybusnavi.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This tool is used for parsing a CSV file into an {@link java.util.ArrayList} of objects of class
 * specified in the type parameter. Requires a {@link com.rubynaxela.citybusnavi.data.DataBinder}
 * to properly bind the raw data from the file to an object
 *
 * @param <T> the type of the data being parsed
 */
public final class CSVParser<T> {

    private final DataBinder<T> binder;

    public CSVParser(DataBinder<T> binder) {
        this.binder = binder;
    }

    /**
     * Parses the specified file
     *
     * @param file a CSV file
     * @param hasHeader whether the file's first line is a header line
     * @param delimiter a character that the values are separated with
     * @return an {@link java.util.ArrayList} of objects containing data stored in the file
     * @throws FileNotFoundException if the file does not exist, is a directory rather than a regular
     *                               file, or for some other reason cannot be opened for reading.
     */
    public ArrayList<T> parse(File file, boolean hasHeader, char delimiter) throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(file);
        Scanner sc = new Scanner(fis);
        ArrayList<T> ret = new ArrayList<>();
        if (hasHeader) sc.nextLine();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            ArrayList<String> data = new ArrayList<>();
            int startPos = 0, endPos = 0;
            boolean insideQuote = false;
            for (char c : (line + delimiter).toCharArray()) {
                if (c == '"') insideQuote = !insideQuote;
                if ((c == delimiter && !insideQuote)) {
                    data.add(line.substring(startPos, endPos));
                    startPos = endPos + 1;
                }
                endPos++;
            }
            ret.add(binder.bind(data.toArray(new String[0])));
        }
        return ret;
    }

    /**
     * Parses the specified file assuming its first line is a header line (and therefore
     * is ignored) and that the values are separated with a comma ({@code ,})
     *
     * @param file a CSV file
     * @return an {@link java.util.ArrayList} of objects containing data stored in the file
     * @throws FileNotFoundException if the file does not exist, is a directory rather than a regular
     *                               file, or for some other reason cannot be opened for reading.
     */
    public ArrayList<T> parse(File file) throws FileNotFoundException {
        return parse(file, true, ',');
    }
}
