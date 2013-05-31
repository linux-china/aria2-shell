package org.mvnsearch.aria2.service.impl;

import junit.framework.TestCase;

/**
 * aria service implementation test
 *
 * @author linux_china
 */
public class AriaServiceImplTest extends TestCase {
    /**
     * aria service
     */
    private AriaServiceImpl ariaService;

    /**
     * Sets up the fixture, for example, open a network connection.
     * This method is called before a test is executed.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        ariaService = new AriaServiceImpl();
        ariaService.connect("localhost", 6800);
    }

    /**
     * test to get aria2ops
     */
    public void testGetAria2Ops() {
        assertNotNull("Aria2ops is null", ariaService.getAria2Ops());
    }
}
