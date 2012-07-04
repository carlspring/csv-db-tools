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

import com.thoughtworks.xstream.XStream;
import org.carlspring.ioc.InjectionException;
import org.carlspring.ioc.PropertyValue;
import org.carlspring.ioc.PropertyValueInjector;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author mtodorov
 */
public class ConfigurationParser
{

    @PropertyValue(key = "configuration.xml")
    private String configurationXML;


    public ConfigurationParser()
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

    public Configuration parse()
            throws IOException
    {
        FileInputStream fis = null;

        try
        {
            fis = new FileInputStream(configurationXML);

            XStream xstream = new XStream();

            // Lists
            xstream.alias("configuration", Configuration.class);
            xstream.alias("fields", List.class);
            xstream.alias("field", Field.class);
            xstream.autodetectAnnotations(true);

            //noinspection unchecked
            return (Configuration) xstream.fromXML(fis);
        }
        finally
        {
            if (fis != null)
            {
                fis.close();
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

}
