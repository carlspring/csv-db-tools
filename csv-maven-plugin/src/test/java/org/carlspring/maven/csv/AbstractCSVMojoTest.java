package org.carlspring.maven.csv;

import org.apache.maven.plugin.testing.AbstractMojoTestCase;

/**
 * @author mtodorov
 */
public abstract class AbstractCSVMojoTest
        extends AbstractMojoTestCase
{

    protected static final String TARGET_TEST_CLASSES = "target/test-classes";
    protected static final String POM_PLUGIN = TARGET_TEST_CLASSES + "/poms/pom.xml";
    protected static final String CSV_FILE = TARGET_TEST_CLASSES + "/users.csv";
    protected static final String CONFIGURATION_XML = TARGET_TEST_CLASSES + "/configuration.xml";


    @Override
    public void setUp()
            throws Exception
    {
        super.setUp();
    }
}
