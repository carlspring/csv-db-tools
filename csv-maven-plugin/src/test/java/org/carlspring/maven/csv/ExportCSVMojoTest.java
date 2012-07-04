package org.carlspring.maven.csv;

/**
 * Copyright 2012 Martin Todorov.
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

import org.carlspring.tools.csv.CSVDataGenerator;
import org.carlspring.tools.csv.CSVFileReader;
import org.carlspring.tools.csv.importer.CSVDataImporter;
import org.junit.Before;

import java.io.*;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author mtodorov
 */
public class ExportCSVMojoTest
        extends AbstractCSVMojoTest
{

    private static final int NUMBER_OF_RECORDS = 10;

    final String CSV_EXPORT_FILE = TARGET_TEST_CLASSES + "/users-export.csv";

    ExportCSVMojo exportCSVMojo;


    @Before
    public void setUp()
            throws Exception
    {
        super.setUp();

        exportCSVMojo = (ExportCSVMojo) lookupMojo("export", POM_PLUGIN);
        exportCSVMojo.setCsvFile(CSV_EXPORT_FILE);
        exportCSVMojo.setConfigurationXML(CONFIGURATION_XML);
        exportCSVMojo.setDelimiter(',');

        importTestData();
    }

    private void importTestData()
            throws IOException, SQLException
    {
        Set<String> columnNames = new LinkedHashSet<String>();
        columnNames.add("username");
        columnNames.add("password");
        columnNames.add("firstname");
        columnNames.add("lastname");
        columnNames.add("comment");

        CSVDataGenerator dataGenerator = new CSVDataGenerator(new File(CSV_FILE), NUMBER_OF_RECORDS);
        dataGenerator.setColumnNames(columnNames);
        dataGenerator.setColumnValuePrefixes(columnNames);
        dataGenerator.generate();

        CSVDataImporter importer = new CSVDataImporter();
        importer.setCsvFile(CSV_FILE);
        importer.setConfigurationXML(CONFIGURATION_XML);
        importer.importData();
    }

    public void testMojo()
            throws Exception
    {
        exportCSVMojo.execute();

        FileInputStream is = null;
        BufferedReader br = null;
        CSVFileReader csvFileReader = null;

        try
        {
            is = new FileInputStream(CSV_EXPORT_FILE);
            br = new BufferedReader(new InputStreamReader(is));

            csvFileReader = new CSVFileReader(CSV_EXPORT_FILE, br, exportCSVMojo.getDelimiter());
            final int numberOfLines = csvFileReader.getNumberOfLines();

            assertEquals("Failed to validate the exported data!", 10, numberOfLines);

            System.out.println("Validated the export of  " + numberOfLines + " records from the database...");
        }
        catch (FileNotFoundException fnfExc)
        {
            fnfExc.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
