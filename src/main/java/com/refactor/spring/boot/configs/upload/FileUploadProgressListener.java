package com.refactor.spring.boot.configs.upload;

import com.refactor.spring.boot.domains.ProgressEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.ProgressListener;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

/**
 * 文件上传进度
 */
@Slf4j
@Component
public class FileUploadProgressListener implements ProgressListener {

    private HttpSession httpSession;

    public void setHttpSession(HttpSession httpSession) {
        this.httpSession = httpSession;
        ProgressEntity status = new ProgressEntity();
        //默认进度为0
        httpSession.setAttribute("status", status);
    }

    /**
     * pBytesRead  到目前为止读取文件的比特数
     * pContentLength 文件总大小
     * pItems 目前正在读取第几个文件
     */
    @Override
    public void update(long pBytesRead, long pContentLength, int pItems) {
        ProgressEntity status = (ProgressEntity) httpSession.getAttribute("status");
        status.setPBytesRead(pBytesRead);
        status.setPContentLength(pContentLength);
        status.setPItems(pItems);
        log.info("UploadProgressListener update ProgressEntity:  " + status.toString());
    }
}
