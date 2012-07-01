package org.carlspring.tools.csv;

/**
 * Copyright 2012.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.Closeable;

/**
 * CSVFile is a class used to handle <a href="http://en.wikipedia.org/wiki/Comma-separated_values">Comma-Separated Values</a> files.
 * <p/>
 * It is abstract because it is the base class used for {@link CSVFileReader} and {@link CSVFileWriter}
 * so you should use one of these (or both) according on what you need to do.
 * <p/>
 * The simplest example for using the classes contained in this package is {@link CSVFileExample}, that simply
 * converts one CSV file into another one that makes use of a different notation for field separator
 * and text qualifier.<br>
 * The example just comprises the following lines:
 * <p/>
 * <pre>
 * import java.util.*;
 * import java.io.*;
 *
 * public class CSVFileExample {
 *
 * 	public static void main(String[] args) throws FileNotFoundException,IOException {
 *
 * 		CSVFileReader in = new CSVFileReader("csv_in.txt", ';', '"');
 * 		CSVFileWriter out = new CSVFileWriter("csv_out.txt", ',', '\'');
 *
 *     Vector<String> fields = in.readFields();
 *     while(fields!=null) {
 *       out.writeFields(fields);
 *       fields = in.readFields();
 *     }
 *
 *     in.close();
 *     out.close();
 *  }
 *
 * }
 * </pre>
 *
 * @author Fabrizio Fazzino
 * @version %I%, %G%
 */
public abstract class CSVFile implements Closeable
{

    /**
     * The default char used as field separator.
     */
    protected static final char DEFAULT_FIELD_SEPARATOR = ',';

    /**
     * The default char used as text qualifier
     */
    protected static final char DEFAULT_TEXT_QUALIFIER = '"';

    /**
     * The current char used as field separator.
     */
    protected char fieldSeparator;

    /**
     * The current char used as text qualifier.
     */
    protected char textQualifier;

    protected String fileName;


    /**
     * CSVFile constructor with the default field separator and text qualifier.
     */
    public CSVFile()
    {
        this(DEFAULT_FIELD_SEPARATOR, DEFAULT_TEXT_QUALIFIER);
    }

    /**
     * CSVFile constructor with a given field separator and the default text qualifier.
     *
     * @param sep The field separator to be used; overwrites the default one
     */
    public CSVFile(char sep)
    {
        this(sep, DEFAULT_TEXT_QUALIFIER);
    }

    /**
     * CSVFile constructor with given field separator and text qualifier.
     *
     * @param sep  The field separator to be used; overwrites the default one
     * @param qual The text qualifier to be used; overwrites the default one
     */
    public CSVFile(char sep, char qual)
    {
        setFieldSeparator(sep);
        setTextQualifier(qual);
    }

    /**
     * Set the current field separator.
     *
     * @param sep The new field separator to be used; overwrites the old one
     */
    public void setFieldSeparator(char sep)
    {
        fieldSeparator = sep;
    }

    /**
     * Set the current text qualifier.
     *
     * @param qual The new text qualifier to be used; overwrites the old one
     */
    public void setTextQualifier(char qual)
    {
        textQualifier = qual;
    }

    /**
     * Get the current field separator.
     *
     * @return The char containing the current field separator
     */
    public char getFieldSeparator()
    {
        return fieldSeparator;
    }

    /**
     * Get the current text qualifier.
     *
     * @return The char containing the current text qualifier
     */
    public char getTextQualifier()
    {
        return textQualifier;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

}

