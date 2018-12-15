package io.renren.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.CompanyInfoDao;
import io.renren.modules.sys.entity.CompanyInfoEntity;
import io.renren.modules.sys.service.CompanyInfoService;


@Service("companyInfoService")
public class CompanyInfoServiceImpl extends ServiceImpl<CompanyInfoDao, CompanyInfoEntity> implements CompanyInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<CompanyInfoEntity> page = this.selectPage(
                new Query<CompanyInfoEntity>(params).getPage(),
                new EntityWrapper<CompanyInfoEntity>()
        );

        return new PageUtils(page);
    }

}
