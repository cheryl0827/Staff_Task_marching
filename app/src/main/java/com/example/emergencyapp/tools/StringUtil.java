package com.example.emergencyapp.tools;


import android.text.TextUtils;

import java.util.IllegalFormatException;
import java.util.Random;

/**
 * 字符串相关工具
 */
public class StringUtil {

    private static Random sRandom = null;

    /**
     * 字符串拼接
     *
     * @param objects
     * @return
     */
    public static String concat(Object... objects) {
        return concatWithSeparate(null, objects);
    }

    /**
     * 字符串拼接
     *
     * @param objects
     * @return
     */
    public static String concatWithSeparate(Object separate, Object... objects) {
        final StringBuilder stringBuilder = new StringBuilder();
        if (objects != null) {
            for (Object obj : objects) {
                if (obj != null) {
                    stringBuilder.append(obj);
                    if (separate != null) {
                        stringBuilder.append(separate);
                    }
                }
            }
        }
        return stringBuilder.toString();
    }

    /**
     * toString，为null时当作""返回
     */
    public static String toString(Object object) {
        if (object == null) return "";
        return String.valueOf(object);
    }

    /**
     * 安全的格式化输出，如果参数对不上不会崩溃，而是输出format的内容
     */
    public static String format(String format, Object... args) {
        String result = format;
        try {
            result = String.format(format, args);
        } catch (IllegalFormatException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 剪切字符串text，直到出现最后一个sub
     */
    public static CharSequence subUntil(StringBuilder text, String sub) {
        if (TextUtils.isEmpty(text) || TextUtils.isEmpty(sub)) return text;
        final int index = text.lastIndexOf(sub);
        if (index > 0) {
            return text.substring(0, index);
        }
        return text;
    }

    /**
     * 将超过@param max的字符串裁掉，max小于1无效
     */
    public static CharSequence maxString(CharSequence text, int max) {
        if (TextUtils.isEmpty(text) || max < 1) return text;
        final int length = text.length();
        return length <= max ? text : text.subSequence(0, max);
    }

    /**
     * 随机生成字符串
     *
     * @param length 生成长度
     */
    public static CharSequence random(int length) {
        return random(length, false);
    }

    /**
     * 随机生成字符串
     *
     * @param length 生成长度
     */
    public static CharSequence random(int length, boolean allNumber) {
        if (length <= 0) return "";
        StringBuilder result = new StringBuilder();
        Random random = getRandom();
        for (int index = 0; index < length; index++) {
            if (allNumber || random.nextBoolean()) {
                result.append(random.nextInt(10));
            } else {
                char c = (char) (random.nextInt(26) + (random.nextBoolean() ? 65 : 97));
                result.append(c);
            }
        }
        return result;
    }

    /*public static CharSequence replaceRange(CharSequence text, int start, int end, CharSequence replacement) {
        if (TextUtils.isEmpty(text)) return text;
        final int length = text.length();
        if (start > length) start = 0;
        CharSequence head, body, tail;
        head = start <= 0 ? "" : text.subSequence(0, start);
        body = end <= 0 || end > length ? "" : text.subSequence(start, end);
        tail = 0 < end && end < length ? text.subSequence(end, length) : "";
        return new StringBuilder(head).append(body).append(tail);
    }*/

    private static Random getRandom() {
        if (sRandom == null) {
            synchronized (StringUtil.class) {
                if (sRandom == null) {
                    sRandom = new Random();
                }
            }
        }
        return sRandom;
    }
}
