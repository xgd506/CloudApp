package com.hue.cn.cloudapp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hue.cn.cloudapp.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Author:xgd
 * @Date:2023/8/24 18:41
 * @Description:
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
}
