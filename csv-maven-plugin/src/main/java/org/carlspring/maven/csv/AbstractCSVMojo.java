package org.carlspring.maven.csv;

/**
 * Copyright 2012 Martin Todorov.
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

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.project.MavenProject;

/**
 * @author Jason Stiefel (jason@stiefel.io)
 * @author mtodorov
 */
public abstract class AbstractCSVMojo
        extends AbstractMojo
{

    /**
     * @parameter default-value="${project}"
     * @required
     * @readonly
     */
    public MavenProject project;

    /**
     * @parameter expression="${basedir}"
     */
    public String basedir;

    /**
     * @parameter expression="${csv.file}"
     * @required
     */
    public String csvFile;

    /**
     * @parameter expression="${configuration.xml}"
     * @required
     */
    public String configurationXML;

    /**
     * @parameter expression="${delimiter}" default-value=","
     * @required
     */
    public char delimiter;


    public MavenProject getProject()
    {
        return project;
    }

    public void setProject(MavenProject project)
    {
        this.project = project;
    }

    public String getBasedir()
    {
        return basedir;
    }

    public void setBasedir(String basedir)
    {
        this.basedir = basedir;
    }

    public String getCsvFile()
    {
        return csvFile;
    }

    public void setCsvFile(String csvFile)
    {
        this.csvFile = csvFile;
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
