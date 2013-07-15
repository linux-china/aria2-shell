package org.mvnsearch.aria2.service;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * aria2 operations
 *
 * @author linux_china
 */
public interface Aria2Ops {
    /**
     * add uri
     *
     * @param url     uri
     * @param options options
     * @return gid
     */
    public String addUri(String[] url, @NotNull Map options);

    /**
     * remove gid
     *
     * @param gid gid
     */
    public void remove(String gid);

    /**
     * pause gid
     *
     * @param gid gid
     */
    public void pause(String gid);

    /**
     * pause all
     */
    public void pauseAll();

    /**
     * unpause gid
     *
     * @param gid gid
     */
    public void unpause(String gid);

    /**
     * unpause all
     */
    public void unpauseAll();

    /**
     * tell status
     *
     * @param gid gid
     * @return status object
     */
    public Map<String, Object> tellStatus(String gid);

    /**
     * get uris
     *
     * @param gid gid
     * @return uris
     */
    public Map<String, Object>[] getUris(String gid);

    /**
     * get files
     *
     * @param gid gid
     * @return files
     */
    public Map<String, Object>[] getFiles(String gid);

    /**
     * get active list
     *
     * @return active list
     */
    public Object[] tellActive();

    /**
     * get waiting list
     *
     * @param offset     offset
     * @param maxResults max results
     * @return waiting list
     */
    public Object[] tellWaiting(Integer offset, Integer maxResults);

    /**
     * get stopped list
     *
     * @param offset     offset
     * @param maxResults max results
     * @return stopped list
     */
    public Object[] tellStopped(Integer offset, Integer maxResults);


    /**
     * get option of gid
     *
     * @param gid gid
     * @return option
     */
    public Map<String, Object> getOption(String gid);

    /**
     * change option
     *
     * @param gid    gid
     * @param option option
     */
    public void changeOption(String gid, Map<String, String> option);

    /**
     * get global option
     *
     * @return global option
     */
    public Map<String, String> getGlobalOption();

    /**
     * change global option
     *
     * @param option option
     */
    public void changeGlobalOption(Map<String, String> option);

    /**
     * get global stat
     *
     * @return global stat
     */
    public Map<String, String> getGlobalStat();

    /**
     * purges completed/error/removed downloads to free memory
     */
    public void purgeDownloadResult();


    /**
     * removes completed/error/removed download denoted by gid from memory
     *
     * @param gid gid
     */
    public void removeDownloadResult(String gid);

    /**
     * get version
     *
     * @return version
     */
    public Map<String, Object> getVersion();

}
