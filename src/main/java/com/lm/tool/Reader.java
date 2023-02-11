package com.lm.tool;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Reader {
    char[] buf = new char[1000];
    String fileContent;
    int from;

    public ArrayList<ReceiveInfo> readFile(String filePath) throws IOException {
        fileContent = new String(FileUtil.readFileBytes(filePath), StandardCharsets.UTF_8);
//        System.out.println(fileContent);
        ArrayList<ReceiveInfo> list = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            readField();
        }
        try {
            while (from < fileContent.length()) {
                ReceiveInfo e = new ReceiveInfo();
                e.uid = Long.parseLong(readField());
                e.uname = readField();
                e.receiver = readField();
                e.phone = readField();
                e.address = readField();
                readField();
                readField();
                list.add(e);
                System.out.printf("%d \t%s \t%s \t%s \t%s\n", e.uid, e.uname, e.receiver, e.phone, e.address);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private String readField() {
        int i = 0;
        for (; i < fileContent.length() && i < buf.length; i++) {
            buf[i] = fileContent.charAt(from + i);
            if (buf[i] == ',' || buf[i] == '\n' || buf[i] == '\r') {
                if (buf[0] == '"' && buf[i] != ',')//碰到地址换行了
                    continue;
                from += i;
                from++;
                return new String(buf, 0, i);
            }
        }
        from++;
        from += i;
        return i > 0 ? new String(buf, 0, i) : "";
    }
}
