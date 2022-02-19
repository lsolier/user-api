package com.lsolier.user.api.session;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class UserApiStore {

    private final HttpSession session;

    public UserApiStore(HttpSession session) {
        this.session = session;
    }

    public void saveToken(String accessToken) {
        this.session.setAttribute("token", accessToken);
    }

    public String getToken() {
        return (String) this.session.getAttribute("token");
    }

}
