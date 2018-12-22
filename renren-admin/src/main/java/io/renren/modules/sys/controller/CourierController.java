package io.renren.modules.sys.controller;

import io.renren.common.annotation.SysLog;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.entity.CourierEntity;
import io.renren.modules.sys.service.CourierService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;



/**
 * 快递员信息表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-12-15 12:06:08
 */
@RestController
@RequestMapping("sys/courier")
public class CourierController {
    @Autowired
    private CourierService courierService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:courier:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = courierService.selectMyPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:courier:info")
    public R info(@PathVariable("id") Integer id){
        CourierEntity courier = courierService.selectById(id);

        return R.ok().put("courier", courier);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:courier:save")
    public R save(@RequestBody CourierEntity courier){
        courierService.insert(courier);

        return R.ok();
    }

    /**
     * 导入配送员信息
     * @param file
     * @return
     */
    @SysLog("导入配送员信息")
    @PostMapping("/import")
    public R importCourier(@RequestParam("file") MultipartFile file) {

        return courierService.importCourier(file);
    }

    @SysLog("批量更新配送员信息")
    @PostMapping("/editPact")
    public R editPact(String batchId, String pactId) {
        return courierService.editBatch(batchId, pactId);
    }

    /**
     * 导出配送员信息
     * @return
     */
    @SysLog("导出配送员信息")
    @GetMapping("/exportCourier")
    public void exportCourier(HttpServletResponse res, Integer[] ids) {
        courierService.exportCourier(ids, res);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:courier:update")
    public R update(@RequestBody CourierEntity courier){
        ValidatorUtils.validateEntity(courier);
        courierService.updateAllColumnById(courier);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:courier:delete")
    public R delete(@RequestBody Integer[] ids){
        courierService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
