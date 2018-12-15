package io.renren.modules.sys.controller;

import java.util.Arrays;
import java.util.Map;

import io.renren.common.validator.ValidatorUtils;
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
