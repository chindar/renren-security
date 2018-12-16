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

import io.renren.modules.sys.entity.PactInfoEntity;
import io.renren.modules.sys.service.PactInfoService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 合同信息表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-12-15 12:06:08
 */
@RestController
@RequestMapping("sys/pactinfo")
public class PactInfoController {
    @Autowired
    private PactInfoService pactInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:pactinfo:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = pactInfoService.queryPage(params);

        return R.ok().put("page", page);
    }

    @RequestMapping("/list2")
    @RequiresPermissions("sys:pactinfo:list")
    public R list2(@RequestParam Map<String, Object> params){
        PageUtils page = pactInfoService.getPactList(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:pactinfo:info")
    public R info(@PathVariable("id") Integer id){
        PactInfoEntity pactInfo = pactInfoService.selectById(id);

        return R.ok().put("pactInfo", pactInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:pactinfo:save")
    public R save(@RequestBody PactInfoEntity pactInfo){
        pactInfoService.insert(pactInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:pactinfo:update")
    public R update(@RequestBody PactInfoEntity pactInfo){
        ValidatorUtils.validateEntity(pactInfo);
        pactInfoService.updateAllColumnById(pactInfo);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:pactinfo:delete")
    public R delete(@RequestBody Integer[] ids){
        pactInfoService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
