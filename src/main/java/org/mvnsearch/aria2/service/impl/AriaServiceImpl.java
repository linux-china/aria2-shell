package org.mvnsearch.aria2.service.impl;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.mvnsearch.aria2.service.AriaService;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * aria service implementation
 *
 * @author linux_china
 */
@SuppressWarnings("unchecked")
@Component("ariaService")
public class AriaServiceImpl implements AriaService {
    /**
     * xml-rpc client
     */
    private XmlRpcClient client;
    /**
     * xml rpc host
     */
    private String host;
    /**
     * xml rpc port
     */
    private int port;
    /**
     * empty params
     */
    private static List<Object> emptyParams = Collections.emptyList();

    /**
     * connect with host
     *
     * @param host host name
     * @param port port
     */
    public void connect(String host, int port) throws Exception {
        this.host = host;
        this.port = port;
        String xmlRpcUrl = "http://" + host + ":" + port + "/rpc";
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL(xmlRpcUrl));
        client = new XmlRpcClient();
        client.setConfig(config);
    }

    /**
     * get xml rpc url
     *
     * @return xml rpc url
     */
    public String getXmlRpcUrl() {
        return "http://" + host + ":" + port + "/rpc";
    }

    /**
     * download url with options
     *
     * @param url     url
     * @param options options
     * @return gid
     */
    public String addUri(String url, Map<Object,Object> options) throws Exception {
        List<Object> params = constructParams(new Object[]{url}, options);
        return (String) client.execute("aria2.addUri", params);
    }

    /**
     * remove download
     *
     * @param gid gid
     * @throws Exception exception
     */
    public void remove(String gid) throws Exception {

    }

    /**
     * pause download
     *
     * @param gid gid
     * @throws Exception exception
     */
    public void pause(String gid) throws Exception {

    }

    /**
     * tell download status
     *
     * @param gid gid
     * @return status information
     */
    public Map<String, String> tellStatus(String gid) {
        return null;
    }

    /**
     * get global stat
     * <pre>
     * numActive:0
     * downloadSpeed:0
     * uploadSpeed:0
     * numWaiting:0
     * numStopped:2
     * </pre>
     *
     * @return global stat
     */
    public Map<String, String> getGlobalStat() throws Exception {
        return (Map<String, String>) client.execute("aria2.getGlobalStat", emptyParams);
    }

    /**
     * construct params
     *
     * @param params params
     * @return list object
     */
    public List<Object> constructParams(Object... params) {
        List<Object> temp = new ArrayList<Object>();
        Collections.addAll(temp, params);
        return temp;
    }

}
