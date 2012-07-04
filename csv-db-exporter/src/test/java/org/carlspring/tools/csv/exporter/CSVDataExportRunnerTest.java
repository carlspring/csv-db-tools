
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

import org.carlspring.tools.csv.CSVFileReader;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static junit.framework.Assert.assertEquals;

/**
 * @author Martin Todorov
 * @email carlspring@gmail.com
 */
public class CSVDataExportRunnerTest extends AbstractPrepopulatingTest
{


    @Test
    public void testExport() throws Exception
    {
        String csvFile = "target/test-classes/users-export-runner.csv";
        char delimiter = ',';

        System.setProperty("csv.file", csvFile);
        System.setProperty("configuration.xml", "target/test-classes/configuration-runner.xml");
        System.setProperty("delimiter", String.valueOf(delimiter));

        CSVDataExportRunner.main(new String[]{});

        FileInputStream is = null;
        BufferedReader br = null;
        CSVFileReader csvFileReader = null;

        int numberOfLines = 0;
        try
        {
            is = new FileInputStream(csvFile);
            br = new BufferedReader(new InputStreamReader(is));

            csvFileReader = new CSVFileReader(csvFile, br, delimiter);

            numberOfLines = csvFileReader.getNumberOfLines();
        }
        finally
        {
            System.getProperties().remove("cvs.file");
            System.getProperties().remove("configuration.xml");
            System.getProperties().remove("delimiter");

            if (is != null)
                is.close();

            if (br != null)
                br.close();

            if (csvFileReader != null)
                csvFileReader.close();
        }

        assertEquals("Failed to export using a WHERE clause!", 5, numberOfLines);
    }

}
