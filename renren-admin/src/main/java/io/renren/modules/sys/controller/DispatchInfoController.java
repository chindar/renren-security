package io.renren.modules.sys.controller;

import java.math.BigDecimal;
import java.util.*;

import com.alibaba.fastjson.JSONObject;
import io.renren.common.utils.MongoUtils;
import io.renren.common.validator.ValidatorUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.sys.entity.DispatchInfoEntity;
import io.renren.modules.sys.service.DispatchInfoService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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
                Workbook book = new HSSFWorkbook(file.getInputStream());
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
