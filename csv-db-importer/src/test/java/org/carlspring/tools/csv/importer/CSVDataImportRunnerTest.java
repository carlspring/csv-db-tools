
package org.carlspring.tools.csv.importer;

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

import org.junit.Test;

/**
 * @author Martin Todorov
 * @email carlspring@gmail.com
 */
public class CSVDataImportRunnerTest
{


    @Test
    public void testImport() throws Exception
    {
        String csvFile = "target/test-classes/users.csv";
        String configurationXML = "target/test-classes/configuration.xml";
        char delimiter = ',';

        System.setProperty("csv.file", csvFile);
        System.setProperty("configuration.xml", configurationXML);
        System.setProperty("delimiter", String.valueOf(delimiter));

        CSVDataImportRunner.main(new String[]{});

        System.getProperties().remove("cvs.file");
        System.getProperties().remove("configuration.xml");
        System.getProperties().remove("delimiter");
    }

}
