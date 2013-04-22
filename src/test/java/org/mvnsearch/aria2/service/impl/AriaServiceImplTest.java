package org.mvnsearch.aria2.service.impl;

import junit.framework.TestCase;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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
     * test to add uri
     *
     * @throws Exception exception
     */
    public void testAddUri() throws Exception {
        String uri = "http://cdntest.aliyun.com/faxianla/metal/m937309-1366561245640.jpg";
        String gid = ariaService.addUri(uri, Collections.emptyMap());
        System.out.println(gid);
    }

    /**
     * test to tell status
     *
     * @throws Exception exception
     */
    public void testTellStatus() throws Exception {
        String gid = "addf295d43548732";
        ariaService.tellStatus(gid);
    }

    /**
     * test to tell stopped
     *
     * @throws Exception exception
     */
    public void testTellStopped() throws Exception {
        List<Map<String, Object>> queue = ariaService.tellStopped(0, 10);
        System.out.println(queue.size());
    }
}
