package org.carlspring.tools.csv;

import org.carlspring.ioc.InjectionException;
import org.carlspring.ioc.PropertyValue;
import org.carlspring.ioc.PropertyValueInjector;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author mtodorov
 */
public class CSVData
{

    @PropertyValue(key = "csv.file")
    protected String csvFile;

    @PropertyValue(key = "configuration.xml")
    protected String configurationXML;

    @PropertyValue(key = "delimiter")
    protected char delimiter = ',';


    public CSVData()
    {
        try
        {
            PropertyValueInjector.inject(this);
        }
        catch (InjectionException e)
        {
            e.printStackTrace();
        }
    }

    public String getCsvFile()
    {
        return csvFile;
    }

    public void setCsvFile(String csvFile)
    {
        this.csvFile = csvFile;
    }

    public void close(Closeable closeable)
    {
        if (closeable != null)
        {
            try
            {
                closeable.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public String getConfigurationXML()
    {
        return configurationXML;
    }

    public void setConfigurationXML(String configurationXML)
    {
        this.configurationXML = configurationXML;
    }

    public char getDelimiter()
    {
        return delimiter;
    }

    public void setDelimiter(char delimiter)
    {
        this.delimiter = delimiter;
    }

}
