package com.rubynaxela.citybusnavi.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public final class CSVParser<T> {

    private final DataBinder<T> binder;

    public CSVParser(DataBinder<T> binder) {
        this.binder = binder;
    }

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

    public ArrayList<T> parse(File file) throws FileNotFoundException {
        return parse(file, true, ',');
    }
}
