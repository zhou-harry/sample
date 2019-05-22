package com.harry.security.web.session;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author harry
 * @version 1.0
 * @title: WebSessionExpiredStrategy
 * @description: Session 并发失效策略处理器
 * @date 2019/5/15 0:03
 */
public class WebSessionExpiredStrategy extends AbstractSessionStrategy implements SessionInformationExpiredStrategy {


    /**
     * @param invalidSessionUrl
     */
    public WebSessionExpiredStrategy(String invalidSessionUrl) {
        super(invalidSessionUrl);
    }

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        onSessionInvalid(event.getRequest(), event.getResponse());
    }

    @Override
    protected boolean isConcurrency() {
        return true;
    }

}
