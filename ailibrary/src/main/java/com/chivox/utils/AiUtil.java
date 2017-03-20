package com.chivox.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.chivox.ChivoxConstants;

import org.apache.http.util.EncodingUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class AiUtil {
    private static String tag = "AiUtil";
    private static int BUFFER_SIZE = 8192;

    public AiUtil() {
    }

    public static String getRecordPath(Context context) {
        String path = context.getCacheDir().getAbsolutePath() + File.separator + ChivoxConstants.CHIVOX;
        File f = new File(path);
        if (!f.exists() || !f.isDirectory()) {
            f.mkdirs();
        }
        return path;
    }

    public static String sha1(String message) {
        try {
            MessageDigest e = MessageDigest.getInstance("SHA-1");
            e.update(message.getBytes(), 0, message.length());
            return bytes2hex(e.digest());
        } catch (Exception var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static String md5(Context c, String fileName) {
        byte[] buf = new byte[BUFFER_SIZE];

        try {
            InputStream e = c.getAssets().open(fileName);
            MessageDigest md = MessageDigest.getInstance("MD5");

            int bytes;
            while ((bytes = e.read(buf, 0, BUFFER_SIZE)) > 0) {
                md.update(buf, 0, bytes);
            }

            e.close();
            return bytes2hex(md.digest());
        } catch (Exception var6) {
            var6.printStackTrace();
            return null;
        }
    }

    public static long getWordCount(String sent) {
        return (long) sent.trim().split("\\W+").length;
    }

    public static long getHanziCount(String pin1yin1) {
        return (long) pin1yin1.trim().split("-").length;
    }

    public static File externalFilesDir(Context c) {
        File f = c.getExternalFilesDir((String) null);
        if (f == null || !f.exists()) {
            f = c.getFilesDir();
        }

        return f;
    }

    public static String readFile(File file) {
        String res = null;

        try {
            FileInputStream e = new FileInputStream(file);
            int length = e.available();
            byte[] buffer = new byte[length];
            e.read(buffer);
            res = EncodingUtils.getString(buffer, "UTF-8");
            e.close();
        } catch (FileNotFoundException var5) {
            var5.printStackTrace();
        } catch (IOException var6) {
            var6.printStackTrace();
        }

        return res;
    }

    public static String readFileFromAssets(Context c, String fileName) {
        String res = null;

        try {
            InputStream e = c.getAssets().open(fileName);
            int length = e.available();
            byte[] buffer = new byte[length];
            e.read(buffer);
            res = EncodingUtils.getString(buffer, "UTF-8");
            e.close();
        } catch (IOException var6) {
            var6.printStackTrace();
        }

        return res;
    }

    public static void writeToFile(String filePath, String string) {
        try {
            FileOutputStream e = new FileOutputStream(filePath);
            byte[] bytes = string.getBytes();
            e.write(bytes);
            e.close();
        } catch (FileNotFoundException var4) {
            var4.printStackTrace();
        } catch (IOException var5) {
            var5.printStackTrace();
        }

    }

    public static void writeToFile(File f, String string) {
        try {
            FileWriter e = new FileWriter(f);
            e.write(string);
            e.close();
        } catch (IOException var3) {
            var3.printStackTrace();
        }

    }

    public static void writeToFile(File f, InputStream is) {
        byte[] buf = new byte[BUFFER_SIZE];

        try {
            FileOutputStream e = new FileOutputStream(f);

            int bytes;
            while ((bytes = is.read(buf, 0, BUFFER_SIZE)) > 0) {
                e.write(buf, 0, bytes);
            }

            e.close();
        } catch (FileNotFoundException var5) {
            var5.printStackTrace();
        } catch (IOException var6) {
            var6.printStackTrace();
        }

    }

    public static File unzipFile(Context c, String fileName) {
        try {
            String e = fileName.replaceAll("\\.[^.]*$", "");
            File filesDir = externalFilesDir(c);
            File targetDir = new File(filesDir, e);
            String md5sum = md5(c, fileName);
            File md5sumFile = new File(targetDir, ".md5sum");
            if (targetDir.isDirectory()) {
                if (md5sumFile.isFile()) {
                    String md5sum2 = readFile(md5sumFile);
                    if (md5sum2.equals(md5sum)) {
                        return targetDir;
                    }
                }

                removeDirectory(targetDir);
            }

            unzip(c, fileName, targetDir);
            writeToFile(md5sumFile, md5sum);
            return targetDir;
        } catch (Exception var8) {
            var8.printStackTrace();
            Log.e(tag, "Failed to extract resource", var8);
            return null;
        }
    }

    private static String bytes2hex(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length * 2);

        for (int i = 0; i < bytes.length; ++i) {
            int v = bytes[i] & 255;
            if (v < 16) {
                sb.append('0');
            }

            sb.append(Integer.toHexString(v));
        }

        return sb.toString();
    }

    private static void removeDirectory(File directory) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();

            for (int i = 0; i < files.length; ++i) {
                if (files[i].isDirectory()) {
                    removeDirectory(files[i]);
                }

                files[i].delete();
            }

            directory.delete();
        }

    }

    private static void unzip(Context c, String fileName, File targetDir) throws IOException {
        InputStream is = c.getAssets().open(fileName);
        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(is, BUFFER_SIZE));

        while (true) {
            ZipEntry ze;
            while ((ze = zis.getNextEntry()) != null) {
                if (ze.isDirectory()) {
                    (new File(targetDir, ze.getName())).mkdirs();
                } else {
                    File file = new File(targetDir, ze.getName());
                    File parentdir = file.getParentFile();
                    if (parentdir != null && !parentdir.exists()) {
                        parentdir.mkdirs();
                    }

                    byte[] buf = new byte[BUFFER_SIZE];
                    FileOutputStream bos = new FileOutputStream(file);

                    int pos;
                    while ((pos = zis.read(buf, 0, BUFFER_SIZE)) > 0) {
                        bos.write(buf, 0, pos);
                    }

                    bos.flush();
                    bos.close();
                }
            }

            zis.close();
            is.close();
            return;
        }
    }


    public static String judgeEvaluateType(String content) {
        String type = "";
        //1代表试用小孩的  0代表试用以前的
        String chivoxType = "0";
//        if(GlobalParams.loginInfo != null){
//            chivoxType = GlobalParams.loginInfo.getData().getChivox();
//        }
        if ("1".equals(chivoxType)) {
            if (!TextUtils.isEmpty(content)) {
                long count = 0;
                String language = distinguish(content);
                if (ChivoxConstants.CHINESE.equals(language)) {
                    count = AiUtil.getHanziCount(content);
                    if (count == 1) {
                        type = ChivoxConstants.CN_WORD;
                    } else if (count > 1) {
                        type = ChivoxConstants.CN_SENTENCE;
                    }
                } else if (ChivoxConstants.ENGLISH.equals(language)) {
                    count = AiUtil.getWordCount(content);
                    if (count == 1) {
                        type = ChivoxConstants.EN_WORD_CHILD;
                    } else if (count > 1) {
                        type = ChivoxConstants.EN_SENTENCE_CHILD;
                    }
                }
            }
        } else {
            if (!TextUtils.isEmpty(content)) {
                long count = 0;
                String language = distinguish(content);
                if (ChivoxConstants.CHINESE.equals(language)) {
                    count = AiUtil.getHanziCount(content);
                    if (count == 1) {
                        type = ChivoxConstants.CN_WORD;
                    } else if (count > 1) {
                        type = ChivoxConstants.CN_SENTENCE;
                    }
                } else if (ChivoxConstants.ENGLISH.equals(language)) {
                    count = AiUtil.getWordCount(content);
                    if (count == 1) {
                        type = ChivoxConstants.EN_WORD;
                    } else if (count > 1) {
                        type = ChivoxConstants.EN_SENTENCE;
                    }
                }
            }
        }
        return type;
    }

    public static String distinguish(String src) {
        String result = "";

        if (src == null || src.isEmpty())
            return result;

        Pattern p;
        Matcher m;

        p = Pattern.compile("[\u4e00-\u9fa5]");
        m = p.matcher(src);
        if (m.find()) {
            return ChivoxConstants.CHINESE;
        }

        p = Pattern.compile("\\p{Alpha}");// 等价于"[a-zA-Z]"
        m = p.matcher(src);
        if (m.find()) {
            return ChivoxConstants.ENGLISH;
        }

        p = Pattern.compile("[0-9]");
        m = p.matcher(src);
        if (m.find()) {
            return ChivoxConstants.ENGLISH;
        }

        p = Pattern.compile("\u0250-\u02AF");// 国际音标
        m = p.matcher(src);
        if (m.find()) {
            return ChivoxConstants.ENGLISH;
        }

        // p = Pattern.compile("\\p{Punct}");//
        // 不包括标点符号，!"#$%&'()*+,-./:;<=>?@[\]^_`{|}~之一。
        // m = p.matcher(src);

        return result;
    }

    public static String replaceStr(String str) {
        if ("one(large) bowl of...".equals(str)) {
            str = "one (large) bowl of...";
        }
        return str;
    }

    public static String formatReftext(String string) {
        /*String retText = orgText
            *//*全角转半角     ，。‘’“”！？*//*
                .replace("，", ", ").replace("。", ". ").replace("‘", "' ").replace("’", "'")
                .replace("“", "\" ").replace("”", "\" ").replace("！", "! ").replace("？", "? ")
                .replace("：", ": ").replace("；", "; ")
			*//*特殊字符去除*//*
                .replace("@", " ").replace("#", " ").replace("$", " ").replace("%", " ")
                .replace("~", " ").replace("`", " ").replace("^", " ").replace("&", " ")
                .replace("*", " ").replace("(", " ").replace(")", " ").replace("_", " ")
                .replace("-", " ").replace("+", " ").replace("=", " ").replace("{", " ")
                .replace("}", " ").replace("[", " ").replace("]", " ").replace("<", " ")
                .replace(">", " ").replace("【", " ").replace("】", " ").replace("、", " ")
                .replace("|", " ").replace("/", " ").replace("￥", " ").replace("（", " ")
                .replace("）", " ").replace("《", " ").replace("》", " ").replace("——", " ")
                .replace("……", " ")
			*//*字母格式转换*//*
                .replace("Ａ", "A").replace("Ｂ", "B").replace("Ｃ", "C").replace("Ｄ", "D")
                .replace("Ｅ", "E").replace("Ｆ", "F").replace("Ｇ", "G").replace("Ｈ", "H")
                .replace("Ｉ", "I").replace("Ｊ", "J").replace("Ｋ", "K").replace("Ｌ", "L")
                .replace("Ｍ", "M").replace("Ｎ", "N").replace("Ｏ", "O").replace("Ｐ", "p")
                .replace("Ｑ", "Q").replace("Ｒ", "R").replace("Ｓ", "S").replace("Ｔ", "T")
                .replace("Ｕ", "U").replace("Ｖ", "V").replace("Ｗ", "W").replace("Ｘ", "X")
                .replace("Ｙ", "Y").replace("Ｚ", "Z")
                .replace("ａ", "a").replace("ｂ", "b").replace("ｃ", "c").replace("ｄ", "d")
                .replace("ｅ", "e").replace("ｆ", "f").replace("ｇ", "g").replace("ｈ", "h")
                .replace("ｉ", "i").replace("ｊ", "j").replace("ｋ", "k").replace("ｌ", "l")
                .replace("ｍ", "m").replace("ｎ", "n").replace("ｏ", "o").replace("ｐ", "p")
                .replace("ｑ", "q").replace("ｒ", "r").replace("ｓ", "s").replace("ｔ", "t")
                .replace("ｕ", "u").replace("ｖ", "v").replace("ｗ", "w").replace("ｘ", "x")
                .replace("ｙ", "y").replace("ｚ", "z");
        return retText;*/
        if(null == string || "".equals(string))
        {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        char[] charArr = string.toCharArray();
        for (int i = 0; i < charArr.length; i++)
        {
            if(charArr[i] >= 65281 && charArr[i] <= 65374)
            {
                charArr[i] = (char) (charArr[i] - 65248);
            }
            else if(charArr[i] == 12288)
            {
                charArr[i] = (char)32;
            }
            else if(charArr[i] == 12290)
            {
                charArr[i] = (char)46;
            }
            else if(charArr[i] == 8216 || charArr[i] == 8217)
            {
                charArr[i] = (char)39;
            }
            else if(charArr[i] == 96)
            {
                charArr[i] = (char)39;
            }
            else if(charArr[i] == 8218)
            {
                charArr[i] = (char)44;
            }
            builder.append(charArr[i]);
        }
        string = new String(charArr);
        Pattern p = Pattern.compile("[-]{2,3}");
        Matcher m = p.matcher(string);
        string = m.replaceAll("—");
        return string;
    }

    public static String addBlank(String str) {
        if (str.contains("(") && str.contains(")")) {
            StringBuilder sBuilder = new StringBuilder(str);
            sBuilder.insert(str.indexOf("("), " ");
            return sBuilder.toString();
        } else
            return str;
    }

}
