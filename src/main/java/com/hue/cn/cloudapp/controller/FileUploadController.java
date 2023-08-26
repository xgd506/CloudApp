package com.hue.cn.cloudapp.controller;

import com.hue.cn.cloudapp.service.FileUploadService;
import com.hue.cn.cloudapp.utils.Result;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @Author:xgd
 * @Date:2023/8/3 9:32
 * @Description:  上传图片  上传到阿里云
 */
@RestController
@RequestMapping("/admin/text")
@CrossOrigin
public class FileUploadController {
    @Resource
    private FileUploadService fileUploadService;

    //文件上传
    @PostMapping("fileUpload")
    public Result fileUpload(MultipartFile file) throws Exception{
        return Result.ok(fileUploadService.fileUpload(file));
    }
}
