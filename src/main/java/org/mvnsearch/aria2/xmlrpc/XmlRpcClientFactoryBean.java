package org.mvnsearch.aria2.xmlrpc;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.remoting.support.UrlBasedRemoteAccessor;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * xml-rpc client factory bean
 *
 * @author linux_china
 */
public class XmlRpcClientFactoryBean extends UrlBasedRemoteAccessor implements MethodInterceptor, InitializingBean, FactoryBean<Object> {
    /**
     * proxy object
     */
    private Object proxyObject = null;
    /**
     * xml-rpc client
     */
    private XmlRpcClient client;
    /**
     * method prefix
     */
    private String methodPrefix = "";

    /**
     * set method prefix
     *
     * @param methodPrefix method prefix
     */
    public void setMethodPrefix(String methodPrefix) {
        this.methodPrefix = methodPrefix;
    }

    /**
     * init logic to create proxy object and xml-rpc client
     */
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        // create proxy
        proxyObject = ProxyFactory.getProxy(getServiceInterface(), this);
        // create xml-rpc client
        try {
            XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
            config.setServerURL(new URL(getServiceUrl()));
            client = new XmlRpcClient();
            client.setConfig(config);
        } catch (Exception ignore) {

        }
    }

    /**
     * get proxy object
     *
     * @return proxy object
     * @throws Exception exception
     */
    public Object getObject() throws Exception {
        return this.proxyObject;
    }

    /**
     * get object type
     *
     * @return service interface
     */
    public Class<?> getObjectType() {
        return getServiceInterface();
    }

    /**
     * singleton mark
     *
     * @return singleton
     */
    public boolean isSingleton() {
        return true;
    }

    /**
     * method invoke interceptor
     *
     * @param invocation method invocation
     * @return return object
     * @throws Throwable exception
     */
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        Object[] arguments = invocation.getArguments();
        List<Object> params = new ArrayList<Object>();
        if (arguments != null && arguments.length > 0) {
            Collections.addAll(params, arguments);
        }
        return client.execute(methodPrefix + method.getName(), params);
    }
}
