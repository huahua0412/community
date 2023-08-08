package com.hua.community.controller;

import com.hua.community.dto.AccesstokenDTO;
import com.hua.community.dto.GithubUser;
import com.hua.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController
{
    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String c_id;
    @Value("${github.client.secret}")
    private String c_sc;
    @Value("${github.redirect.uri}")
    private String ur;


    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name = "state") String state)
    {
        AccesstokenDTO accesstokenDTO=new AccesstokenDTO();


        System.out.println(c_id);
        System.out.println(c_sc);
        System.out.println(ur);


        accesstokenDTO.setClient_id(c_id);
        accesstokenDTO.setCode(code);
        accesstokenDTO.setClient_secret(c_sc);
        accesstokenDTO.setRedirect_uri(ur);
        accesstokenDTO.setState(state);
        String accesstoken=githubProvider.getAccessTokenDTO(accesstokenDTO);
        System.out.println(accesstoken);
        GithubUser user= githubProvider.getgithubUser(accesstoken);
        System.out.println(user.getName());
        return "index";
    }

}
