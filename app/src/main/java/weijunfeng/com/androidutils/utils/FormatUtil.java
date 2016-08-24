package weijunfeng.com.androidutils.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hexin on 2016/8/23.
 */
public class FormatUtil {
    public static void main(String[] args) {
        String ss = "12222";

        System.out.println(String.format("¥%,f", 1222227444447.0f));
        System.out.println("1234567890".replaceAll("(?<=\\w)(?=(?:\\w{3})+$)", ","));
//        System.out.println(new StringBuilder(new StringBuilder("1234567890").reverse().toString().replaceAll("(?<=\\w)(?=(?:\\w{3})+$)", ",")).reverse().toString());
        System.out.println("12345678901".replaceAll("(?<=(^(?:\\w{3})+))", ","));

        System.out.println("12345678902".replaceAll("(?<=\\d)(?=(?:\\d{4})+$)", ","));


        System.out.println("12345678903".replaceAll("(?=(?:\\d{4})+$)", ","));


        System.out.println("12345678904.000".replaceAll("(?<=(^(\\d{3})+))", ","));


        System.out.println("#12345678905.000033".replaceAll("(?<=^\\d+)(?=(\\d{3})+\\b)", ","));


        System.out.println("1234500006".replaceAll("(?=\\d)(?<=(^(\\d{3})+$))", ","));
        System.out.println("$1234500007.00".replaceAll("(?=(?!\\b))(?=(\\d{3})+\\b)", ","));
        System.out.println("123450000700007".replaceAll("((?<=(\\d{1,4}))|(?<=(^\\d+)))(?=(\\d{3})+\\b)", ","));
        System.out.println("123450000700$".replaceAll("(?!\\d{3,}+$)(?!\\b+$)(?=(\\w{3})+\\b)", ","));

        String str = " 你好，空指针，1234Welcome to 游戏大厅！ ";
        Pattern pattern = Pattern.compile("^\\d+$");
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            System.out.println(matcher.group());
        }
        String sss = "123456a7dd8X.000";
        StringBuilder stringBuilder = new StringBuilder(sss);
        String s = stringBuilder.reverse().toString();
        stringBuilder.setLength(0);
        String s1 = stringBuilder.append(s.replaceAll("(?=(?:\\w{4})+\\b)", " ")).reverse().toString();
        System.out.println("s1 = " + s1);
        System.out.println(sss.replaceAll("(?<=^\\w+)(?=(?:\\w{3})+\\b)", ","));
        System.out.println("aaaa12456,90".replaceAll("\\d+", ","));
        System.out.println("s1 = " + sss);
        System.out.println("get = " + getNumbers2("9999.90990#89099."));
        String a = "I paid $90 for 10 oranges, 12 pears and 8 apples. I saved $5 on ";
        Pattern p = Pattern.compile("(?<=\\$)\\d+");
        Matcher m = p.matcher(a);
        while (m.find()) {
            String group = m.group();
            System.out.println("ss" + group);
        }

        a = "https://mail.huawei.com ";
        p = Pattern.compile(".+(?=:)");
        m = p.matcher(a);
        while (m.find()) {
            String group = m.group();
            System.out.println("ss1" + group);
        }
        Pattern p0 = Pattern.compile("cat");
        Matcher m0 = p0.matcher("one cat two cats in the yard");
        StringBuffer sb = new StringBuffer();
        while (m0.find()) {
            m0.appendReplacement(sb, "dog");
        }
        m0.appendTail(sb);
        System.out.println(sb.toString());
    }

    public static String getNumbers(String content) {
        Pattern pattern = Pattern.compile("\\d{4,}+(\\.?+)\\d+\\b");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            System.out.println("matcher = " + matcher.group());
            content = content.replace(matcher.group(), matcher.group().replaceAll("(?<=^\\d+)(?=(\\d{3})+\\b)", ","));
        }
        return matcher.toMatchResult().toString();
    }

    public static String getNumbers2(String content) {
        Pattern pattern = Pattern.compile("\\d{4,}(\\.?)\\d*\\b");
        Matcher matcher = pattern.matcher(content);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            System.out.println("matcher = " + matcher.group());
            matcher.appendReplacement(sb, matcher.group().replaceAll("(?<=^\\d+)(?=(\\d{3})+\\b)", ","));
        }
//        matcher.appendTail(sb);
//        while (matcher.find()) {
//            System.out.println("matcher = " + matcher.group());
//            content = content.replace(matcher.group(), matcher.group().replaceAll("(?<=^\\d+)(?=(\\d{3})+\\b)", ","));
//        }
        return matcher.appendTail(sb).toString();
    }
}
