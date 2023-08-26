package com.hue.cn.cloudapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hue.cn.cloudapp.entity.Text;
import com.hue.cn.cloudapp.entity.TextVo;
import com.hue.cn.cloudapp.mapper.TextMapper;
import com.hue.cn.cloudapp.service.TextService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author:xgd
 * @Date:2023/8/25 19:04
 * @Description:
 */
@Service
public class TextServiceImpl extends ServiceImpl<TextMapper, Text> implements TextService {
    @Override
    public IPage<Text> findAllText(Page<Text> pageParams) {
        Page<Text> textPage = baseMapper.selectPage(pageParams, null);
        return textPage;
    }

    @Override
    public List<Text> findByCategory(String category) {
        LambdaQueryWrapper<Text> wrapper=new LambdaQueryWrapper<>();
        wrapper.like(Text::getCategory,category);
        List<Text> textList = baseMapper.selectList(wrapper);
        return textList;
    }

    //保存上传的文章
    @Override
    public void saveText(TextVo textVo) {
        Text text=new Text();
        text.setTitle(textVo.getTitle());
        text.setAuthor(textVo.getAuthor());
        text.setCategory(textVo.getCategory());
        text.setContent(textVo.getContent());
        text.setCoverImage(textVo.getCoverImage());
        baseMapper.insert(text);

    }
}
