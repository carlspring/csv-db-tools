package org.carlspring.tools.csv;

import org.carlspring.tools.csv.mapping.Field;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mtodorov
 */
public class FieldUtils
{

    public static List<String> renameFields(List<String> fields,
                                            List<Field> fieldList)
    {
        List<String> tableFields = new ArrayList<String>();

        for (Field field : fieldList)
        {
            if (field.getIncludeExplicitly() != null)
            {
                tableFields.add(field.getColumnName());
            }
            else
            {
                for (String fieldName : fields)
                {
                    if (fieldName.equalsIgnoreCase(field.getCsvColumnName()))
                    {
                        tableFields.add(field.getColumnName());
                        break;
                    }
                }
            }
        }

        return tableFields;
    }

    public static List<String> getCSVFields(List<Field> fields)
    {
        List<String> csvFields = new ArrayList<String>();

        for (Field field : fields)
        {
            csvFields.add(field.getCsvColumnName());
        }

        return csvFields;
    }

    public static List<String> getTableFields(List<Field> fields)
    {
        List<String> tableFields = new ArrayList<String>();

        for (Field field : fields)
        {
            tableFields.add(field.getColumnName());
        }

        return tableFields;
    }

}
