package life.majiang.community.provider;


import com.alibaba.fastjson.JSON;
import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.dto.GithupUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithupProvider {

    public String getAccessToken(AccessTokenDTO accessTokenDTO){

        String url ="https://github.com/login/oauth/access_token";
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDTO),mediaType);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String[] split = string.split("&");
            String[] str_token = split[0].split("=");
            String token = str_token[1];
            return token;
        } catch (IOException e) {
           // e.printStackTrace();
        }
        return null;
    }


    public GithupUser getUser(String accessToken){

        String url ="https://api.github.com/user?access_token=";
        OkHttpClient client = new OkHttpClient();


            Request request = new Request.Builder()
                    .url(url+accessToken)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                String string = response.body().string();
                GithupUser githupUser = JSON.parseObject(string, GithupUser.class);
                return githupUser;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
    }

}
