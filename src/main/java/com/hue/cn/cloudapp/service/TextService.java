package com.hue.cn.cloudapp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hue.cn.cloudapp.entity.Text;
import com.hue.cn.cloudapp.entity.TextVo;

import java.util.List;

/**
 * @Author:xgd
 * @Date:2023/8/25 19:04
 * @Description:
 */
public interface TextService extends IService<Text> {
    IPage<Text> findAllText(Page<Text> pageParams);

    List<Text> findByCategory(String category);

    void saveText(TextVo textVo);
}
