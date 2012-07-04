
package org.carlspring.tools.csv.importer;

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
import org.junit.Test;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Martin Todorov
 * @email carlspring@gmail.com
 */
public class CSVLargeDataImporterIntegrationTest
{

    private static final String MAPPING_XML = "target/test-classes/configuration-large.xml";

    private static final String CSV_FILE = "target/test-classes/csv-large.csv";

    private static final int NUMBER_OF_RECORDS = 1000;


    @Test
    public void testImport() throws Exception
    {
        Set<String> columnNames = new LinkedHashSet<String>();
        columnNames.add("username");
        columnNames.add("password");
        columnNames.add("firstname");
        columnNames.add("lastname");

        CSVDataGenerator dataGenerator = new CSVDataGenerator(new File(CSV_FILE), NUMBER_OF_RECORDS);
        dataGenerator.setColumnNames(columnNames);
        dataGenerator.setColumnValuePrefixes(columnNames);
        dataGenerator.generate();

        System.setProperty("configuration.xml", MAPPING_XML);

        CSVDataImporter importer = new CSVDataImporter();
        importer.setCsvFile(CSV_FILE);
        importer.setConfigurationXML("target/test-classes/configuration-large.xml");
        importer.setDelimiter(',');
        importer.importData();

        System.getProperties().remove("configuration.xml");
    }

}
