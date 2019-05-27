package com.harry.base.excel.util;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

/**
 * @author harry
 * @version 1.0
 * @title: DateUtil
 * @description: TODO
 * @date 2019/5/26 15:14
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtil {

    public final static SimpleDateFormat ENGLISH_LOCAL_DF = new SimpleDateFormat(
            "EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
    private final static LoadingCache<String, SimpleDateFormat> mDateFormatLoadingCache =
            CacheBuilder.newBuilder()
                    .maximumSize(5)
                    .build(new CacheLoader<String, SimpleDateFormat>() {

                        @Override
                        public SimpleDateFormat load(String pattern) {
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                            simpleDateFormat.setLenient(true);
                            return simpleDateFormat;
                        }
                    });

    public static Date parse(String pattern, Object value) throws Exception {
        String valueString = (String) value;
        return DateUtil.mDateFormatLoadingCache.get(pattern).parse(valueString);
    }

    public static String format(String pattern, Date value) {
        try {
            return DateUtil.mDateFormatLoadingCache.get(pattern).format(value);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return value.toString();
    }
}
