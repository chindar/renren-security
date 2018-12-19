package io.renren.modules.sys.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.mongodb.gridfs.GridFSDBFile;
import io.renren.common.utils.MongoUtils;
import io.renren.common.validator.ValidatorUtils;
import org.apache.http.entity.FileEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.renren.modules.sys.entity.CompanyInfoEntity;
import io.renren.modules.sys.service.CompanyInfoService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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

    @RequestMapping("/list2")
    @RequiresPermissions("sys:companyinfo:list")
    public R list2(@RequestParam Map<String, Object> params){
        PageUtils page = companyInfoService.getCompanyList(params);

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

    /**
     * 上传图片文件
     * @param multipartRequest
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("upload")
    public R uploadFiles(MultipartHttpServletRequest multipartRequest,HttpServletRequest request,
                         HttpServletResponse response,
                         @RequestParam(value = "dbname",defaultValue = "")String dbname
                         ) throws Exception {
        Map result = new HashMap();
        try{
            String QUERY_FILE_PATH = request.getScheme() + "://" +
                    request.getServerName() + ":" + request.getServerPort() +
                    request.getContextPath() + "/";
            // 封装上传文件的Map
            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
            MultipartFile file=null;
            for(Map.Entry<String,MultipartFile> entry:fileMap.entrySet()){
                file=entry.getValue();
                String fileId=String.valueOf(System.currentTimeMillis());
                MongoUtils mongo=new MongoUtils();
                // 保存文件到mongodb,以指标id作为文件的唯一标识
                mongo.saveFile(file.getInputStream(),fileId,file.getOriginalFilename(),dbname);
                result.put("fileId",fileId);
                result.put("fileName",file.getOriginalFilename());
                result.put("fileUrl",MessageFormat.format("{0}sys/companyinfo/getFile?fileId={1}&dbname={2}", QUERY_FILE_PATH, fileId,dbname));
            }
        }catch (Exception e){
            e.printStackTrace();

        }
        return R.ok().put("data",result);
    }

    @RequestMapping(value = "/getFile")
    @ResponseBody
    public void getFile(
            @RequestParam(value = "fileId",defaultValue = "") String fileId,
            @RequestParam(value = "dbname",defaultValue = "")String dbname,
            HttpServletRequest request,HttpServletResponse response) throws IOException {
        try {
            MongoUtils mongo=new MongoUtils();
            HashMap<String, Object> params = new HashMap<String, Object>(4);
            params.put("fileId", fileId);
            GridFSDBFile file = mongo.findFirstFile(params,dbname);
            response.setContentType(file.getContentType());
            // 输出流
            ServletOutputStream out = null;
            byte[] bytes = input2byte(file.getInputStream());
            out = response.getOutputStream();
            out.write(bytes);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static final byte[] input2byte(InputStream inStream)
            throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] in2b = swapStream.toByteArray();
        return in2b;
    }
}
