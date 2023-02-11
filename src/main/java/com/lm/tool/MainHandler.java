package com.lm.tool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Consumer;

public class MainHandler {
    public static void main(String[] args) {
        readSystemInInLoop(e -> {
            try {
                ArrayList<ReceiveInfo> receiveInfos = new Reader().readFile(e);
                FileUtil.writeToExcel(receiveInfos, e + ".xls");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    public static void readSystemInInLoop(Consumer<String> action) {
        Scanner in = new Scanner(System.in);
        System.out.println("请输入源文件全路径（可将文件拖入），然后按回车键，输入q退出");
        while (true) {
            String line = in.nextLine();
            if (line.equalsIgnoreCase("q")) {
                in.close();
                return;
            }
            action.accept(line);
        }
    }
}
