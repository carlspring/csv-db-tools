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

import org.carlspring.tools.csv.mapping.Configuration;
import org.carlspring.tools.csv.mapping.ConfigurationParser;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

/**
 * @author mtodorov
 */
public class ConfigurationParserTest
{


    @Test
    public void testParser()
            throws IOException
    {
        ConfigurationParser parser = new ConfigurationParser();
        parser.setMappingXML("target/test-classes/mapping.xml");

        final Configuration configuration = parser.parse();

        assertNotNull("Failed to parse the mapping file!", configuration.getJdbcSettings().getUrl());
    }

}
