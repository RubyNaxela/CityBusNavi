package com.rubynaxela.citybusnavi.data;

/**
 * A {@code DataBinder} is required for the {@link com.rubynaxela.citybusnavi.data.CSVParser} to properly
 * bind the raw data from the file to an object. Override the {@link DataBinder#bind(String[])} method
 * to define how the object should be constructed.<br> Example. Let's suppose we have a following class:<br>
 * <code>
 * class Product {<br>
 *     String id, name, type, desc;<br>
 *     int quantity;<br>
 *     double price;<br>
 * }
 * </code><br>
 * An example {@code DataBinder} for this class:<br>
 * <code>
 * new CSVParser&lt;Product&gt;(data -&gt; {<br>
 *     final Product product = new Product();<br>
 *     product.id = data[0];<br>
 *     product.name = data[1];<br>
 *     product.type = data[2];<br>
 *     product.desc = data[3];<br>
 *     product.quantity = Integer.parseInt(data[4]);<br>
 *     product.price = Double.parseDouble(data[5]);<br>
 *     return stop;<br>
 * })
 * </code>
 *
 * @param <T> the type of the object that the data is being binded to
 */
public interface DataBinder<T> {
    T bind(String[] rawData);
}
