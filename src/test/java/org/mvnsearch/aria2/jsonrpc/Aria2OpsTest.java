package org.mvnsearch.aria2.jsonrpc;

import junit.framework.TestCase;
import org.mvnsearch.aria2.service.Aria2Ops;
import org.mvnsearch.aria2.xmlrpc.XmlRpcFactoryBean;

import java.util.*;

/**
 * aria2 operations test
 *
 * @author linux_china
 */
public class Aria2OpsTest extends TestCase {
    /**
     * aria2ops
     */
    Aria2Ops aria2Ops;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        XmlRpcFactoryBean factoryBean = new XmlRpcFactoryBean();
        factoryBean.setMethodPrefix("aria2.");
        factoryBean.setServiceUrl("http://127.0.0.1:6800/rpc");
        factoryBean.setServiceInterface(Aria2Ops.class);
        factoryBean.afterPropertiesSet();
        this.aria2Ops = (Aria2Ops) factoryBean.getObject();
    }

    /**
     * test to get global stat
     */
    public void testGetGlobalStat() {
        Map<String, String> info = aria2Ops.getGlobalStat();
        System.out.println(info);
    }

    /**
     * test to get global option
     */
    public void testGestGlobalOption() {
        Map<String, String> option = aria2Ops.getGlobalOption();
        for (Map.Entry<String, String> entry : option.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }

    /**
     * test add uri
     */
    public void testAddUri() {
        String uri = "http://cdntest.aliyun.com/faxianla/metal/m963780-1369993900101.jpg";
        String gid = aria2Ops.addUri(new String[]{uri}, Collections.emptyMap());
        System.out.println(gid);
    }

    /**
     * test status
     */
    public void testTellStatus() {
        String gid = "b562c36b108d66b2";
        Map<String, Object> status = aria2Ops.tellStatus(gid);
        System.out.println(status);
    }

    /**
     * test stoppped
     */
    public void testTellStopped() {
        List<Map<String, Object>> result = convertArrayIntoList(aria2Ops.tellStopped(0, 10));
        System.out.println(result);
    }

    /**
     * convert array into list
     *
     * @param array array
     * @return list
     */
    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> convertArrayIntoList(Object[] array) {
        if (array != null && array.length > 0) {
            List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
            for (Object obj : array) {
                result.add((Map<String, Object>) obj);
            }
            return result;
        }
        return Collections.emptyList();
    }
}
