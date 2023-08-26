package com.hue.cn.cloudapp.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Date;

/**
 * @Author:xgd
 * @Date:2023/8/24 18:41
 * @Description:
 */
@Data
@TableName("user")
public class SysUser {
    private Long id;
    private String username;
    private String password;
    private String role;
}
