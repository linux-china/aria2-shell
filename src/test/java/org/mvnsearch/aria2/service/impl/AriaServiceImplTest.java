package org.mvnsearch.aria2.service.impl;

import junit.framework.TestCase;

import java.util.Collections;
import java.util.HashMap;
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
        Map<Object, Object> params = new HashMap<Object, Object>();
//      params.put("gid", "181b1205c1da6370");
        params.put("gid", "0000000000000001");
        String gid = ariaService.addUri(uri, params);
        System.out.println(gid);
    }

    /**
     * test to info status
     *
     * @throws Exception exception
     */
    public void testTellStatus() throws Exception {
        String gid = "addf295d43548732";
        ariaService.tellStatus(gid);
    }

    /**
     * test to info stopped
     *
     * @throws Exception exception
     */
    public void testTellStopped() throws Exception {
        List<Map<String, Object>> queue = ariaService.tellStopped(0, 10);
        for (Map<String, Object> task : queue) {
            String gid = (String) task.get("gid");
            String status = (String) task.get("status");
            Map<String, Object> files = (Map<String, Object>) ((Object[]) task.get("files"))[0];
            String path = (String) files.get("path");
            Map<String, Object> uriInfo = (Map<String, Object>) ((Object[]) files.get("uris"))[0];
            String uri = (String) uriInfo.get("uri");
            System.out.println(task);
        }
    }

    /**
     * test to get version
     *
     * @throws Exception exception
     */
    public void testVersion() throws Exception {
        Map<String, Object> version = ariaService.version();
        for (Map.Entry<String, Object> entry : version.entrySet()) {
            System.out.println(entry.getKey());
        }
    }
}
