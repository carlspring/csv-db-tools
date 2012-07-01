
package org.carlspring.tools.csv.exporter;

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

import org.carlspring.tools.csv.CSVDataGenerator;
import org.carlspring.tools.csv.importer.CSVDataImporter;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Martin Todorov
 * @email carlspring@gmail.com
 */
public class CSVDataExporterTest
{

    private static final String MAPPING_XML = "target/test-classes/mapping.xml";

    private static final String CSV_FILE = "target/test-classes/users.csv";

    private static final int NUMBER_OF_RECORDS = 10;


    @Before
    public void setUp()
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

        System.setProperty("mapping.xml", MAPPING_XML);

        CSVDataImporter importer = new CSVDataImporter();

        importer.setCsvFile(CSV_FILE);
        importer.importData();
    }

    @Test
    public void testExport() throws Exception
    {
        System.setProperty("mapping.xml", "target/test-classes/mapping.xml");

        CSVDataExporter exporter = new CSVDataExporter();
        exporter.setCsvFile("target/test-classes/users-exported.csv");
        exporter.exportData();
    }

}
