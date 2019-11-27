package util;
import org.apache.commons.lang3.StringUtils;

import	java.util.regex.Pattern;

import java.util.regex.Pattern;

public class ValidatorUtil {

    private static final String mobile_pattern="1\\d{10}";

    public static boolean isMobile(String value){
        return StringUtils.isNotBlank(value)&&value.matches(mobile_pattern);
    }
}
