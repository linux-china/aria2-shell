package org.mvnsearch.aria2.shell.commands;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.fusesource.jansi.Ansi;
import org.mvnsearch.aria2.service.AriaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Map;

/**
 * Aria Shell operation commands
 *
 * @author linux_china
 */
@SuppressWarnings("StringConcatenationInsideStringBufferAppend")
@Component
public class AriaShellOperationCommands implements CommandMarker {
    /**
     * log
     */
    private Logger log = LoggerFactory.getLogger(AriaShellOperationCommands.class);
    /**
     * The platform-specific line separator.
     */
    public static final String LINE_SEPARATOR = SystemUtils.LINE_SEPARATOR;
    /**
     * aria service
     */
    @Autowired
    private AriaService ariaService;

    /**
     * init method: load current bucket
     */
    @PostConstruct
    public void init() {
        connect("localhost", "6800");
    }

    /**
     * config command to save aliyun OSS information
     *
     * @return result
     */
    @CliCommand(value = "connect", help = "Connect with aria xml-rpc")
    public String connect(@CliOption(key = {"host"}, mandatory = false, help = "Host") String host,
                          @CliOption(key = {"port"}, mandatory = false, help = "Port") String port) {
        try {
            if (StringUtils.isEmpty(host)) {
                host = "localhost";
            }
            if (StringUtils.isEmpty(port)) {
                port = "6800";
            }
            ariaService.connect(host, Integer.valueOf(port));
        } catch (Exception e) {
            log.error("connect", e);
            return wrappedAsRed(e.getMessage());
        }
        return "Connected with " + ariaService.getXmlRpcUrl();
    }

    /**
     * display global state
     *
     * @return global state
     */
    @CliCommand(value = "globalState", help = "Display global state")
    public String globalState() {
        try {
            StringBuilder builder = new StringBuilder();
            Map<String, String> globalStat = ariaService.getGlobalStat();
            for (Map.Entry<String, String> entry : globalStat.entrySet()) {
                builder.append(entry.getKey() + ":" + entry.getValue() + LINE_SEPARATOR);
            }
            return builder.toString().trim();
        } catch (Exception e) {
            log.error("connect", e);
            return wrappedAsRed(e.getMessage());
        }
    }

    /**
     * download oss object
     *
     * @return message
     */
    @CliCommand(value = "add", help = "Add download url")
    public String add(@CliOption(key = {""}, mandatory = true, help = "URL") String url) {
        try {
            String gid = ariaService.addUri(url, Collections.emptyMap());
            return "Download added and GID is " + gid;
        } catch (Exception e) {
            log.error("connect", e);
            return wrappedAsRed(e.getMessage());
        }
    }

    /**
     * wrapped as red with Jansi
     *
     * @param text text
     * @return wrapped text
     */
    private String wrappedAsRed(String text) {
        return Ansi.ansi().fg(Ansi.Color.RED).a(text).toString();
    }


    /**
     * wrapped as yellow with Jansi
     *
     * @param text text
     * @return wrapped text
     */
    private String wrappedAsYellow(String text) {
        return Ansi.ansi().fg(Ansi.Color.YELLOW).a(text).toString();
    }

}