package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.CityInfoEntity;

import java.util.Map;

/**
 * 城市信息表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-12-15 12:06:08
 */
public interface CityInfoService extends IService<CityInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

