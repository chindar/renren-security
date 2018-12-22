package io.renren.modules.sys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;
import io.renren.modules.sys.dao.CityInfoDao;
import io.renren.modules.sys.dao.CourierDao;
import io.renren.modules.sys.entity.CourierEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.CourierService;
import io.renren.modules.sys.vo.CourierVo;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@SuppressWarnings("ALL")
@Service("courierService")
public class CourierServiceImpl extends ServiceImpl<CourierDao, CourierEntity> implements CourierService {

    @Autowired
    private CityInfoDao cityInfoDao;
    @Autowired
    private CourierDao courierDao;


    private static List<Object> templetList = CollUtil.newArrayList();

    static {
        // 片区 城市 站点 erp账号 配送员姓名 身份证 电话 银行卡号 开户行 联行号 入职时间 离职时间 状态 备注

        templetList.add("片区");
        templetList.add("城市");
        templetList.add("站点");
        templetList.add("erp账号");
        templetList.add("配送员姓名");
        templetList.add("身份证");
        templetList.add("电话");
        templetList.add("银行卡号");
        templetList.add("开户行");
        templetList.add("联行号");
        templetList.add("入职时间");
        templetList.add("离职时间");
        templetList.add("状态");
        templetList.add("备注");
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<CourierEntity> page = this.selectPage(
                new Query<CourierEntity>(params).getPage(),
                new EntityWrapper<CourierEntity>()
        );

        return new PageUtils(page);
    }


    public PageUtils selectMyPage(Map<String, Object> params) {
        Page<CourierVo> page = new Query<CourierVo>(params).getPage();
        CourierVo courierVo = BeanUtil.mapToBean(params, CourierVo.class, true);
        page.setRecords(courierDao.selectMyPage(page, courierVo));
        return new PageUtils(page);
    }

    /**
     * 上传文件
     *
     * @param multipartFile
     * @return
     */
    @Override
    public R importCourier(MultipartFile multipartFile) {
        final String batchId = RandomUtil.simpleUUID();
        try {
            InputStream is = multipartFile.getInputStream();
            String filename = multipartFile.getOriginalFilename();
            ExcelReader reader = ExcelUtil.getReader(is, 0);
            // TODO: 2018/12/15 判断是否为规定模板
            List<List<Object>> readFirstList = reader.read(0, 0);
            List<Object> columnList = readFirstList.get(0);
            if (isTemplet(columnList, templetList)) {
                // TODO: 2018/12/15 读取文件
                List<List<Object>> contentList = reader.read(1);
                // TODO: 2018/12/15 解析数据
                List<CourierEntity> courierList = CollUtil.newArrayList();
                contentList.forEach(lineList -> {
                    CourierEntity courierEntity = new CourierEntity();
                    // 片区 城市 站点 erp账号 姓名 身份证号码 电话 银行卡号 开户行 联行号 入职时间 离职时间 状态 备注
                    courierEntity.setArea(Convert.toStr(lineList.get(0)));
                    // 根据城市名称获取城市id
                    Integer cityId = cityInfoDao.getIdByCityName(lineList.get(1));
                    courierEntity.setCityId(cityId);
                    courierEntity.setSite(Convert.toStr(lineList.get(2)));
                    courierEntity.setErpId(Convert.toStr(lineList.get(3)));
                    courierEntity.setCourierName(Convert.toStr(lineList.get(4)));
                    courierEntity.setCardId(Convert.toStr(lineList.get(5)));
                    courierEntity.setPhone(Convert.toStr(lineList.get(6)));
                    courierEntity.setBankCardId(Convert.toStr(lineList.get(7)));
                    courierEntity.setDepositBank(Convert.toStr(lineList.get(8)));
                    courierEntity.setJoinBankNumber(Convert.toStr(lineList.get(9)));
                    courierEntity.setEntryDate(DateUtil.parse(Convert.toStr(lineList.get(10))));
                    courierEntity.setLeaveDate(DateUtil.parse(Convert.toStr(lineList.get(11))));
                    String statusStr = Convert.toStr(lineList.get(12));
                    int status = StrUtil.equals(statusStr, "在职") ? 1 : 0;
                    courierEntity.setStatus(status);
                    courierEntity.setComment(Convert.toStr(lineList.get(13)));
                    courierEntity.setIsDelete(0);
                    String username = ((SysUserEntity) SecurityUtils.getSubject().getPrincipal()).getUsername();
                    courierEntity.setCreater(username);
                    courierEntity.setCreateDate(DateUtil.date());
                    courierEntity.setBatchId(batchId);
                    courierList.add(courierEntity);
                });
                // TODO: 2018/12/15 存入数据库
                if (CollUtil.isNotEmpty(courierList)) {
                    this.insertBatch(courierList);
                }
            }
        } catch (IOException e) {
            e.getStackTrace();
            R.error("导入失败, 解析Excel异常!");
        }
        return R.ok().put("batchId", batchId);
    }

    /**
     * 批量更新配送员信息
     *
     * @param batchId
     * @param pactId
     * @return
     */
    @Override
    public R editBatch(String batchId, String pactId) {
        courierDao.updateByBatch(batchId, pactId);
        return R.ok();
    }

    /**
     * 导出配送员信息
     *
     * @param ids
     * @param res
     */
    @Override
    public void exportCourier(Integer[] ids, HttpServletResponse res) {

        OutputStream bos = null;
        try {
            List<CourierVo> courierList = courierDao.selectByIds(Arrays.asList(ids));

            // 第一步，创建一个webbook，对应一个Excel文件
            XSSFWorkbook wb = new XSSFWorkbook();
            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
            XSSFSheet sheet = wb.createSheet("配送员信息");

            // 设置所有单元格大小 -- 宽度
            sheet.setDefaultColumnWidth(14);
            // 设置所有单元格大小 -- 高度
            sheet.setDefaultRowHeightInPoints(14);

            /**
             * 数据表内容
             */
            // 添加表头标题
            XSSFRow row = sheet.createRow(0);
            //设置行高
            row.setHeight((short) 600);
            XSSFCell cell = null;
            // 设置表头名称
            for (int i = 0; i < templetList.size(); i++) {
                cell = row.createCell(i);
                cell.setCellValue(Convert.toStr(templetList.get(i)));

            }

            // 遍历运营数据list
            for (int i = 0; i < courierList.size(); i++) {
                CourierVo vo = courierList.get(i);
                // 确定内容开始行
                row = sheet.createRow(row.getRowNum() + 1);
                //设置行高
                row.setHeight((short) 600);
                // 片区
                cell = row.createCell(0);
                cell.setCellValue(vo.getArea());
                // 城市
                cell = row.createCell(1);
                cell.setCellValue(vo.getCityName());
                // 站点
                cell = row.createCell(2);
                cell.setCellValue(vo.getSite());
                // erp账号
                cell = row.createCell(3);
                cell.setCellValue(vo.getErpId());
                // 配送员姓名
                cell = row.createCell(4);
                cell.setCellValue(vo.getCourierName());
                // 身份证
                cell = row.createCell(5);
                cell.setCellValue(vo.getCardId());
                // 电话
                cell = row.createCell(6);
                cell.setCellValue(vo.getPhone());
                // 银行卡号
                cell = row.createCell(7);
                cell.setCellValue(vo.getBankCardId());
                // 开户行
                cell = row.createCell(8);
                cell.setCellValue(vo.getDepositBank());
                // 联行号
                cell = row.createCell(9);
                cell.setCellValue(vo.getJoinBankNumber());
                // 入职时间
                cell = row.createCell(10);
                cell.setCellValue(vo.getEntryDate());
                // 离职时间
                cell = row.createCell(11);
                cell.setCellValue(vo.getLeaveDate());
                // 状态
                Integer statusI = vo.getStatus();
                if (ObjectUtil.isNotNull(statusI)) {
                    int status = statusI.intValue();
                    cell = row.createCell(12);
                    cell.setCellValue(status == 1 ? "在职" : status == 0 ? "离职" : "");
                }
                // 备注
                cell = row.createCell(13);
                cell.setCellValue(vo.getComment());
            }

            String filename = "快递员信息.xlsx";
            res.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("gb2312"), "iso8859-1"));
            res.addHeader("Pargam", "no-cache");
            res.addHeader("Cache-Control", "no-cache");
            res.setContentType("application/vnd.ms-excel;charset=UTF8");
            bos = new BufferedOutputStream(res.getOutputStream());
            wb.write(bos);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtil.close(bos);
        }
    }

    /**
     * 是否为Excel模板
     *
     * @param columnList
     * @param templetList
     * @return
     */
    private boolean isTemplet(List<Object> columnList, List<Object> templetList) {
        int templetSize = templetList.size();
        int colSize = columnList.size();
        if (templetSize == colSize) {
            int count = 0;
            for (int i = 0; i < colSize; i++) {
                if (templetList.get(i).equals(columnList.get(i))) {
                    count++;
                }
            }
            if (count == templetSize) {
                return true;
            }
        }
        return false;
    }
}
