package com.easy.service.impl;

import com.easy.domain.ResponseResult;
import com.easy.domain.entity.LoginUser;
import com.easy.domain.entity.User;
import com.easy.domain.vo.BlogUserLoginVo;
import com.easy.domain.vo.UserInfoVo;
import com.easy.service.BlogLoginService;
import com.easy.utils.BeanCopyUtils;
import com.easy.utils.JwtUtil;
import com.easy.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author wxx
 * @create 08--07--10:45
 * @description:
 * 重写login方法，将用户名密码封装到authenticate，供UserDetailServiceImpl里面loadUserByUsername使用
 */
@Service
//认证，判断用户登录是否成功
public class BlogLoginServiceImpl implements BlogLoginService {
    @Autowired
    //AuthenticationManager是security官方提供的接口
    private AuthenticationManager authenticationManager;

    @Autowired
    //RedisCache是我们在ez-framework工程的config目录写的类
    private RedisCache redisCache;

    //登录
    @Override
    public ResponseResult login(User user) {
        //封装登录的用户名和密码
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        //在下一行之前，封装的数据会先走UserDetailsServiceImpl实现类，这个实现类在我们的ez-framework工程的service/impl目录里面
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //上面那一行会得到所有的认证用户信息authenticate。然后下一行需要判断用户认证是否通过，如果authenticate的值是null，就说明认证没有通过
        //判断是否认证通过
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        //获取userid 生成token
        // authenticate.getPrincipal()获取认证的主体
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        //把这个userid通过我们写的JwtUtil工具类转成密文，这个密文就是token值
        String jwt = JwtUtil.createJWT(userId);

        //下面那行的第一个参数: 把上面那行的jwt，也就是token值保存到Redis。存到时候是键值对的形式，值就是jwt，key要加上 "bloglogin:" 前缀
        //下面那行的第二个参数: 要把哪个对象存入Redis。我们写的是loginUser，里面有权限信息，后面会用到
        //把用户信息存入redis
        redisCache.setCacheObject("bloglogin:"+userId,loginUser);
        //把token和userinfo封装 返回
        //把User转换成UserInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo vo = new BlogUserLoginVo(jwt,userInfoVo);
        return ResponseResult.okResult(vo);
    }

    //退出
    @Override
    public ResponseResult logout() {
        //获取token 解析获取userid
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //获取userid
        Long userId = loginUser.getUser().getId();
        //删除redis中的用户信息
        redisCache.deleteObject("bloglogin:"+userId);
        return ResponseResult.okResult();
    }
}
