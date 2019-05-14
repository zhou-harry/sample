package com.harry.security.session;

import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author harry
 * @version 1.0
 * @title: CustomerInvalidSessionStrategy
 * @description: TODO
 * @date 2019/5/15 0:01
 */
public class CustomerInvalidSessionStrategy extends AbstractSessionStrategy implements InvalidSessionStrategy {


    /**
     * @param invalidSessionUrl
     */
    public CustomerInvalidSessionStrategy(String invalidSessionUrl) {
        super(invalidSessionUrl);
    }

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        onSessionInvalid(request, response);
    }


}
