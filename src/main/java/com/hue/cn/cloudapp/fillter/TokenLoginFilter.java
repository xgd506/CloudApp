package com.hue.cn.cloudapp.fillter;

import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hue.cn.cloudapp.config.CustomUser;
import com.hue.cn.cloudapp.entity.LoginVo;
import com.hue.cn.cloudapp.utils.JwtHelper;
import com.hue.cn.cloudapp.utils.ResponseUtil;
import com.hue.cn.cloudapp.utils.Result;
import com.hue.cn.cloudapp.utils.ResultCodeEnum;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author:xgd
 * @Date:2023/4/12 19:46
 * @Description:  登陆拦截器   用户认证
 */
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {
    private RedisTemplate redisTemplate;
    //构造器 传入AuthenticationManager
    public TokenLoginFilter(AuthenticationManager auth,RedisTemplate redisTemplate){
        //将auth放入
        this.setAuthenticationManager(auth);
        this.setPostOnly(false);
        //指定登录接口及提交方式，可以指定任意路径
        this.setRequiresAuthenticationRequestMatcher
                (new AntPathRequestMatcher("/system/user/login","POST"));
        this.redisTemplate=redisTemplate;
    }

    /**
     * 登录认证
     * @param req
     * @param res
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {
        try {
            //获得登陆信息
            LoginVo loginVo = new ObjectMapper().readValue(req.getInputStream(), LoginVo.class);
            //认证校验由框架完成  将信息封装到Authentication中
            // 调用AuthenticationManager中的authenticate方法
            //检验①通过用户名查询用户②密码校验
            Authentication authenticationToken = new UsernamePasswordAuthenticationToken
                    (loginVo.getUsername(), loginVo.getPassword());
            return this.getAuthenticationManager().authenticate(authenticationToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 登录成功
     * @param request
     * @param response
     * @param chain
     * @param auth
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        CustomUser customUser = (CustomUser) auth.getPrincipal();
        //校验成功 将信息封装成token
        String token = JwtHelper.createToken(customUser.getSysUser().getId(), customUser.getSysUser().getUsername());
        Map<String, Object> map = new HashMap<>();
        System.out.println(customUser.getAuthorities());
        //将权限信息保存到redis中
        redisTemplate.opsForValue().set(customUser.getUsername(), JSON.toJSONString(customUser.getAuthorities()));
        map.put("token", token);
        //将token放入请求头中 ResponseUtil原生的方法
        //用户授权是需要取出该授权有后端进行
        ResponseUtil.out(response, Result.ok(map));
    }

    /**
     * 登录失败
     * @param request
     * @param response
     * @param e
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException e) throws IOException, ServletException {
        //校验失败
        if(e.getCause() instanceof RuntimeException) {
            ResponseUtil.out(response, Result.build(null,ResultCodeEnum.FAIL));
        } else {
            ResponseUtil.out(response, Result.build(null,ResultCodeEnum.FAIL));
        }
    }
}
