package com.refactor.spring.boot.controllers;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/upload", description = "微信接口验证")
public class UploadServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

            List<FileItem> items = null;
            try {
                items = upload.parseRequest(request);
            } catch (FileUploadException e) {
                e.printStackTrace();
            }

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
                    try {
                        item.write(uploadedFile);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }  else {
            // 文件解析失败
        }
    }
}
