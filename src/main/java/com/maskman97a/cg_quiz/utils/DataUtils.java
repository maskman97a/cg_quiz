package com.maskman97a.cg_quiz.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class DataUtils {
    private DataUtils() {

    }

    public static String objectToJson(Object o) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(o);
    }

    public static boolean isNullOrEmpty(CharSequence cs) {
        return nullOrEmpty(cs);
    }

    public static boolean isNullOrEmpty(final Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean nullOrEmpty(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static LocalDate string2LocalDate(String obj) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(obj, formatter);
        } catch (Exception e) {
            return null;
        }
    }


    public static Long safeToLong(Object obj) {
        return safeToLong(obj, 0L);
    }

    public static Long safeToLong(Object obj, Long defaultValue) {
        if (obj == null) {
            return defaultValue;
        }
        try {
            return Long.parseLong(obj.toString());
        } catch (final NumberFormatException nfe) {
            return defaultValue;
        }
    }

    public static String safeToString(Object obj) {
        return safeToString(obj, "");
    }

    public static String safeToString(Object obj, String defaultValue) {
        if (obj == null) {
            return defaultValue;
        }
        return obj.toString();
    }

    public static boolean isNullObject(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof String) {
            return isNullOrEmpty(obj.toString());
        }
        return false;
    }

    public static Long parseToLong(Object value, Long defaultVal) {
        try {
            String str = parseToString(value);
            if (nullOrEmpty(str)) {
                return null;
            }
            return Long.parseLong(str);
        } catch (Exception e) {
            return defaultVal;
        }
    }

    public static Long parseToLong(Object value) {
        return parseToLong(value, null);
    }

    public static Double parseToDouble(Object value) {
        return parseToDouble(value, null);
    }

    public static Double parseToDouble(Object value, Double defaultVal) {
        try {
            String str = parseToString(value);
            if (nullOrEmpty(str)) {
                return null;
            }
            return Double.parseDouble(str);
        } catch (Exception e) {
            return defaultVal;
        }
    }

    public static String parseToString(Object value, String defaultVal) {
        try {
            if (value == null) return "";
            return String.valueOf(value);
        } catch (Exception e) {
            return defaultVal;
        }
    }

    public static String parseToString(Object value) {
        return parseToString(value, "");
    }

    public static LocalDateTime parseToLocalDatetime(Object value) {
        if (value == null)
            return null;
        String tmp = parseToString(value, null);
        if (tmp == null)
            return null;

        try {
            LocalDateTime rtn = convertStringToLocalDateTime(tmp, "yyyy-MM-dd HH:mm:ss.S");
            return rtn;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static LocalDateTime convertStringToLocalDateTime(String value, String format) throws Exception {
        Date date = convertStringToTime(value, format);
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static Date convertStringToTime(String date, String pattern) throws ParseException {
        if (date == null || "".equals(date.trim())) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.parse(date);

    }

    public static boolean safeEqual(Long obj1, Long obj2) {
        if (obj1 == obj2) return true;
        return ((obj1 != null) && (obj2 != null) && (obj1.compareTo(obj2) == 0));
    }

    public static boolean safeEqual(BigInteger obj1, BigInteger obj2) {
        if (obj1 == obj2) return true;
        return ((obj1 != null) && (obj2 != null) && obj1.equals(obj2));
    }

    public static boolean safeEqual(String obj1, String obj2) {
        if (obj1 == obj2) return true;
        return ((obj1 != null) && (obj2 != null) && obj1.equals(obj2));
    }

    public static boolean safeEqual(Short obj1, Short obj2) {
        if (obj1 == obj2) return true;
        return ((obj1 != null) && (obj2 != null) && (obj1.compareTo(obj2) == 0));
    }

    public static boolean safeEqual(Object obj1, Object obj2) {
        if (obj1 == obj2) return true;
        return ((obj1 != null) && (obj2 != null) && obj1.toString().equals(obj2.toString()));
    }

    public static String convertLocalDateTimeToString(LocalDateTime date, String format) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        return dtf.format(date);
    }

    public static String formatContent(String content) {
        String contentResult = "";
        if (!isNullObject(content)) {
            contentResult = content.length() > 2000 ?
                    (content.substring(0, 1900) + "..." + content.substring(content.length() - 3, content.length())) :
                    content;
        }
        return contentResult;
    }


    public static BigDecimal safeToBigDecimal(Object obj1) {
        if (obj1 == null) {
            return BigDecimal.ZERO;
        }
        try {
            return new BigDecimal(obj1.toString());
        } catch (final NumberFormatException nfe) {
            log.error(nfe.getMessage(), nfe);
            return BigDecimal.ZERO;
        }
    }

    public static boolean isNullOrZero(Long value) {
        return (value == null || value.equals(0L));
    }

    /*
     * Kiem tra Long bi null hoac zero
     *
     * @param value
     * @return
     */
    public static boolean isNullOrZero(Double value) {
        return (value == null || value == 0);
    }

    /*
     * Kiem tra Long bi null hoac zero
     *
     * @param value
     * @return
     */
    public static boolean isNullOrZero(String value) {
        return (value == null || safeToLong(value).equals(0L));
    }

    /*
     * Kiem tra Long bi null hoac zero
     *
     * @param value
     * @return
     */
    public static boolean isNullOrZero(Integer value) {
        return (value == null || value.equals(0));
    }

    /**
     * Kiem tra Bigdecimal bi null hoac zero
     *
     * @param value
     * @return
     */
    public static boolean isNullOrZero(BigDecimal value) {
        return (value == null || value.compareTo(BigDecimal.ZERO) == 0);
    }

    public static Double safeToDouble(Object obj1, Double defaultValue) {
        if (obj1 == null) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(obj1.toString());
        } catch (final NumberFormatException nfe) {
            log.error(nfe.getMessage(), nfe);
            return defaultValue;
        }
    }

    public static Double safeToDouble(Object obj1) {
        return safeToDouble(obj1, 0.0);
    }

    public static String generateTransId(String str) {
        String strHeader = "";
        DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern("yyMMddHHmmss");
        LocalDateTime ldt = LocalDateTime.now();
        String strDate = ldt.format(CUSTOM_FORMATTER);
        int lengthStr = str.length();
        String result = "";
        if (lengthStr > 5) {
            str = str.substring(lengthStr - 5, lengthStr);
        } else {
            int fillNum = 5 - lengthStr;
            char[] c = new char[fillNum];
            Arrays.fill(c, '0');
            result = new String(c);
        }
        return strHeader + strDate + result + str;
    }

    public static String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber.startsWith("856")) {
            return phoneNumber;
        } else {
            if (phoneNumber.startsWith("20") || phoneNumber.startsWith("30") || phoneNumber.startsWith("020") || phoneNumber.startsWith("030")) {
                if (phoneNumber.startsWith("020") || phoneNumber.startsWith("030")) {
                    phoneNumber = phoneNumber.substring(1);
                }
                return "856" + phoneNumber;
            } else {
                if (phoneNumber.startsWith("9") || phoneNumber.startsWith("7")) {
                    phoneNumber = "20" + phoneNumber;
                }
                return "856" + phoneNumber;
            }
        }
    }

    public static LocalDateTime stringToLocalDateTme(String date) {
        if (date != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
            LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
            return dateTime;
        }
        return null;
    }


    public static boolean isStringNullOrEmpty(Object obj1) {
        return obj1 == null || obj1.toString().trim().equals("");
    }

    public static boolean isNullOrEmpty(List<Object[]> obj1) {
        return obj1 == null || obj1.isEmpty();
    }


    public static int getTotalPage(int totalRecord, int pageLimit) {
        return (int) Math.ceil((double) totalRecord / pageLimit);
    }


    public static Long getLong(Object obj) {
        if (obj != null) {
            String s = obj.toString().split("\\.")[0];
            return Long.parseLong(s);
        }
        return null;
    }

    public static String getString(Object obj) {
        if (obj != null) {
            return obj.toString();
        }
        return null;
    }

    public static Double getDouble(Object obj) {
        if (obj != null) {
            return Double.parseDouble(obj.toString());
        }
        return null;
    }

    public static Integer getInteger(Object obj) {
        if (obj != null) {
            return Integer.parseInt(obj.toString());
        }
        return null;
    }

    public static boolean validString(Object temp) {
        return temp != null && !temp.toString().trim().isEmpty();
    }

    public static String genOTP() {
        int min = 100000;
        int max = 999999;
        int random_int = (int) (Math.random() * (max - min + 1) + min);
        String OTP = String.valueOf(random_int);
        return OTP;
    }

    public static String formatNumber(Long number) {
        if (number != null) {
            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            return decimalFormat.format(number);
        } else {
            return null;
        }
    }

    public static boolean isNumber(String str) {
        return !isNullOrEmpty(str) && str.trim().matches("^\\d+$");
    }


    public static String formatContent(String content, int maxLength) {
        String contentResult = "";
        if (!isNullObject(content)) {
            contentResult = content.length() > maxLength ?
                    (content.substring(0, maxLength - 20) + "..." + content.substring(content.length() - 5, content.length())) :
                    content;
        }
        return contentResult;
    }

    public static String getMd5(String input) {
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            // of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isCurrentDateInRange(LocalDateTime startDate, LocalDateTime endDate) {
        LocalDateTime currentDate = LocalDateTime.now();
        return !currentDate.isBefore(startDate) && !currentDate.isAfter(endDate);
    }

    public static String maskEmail(String email) {
        if (email == null || !email.contains("@")) {
            return email; // Invalid email, return as is
        }

        String[] parts = email.split("@");
        String localPart = parts[0];
        String domainPart = parts[1];

        if (localPart.length() > 2) {
            return localPart.charAt(0) + "****" + localPart.charAt(localPart.length() - 1) + "@" + domainPart;
        }

        return "****@" + domainPart; // For very short local parts
    }

    public static <T, R> List<R> convertList(List<T> list, Function<T, R> func) {
        return list.stream().map(func).collect(Collectors.toList());
    }
}
