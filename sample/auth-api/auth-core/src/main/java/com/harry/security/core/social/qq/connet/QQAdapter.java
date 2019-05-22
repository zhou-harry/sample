package com.harry.security.core.social.qq.connet;

import com.harry.security.core.social.qq.api.QQ;
import com.harry.security.core.social.qq.api.QQUserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * @author harry
 * @version 1.0
 * @title: QQAdapter
 * @description: 三方用户数据 适配转换器
 * @date 2019/5/12 15:50
 */
public class QQAdapter implements ApiAdapter<QQ> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean test(QQ api) {
        return true;
    }

    @Override
    public void setConnectionValues(QQ api, ConnectionValues values) {

        QQUserInfo userInfo = api.getUserInfo();
        logger.info("QQImpl调用获取用户信息： " + userInfo.toString());

        values.setDisplayName(userInfo.getNickname()); // 昵称
        values.setImageUrl(userInfo.getFigureurl_qq_1()); // 图像url 40x40
        values.setProfileUrl(null); //个人主页
        values.setProviderUserId(userInfo.getOpenId()); // openid
    }

    /**
     * 用户第三方用户绑定解绑
     * @param api
     * @return
     */
    @Override
    public UserProfile fetchUserProfile(QQ api) {
        return null;
    }

    @Override
    public void updateStatus(QQ api, String message) {

    }
}
