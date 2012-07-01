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
public class Configuration
{

    @XStreamAsAttribute
    private JdbcSettings jdbcSettings;

    @XStreamAsAttribute
    private Mapping mapping;

    @XStreamAsAttribute
    private boolean deleteAllDataFromTableBeforeInserting;


    public Configuration()
    {
    }

    public JdbcSettings getJdbcSettings()
    {
        return jdbcSettings;
    }

    public void setJdbcSettings(JdbcSettings jdbcSettings)
    {
        this.jdbcSettings = jdbcSettings;
    }

    public Mapping getMapping()
    {
        return mapping;
    }

    public void setMapping(Mapping mapping)
    {
        this.mapping = mapping;
    }

    public boolean deleteAllDataBeforeInserting()
    {
        return deleteAllDataFromTableBeforeInserting;
    }

    public void setDeleteAllDataFromTableBeforeInserting(boolean deleteAllDataFromTableBeforeInserting)
    {
        this.deleteAllDataFromTableBeforeInserting = deleteAllDataFromTableBeforeInserting;
    }

}
