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

import java.util.List;

/**
 * @author mtodorov
 */
public class Mapping
{

    @XStreamAsAttribute
    private String tableName;

    @XStreamAsAttribute
    private List<Field> fields;

    @XStreamAsAttribute
    private String sequenceLookupSQL;


    public Mapping()
    {
    }

    public String getTableName()
    {
        return tableName;
    }

    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }

    public List<Field> getFields()
    {
        return fields;
    }

    public void setFields(List<Field> fields)
    {
        this.fields = fields;
    }

    public String getSequenceLookupSQL()
    {
        return sequenceLookupSQL;
    }

    public void setSequenceLookupSQL(String sequenceLookupSQL)
    {
        this.sequenceLookupSQL = sequenceLookupSQL;
    }

}
