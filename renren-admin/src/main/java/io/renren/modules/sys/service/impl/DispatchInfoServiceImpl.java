package io.renren.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.DispatchInfoDao;
import io.renren.modules.sys.entity.DispatchInfoEntity;
import io.renren.modules.sys.service.DispatchInfoService;


@Service("dispatchInfoService")
public class DispatchInfoServiceImpl extends ServiceImpl<DispatchInfoDao, DispatchInfoEntity> implements DispatchInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<DispatchInfoEntity> page = this.selectPage(
                new Query<DispatchInfoEntity>(params).getPage(),
                new EntityWrapper<DispatchInfoEntity>()
        );

        return new PageUtils(page);
    }

}
