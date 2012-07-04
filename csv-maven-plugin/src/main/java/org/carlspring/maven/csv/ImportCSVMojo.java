package org.carlspring.maven.csv;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.carlspring.tools.csv.importer.CSVDataImporter;

import java.io.FileNotFoundException;
import java.sql.SQLException;

/**
 * @goal    import
 * @author  mtodorov
 */
public class ImportCSVMojo extends AbstractCSVMojo
{

    @Override
    public void execute()
            throws MojoExecutionException, MojoFailureException
    {
        try
        {
            CSVDataImporter importer = new CSVDataImporter();
            importer.setCsvFile(getCsvFile());
            importer.setConfigurationXML(getConfigurationXML());
            importer.setDelimiter(getDelimiter());
            importer.importData();
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
