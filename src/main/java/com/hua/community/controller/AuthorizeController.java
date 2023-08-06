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
    /*
    @Value("${github.client.id}")
    private String c_id;
    @Value("${github.client.secret}")
    private String c_sc;
    @Value("${github.redirect.uri}")
    private String ur;
    */

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name = "state") String state)
    {
        AccesstokenDTO accesstokenDTO=new AccesstokenDTO();

        /*
        System.out.println(c_id);
        System.out.println(c_sc);
        System.out.println(ur);
         */

        accesstokenDTO.setClient_id("f32965c43d6030acc89c");
        accesstokenDTO.setCode(code);
        accesstokenDTO.setClient_secret("71a8afc70b559781ec80f3b837a2404746974eed");
        accesstokenDTO.setRedirect_uri("http://localhost:8887/callback");
        accesstokenDTO.setState(state);
        String accesstoken=githubProvider.getAccessTokenDTO(accesstokenDTO);
        System.out.println(accesstoken);
        GithubUser user= githubProvider.getgithubUser(accesstoken);
        System.out.println(user.getName());
        return "index";
    }

}
