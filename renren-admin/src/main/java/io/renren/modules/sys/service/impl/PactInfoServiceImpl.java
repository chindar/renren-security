package io.renren.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.PactInfoDao;
import io.renren.modules.sys.entity.PactInfoEntity;
import io.renren.modules.sys.service.PactInfoService;


@Service("pactInfoService")
public class PactInfoServiceImpl extends ServiceImpl<PactInfoDao, PactInfoEntity> implements PactInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<PactInfoEntity> page = this.selectPage(
                new Query<PactInfoEntity>(params).getPage(),
                new EntityWrapper<PactInfoEntity>()
        );

        return new PageUtils(page);
    }

}
