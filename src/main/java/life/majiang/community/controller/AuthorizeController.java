package life.majiang.community.controller;

import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.dto.GithupUser;
import life.majiang.community.model.User;
import life.majiang.community.provider.GithupProvider;
import life.majiang.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    private UserService userService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(RedirectUri);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(ClientSecret);
        String accessToken = githupProvider.getAccessToken(accessTokenDTO);
        GithupUser githupuser = githupProvider.getUser(accessToken);
       if(githupuser!=null && githupuser.getId() != null){

           User user = new User();
           String token = UUID.randomUUID().toString();
           user.setToken(token);
           user.setAccountId(String.valueOf(githupuser.getId()));
           user.setName(githupuser.getName());

           user.setAvatarUrl(githupuser.getAvatar_url());
           userService.createOrUpdate(user);
           //userMapper.insert(user);

           response.addCookie(new Cookie("token",token));
            //登录成功，写session和cookies

           // request.getSession().setAttribute("user",githupuser);
            return "redirect:/";

       }else{
           //登录失败，重新登录
           return "redirect:/";
       }
    }

    @GetMapping("/logout")
    public String logOut(HttpServletRequest request,HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
