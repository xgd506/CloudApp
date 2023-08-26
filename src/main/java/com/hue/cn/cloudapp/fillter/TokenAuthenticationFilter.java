package com.hue.cn.cloudapp.fillter;

import com.alibaba.fastjson.JSON;

import com.hue.cn.cloudapp.config.CustomUser;
import com.hue.cn.cloudapp.config.LoginUserInfoHelper;
import com.hue.cn.cloudapp.service.UserService;
import com.hue.cn.cloudapp.utils.JwtHelper;
import com.hue.cn.cloudapp.utils.ResponseUtil;
import com.hue.cn.cloudapp.utils.Result;
import com.hue.cn.cloudapp.utils.ResultCodeEnum;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @Author:xgd
 * @Date:2023/4/12 20:07
 * @Description:  用户的登陆状态保存在token中，每当访问除登陆接口时需要取出token中的登陆状态进行校验
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    public TokenAuthenticationFilter(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

//    @Resource private UserService userService;
    private RedisTemplate redisTemplate;
    //对token进行解析
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        logger.info("uri:"+request.getRequestURI());
        //如果是登录接口，直接放行
        if("/system/user/login".equals(request.getRequestURI())) {

            chain.doFilter(request, response);
            return;
        }
        //对Authentication认证信息进行验证
        //①返回一个包含登陆状态的Auth对象②返回一个null 证明不处于登陆状态
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        if(null != authentication) {
            //将Authentication对象放入上下文环境
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //放行
            chain.doFilter(request, response);
        } else {
            ResponseUtil.out(response, Result.build(null, ResultCodeEnum.PERMISSION));
        }
    }
    //将认证信息从request请求域中取出
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        // token置于header里
        String token = request.getHeader("token");
        logger.info("token:"+token);
        if (!StringUtils.isEmpty(token)) {
            //将登陆信息记录到登陆类中LoginUserInfoHelper
            LoginUserInfoHelper.setUserId(JwtHelper.getUserId(token));
            LoginUserInfoHelper.setUsername(JwtHelper.getUsername(token));
            //取出token中username，判断是否处于登陆状态
            String useruame = JwtHelper.getUsername(token);
            logger.info("useruame:"+useruame);
//            UserDetails userDetails = userService.loadUserByUsername(useruame);
            if (!StringUtils.isEmpty(useruame)) {
                //将信息返回 取出redis的数据
                String authString = (String) redisTemplate.opsForValue().get(useruame);
                if(!StringUtils.isEmpty(authString)){
                    List<Map> maps = JSON.parseArray(authString, Map.class);
                    System.out.println(maps);
                    List<SimpleGrantedAuthority> authorities=new ArrayList<>();
                    for (Map map:maps
                         ) {
                        authorities.add(new SimpleGrantedAuthority((String)map.get("authority")));
                    }
                    //System.out.println(authorities);
                    return new UsernamePasswordAuthenticationToken(useruame, null, authorities);
                }else{
                    return new UsernamePasswordAuthenticationToken(useruame, null, Collections.emptyList());
                }
            }
        }
        //为空返回null，在之后进行判断
        return null;
    }
}
