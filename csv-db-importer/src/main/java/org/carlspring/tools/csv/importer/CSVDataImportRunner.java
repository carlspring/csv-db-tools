package org.carlspring.tools.csv.importer;

import java.io.FileNotFoundException;
import java.sql.SQLException;

/**
 * @author mtodorov
 */
public class CSVDataImportRunner
{

    CSVDataImporter importer = new CSVDataImporter();


    public CSVDataImportRunner()
    {
    }

    public static void main(String[] args)
            throws FileNotFoundException, SQLException
    {
        final CSVDataImportRunner runner = new CSVDataImportRunner();

        if (System.getProperty("csv.file") == null || System.getProperty("mapping.xml") == null)
        {
            System.out.println("Usage:");
            System.out.println("    java -jar csv-importer*standalone*.jar -Dcsv.file=my.csv -Dmapping.xml=mapping.xml [-Ddelimiter=,]");
        }

        // runner.getImporter().setCsvFile(System.getProperty("csv.file"));

        if (System.getProperty("delimiter") != null)
            runner.getImporter().setDelimiter(System.getProperty("delimiter").charAt(0));

        runner.getImporter().importData();
    }

    public CSVDataImporter getImporter()
    {
        return importer;
    }

    public void setImporter(CSVDataImporter importer)
    {
        this.importer = importer;
    }

}
