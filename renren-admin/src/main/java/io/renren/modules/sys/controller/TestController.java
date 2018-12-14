package io.renren.modules.sys.controller;

import com.alibaba.fastjson.JSONObject;
import io.renren.common.utils.MongoUtils;
import io.renren.common.utils.POIUtils;
import io.renren.common.utils.R;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sys/test")
public class TestController {

    @RequestMapping(value = "info")
    public R test(){
        System.out.println("锄禾日当午，汗滴禾下土。");
        return R.ok("操作成功！");
    }


//    public static void main(String[] args) throws IOException {
//        FileInputStream fileIn = new FileInputStream("E:/StudentTemplate.xls");
//        Workbook book = new HSSFWorkbook(fileIn);
//
////        MultipartFile file= null;
////        Workbook workbook = POIUtils.getWorkBook(file);
//
//        //定一个list？
//        List<JSONObject> list = new ArrayList<>();
//
//        //获取一个sheet也就是一个工作簿
//        Sheet sheet = book.getSheetAt(0);
//
//        //获取总行数
//        int lastRowNum = sheet.getLastRowNum();
//
//        //获取最后一列总数
////        int lastRow = rows.getPhysicalNumberOfCells();
//
//
//        for (int j = 0; j <= lastRowNum; j++) {
//            Row rows = sheet.getRow(j);
//            rows.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
////            cellVal = cell.getStringCellValue();
//            rows.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
//            rows.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
//            System.out.println(rows.getCell(0).getStringCellValue());
//            System.out.println(rows.getCell(1).getStringCellValue());
//            System.out.println(rows.getCell(2).getStringCellValue());
//        }
//    }
    public static void main(String[] args) throws IOException {

        File file = new File("E:/StudentTemplate.xls");
        String fileId="123456";
        MongoUtils mongo=new MongoUtils();
        // 保存文件到mongodb,以指标id作为文件的唯一标识
        mongo.saveFile(new FileInputStream(file),fileId,file.getName().substring(file.getName().indexOf("."), file.getName().length()),"test");

    }
}
