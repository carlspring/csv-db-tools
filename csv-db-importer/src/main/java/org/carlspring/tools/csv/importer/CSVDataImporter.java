
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

import org.carlspring.tools.csv.CSVData;
import org.carlspring.tools.csv.CSVFileReader;
import org.carlspring.tools.csv.FieldUtils;
import org.carlspring.tools.csv.dao.CSVDao;
import org.carlspring.tools.csv.mapping.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Martin Todorov
 */
public class CSVDataImporter extends CSVData
{

    private static final Logger logger = LoggerFactory.getLogger(CSVDataImporter.class);


    public CSVDataImporter()
    {
        super();
    }

    /**
     * Writes the data to the database.
     */
    public void importData()
            throws FileNotFoundException, SQLException
    {
        File file = new File(csvFile);

        FileInputStream is = null;
        BufferedReader br = null;
        CSVFileReader csvFileReader = null;

        try
        {
            is = new FileInputStream(file);
            br = new BufferedReader(new InputStreamReader(is));

            csvFileReader = new CSVFileReader(file.getAbsolutePath(), br, getDelimiter());

            CSVDao dao = new CSVDao(getConfigurationXML());

            final Configuration configuration = dao.getConfiguration();
            List<String> fields = FieldUtils.renameFields(csvFileReader.readFields(),
                                                          configuration.getMapping().getFields());

            if (configuration.deleteAllDataBeforeInserting())
            {
                logger.info("Removing all existing records...");
                dao.deleteAll();
            }

            logger.info("Pumping " + csvFileReader.getNumberOfLines() + " records to the database...");

            final long startTime = System.currentTimeMillis();

            List<String> row;
            while ((row = csvFileReader.readFields()) != null)
            {
                dao.insert(fields, row);
            }

            final long endTime = System.currentTimeMillis();

            logger.info("The import took " + (endTime - startTime) + " ms.");
        }
        catch (FileNotFoundException fnfExc)
        {
            fnfExc.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            close(csvFileReader);
            close(br);
            close(is);
        }
    }

}
