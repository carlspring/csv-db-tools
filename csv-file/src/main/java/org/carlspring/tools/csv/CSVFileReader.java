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

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * CSVFileReader is a class derived from CSVFile used to parse an existing CSV file.
 * <p>
 * Adapted from a C++ original that is Copyright (C) 1999 Lucent Technologies.<br>
 * Excerpted from 'The Practice of Programming' by Brian Kernighan and Rob Pike.
 * <p>
 * Included by permission of the <a href="http://tpop.awl.com/">Addison-Wesley</a> web site, which says:
 * <cite>"You may use this code for any purpose, as long as you leave the copyright notice and book citation attached"</cite>.
 *
 * @author  Brian Kernighan and Rob Pike (C++ original)
 * @author  Ian F. Darwin (translation into Java and removal of I/O)
 * @author  Ben Ballard (rewrote handleQuotedField to handle double quotes and for readability)
 * @author  Fabrizio Fazzino (added integration with CSVFile, handling of variable textQualifier and Vector with explicit String type)
 * @author  Martin Todorov (re-worked things and fixed some minor issues)
 * @version %I%, %G%
 */
public class CSVFileReader extends CSVFile
{

    /**
     * The buffered reader linked to the CSV file to be read.
     */
    protected BufferedReader in;


    /**
     * CSVFileReader constructor with a given field separator.
     *
     * @param fileName  The file name
     * @param br        The name of the BufferedReader to be used for reading
     * @param sep       The field separator to be used; overwrites the default one
     */
    public CSVFileReader(String fileName, BufferedReader br, char sep)
    {
        super(sep);
        this.fileName = fileName;
        in = br;
    }

    /**
     * Split the next line of the input CSV file into fields.
     * <p/>
     * This is currently the most important function of the package.
     *
     * @return Vector of strings containing each field from the next line of the file
     * @throws java.io.IOException If an error occurs while reading the new line from the file
     */
    public List<String> readFields() throws IOException
    {
        List<String> fields = new ArrayList<String>();
        StringBuffer sb = new StringBuffer();
        String line = in.readLine();

        if (line == null)
            return null;

        if (line.length() == 0)
        {
            fields.add(line);
            return fields;
        }

        int i = 0;
        do
        {
            sb.setLength(0);
            if (i < line.length() && line.charAt(i) == textQualifier)
            {
                i = handleQuotedField(line, sb, ++i);  // skip quote
            }
            else
            {
                i = handlePlainField(line, sb, i);
            }

            // Make sure the field name is trimmed before adding it
            fields.add(sb.toString().trim());
            i++;
        }
        while (i < line.length());

        return fields;
    }

	/**
	 * @author  Martin Todorov
	 * @since   25/06/2012
	 * @return  The number of lines (excluding the header) contained in this file.
	 * @throws  IOException
	 */
    public int getNumberOfLines()
            throws IOException
    {
        InputStream is = null;

        try
        {
            is = new BufferedInputStream(new FileInputStream(getFileName()));

            byte[] bytes = new byte[1024];

            int count = 0;
            int readChars = 0;

            while ((readChars = is.read(bytes)) != -1)
            {
                for (int i = 0; i < readChars; ++i)
                {
                    if (bytes[i] == '\n')
                    {
                        ++count;
                    }
                }
            }

            // Subtract the line containing the column header
            return count - 1;
        }
        finally
        {
            if (is != null)
            {
                is.close();
            }
        }
    }

    /**
     * Close the input CSV file.
     *
     * @throws IOException If an error occurs while closing the file
     */
    public void close() throws IOException
    {
        in.close();
    }

    /**
     * Handles a quoted field.
     *
     * @return index of next separator
     */
    protected int handleQuotedField(String s, StringBuffer sb, int i)
    {
        int j;
        int len = s.length();

        for (j = i; j < len; j++)
        {
            if ((s.charAt(j) == textQualifier) && (j + 1 < len))
            {
                if (s.charAt(j + 1) == textQualifier)
                {
                    j++;  // skip escape char
                }
                else if (s.charAt(j + 1) == fieldSeparator)
                {
                    // next delimiter
                    j++;  // skip end quotes
                    break;
                }
            }
            else if ((s.charAt(j) == textQualifier) && (j + 1 == len))
            {
                // end quotes at end of line
                break; // done
            }

            sb.append(s.charAt(j));  // regular character
        }

        return j;
    }

    /**
     * Handles an unquoted field.
     *
     * @return index of next separator
     */
    protected int handlePlainField(String s, StringBuffer sb, int i)
    {
        int j = s.indexOf(fieldSeparator, i); // look for separator
        if (j == -1)
        {
            // none found
            sb.append(s.substring(i));
            return s.length();
        }
        else
        {
            sb.append(s.substring(i, j));
            return j;
        }
    }

}
