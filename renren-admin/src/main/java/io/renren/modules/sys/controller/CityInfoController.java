package io.renren.modules.sys.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.entity.CityInfoEntity;
import io.renren.modules.sys.service.CityInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 城市信息表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-12-15 12:06:08
 */
@RestController
@RequestMapping("sys/cityinfo")
public class CityInfoController {
    @Autowired
    private CityInfoService cityInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:cityinfo:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cityInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:cityinfo:info")
    public R info(@PathVariable("id") Integer id){
        CityInfoEntity cityInfo = cityInfoService.selectById(id);

        return R.ok().put("cityInfo", cityInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:cityinfo:save")
    public R save(@RequestBody CityInfoEntity cityInfo){
        cityInfoService.insert(cityInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:cityinfo:update")
    public R update(@RequestBody CityInfoEntity cityInfo){
        ValidatorUtils.validateEntity(cityInfo);
        cityInfoService.updateAllColumnById(cityInfo);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:cityinfo:delete")
    public R delete(@RequestBody Integer[] ids){
        cityInfoService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
