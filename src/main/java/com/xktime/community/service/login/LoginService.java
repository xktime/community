package com.xktime.community.service.login;

import com.xktime.community.model.entity.User;
import com.xktime.community.model.enums.ScopeEnum;

public interface LoginService {

    User getUser(String code, String state);

    String getOAuthRedirectURL(String state, ScopeEnum scope, boolean allow_signup);

    String getOAuthRedirectURL(String state, String login, ScopeEnum scope, boolean allow_signup);

}
