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

import org.carlspring.tools.csv.CSVData;
import org.carlspring.tools.csv.CSVFileWriter;
import org.carlspring.tools.csv.FieldUtils;
import org.carlspring.tools.csv.dao.CSVDao;
import org.carlspring.tools.csv.mapping.Configuration;
import org.carlspring.tools.csv.mapping.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Martin Todorov
 */
public class CSVDataExporter
        extends CSVData
{

    private static final Logger logger = LoggerFactory.getLogger(CSVDataExporter.class);


    public CSVDataExporter()
    {
    }

    /**
     * Exports the data to a CSV file.
     */
    public void exportData()
            throws FileNotFoundException, SQLException
    {
        File file = new File(csvFile);

        FileOutputStream fos = null;
        CSVFileWriter csvFileWriter = null;

        try
        {
            fos = new FileOutputStream(file);

            csvFileWriter = new CSVFileWriter(file.getAbsolutePath(), getDelimiter());

            CSVDao dao = new CSVDao(getConfigurationXML());

            Configuration configuration = dao.getConfiguration();

            long startTime = System.currentTimeMillis();

            final List<Field> fields = configuration.getMapping().getFields();

            generateHeader(fields, fos);

            int numberOfRows = dao.export(fields, fos, getDelimiter(), configuration.getExportWhereClause());

            long endTime = System.currentTimeMillis();

            logger.info("The export took " + (endTime - startTime) + " ms for " + numberOfRows + " rows.");
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
            close(csvFileWriter);
            close(fos);
        }
    }

    private void generateHeader(List<Field> fields,
                                FileOutputStream fos)
            throws IOException
    {
        List<String> csvFields = FieldUtils.getCSVFields(fields);

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < csvFields.size(); i++)
        {
            String csvField = csvFields.get(i);

            sb.append(csvField.toUpperCase());

            if (i < csvFields.size() - 1)
                sb.append(delimiter);
        }

        sb.append("\n");

        logger.debug("Writing CSV header...");
        logger.debug(sb.toString());

        fos.write(sb.toString().getBytes());
        fos.flush();
    }

}
