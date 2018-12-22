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
//                    rows.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
//                    rows.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
                    rows.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
//                    rows.getCell(10).setCellType(Cell.CELL_TYPE_STRING);
//                    rows.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
                    rows.getCell(12).setCellType(Cell.CELL_TYPE_STRING);
                    rows.getCell(13).setCellType(Cell.CELL_TYPE_STRING);
//                    rows.getCell(14).setCellType(Cell.CELL_TYPE_STRING);
//                    rows.getCell(15).setCellType(Cell.CELL_TYPE_STRING);
//                    rows.getCell(16).setCellType(Cell.CELL_TYPE_STRING);
//                    rows.getCell(17).setCellType(Cell.CELL_TYPE_STRING);
//                    rows.getCell(18).setCellType(Cell.CELL_TYPE_STRING);
                    rows.getCell(19).setCellType(Cell.CELL_TYPE_STRING);
                    rows.getCell(20).setCellType(Cell.CELL_TYPE_STRING);
                    rows.getCell(21).setCellType(Cell.CELL_TYPE_STRING);
                    DispatchInfoEntity entity = new DispatchInfoEntity();
                    entity.setMonth(rows.getCell(0).getStringCellValue().trim());
                    entity.setArea(rows.getCell(1).getStringCellValue().trim());
                    entity.setCityName(rows.getCell(2).getStringCellValue().trim());
                    entity.setSite(rows.getCell(3).getStringCellValue().trim());
                    entity.setErpId(rows.getCell(4).getStringCellValue().trim());
                    entity.setCourierName(rows.getCell(5).getStringCellValue().trim());
                    entity.setRemark(rows.getCell(6).getStringCellValue().trim());
                    entity.setAllOrderTotal(Integer.valueOf((int)rows.getCell(7).getNumericCellValue()));
                    entity.setCountOrderTotal(Integer.valueOf((int)rows.getCell(8).getNumericCellValue()));
                    entity.setTotalMoney(new BigDecimal(rows.getCell(9).getStringCellValue().trim()));
                    entity.setSmall(Integer.valueOf((int)rows.getCell(10).getNumericCellValue()));
                    entity.setLarge(Integer.valueOf((int)rows.getCell(11).getNumericCellValue()));
                    entity.setThrIdentical(rows.getCell(12).getStringCellValue().trim());
                    entity.setAfterSale(rows.getCell(13).getStringCellValue().trim());
                    entity.setFirstCount(Integer.valueOf((int)rows.getCell(14).getNumericCellValue()));
                    entity.setAgainCount(Integer.valueOf((int)rows.getCell(15).getNumericCellValue()));
                    entity.setOtherCount(Integer.valueOf((int)rows.getCell(16).getNumericCellValue()));
                    entity.setBadCount(Integer.valueOf((int)rows.getCell(17).getNumericCellValue()));
                    entity.setComplaintCount(Integer.valueOf((int)rows.getCell(18).getNumericCellValue()));
                    entity.setFineMoney(new BigDecimal(rows.getCell(19).getStringCellValue().trim()));
                    entity.setDeductMoney(new BigDecimal(rows.getCell(20).getStringCellValue().trim()));
                    entity.setSalary(new BigDecimal(rows.getCell(21).getStringCellValue().trim()));
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
         * 数据表内容
         */
        // 添加表头标题
        XSSFRow row = sheet.createRow(0);
        //设置行高
        row.setHeight((short)600);
        // 设置表头名称
        XSSFCell cell = row.createCell(0);
        cell.setCellValue("月份");

        cell = row.createCell(1);
        cell.setCellValue("片区");

        cell = row.createCell(2);
        cell.setCellValue("城市");

        cell = row.createCell(3);
        cell.setCellValue("站点");

        cell = row.createCell(4);
        cell.setCellValue("erp账号");

        cell = row.createCell(5);
        cell.setCellValue("配送员姓名");

        cell = row.createCell(6);
        cell.setCellValue("备注");

        cell = row.createCell(7);
        cell.setCellValue("总单量");

        cell = row.createCell(8);
        cell.setCellValue("合并后单量");

        cell = row.createCell(9);
        cell.setCellValue("费用合计");

        cell = row.createCell(10);
        cell.setCellValue("小件");

        cell = row.createCell(11);
        cell.setCellValue("大件");

        cell = row.createCell(12);
        cell.setCellValue("三同");

        cell = row.createCell(13);
        cell.setCellValue("售后取件");

        cell = row.createCell(14);
        cell.setCellValue("接货首单量");

        cell = row.createCell(15);
        cell.setCellValue("接货续单量");

        cell = row.createCell(16);
        cell.setCellValue("其他单量");

        cell = row.createCell(17);
        cell.setCellValue("差评");

        cell = row.createCell(18);
        cell.setCellValue("投诉");

        cell = row.createCell(19);
        cell.setCellValue("罚款合计");

        cell = row.createCell(20);
        cell.setCellValue("其他扣款");

        cell = row.createCell(21);
        cell.setCellValue("实发工资");


        // 遍历运营数据list
        for (int i = 0; i < dispatchlist.size(); i++){
            DispatchInfoEntity vo=dispatchlist.get(i);
            // 确定内容开始行
            row = sheet.createRow(row.getRowNum()+1);
            //设置行高
            row.setHeight((short) 600);
            //月份
            cell = row.createCell(0);
            if(vo.getMonth() != null)
                cell.setCellValue(vo.getMonth());
            //片区
            cell = row.createCell(1);
            if(vo.getArea() != null)
                cell.setCellValue(vo.getArea());
            //城市
            cell = row.createCell(2);
            if(vo.getCityName() != null)
                cell.setCellValue(vo.getCityName());
            //站点
            cell = row.createCell(3);
            if(vo.getSite() != null)
                cell.setCellValue(vo.getSite());
            //erp账号
            cell = row.createCell(4);
            if(vo.getErpId() != null)
                cell.setCellValue(vo.getErpId());
            //配送员姓名
            cell = row.createCell(5);
            if(vo.getCourierName() != null)
                cell.setCellValue(vo.getCourierName());
            //备注
            cell = row.createCell(6);
            if(vo.getRemark() != null)
                cell.setCellValue(vo.getRemark());
            //总单量
            cell = row.createCell(7);
            if(vo.getAllOrderTotal() != null)
                cell.setCellValue(vo.getAllOrderTotal());
            //合计单量
            cell = row.createCell(8);
            if(vo.getCountOrderTotal() != null)
                cell.setCellValue(vo.getCountOrderTotal());
            //费用合计
            cell = row.createCell(9);
            if(vo.getTotalMoney() != null)
                cell.setCellValue(vo.getTotalMoney().toString());
            //小件
            cell = row.createCell(10);
            if(vo.getSmall() != null)
                cell.setCellValue(vo.getSmall());
            //大件
            cell = row.createCell(11);
            if(vo.getLarge() != null)
                cell.setCellValue(vo.getLarge());
            //三同
            cell = row.createCell(12);
            if(vo.getThrIdentical() != null)
                cell.setCellValue(vo.getThrIdentical());
            //售后取件
            cell = row.createCell(13);
            if(vo.getAfterSale() != null)
                cell.setCellValue(vo.getAfterSale());
            //接货首单量
            cell = row.createCell(14);
            if(vo.getFirstCount() != null)
                cell.setCellValue(vo.getFirstCount());
            //接货续单量
            cell = row.createCell(15);
            if(vo.getAgainCount() != null)
                cell.setCellValue(vo.getAgainCount());
            //其他单量
            cell = row.createCell(16);
            if(vo.getOtherCount() != null)
                cell.setCellValue(vo.getOtherCount());
            //差评
            cell = row.createCell(17);
            if(vo.getBadCount() != null)
                cell.setCellValue(vo.getBadCount());
            //投诉
            cell = row.createCell(18);
            if(vo.getComplaintCount() != null)
                cell.setCellValue(vo.getComplaintCount());
            //罚款合计
            cell = row.createCell(19);
            if(vo.getFineMoney() != null)
                cell.setCellValue(vo.getFineMoney().toString());
            //扣款
            cell = row.createCell(20);
            if(vo.getDeductMoney() != null)
                cell.setCellValue(vo.getDeductMoney().toString());
            //工资
            cell = row.createCell(21);
            if(vo.getSalary() != null)
                cell.setCellValue(vo.getSalary().toString());

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
