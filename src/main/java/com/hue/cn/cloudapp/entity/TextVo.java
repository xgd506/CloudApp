package com.hue.cn.cloudapp.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author:xgd
 * @Date:2023/8/25 20:05
 * @Description:
 */
@Data
public class TextVo {
    private String title;
    private String content;
    private String author;
    private String category;
    private String coverImage;
}
