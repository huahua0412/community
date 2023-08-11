package com.hua.community.controller;

import com.hua.community.dto.AccesstokenDTO;
import com.hua.community.dto.GithubUser;
import com.hua.community.mapper.UserMapper;
import com.hua.community.model.MyUser;
import com.hua.community.provider.GithubProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String c_id;
    @Value("${github.client.secret}")
    private String c_sc;
    @Value("${github.redirect.uri}")
    private String ur;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request) {
        AccesstokenDTO accesstokenDTO = new AccesstokenDTO();

        accesstokenDTO.setClient_id(c_id);
        accesstokenDTO.setCode(code);
        accesstokenDTO.setClient_secret(c_sc);
        accesstokenDTO.setRedirect_uri(ur);
        accesstokenDTO.setState(state);
        String accesstoken = githubProvider.getAccessTokenDTO(accesstokenDTO);
        GithubUser githubUser = githubProvider.getgithubUser(accesstoken);
        System.out.println(accesstoken);
        System.out.println(githubUser.getName());
        if (githubUser != null) {
            //登录成功写cookie
            MyUser user = new MyUser();
            user.setToken(UUID.randomUUID().toString());
            user.setName(githubUser.getName());
            user.setAccountid(String.valueOf(githubUser.getId()));
            user.setGmtcreate(System.currentTimeMillis());
            user.setGmtmodified(user.getGmtcreate());
            userMapper.insert(user);
            request.getSession().setAttribute("user", githubUser);
            return "redirect:/";    //地址不变，页面渲染成index
        } else {
            //登录失败
            return "redirect:/";
        }
    }

}
