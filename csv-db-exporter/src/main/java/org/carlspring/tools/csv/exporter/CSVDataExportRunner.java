package org.carlspring.tools.csv.exporter;

import java.io.FileNotFoundException;
import java.sql.SQLException;

/**
 * @author mtodorov
 */
public class CSVDataExportRunner
{

    CSVDataExporter exporter;


    public CSVDataExportRunner()
    {
    }

    public static void main(String[] args)
            throws FileNotFoundException, SQLException
    {
        final CSVDataExportRunner runner = new CSVDataExportRunner();

        if (System.getProperty("csv.file") == null || System.getProperty("mapping.xml") == null)
        {
            System.out.println("Usage:");
            System.out.println("    java -jar csv-exporter.jar -Dcsv.file=my.csv -Dmapping.xml=mapping.xml");
        }

        runner.getExporter().exportData();
    }

    public CSVDataExporter getExporter()
    {
        return exporter;
    }

    public void setExporter(CSVDataExporter exporter)
    {
        this.exporter = exporter;
    }

}
