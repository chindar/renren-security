package io.renren.modules.sys.controller;

import com.mongodb.gridfs.GridFSDBFile;
import io.renren.common.utils.MongoUtils;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.common.validator.group.UpdateGroup;
import io.renren.modules.sys.entity.PactInfoEntity;
import io.renren.modules.sys.service.PactInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    public R list2(@RequestParam Map<String, Object> params, HttpServletRequest request){
        String QUERY_FILE_PATH = request.getScheme() + "://" +
                request.getServerName() + ":" + request.getServerPort() +
                request.getContextPath() + "/";
        PageUtils page = pactInfoService.getPactList(params, QUERY_FILE_PATH);

        return R.ok().put("page", page);
    }

    /**
     * 获取全部合同信息
     * @return
     */
    @RequestMapping("/all")
    public R all() {
        List<PactInfoEntity> pactList = pactInfoService.getAll();
        return R.ok().put("pactList", pactList);
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
        ValidatorUtils.validateEntity(pactInfo, UpdateGroup.class);
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

    /**
     * 下载合同文件
     * @param fileId
     * @param response
     */
    @RequestMapping("downloadFile")
    public void downloadFile(
            @RequestParam(value = "fileId", defaultValue = "") String fileId,
            @RequestParam(value = "dbname",defaultValue = "") String dbname,
            HttpServletResponse response) {
        try {
//            String dbname = "pact";
            MongoUtils mongo = new MongoUtils();
            HashMap<String, Object> params = new HashMap<String, Object>(4);
            params.put("fileId", fileId);
            GridFSDBFile file = mongo.findFirstFile(params,dbname);
            if (file == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            String contentType = String.valueOf(file.get("contentType"));
            String fileName = String.valueOf(file.get("filename"));
            response.setContentType(contentType);
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "iso8859-1"));
            try (InputStream in = file.getInputStream();
                 BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream())) {
                byte[] buff = new byte[10240];
                int i;
                while ((i = in.read(buff)) > 0) {
                    out.write(buff, 0, i);
                }
                out.flush();
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
