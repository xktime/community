package com.xktime.community.service.login;

import com.alibaba.fastjson.JSON;
import com.xktime.community.model.dto.GithubAccessTokenDTO;
import com.xktime.community.model.dto.GithubUserDTO;
import com.xktime.community.model.entity.User;
import com.xktime.community.model.enums.ScopeEnum;
import okhttp3.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Service
public class GithubLoginServiceImpl implements LoginService {
    @Value("${login.github.client.id}")
    private String client_id;
    @Value("${login.github.redirect.client_secret}")
    private String client_secret;
    @Value("${login.github.redirect.uri}")
    private String redirect_uri;
    @Value("${login.github.request.uri}")
    private String request_uri;

    @Override
    public User getUser(String code, String state) {
        GithubUserDTO githubUserDTO = getGithubUser(code, state);
        if (githubUserDTO == null) {
            return null;
        }
        return transferGithubUserToUser(githubUserDTO);
    }

    /**
     *
     * @param state  随机值，用来防止 cross-sit 攻击
     * @param scope 指定了最后能获取到的信息，取值范围有 user 和 repo 等等,默认同时取 user 和 repo 的信息
     * @param allowSignUp 是否允许用户在认证的时候注册 Github 账号
     * @return
     */
    @Override
    public String getOAuthRedirectURL(String state, ScopeEnum scope, boolean allowSignUp) {
        if (state == null) {
            throw new IllegalArgumentException("state不能为空");
        }
        StringBuilder requestUri = new StringBuilder(request_uri).append("?");
        requestUri.append("client_id=").append(client_id);
        requestUri.append("&redirect_uri=").append(requestUri);
        requestUri.append("&state=").append(state);
        if (scope != null) {
            requestUri.append("&scope=").append(scope.getValue());
        }
        requestUri.append("&allow_signup=").append(allowSignUp);
        return requestUri.toString();
    }

    /**
     *
     * @param state  随机值，用来防止 cross-sit 攻击
     * @param login 推荐登录的 Github 账户
     * @param scope 指定了最后能获取到的信息，取值范围有 user 和 repo 等等,默认同时取 user 和 repo 的信息
     * @param allowSignUp 是否允许用户在认证的时候注册 Github 账号
     * @return
     */
    @Override
    public String getOAuthRedirectURL(String state, String login, ScopeEnum scope, boolean allowSignUp) {
        if (state == null) {
            throw new IllegalArgumentException("state不能为空");
        }
        StringBuilder requestUri = new StringBuilder(getOAuthRedirectURL(state, scope, allowSignUp));
        requestUri.append("&login=").append(login);
        return requestUri.toString();
    }


    private User transferGithubUserToUser(GithubUserDTO githubUser) {
        User user = new User();
        user.setToken(UUID.randomUUID().toString());
        user.setAccountId(githubUser.getId());
        user.setBio(githubUser.getBio());
        user.setLogin(githubUser.getLogin());
        user.setAvatarUrl(githubUser.getAvatar_url());
        //如果设置了名字就用名字，否则用账户名
        String name = githubUser.getName() == null ? githubUser.getLogin() : githubUser.getName();
        user.setName(name);
        user.setLoginTime(new Date());
        return user;
    }

    private GithubUserDTO getGithubUser(String code, String state) {
        GithubAccessTokenDTO accessTokenDTO = new GithubAccessTokenDTO();
        accessTokenDTO.setState(state);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_id(client_id);
        accessTokenDTO.setClient_secret(client_secret);
        accessTokenDTO.setRedirect_uri(redirect_uri);
        return getGithubUserByToken(getAccessToken(accessTokenDTO));
    }

    private GithubUserDTO getGithubUserByToken(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?" + accessToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response != null && response.body() != null) {
                String userData = response.body().string();
                return JSON.parseObject(userData, GithubUserDTO.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getAccessToken(GithubAccessTokenDTO accessTokenDTO) {
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
}
