package com.harry.security.core.social.weixin.connet;

import com.harry.security.core.social.weixin.api.Weixin;
import com.harry.security.core.social.weixin.api.WeixinUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * @author harry
 * @version 1.0
 * @title: WeixinAdapter
 * @description: 微信 api适配器，将微信 api的数据模型转为spring social的标准模型。
 * @date 2019/5/12 18:28
 */
public class WeixinAdapter implements ApiAdapter<Weixin> {

    private String openId;

    public WeixinAdapter() {}

    public WeixinAdapter(String openId){
        this.openId = openId;
    }

    @Override
    public boolean test(Weixin api) {
        return true;
    }

    @Override
    public void setConnectionValues(Weixin api, ConnectionValues values) {
        WeixinUserInfo profile = api.getUserInfo(openId);
        values.setProviderUserId(profile.getOpenid());
        values.setDisplayName(profile.getNickname());
        values.setImageUrl(profile.getHeadimgurl());
    }

    @Override
    public UserProfile fetchUserProfile(Weixin api) {
        return null;
    }

    @Override
    public void updateStatus(Weixin api, String message) {

    }
}
