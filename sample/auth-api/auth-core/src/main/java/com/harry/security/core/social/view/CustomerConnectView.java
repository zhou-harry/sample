package com.harry.security.core.social.view;

import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author harry
 * @version 1.0
 * @title: CustomerConnectView
 * @description: 绑定第三方完成后的返回视图，多个第三方可视图重用
 * @see org.springframework.social.connect.web.ConnectController.connectedView(String providerId)
 * @date 2019/5/12 22:02
 */
public class CustomerConnectView extends AbstractView {

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {

        response.setContentType("text/html;charset=UTF-8");

        String name = this.getBeanName();

        if (name.endsWith("Connected")){
            response.getWriter().write("<h3>绑定成功</h3>");
        }else {
            response.getWriter().write("<h3>解绑成功</h3>");
        }
    }

}
