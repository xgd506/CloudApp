package com.hue.cn.cloudapp.controller;

import com.hue.cn.cloudapp.entity.Text;
import com.hue.cn.cloudapp.entity.TextVo;
import com.hue.cn.cloudapp.service.TextService;
import com.hue.cn.cloudapp.utils.Result;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author:xgd
 * @Date:2023/8/24 18:51
 * @Description:
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Resource
    private TextService textService;
    //管理员权限
    @RequestMapping("/loadup")
    public Result save(@RequestBody TextVo textVo){
        textService.saveText(textVo);
        return Result.ok(null);
    }
    //删除文章
    @RequestMapping("/delete/{id}")
    public Result delete(@PathVariable Long id){
        textService.removeById(id);
        return Result.ok(null);
    }


}
