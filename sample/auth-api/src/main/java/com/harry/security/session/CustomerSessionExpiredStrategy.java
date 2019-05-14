package com.harry.security.session;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author harry
 * @version 1.0
 * @title: CustomerSessionExpiredStrategy
 * @description: TODO
 * @date 2019/5/15 0:03
 */
public class CustomerSessionExpiredStrategy extends AbstractSessionStrategy implements SessionInformationExpiredStrategy {


    /**
     * @param invalidSessionUrl
     */
    public CustomerSessionExpiredStrategy(String invalidSessionUrl) {
        super(invalidSessionUrl);
    }

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        onSessionInvalid(event.getRequest(), event.getResponse());
    }


}
