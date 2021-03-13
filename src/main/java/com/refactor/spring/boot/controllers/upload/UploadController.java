package com.refactor.spring.boot.controllers.upload;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.system.SystemUtil;
import com.refactor.spring.boot.domains.JsonResult;
import com.refactor.spring.boot.domains.ProgressEntity;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

/**
 * 文件上传控制器
 */
@Slf4j
@RestController
public class UploadController {

    /**
     * 配置的上传路径
     */
    @Value("${file.path}")
    private String filePath;

    /**
     * 普通文件上传
     *
     * @param files
     * @param request
     * @return
     */
    @PostMapping("/uploadFile")
    @ApiOperation("普通文件上传")
    public JsonResult uploadFile(@RequestParam("files") List<CommonsMultipartFile> files, HttpServletRequest request) {
        StringBuffer buffer = new StringBuffer();
        for (CommonsMultipartFile file : files) {
            //获取原文件名称和后缀
            String originalFilename = file.getOriginalFilename();
            // 获取文件后缀名
            String fil_extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

            LocalDateTime now = LocalDateTimeUtil.now();
            int year = now.getYear();
            int month = now.getMonthValue();
            int day = now.getDayOfMonth();
            long milli = now.toInstant(ZoneOffset.of("+8")).toEpochMilli();
            String path = StrUtil.format("{}/{}/{}/{}", year, month, day, milli + originalFilename);
            try {
                File file1 = new File(filePath + File.separator + path);
                boolean mkdirs = file1.mkdirs();
                log.info("文件夹{}创建{}", file1.getAbsolutePath(), mkdirs ? "成功" : "失败");
                file.transferTo(file1);
                log.info("{} 上传成功！", originalFilename);
                if (StrUtil.isBlank(buffer)) {
                    path = StrUtil.format("{}://{}:{}/{}/{}", request.getScheme(), StrUtil.equals(SystemUtil.getHostInfo().getAddress(), "127.0.0.1") ? "192.168.31.120" : SystemUtil.getHostInfo().getAddress(), request.getServerPort(), "upload", path);
                } else {
                    path = StrUtil.format(",{}://{}:{}/{}/{}", request.getScheme(), StrUtil.equals(SystemUtil.getHostInfo().getAddress(), "127.0.0.1") ? "192.168.31.120" : SystemUtil.getHostInfo().getAddress(), request.getServerPort(), "upload", path);
                }
                buffer.append(path);
            } catch (IOException e) {
                e.printStackTrace();
                log.error("{} 上传失败！", originalFilename);
                continue;
            }

        }
        return new JsonResult(buffer.toString());
    }

    /**
     * 获取上传进度
     *
     * @param request
     * @return
     */
    @GetMapping(value = "/uploadStatus")
    @ApiOperation("获取上传进度")
    @ResponseBody
    public Object uploadStatus(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ProgressEntity percent = (ProgressEntity) session.getAttribute("status");
        return percent;
    }

}

