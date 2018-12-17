package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.DispatchInfoEntity;

import java.util.Map;

/**
 * 配送信息表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-12-15 12:06:08
 */
public interface DispatchInfoService extends IService<DispatchInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils getDispatchList(Map<String, Object> params);

    int importData(DispatchInfoEntity entity);
}

