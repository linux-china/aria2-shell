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
     * get host
     *
     * @return host
     */
    public String getHost() {
        return host;
    }

    /**
     * get port
     *
     * @return port
     */
    public int getPort() {
        return port;
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
    public String addUri(String url, Map<Object, Object> options) throws Exception {
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
        List<Object> params = constructParams(gid);
        client.execute("aria2.pause", params);
    }

    /**
     * unpause gid
     *
     * @param gid gid
     * @throws Exception exception
     */
    public void unpause(String gid) throws Exception {
        List<Object> params = constructParams(gid);
        client.execute("aria2.unpause", params);
    }

    /**
     * info download status
     *
     * @param gid gid
     * @return status information
     */
    public Map<String, Object> tellStatus(String gid) throws Exception {
        List<Object> params = constructParams(gid);
        return (Map<String, Object>) client.execute("aria2.tellStatus", params);
    }

    /**
     * info waiting
     *
     * @param offset     offset
     * @param maxResults max results
     * @return gid list
     */
    public List<Map<String, Object>> tellWaiting(int offset, int maxResults) throws Exception {
        List<Object> params = constructParams(offset, maxResults);
        Object[] results = (Object[]) client.execute("aria2.tellWaiting", params);
        return convertToList(results);
    }

    /**
     * info active
     *
     * @return active list
     * @throws Exception exception
     */
    public List<Map<String, Object>> tellActive() throws Exception {
        Object[] results = (Object[]) client.execute("aria2.tellActive", emptyParams);
        return convertToList(results);
    }

    /**
     * info stopped
     *
     * @param offset     offset
     * @param maxResults max results
     * @return stopped list
     * @throws Exception exception
     */
    public List<Map<String, Object>> tellStopped(int offset, int maxResults) throws Exception {
        List<Object> params = constructParams(offset, maxResults);
        Object[] results = (Object[]) client.execute("aria2.tellStopped", params);
        return convertToList(results);
    }

    /**
     * convert to list
     *
     * @param results results
     * @return item list
     */
    private List<Map<String, Object>> convertToList(Object[] results) {
        if (results != null && results.length > 0) {
            List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
            for (Object result : results) {
                items.add((Map<String, Object>) result);
            }
            return items;
        }
        return Collections.emptyList();
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
     * Pause all the active downloads
     */
    public void sleep() throws Exception {
        client.execute("aria2.pauseAll", emptyParams);
    }

    /**
     * Resume all the paused downloads
     */
    public void wake() throws Exception {
        client.execute("aria2.unpauseAll", emptyParams);
    }

    /**
     * Clear the list of stopped downloads and errors
     */
    public void purge() throws Exception {
        client.execute("aria2.purgeDownloadResult", emptyParams);
    }

    /**
     * get version information
     *
     * @return version information
     * @throws Exception exception
     */
    public Map<String, Object> version() throws Exception {
        return (Map<String, Object>) client.execute("aria2.getVersion", emptyParams);
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
