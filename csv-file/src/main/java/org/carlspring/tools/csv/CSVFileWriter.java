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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

/**
 * CSVFileWriter is a class derived from CSVFile used to format some fields into a new CSV file.
 *
 * @author Fabrizio Fazzino
 * @version %I%, %G%
 */
public class CSVFileWriter
        extends CSVFile
{

    /**
     * The print writer linked to the CSV file to be written.
     */
    protected PrintWriter out;

    /**
     * CSVFileWriter constructor just need the name of the CSV file that will be written.
     *
     * @param fileName The name of the CSV file to be opened for writing
     * @throws IOException If an error occurs while creating the file
     */
    public CSVFileWriter(String fileName)
            throws IOException
    {
        super();
        out = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
        this.fileName = fileName;
    }

    /**
     * CSVFileWriter constructor with a given field separator.
     *
     * @param fileName The name of the CSV file to be opened for reading
     * @param sep            The field separator to be used; overwrites the default one
     * @throws IOException If an error occurs while creating the file
     */
    public CSVFileWriter(String fileName, char sep)
            throws IOException
    {
        super(sep);
        out = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
        this.fileName = fileName;
    }

    /**
     * CSVFileWriter constructor with given field separator and text qualifier.
     *
     * @param fileName The name of the CSV file to be opened for reading
     * @param sep            The field separator to be used; overwrites the default one
     * @param qual           The text qualifier to be used; overwrites the default one
     * @throws IOException If an error occurs while creating the file
     */
    public CSVFileWriter(String fileName, char sep, char qual)
            throws IOException
    {
        super(sep, qual);
        out = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
        this.fileName = fileName;
    }

    /**
     * Close the output CSV file.
     *
     * @throws IOException If an error occurs while closing the file
     */
    public void close()
            throws IOException
    {
        out.flush();
        out.close();
    }

    /**
     * Join the fields and write them as a new line to the CSV file.
     *
     * @param fields The vector of strings containing the fields
     */
    public void writeFields(Vector<String> fields)
    {
        int n = fields.size();
        for (int i = 0; i < n; i++)
        {
            out.print(textQualifier + fields.get(i) + textQualifier);
            if (i < (n - 1)) out.print(fieldSeparator);
        }

        out.println();
    }

}

