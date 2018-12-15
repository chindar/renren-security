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

import io.renren.modules.sys.entity.CompanyInfoEntity;
import io.renren.modules.sys.service.CompanyInfoService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 公司信息表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-12-15 12:06:08
 */
@RestController
@RequestMapping("sys/companyinfo")
public class CompanyInfoController {
    @Autowired
    private CompanyInfoService companyInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:companyinfo:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = companyInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:companyinfo:info")
    public R info(@PathVariable("id") Integer id){
        CompanyInfoEntity companyInfo = companyInfoService.selectById(id);

        return R.ok().put("companyInfo", companyInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:companyinfo:save")
    public R save(@RequestBody CompanyInfoEntity companyInfo){
        companyInfoService.insert(companyInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:companyinfo:update")
    public R update(@RequestBody CompanyInfoEntity companyInfo){
        ValidatorUtils.validateEntity(companyInfo);
        companyInfoService.updateAllColumnById(companyInfo);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:companyinfo:delete")
    public R delete(@RequestBody Integer[] ids){
        companyInfoService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
