package cn.madeai.blog;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tool {
    public static List<String> getImagePath(String htmlText) {
        List<String> imagePaths = new ArrayList<String>();
        Pattern p = Pattern.compile("<img\\b[^>]*\\bsrc\\b\\s*=\\s*('|\")?([^'\"\n\r\f>]+(\\.jpg|\\.bmp|\\.gif|\\.png|\\.jpe|\\.jpeg|\\.pic)\\b)[^>]*>", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(htmlText);
        String quote = null;
        String src = null;
        while (m.find()) {
            quote = m.group(1);
            src = (quote == null || quote.trim().length() == 0) ? m.group(2).split("\\s+")[0] : m.group(2);
            imagePaths.add(src);
        }
        return imagePaths;
    }

    public static List<String> getMarkdownImagePath(String markdownText){
        List<String> imagePaths = new ArrayList<String>();
        Matcher matcher = Pattern.compile("(?:!\\[(.*?)\\]\\((.*?)\\))").matcher(markdownText);
        while (matcher.find()) {
            String str=matcher.group(2).split(" ")[0];
            if ("/assets/".equals(str.substring(0,8))){
                str="http://blog.madeai.cn"+str;
            }
            imagePaths.add(str);
        }
        return imagePaths;
    }

    public static String getRandomImage(){
        return Config.IMAGES_URL+(int)(Math.random()*18+1)+".png";
    }
}
