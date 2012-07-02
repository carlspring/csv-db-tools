package org.carlspring.maven.csv;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.carlspring.tools.csv.exporter.CSVDataExporter;

import java.io.FileNotFoundException;
import java.sql.SQLException;

/**
 * @goal    export
 * @author  mtodorov
 */
public class ExportCSVMojo
        extends AbstractCSVMojo
{

    @Override
    public void execute()
            throws MojoExecutionException, MojoFailureException
    {
        try
        {
            CSVDataExporter exporter = new CSVDataExporter();
            exporter.setCsvFile(getCsvFile());
            exporter.setConfigurationXML(getConfigurationXML());
            exporter.setDelimiter(getDelimiter());
            exporter.exportData();
        }
        catch (FileNotFoundException e)
        {
            throw new MojoExecutionException(e.getMessage(), e);
        }
        catch (SQLException e)
        {
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }

}
