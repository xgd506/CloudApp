package com.hue.cn.cloudapp.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author:xgd
 * @Date:2023/8/25 19:06
 * @Description:
 */
@Data
@TableName("text")
public class Text {
    @TableField("id")
    private Long id;
    @TableField("title")
    private String title;
    @TableField("content")
    private String content;
    @TableField("author")
    private String author;
    @TableField("category")
    private String category;
    @TableField("cover_image")
    private String coverImage;
}
