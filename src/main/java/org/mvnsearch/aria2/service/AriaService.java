package org.mvnsearch.aria2.service;

/**
 * aria service
 *
 * @author linux_china
 */
public interface AriaService {
    /**
     * connect with host
     *
     * @param host host name
     * @param port port
     * @throws java.lang.Exception
     */
    public void connect(String host, int port) throws Exception;

    /**
     * get host
     *
     * @return host
     */
    public String getHost();

    /**
     * get port
     *
     * @return port
     */
    public int getPort();

    /**
     * get xml rpc url
     *
     * @return xml rpc url
     */
    public String getXmlRpcUrl();

    /**
     * get aria2 ops
     *
     * @return ops
     */
    public Aria2Ops getAria2Ops();
}
