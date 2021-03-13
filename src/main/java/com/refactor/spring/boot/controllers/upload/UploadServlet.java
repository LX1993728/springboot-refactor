package com.refactor.spring.boot.controllers.upload;

import com.refactor.spring.boot.tools.ServletTool;
import org.apache.commons.fileupload.FileItem;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 使用最原生的servlet，因为request未经过springboot的MultiPartRequest处理，天生支持Commons-FileUpload
 */
@WebServlet(urlPatterns = "/upload", description = "原生fileUpload文件上传")
public class UploadServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 上传文件夹
//        String uploadDir = request.getServletContext().getRealPath("upload");
        String uploadDirPath = "upload";
        String tempDirPath = "temp";
        File uploadDir = new File(uploadDirPath);
        File tempDir = new File(tempDirPath);
        if (!uploadDir.exists()){
            uploadDir.mkdirs();
        }
        if (!tempDir.exists()){
            tempDir.mkdirs();
        }

        // file less than 10kb will be store in memory, otherwise in file system.
        final int threshold = 10240;
        final int maxRequestSize = 1024 * 1024 * 4;	// 4MB

        // 返回的结果Map集合
        Map<String, Object> resultMap = new HashMap<>();
        List<String> urls = new ArrayList<>();

        if(ServletFileUpload.isMultipartContent(request)) {
            // Create a factory for disk-based file items.
            DiskFileItemFactory factory = new DiskFileItemFactory(threshold, tempDir);
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

            assert items != null;
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
                    // 相对地址
                    String path = uploadedFile.getPath();
                    // 相对路径 避免window地址中的双斜杠 '\\'
                    String url = path.replaceAll("\\\\", "/");
                    urls.add(uploadedFile.getPath());
                }
            }
        }  else {
            // 文件解析失败
        }

        resultMap.put("urls", urls);
        ServletTool.writeJsonStrForObject(response, resultMap);
    }
}
