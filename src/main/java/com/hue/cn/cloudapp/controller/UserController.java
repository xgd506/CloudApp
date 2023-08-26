package com.hue.cn.cloudapp.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hue.cn.cloudapp.entity.Text;
import com.hue.cn.cloudapp.service.TextService;
import com.hue.cn.cloudapp.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author:xgd
 * @Date:2023/8/25 19:01
 * @Description:
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private TextService textService;
    //查询管理员发表的所有科普文章
    @RequestMapping("/findAll/{page}/{limit}")
    public Result findAll(@PathVariable Long page,
                          @PathVariable Long limit){
        Page<Text> pageParams = new Page<>(page, limit);
        IPage<Text> pageModel = textService.findAllText(pageParams);
        return Result.ok(pageModel);
    }
    //根据文章id查询指定文章
    @GetMapping("/get/{id}")
    public Result get(@PathVariable Long id){
        Text text = textService.getById(id);
        return Result.ok(text);
    }
    //查询该分类的所有文章
    @GetMapping("/findByCategory/{category}")
    public Result findByCategory(@PathVariable String category){

       List<Text> textList = textService.findByCategory(category);
       return Result.ok(textList);
    }

}
