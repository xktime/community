package com.xktime.community.provider;

import com.alibaba.fastjson.JSON;
import com.xktime.community.dto.AccessTokenDTO;
import com.xktime.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {

    public String getAccessToken (AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response != null && response.body() != null) {
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getGithubUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
            .url("https://api.github.com/user?" + accessToken)
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (response != null && response.body() != null) {
                String userData = response.body().string();
                return JSON.parseObject(userData, GithubUser.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
