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
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

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
    @CliCommand(value = "connect", help = "Connect with aria2 through xml-rpc")
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
    @CliCommand(value = "stats", help = "Output download statistics")
    public String stats() {
        try {
            StringBuilder builder = new StringBuilder();
            Map<String, String> globalStat = ariaService.getGlobalStat();
            for (Map.Entry<String, String> entry : globalStat.entrySet()) {
                builder.append(entry.getKey() + ":" + entry.getValue() + LINE_SEPARATOR);
            }
            return builder.toString().trim();
        } catch (Exception e) {
            log.error("stats", e);
            return wrappedAsRed(e.getMessage());
        }
    }

    /**
     * Pause all the active downloads
     *
     * @return message
     */
    @CliCommand(value = "sleep", help = "Pause all the active downloads")
    public String sleep() {
        try {
            ariaService.sleep();
            return "Aria sleeped!";
        } catch (Exception e) {
            log.error("sleep", e);
            return wrappedAsRed(e.getMessage());
        }
    }

    /**
     * Pause all the active downloads
     *
     * @return message
     */
    @CliCommand(value = "wake", help = "Resume all the paused downloads")
    public String wake() {
        try {
            ariaService.sleep();
            return "Aria waked!";
        } catch (Exception e) {
            log.error("wake", e);
            return wrappedAsRed(e.getMessage());
        }
    }

    /**
     * Clear the list of stopped downloads and errors
     *
     * @return message
     */
    @CliCommand(value = "purge", help = "Clear the list of stopped downloads and errors")
    public String purge() {
        try {
            ariaService.sleep();
            return "Aria purged!";
        } catch (Exception e) {
            log.error("purge", e);
            return wrappedAsRed(e.getMessage());
        }
    }

    /**
     * add download url
     *
     * @return message
     */
    @CliCommand(value = "add", help = "Download the given url")
    public String add(@CliOption(key = {""}, mandatory = true, help = "URL") String url) {
        try {
            String gid = ariaService.addUri(url, Collections.emptyMap());
            return "Download added and GID is " + gid;
        } catch (Exception e) {
            log.error("add", e);
            return wrappedAsRed(e.getMessage());
        }
    }

    /**
     * remove download
     *
     * @return message
     */
    @CliCommand(value = "remove", help = "Remove the download corresponding to the given GID")
    public String remove(@CliOption(key = {""}, mandatory = true, help = "gid") String gid) {
        try {
            ariaService.remove(gid);
            return "gid: " + gid + " removed!";
        } catch (Exception e) {
            log.error("remove", e);
            return wrappedAsRed(e.getMessage());
        }
    }

    /**
     * pause download
     *
     * @return message
     */
    @CliCommand(value = "pause", help = "Pause the download corresponding to the given GID")
    public String pause(@CliOption(key = {""}, mandatory = true, help = "gid") String gid) {
        try {
            ariaService.pause(gid);
            return "gid: " + gid + " paused!";
        } catch (Exception e) {
            log.error("pause", e);
            return wrappedAsRed(e.getMessage());
        }
    }

    /**
     * resume download
     *
     * @return message
     */
    @CliCommand(value = "resume", help = "Resume the download corresponding to the given GID")
    public String resume(@CliOption(key = {""}, mandatory = true, help = "gid") String gid) {
        try {
            ariaService.unpause(gid);
            return "gid: " + gid + " resumed!";
        } catch (Exception e) {
            log.error("resume", e);
            return wrappedAsRed(e.getMessage());
        }
    }

    /**
     * info status
     *
     * @param gid download gid
     * @return status
     */
    @SuppressWarnings("unchecked")
    @CliCommand(value = "info", help = "Display gid information")
    public String info(@CliOption(key = {""}, mandatory = true, help = "gid") String gid) {
        try {
            if (gid.equals("stopped")) {
                tellStopped();
            } else if (gid.equals("waitting")) {
                tellWaiting();
            } else if (gid.equals("active")) {
                tellActive();
            } else {
                Map<String, Object> status = ariaService.tellStatus(gid);
                if (status != null) {
                    printDetail(status);
                }
            }
        } catch (Exception e) {
            log.error("info", e);
            return wrappedAsRed(e.getMessage());
        }
        return null;
    }

    /**
     * info stopped
     *
     * @return stopped information
     */
    @CliCommand(value = "stopped", help = "Stopped Queue")
    public String tellStopped() {
        try {
            List<Map<String, Object>> items = ariaService.tellStopped(0, 10);
            printTasks("Stopped", items);
            return null;
        } catch (Exception e) {
            log.error("tellStopped", e);
            return wrappedAsRed(e.getMessage());
        }
    }

    /**
     * info stopped
     *
     * @return stopped information
     */
    @CliCommand(value = "errors", help = "List of errors")
    public String tellErrors() {
        try {
            List<Map<String, Object>> items = ariaService.tellStopped(0, 100);
            printTasks("Errors", items);
            return null;
        } catch (Exception e) {
            log.error("errors", e);
            return wrappedAsRed(e.getMessage());
        }
    }

    /**
     * info paused
     *
     * @return paused information
     */
    @CliCommand(value = "paused", help = "List of paused downloads")
    public String tellWaiting() {
        try {
            List<Map<String, Object>> items = ariaService.tellWaiting(0, 10);
            printTasks("Paused", items);
            return null;
        } catch (Exception e) {
            log.error("tellWaiting", e);
            return wrappedAsRed(e.getMessage());
        }
    }

    /**
     * info stopped
     *
     * @return stopped information
     */
    @CliCommand(value = "list", help = "List of active downloads")
    public String tellActive() {
        try {
            List<Map<String, Object>> items = ariaService.tellActive();
            printTasks("Active", items);
            return null;
        } catch (Exception e) {
            log.error("tellActive", e);
            return wrappedAsRed(e.getMessage());
        }
    }

    /**
     * start aria2
     *
     * @return start aria2
     */
    @CliCommand(value = "start", help = "Start aria2c")
    public String startAria2() {
        try {
            List<String> args = new ArrayList<String>();
            args.add("aria2c");
            args.add("--enable-rpc");
            args.add("--rpc-allow-origin-all");
            args.add("--rpc-listen-all");
            args.add("--dir=/tmp/aria");
            args.add("--daemon=true");
            executeCommand(args.toArray(new String[args.size()]));
            return connect("localhost", "6800");
        } catch (Exception e) {
            log.error("start", e);
            return wrappedAsRed(e.getMessage());
        }
    }

    /**
     * stop aria2
     *
     * @return stop status
     */
    @CliCommand(value = "stop", help = "Stop aria2c")
    public String stopAria2() {
        try {
            List<String> args = new ArrayList<String>();
            args.add("killall");
            args.add("-9");
            args.add("aria2c");
            executeCommand(args.toArray(new String[args.size()]));
            return "Aria2 Stopped";
        } catch (Exception e) {
            log.error("start", e);
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

    /**
     * print status
     *
     * @param tasks download task
     */
    @SuppressWarnings("unchecked")
    private void printTasks(String title, List<Map<String, Object>> tasks) {
        if (tasks == null || tasks.isEmpty()) {
            System.out.println("No task found!");
            return;
        }
        for (Map<String, Object> task : tasks) {
            System.out.println("==============" + title + "==========");
            System.out.println("gid:" + task.get("gid"));
            System.out.println("status:" + task.get("status"));
            Map<String, Object> files = (Map<String, Object>) ((Object[]) task.get("files"))[0];
            System.out.println("path:" + files.get("path"));
            Map<String, Object> uriInfo = (Map<String, Object>) ((Object[]) files.get("uris"))[0];
            System.out.println("path:" + uriInfo.get("uri"));
            System.out.println("========================");
        }
    }

    /**
     * print detail information
     *
     * @param task task
     */
    @SuppressWarnings("unchecked")
    private void printDetail(Map<String, Object> task) {
        Object[] files = (Object[]) task.get("files");
        System.out.println("Files:");
        for (Object temp : files) {
            Map<String, Object> file = (Map<String, Object>) temp;
            for (Map.Entry<String, Object> entry : file.entrySet()) {
                Object value = entry.getValue();
                if (value instanceof String) {
                    System.out.println("  " + entry.getKey() + ":" + value);
                }
            }
            System.out.println("  uris:");
            Object[] uris = (Object[]) file.get("uris");
            for (Object temp2 : uris) {
                Map<String, String> uri = (Map<String, String>) temp2;
                for (Map.Entry<String, String> entry2 : uri.entrySet()) {
                    System.out.println("    " + entry2.getKey() + ":" + entry2.getValue());
                }
            }
        }
        System.out.println("Basic:");
        for (Map.Entry<String, Object> entry : task.entrySet()) {
            if (!entry.getKey().equals("files")) {
                System.out.println("  " + entry.getKey() + ":" + entry.getValue());
            }
        }
    }

    /**
     * execute command
     *
     * @param args args
     * @return output
     * @throws Exception exception
     */
    private String executeCommand(String[] args) throws Exception {
        Process process = new ProcessBuilder(args).start();
        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        List<String> lines = new ArrayList<String>();
        String line;
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }
        process.destroy();
        return StringUtils.join(lines, "\r\n");
    }

}