package life.majiang.community.controller;

import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.dto.GithupUser;
import life.majiang.community.provider.GithupProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AuthorizeController {

    @Autowired
    private GithupProvider githupProvider;

    @Value("${githup.Client_id}")
    private String clientId;
    @Value("${githup.Redirect_uri}")
    private String RedirectUri;
    @Value("${githup.Client_secret}")
    private String ClientSecret;
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(RedirectUri);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(ClientSecret);
        String accessToken = githupProvider.getAccessToken(accessTokenDTO);
        GithupUser user = githupProvider.getUser(accessToken);
       if(user!=null){
        //登录成功，写session和cookies
            request.getSession().setAttribute("user",user);
            return "redirect:/";

       }else{
           //登录失败，重新登录
           return "redirect:/";
       }
       // return "index";
    }
}