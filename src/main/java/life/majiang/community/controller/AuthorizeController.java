package life.majiang.community.controller;

import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.dto.GithupUser;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.User;
import life.majiang.community.provider.GithupProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

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

    @Autowired
    private UserMapper userMapper;

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
        GithupUser githupuser = githupProvider.getUser(accessToken);
       if(githupuser!=null){
        //登录成功，写session和cookies
           User user = new User();
           user.setToken(UUID.randomUUID().toString());
           user.setAccounId(String.valueOf(githupuser.getId()));
           user.setName(githupuser.getName());
           user.setGmtCreate(System.currentTimeMillis());
           user.setGmtModified(user.getGmtCreate());
           userMapper.insert(user);

            request.getSession().setAttribute("user",githupuser);
            return "redirect:/";

       }else{
           //登录失败，重新登录
           return "redirect:/";
       }
       // return "index";
    }
}
