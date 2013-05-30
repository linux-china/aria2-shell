package org.mvnsearch.aria2.shell.commands;

import org.apache.commons.lang3.StringUtils;
import org.mvnsearch.aria2.service.AriaService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.support.DefaultPromptProvider;
import org.springframework.shell.support.util.OsUtils;
import org.springframework.stereotype.Component;

/**
 * Aria Shell prompt provider
 *
 * @author linux_china
 */
@Component
public class AriaShellPromptProvider extends DefaultPromptProvider implements InitializingBean {
    /**
     * prompt
     */
    public static String prompt = "aria2c";
    /**
     * symbol
     */
    public static String symbol = "#";
    /**
     * aria service
     */
    @Autowired
    private AriaService ariaService;

    /**
     * init method
     *
     * @throws Exception exception
     */
    public void afterPropertiesSet() throws Exception {
        //if Windows OS, adjust symbo to '>'
        if ((OsUtils.isWindows())) {
            symbol = ">";
        }
    }

    /**
     * prompt
     *
     * @return prompt
     */
    @Override
    public String getPrompt() {
        if (StringUtils.isEmpty(ariaService.getHost())) {
            return "[" + prompt + "]" + symbol;
        } else {
            return "[" + ariaService.getHost() + ":" + ariaService.getPort() + "]" + symbol;
        }
    }

    /**
     * name
     *
     * @return name
     */
    @Override
    public String name() {
        return "aria-shell-java-cli-prompt-provider";
    }

}
