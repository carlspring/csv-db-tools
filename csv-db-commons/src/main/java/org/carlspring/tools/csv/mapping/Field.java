package org.carlspring.tools.csv.mapping;

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

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * @author mtodorov
 */
public class Field
{

    @XStreamAsAttribute
    private String csvColumnName;

    @XStreamAsAttribute
    private String columnName;

    @XStreamAsAttribute
    private String type;

    @XStreamAsAttribute
    private String useValue;

    @XStreamAsAttribute
    private String includeExplicitly;


    public Field()
    {
    }

    public String getCsvColumnName()
    {
        return csvColumnName;
    }

    public void setCsvColumnName(String csvColumnName)
    {
        this.csvColumnName = csvColumnName;
    }

    public String getColumnName()
    {
        return columnName;
    }

    public void setColumnName(String columnName)
    {
        this.columnName = columnName;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getUseValue()
    {
        return useValue;
    }

    public void setUseValue(String useValue)
    {
        this.useValue = useValue;
    }

    public String getIncludeExplicitly()
    {
        return includeExplicitly;
    }

    public void setIncludeExplicitly(String includeExplicitly)
    {
        this.includeExplicitly = includeExplicitly;
    }

}
