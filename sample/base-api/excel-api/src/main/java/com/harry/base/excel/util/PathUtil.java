package com.harry.base.excel.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.File;

/**
 * @author harry
 * @version 1.0
 * @title: PathUtil
 * @description: TODO
 * @date 2019/5/26 14:39
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PathUtil {

    public static String getClasspath() {
        return PathUtil.class.getResource("/").getPath();
    }

    public static String getFilePathByClasspath(String name) {
        return PathUtil.getClasspath() + name;
    }

    public static File getFileByClasspath(String name) {
        return new File(PathUtil.getFilePathByClasspath(name));
    }
}
