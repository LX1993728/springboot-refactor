package com.refactor.spring.boot.controllers;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

@Slf4j
@Scope("prototype")
@Controller
public class TestController3 {

    {
        log.info("=======初始化创建控制器================");
    }

    @GetMapping("/scope")
    public Object scope() {
        return new HttpEntity<String>("scope");
    }

    /**
     * 实现文件上传
     *
     * @param request  请求
     * @param response 响应
     * @return 页面
     */
    @PostMapping(value = "/apache/upload")
    public String upload(HttpServletRequest request,
                         HttpServletResponse response) throws Exception {
        // 上传文件夹
        String uploadDir = request.getServletContext().getRealPath("/WEB-INF/upload/");
        File tempDir = new File(uploadDir);

        // file less than 10kb will be store in memory, otherwise in file system.
        final int threshold = 10240;
        final int maxRequestSize = 1024 * 1024 * 4;	// 4MB

        if(ServletFileUpload.isMultipartContent(request)) {
            // Create a factory for disk-based file items.
            FileItemFactory factory = new DiskFileItemFactory(threshold, tempDir);
            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);
            // Set overall request size constraint.
            upload.setSizeMax(maxRequestSize);

            List<FileItem> items = upload.parseRequest(request);

            for(FileItem item : items) {
                // 普通的表单字段
                if(item.isFormField()) {
                    String name = item.getFieldName();
                    String value = item.getString();
                    System.out.println(name + ": " + value);
                } else {
                    // 真实的文件
                    //file upload
                    String fieldName = item.getFieldName();
                    String fileName = item.getName();
                    File uploadedFile = new File(uploadDir + File.separator + fieldName + "_" + fileName);
                    item.write(uploadedFile);
                }
            }
        }  else {
            // 文件解析失败
        }
        return "apache";

    }

}
