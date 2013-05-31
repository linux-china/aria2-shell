package org.mvnsearch.aria2.service.impl;

import org.mvnsearch.aria2.service.Aria2Ops;
import org.mvnsearch.aria2.service.AriaService;
import org.mvnsearch.aria2.xmlrpc.XmlRpcClientFactoryBean;
import org.springframework.stereotype.Component;

/**
 * aria service implementation
 *
 * @author linux_china
 */
@SuppressWarnings("unchecked")
@Component("ariaService")
public class AriaServiceImpl implements AriaService {
    /**
     * xml rpc host
     */
    private String host;
    /**
     * xml rpc port
     */
    private int port;
    /**
     * aria2 ops
     */
    private Aria2Ops aria2Ops;

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
        XmlRpcClientFactoryBean factoryBean = new XmlRpcClientFactoryBean();
        factoryBean.setMethodPrefix("aria2.");
        factoryBean.setServiceUrl(xmlRpcUrl);
        factoryBean.setServiceInterface(Aria2Ops.class);
        factoryBean.afterPropertiesSet();
        this.aria2Ops = (Aria2Ops) factoryBean.getObject();
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
     * get aria2 ops
     *
     * @return ops
     */
    public Aria2Ops getAria2Ops() {
        return this.aria2Ops;
    }
}
