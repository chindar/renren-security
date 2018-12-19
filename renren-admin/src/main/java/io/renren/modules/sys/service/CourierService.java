package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.sys.entity.CourierEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 快递员信息表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-12-15 12:06:08
 */
public interface CourierService extends IService<CourierEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 上传文件
     * @param file
     * @return
     */
    R importCourier(MultipartFile file);

    PageUtils selectMyPage(Map<String, Object> params);

    /**
     * 批量更新配送员信息
     * @param batchId
     * @param pactId
     * @return
     */
    R editBatch(String batchId, String pactId);
}

