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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author mtodorov
 */
public class CSVDataGenerator
{

    private static final Logger logger = LoggerFactory.getLogger(CSVDataGenerator.class);

    private File file;

    private int numberOfRowsToGenerate;

    private Set<String> columnNames = new LinkedHashSet<String>();

    private Set<String> columnValuePrefixes = new LinkedHashSet<String>();


    public CSVDataGenerator()
    {
    }

    public CSVDataGenerator(File file, int numberOfRowsToGenerate)
    {
        this.file = file;
        this.numberOfRowsToGenerate = numberOfRowsToGenerate;
    }

    public void generate()
            throws IOException
    {
        BufferedWriter bw = null;

        try
        {
            logger.info("Generating CSV file '" + file.getAbsoluteFile().getAbsolutePath() + "'...");

            // fos = new FileOutputStream(file);
            bw = new BufferedWriter(new FileWriter(file));

            final long startTime = System.currentTimeMillis();

            writeHeader(bw);

            generateRecords(bw);

            final long endTime = System.currentTimeMillis();

            logger.info("Generation took " + (endTime - startTime) + " ms.");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (bw != null)
                bw.close();
        }
    }

    private void generateRecords(Writer bw)
            throws IOException
    {
        logger.info("Generating " + numberOfRowsToGenerate + " records...");

        for (int i = 0; i < numberOfRowsToGenerate; i++)
        {
            int j = 0;
            for (String columnValue : columnValuePrefixes)
            {
                bw.write(columnValue);
                bw.write(String.valueOf(i + 1));

                if (j < columnValuePrefixes.size() - 1)
                    bw.write(", ");

                j++;
            }

            bw.write("\n");
            bw.flush();
        }
    }

    private void writeHeader(Writer bw)
            throws IOException
    {
        logger.info("Writing header...");

        int i = 0;
        for (String columnName : columnNames)
        {
            bw.write(columnName.toUpperCase());

            if (i < columnNames.size() - 1)
                bw.write(", ");

            i++;
        }

        bw.write("\n");
        bw.flush();
    }

    public File getFile()
    {
        return file;
    }

    public void setFile(File file)
    {
        this.file = file;
    }

    public int getNumberOfRowsToGenerate()
    {
        return numberOfRowsToGenerate;
    }

    public void setNumberOfRowsToGenerate(int numberOfRowsToGenerate)
    {
        this.numberOfRowsToGenerate = numberOfRowsToGenerate;
    }

    public Set<String> getColumnNames()
    {
        return columnNames;
    }

    public void setColumnNames(Set<String> columnNames)
    {
        this.columnNames = columnNames;
    }

    public Set<String> getColumnValuePrefixes()
    {
        return columnValuePrefixes;
    }

    public void setColumnValuePrefixes(Set<String> columnValuePrefixes)
    {
        this.columnValuePrefixes = columnValuePrefixes;
    }

}
