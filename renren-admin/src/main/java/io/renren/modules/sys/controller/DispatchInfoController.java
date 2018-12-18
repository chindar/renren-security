package io.renren.modules.sys.controller;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.*;

import com.alibaba.fastjson.JSONObject;
import io.renren.common.utils.*;
import io.renren.common.validator.ValidatorUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.sys.entity.DispatchInfoEntity;
import io.renren.modules.sys.service.DispatchInfoService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 配送信息表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-12-15 12:06:08
 */
@RestController
@RequestMapping("sys/dispatchinfo")
public class DispatchInfoController {
    @Autowired
    private DispatchInfoService dispatchInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:dispatchinfo:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = dispatchInfoService.queryPage(params);

        return R.ok().put("page", page);
    }

    @RequestMapping("/list2")
    @RequiresPermissions("sys:dispatchinfo:list")
    public R list2(@RequestParam Map<String, Object> params){
        PageUtils page = dispatchInfoService.getDispatchList(params);

        return R.ok().put("page", page);
    }

    /**
     * 导入数据模板
     * @param multipartRequest
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/importdata")
    public R importdata(MultipartHttpServletRequest multipartRequest,
                HttpServletResponse response) throws Exception {
        Map result = new HashMap();
        try{
            // 封装上传文件的Map
            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
            MultipartFile file=null;
            for(Map.Entry<String,MultipartFile> entry:fileMap.entrySet()){
                file=entry.getValue();
//                String fileId=String.valueOf(System.currentTimeMillis());
//                MongoUtils mongo=new MongoUtils();
//                // 保存文件到mongodb,以指标id作为文件的唯一标识
//                mongo.saveFile(file.getInputStream(),fileId,file.getOriginalFilename(),"pact");
//                result.put("fileId",fileId);
//                result.put("fileName",file.getOriginalFilename());
                XSSFWorkbook book = new XSSFWorkbook(file.getInputStream());
                //定一个list？
                List<JSONObject> list = new ArrayList<>();
                //获取一个sheet也就是一个工作簿
                Sheet sheet = book.getSheetAt(0);
                //获取总行数
                int lastRowNum = sheet.getLastRowNum();
                //获取最后一列总数
        //        int lastRow = rows.getPhysicalNumberOfCells();
                for (int j = 1; j <= lastRowNum; j++) {
                    Row rows = sheet.getRow(j);
                    rows.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                    rows.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                    rows.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                    rows.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                    rows.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                    rows.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
                    rows.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
                    rows.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
                    rows.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
                    rows.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
                    rows.getCell(10).setCellType(Cell.CELL_TYPE_STRING);
                    rows.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
                    rows.getCell(12).setCellType(Cell.CELL_TYPE_STRING);
                    rows.getCell(13).setCellType(Cell.CELL_TYPE_STRING);
                    rows.getCell(14).setCellType(Cell.CELL_TYPE_STRING);
                    rows.getCell(15).setCellType(Cell.CELL_TYPE_STRING);
                    DispatchInfoEntity entity = new DispatchInfoEntity();
                    entity.setMonth(rows.getCell(0).getStringCellValue().trim());
                    entity.setArea(rows.getCell(1).getStringCellValue().trim());
                    entity.setCityName(rows.getCell(2).getStringCellValue().trim());
                    entity.setSite(rows.getCell(3).getStringCellValue().trim());
                    entity.setErpId(rows.getCell(4).getStringCellValue().trim());
                    entity.setCourierName(rows.getCell(5).getStringCellValue().trim());
                    entity.setRemark(rows.getCell(6).getStringCellValue().trim());
                    entity.setAllOrderTotal(Integer.parseInt(rows.getCell(7).getStringCellValue().trim()));
                    entity.setCountOrderTotal(Integer.parseInt(rows.getCell(8).getStringCellValue().trim()));
                    entity.setSmall(Integer.parseInt(rows.getCell(9).getStringCellValue().trim()));
                    entity.setLarge(Integer.parseInt(rows.getCell(10).getStringCellValue().trim()));
                    entity.setThrIdentical(rows.getCell(11).getStringCellValue().trim());
                    entity.setAfterSale(rows.getCell(12).getStringCellValue().trim());
                    entity.setSellerPick(rows.getCell(13).getStringCellValue().trim());
                    entity.setDeductMoney(new BigDecimal(rows.getCell(14).getStringCellValue().trim()));
                    entity.setSalary(new BigDecimal(rows.getCell(15).getStringCellValue().trim()));
                    dispatchInfoService.importData(entity);

                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return R.error(e.getMessage());
        }
        return R.ok().put("data",result);
    }

    /**
     * 导出模板
     * @param request
     * @param response
     * @param
     * @throws Exception
     */
    @RequestMapping("/exportdata")
    public void exportdata(HttpServletRequest request, HttpServletResponse response,
                           @RequestParam(value="id",defaultValue = "") String id
			) throws Exception {
        String[] arr = id.split(",");
        Integer[] ids = new Integer[arr.length];
        for(int i=0;i<arr.length;i++) {
            ids[i]=Integer.parseInt(arr[i]);
        }
        List<DispatchInfoEntity> dispatchlist = dispatchInfoService.getExportData(ids);
        // 第一步，创建一个webbook，对应一个Excel文件
        XSSFWorkbook wb = new XSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        XSSFSheet sheet = wb.createSheet("配送员配送数据");

        // 设置所有单元格大小 -- 宽度
        sheet.setDefaultColumnWidth(14);
        // 设置所有单元格大小 -- 高度
        sheet.setDefaultRowHeightInPoints(14);
        //        sheet.createFreezePane(0, 2, 0, 2);
        /**
         * 样式设置
         * 1.所有字体为Arial格式
         * 2.所有表头部分字体大小为14号，其他部分为11号
         */
        // 1.表头样式,蓝色14号字体,加粗,
        XSSFCellStyle styleTitle = wb.createCellStyle();
        // 居中
        styleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleTitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        XSSFFont font = wb.createFont();
        // 字体颜色
        //        font.setColor(HSSFColor.SKY_BLUE.index);//HSSFColor.VIOLET.index //字体颜色
        // 字体大小
        font.setFontHeightInPoints((short)14);
        //字体格式
        font.setFontName("Arial");
        // 字体增粗
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        styleTitle.setFont(font);
        /**
         * 表格内容样式设置
         * 1.表头样式
         * 2.表格内容样式
         */
        // 1.表头样式
        // 设置居中样式
        XSSFCellStyle styleContentTitle = wb.createCellStyle();
        styleContentTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleContentTitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 字体大小
        XSSFFont fontContentTitle = wb.createFont();
        fontContentTitle.setFontHeightInPoints((short) 11);
        // 字体增粗
        fontContentTitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        styleContentTitle.setFont(fontContentTitle);

        // 2.表格内容样式
        // 设置居中样式
        XSSFCellStyle styleContent = wb.createCellStyle();
        styleContent.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleContent.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 设置自动换行
        styleContent.setWrapText(true);
        // 字体大小
        XSSFFont fontContent = wb.createFont();
        fontContent.setFontHeightInPoints((short) 11);
        styleContent.setFont(fontContent);

        /**
         * 数据表内容
         */
        // 添加表头标题
        XSSFRow row = sheet.createRow(0);
        //设置行高
        row.setHeight((short)600);
        // 设置表头名称
        XSSFCell cell = row.createCell(0);
        cell.setCellValue("月份");
        cell.setCellStyle(styleContentTitle);

        cell = row.createCell(1);
        cell.setCellValue("片区");
        cell.setCellStyle(styleContentTitle);

        cell = row.createCell(2);
        cell.setCellValue("城市");
        cell.setCellStyle(styleContentTitle);

        cell = row.createCell(3);
        cell.setCellValue("站点");
        cell.setCellStyle(styleContentTitle);

        cell = row.createCell(4);
        cell.setCellValue("erp账号");
        cell.setCellStyle(styleContentTitle);

        cell = row.createCell(5);
        cell.setCellValue("配送员姓名");
        cell.setCellStyle(styleContentTitle);

        cell = row.createCell(6);
        cell.setCellValue("备注");
        cell.setCellStyle(styleContentTitle);

        cell = row.createCell(7);
        cell.setCellValue("总单量");
        cell.setCellStyle(styleContentTitle);

        cell = row.createCell(8);
        cell.setCellValue("合计单量");
        cell.setCellStyle(styleContentTitle);

        cell = row.createCell(9);
        cell.setCellValue("小件");
        cell.setCellStyle(styleContentTitle);

        cell = row.createCell(10);
        cell.setCellValue("大件");
        cell.setCellStyle(styleContentTitle);

        cell = row.createCell(11);
        cell.setCellValue("三同");
        cell.setCellStyle(styleContentTitle);

        cell = row.createCell(12);
        cell.setCellValue("售后取件");
        cell.setCellStyle(styleContentTitle);

        cell = row.createCell(13);
        cell.setCellValue("商家接货");
        cell.setCellStyle(styleContentTitle);

        cell = row.createCell(14);
        cell.setCellValue("扣款");
        cell.setCellStyle(styleContentTitle);

        cell = row.createCell(15);
        cell.setCellValue("工资");
        cell.setCellStyle(styleContentTitle);


        // 遍历运营数据list
        for (int i = 0; i < dispatchlist.size(); i++){
            DispatchInfoEntity vo=dispatchlist.get(i);
            // 确定内容开始行
            row = sheet.createRow(row.getRowNum()+1);
            //设置行高
            row.setHeight((short) 600);
            //月份
            cell = row.createCell(0);
            cell.setCellValue(vo.getMonth());
            cell.setCellStyle(styleContent);
            //片区
            cell = row.createCell(1);
            cell.setCellValue(vo.getArea());
            cell.setCellStyle(styleContent);
            //城市
            cell = row.createCell(2);
            cell.setCellValue(vo.getCityName());
            cell.setCellStyle(styleContent);
            //站点
            cell = row.createCell(3);
            cell.setCellValue(vo.getSite());
            cell.setCellStyle(styleContent);
            //erp账号
            cell = row.createCell(4);
            cell.setCellValue(vo.getErpId());
            cell.setCellStyle(styleContent);
            //配送员姓名
            cell = row.createCell(5);
            cell.setCellValue(vo.getCourierName());
            cell.setCellStyle(styleContent);
            //备注
            cell = row.createCell(6);
            cell.setCellValue(vo.getRemark());
            cell.setCellStyle(styleContent);
            //总单量
            cell = row.createCell(7);
            cell.setCellValue(vo.getAllOrderTotal());
            cell.setCellStyle(styleContent);
            //合计单量
            cell = row.createCell(8);
            cell.setCellValue(vo.getCountOrderTotal());
            cell.setCellStyle(styleContent);
            //小件
            cell = row.createCell(9);
            cell.setCellValue(vo.getSmall());
            cell.setCellStyle(styleContent);
            //大件
            cell = row.createCell(10);
            cell.setCellValue(vo.getLarge());
            cell.setCellStyle(styleContent);
            //三同
            cell = row.createCell(11);
            cell.setCellValue(vo.getThrIdentical());
            cell.setCellStyle(styleContent);
            //售后取件
            cell = row.createCell(12);
            cell.setCellValue(vo.getAfterSale());
            cell.setCellStyle(styleContent);
            //商家接货
            cell = row.createCell(13);
            cell.setCellValue(vo.getSellerPick());
            cell.setCellStyle(styleContent);
            //扣款
            cell = row.createCell(14);
            cell.setCellValue(vo.getDeductMoney().toString());
            cell.setCellStyle(styleContent);
            //工资
            cell = row.createCell(15);
            cell.setCellValue(vo.getSalary().toString());
            cell.setCellStyle(styleContent);

        }
    // 下载文件
        try {
            String filename = "快递员配送数据表.xlsx";
            response.setHeader("Content-Disposition", "attachment;filename="+new String(filename.getBytes("gb2312"), "iso8859-1"));
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
            response.setContentType("application/vnd.ms-excel;charset=UTF8");
            OutputStream os= new BufferedOutputStream(response.getOutputStream());
            wb.write(os);
            os.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:dispatchinfo:info")
    public R info(@PathVariable("id") Integer id){
        DispatchInfoEntity dispatchInfo = dispatchInfoService.selectById(id);

        return R.ok().put("dispatchInfo", dispatchInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:dispatchinfo:save")
    public R save(@RequestBody DispatchInfoEntity dispatchInfo){
        dispatchInfoService.insert(dispatchInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:dispatchinfo:update")
    public R update(@RequestBody DispatchInfoEntity dispatchInfo){
        ValidatorUtils.validateEntity(dispatchInfo);
        dispatchInfoService.updateAllColumnById(dispatchInfo);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:dispatchinfo:delete")
    public R delete(@RequestBody Integer[] ids){
        dispatchInfoService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
