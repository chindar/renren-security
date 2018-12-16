package io.renren.modules.sys.controller;

import java.io.FileInputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import io.renren.common.utils.MongoUtils;
import io.renren.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.renren.modules.sys.entity.PactInfoEntity;
import io.renren.modules.sys.service.PactInfoService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;


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

    /**
     * 上傳合同文件
     * @param multipartRequest
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("upload")
    public R uploadFiles(MultipartHttpServletRequest multipartRequest,
                    HttpServletResponse response) throws Exception {
        Map result = new HashMap();
        try{
            // 封装上传文件的Map
            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
            MultipartFile file=null;
            for(Map.Entry<String,MultipartFile> entry:fileMap.entrySet()){
                file=entry.getValue();
                String fileId=String.valueOf(System.currentTimeMillis());
                MongoUtils mongo=new MongoUtils();
                // 保存文件到mongodb,以指标id作为文件的唯一标识
//                mongo.saveFile(file,fileId,file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."), file.getOriginalFilename().length()),"pact");
                mongo.saveFile(file.getInputStream(),fileId,file.getOriginalFilename(),"pact");
                result.put("fileId",fileId);
                result.put("fileName",file.getOriginalFilename());
            }
        }catch (Exception e){
            e.printStackTrace();

        }
        return R.ok().put("data",result);
    }
}
