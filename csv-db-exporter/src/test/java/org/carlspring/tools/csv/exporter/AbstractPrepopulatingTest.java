package org.carlspring.tools.csv.exporter;

import org.carlspring.tools.csv.CSVDataGenerator;
import org.carlspring.tools.csv.importer.CSVDataImporter;
import org.junit.Before;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author mtodorov
 */
public class AbstractPrepopulatingTest
{

    public static final String CONFIGURATION_XML = "target/test-classes/configuration.xml";

    public static final String CSV_FILE = "target/test-classes/users.csv";

    public static final int NUMBER_OF_RECORDS = 10;


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

        System.setProperty("configuration.xml", CONFIGURATION_XML);

        CSVDataImporter importer = new CSVDataImporter();

        importer.setCsvFile(CSV_FILE);
        importer.importData();
    }

}
