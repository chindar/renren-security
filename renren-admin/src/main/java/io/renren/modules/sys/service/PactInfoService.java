package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.PactInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * 合同信息表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-12-15 12:06:08
 */
public interface PactInfoService extends IService<PactInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils getPactList(Map<String, Object> params,String path);

    /**
     * 获取全部合同信息
     * @return
     */
    List<PactInfoEntity> getAll();
}

