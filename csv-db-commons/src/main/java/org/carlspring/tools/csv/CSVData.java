package org.carlspring.tools.csv;

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

    protected char delimiter = ',';


    public CSVData()
    {
        try
        {
            PropertyValueInjector.inject(this);
        }
        catch (IOException e)
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

    public char getDelimiter()
    {
        return delimiter;
    }

    public void setDelimiter(char delimiter)
    {
        this.delimiter = delimiter;
    }

}
