package com.lm.tool;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;

import java.awt.*;
import java.io.*;
import java.util.List;

public class FileUtil {
    /** 读取文件全部内容 */
    public static byte[] readFileBytes(String filePath) throws FileNotFoundException, IOException {
        File file = new File(filePath);
        byte[] bytes = new byte[(int) file.length()];
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            fis.read(bytes);
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
        return bytes;
    }

    public static void writeToExcel(List<ReceiveInfo> list, String excelFile) throws IOException {
        FileOutputStream fos = new FileOutputStream(excelFile);
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("sheet1");
        int rownum = 0;
        int colnum = 0;
        {
            HSSFRow row = sheet.createRow(rownum++);
            row.createCell(colnum++).setCellValue("uid");
            row.createCell(colnum++).setCellValue("用户名");
            row.createCell(colnum++).setCellValue("收件人");
            row.createCell(colnum++).setCellValue("电话");
            row.createCell(colnum++).setCellValue("地址");
        }
        CellStyle cellStyleDate = wb.createCellStyle();
        cellStyleDate.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
        for (ReceiveInfo e : list) {
            colnum = 0;
            HSSFRow row = sheet.createRow(rownum++);
            row.createCell(colnum++).setCellValue(e.uid);
            row.getCell(colnum - 1).setCellStyle(cellStyleDate);
            row.createCell(colnum++).setCellValue(e.uname);
            row.createCell(colnum++).setCellValue(e.receiver);
            row.createCell(colnum++).setCellValue(e.phone);
            row.createCell(colnum++).setCellValue(e.address);
        }
        sheet.createFreezePane(0, 1);
        wb.write(fos);
        wb.close();
        fos.close();
        System.out.println("导出成功：" + excelFile);
        Desktop.getDesktop().open(new File(excelFile));
    }
}
