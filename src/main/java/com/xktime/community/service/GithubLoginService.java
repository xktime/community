package com.xktime.community.service;

import com.alibaba.fastjson.JSON;
import com.xktime.community.model.dto.AccessTokenDTO;
import com.xktime.community.model.dto.GithubUserDTO;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GithubLoginService {
	@Value("${github.client.id}")
	private String client_id;
	@Value("${github.redirect.client_secret}")
	private String client_secret;
	@Value("${github.redirect.uri}")
	private String redirect_uri;

	public GithubUserDTO getGithubUser(String code, String state) {
		AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
		accessTokenDTO.setState(state);
		accessTokenDTO.setCode(code);
		accessTokenDTO.setClient_id(client_id);
		accessTokenDTO.setClient_secret(client_secret);
		accessTokenDTO.setRedirect_uri(redirect_uri);
		String accessToken = getAccessToken(accessTokenDTO);
		return getGithubUserByToken(accessToken);
	}

	private String getAccessToken(AccessTokenDTO accessTokenDTO) {
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
}