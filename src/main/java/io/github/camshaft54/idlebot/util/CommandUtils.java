package io.github.camshaft54.idlebot.util;

import org.apache.commons.lang.StringUtils;

public class CommandUtils {
    public static boolean isInteger(String str) {
        if (str == null) return false;
        if (str.length() != 1 && str.charAt(0) == '-') str = str.substring(1);
        return StringUtils.isNumeric(str);
    }
}
