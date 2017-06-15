package download;

import domain.Page;
import domain.Request;
import domain.Site;

/**
 * 根据URL负责下载页面信息
 */
public interface Downloader {

    /**
     * 下载网面存储在Page中
     * @param request request
     * @param site site
     * @return  page
     */
    Page download(Request request, Site site);
}
