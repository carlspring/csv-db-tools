package org.carlspring.tools.csv.exporter;

import java.io.FileNotFoundException;
import java.sql.SQLException;

/**
 * @author mtodorov
 */
public class CSVDataExportRunner
{

    private CSVDataExporter exporter = new CSVDataExporter();


    public CSVDataExportRunner()
    {
    }

    public static void main(String[] args)
            throws FileNotFoundException, SQLException
    {
        final CSVDataExportRunner runner = new CSVDataExportRunner();

        if (System.getProperty("csv.file") == null || System.getProperty("configuration.xml") == null)
        {
            System.out.println("Usage:");
            System.out.println("    java -Dcsv.file=my.csv -Dconfiguration.xml=configuration.xml [-Ddelimiter=,] -jar csv-db-exporter-${version}-standalone.jar");
        }

        if (System.getProperty("delimiter") != null)
            runner.getExporter().setDelimiter(System.getProperty("delimiter").charAt(0));

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
