package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.SqlHelper;
import io.renren.common.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Autowired
    private PactInfoDao dao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<PactInfoEntity> page = this.selectPage(
                new Query<PactInfoEntity>(params).getPage(),
                new EntityWrapper<PactInfoEntity>()
        );
        System.out.println(page);
        return new PageUtils(page);
    }

    @Override
    public PageUtils getPactList(Map<String, Object> params) {
        PactInfoEntity entity = new PactInfoEntity();
        Page page = new Query<PactInfoEntity>(params).getPage();
        if (Tools.notEmpty(params.get("businessName").toString()))
            entity.setBusinessName(params.get("businessName").toString());
        if (Tools.notEmpty(params.get("cityId").toString()))
            entity.setCityId(Integer.valueOf(params.get("cityId").toString()));
        if (Tools.notEmpty(params.get("pactStatus").toString()))
            entity.setPactStatus(Integer.valueOf(params.get("pactStatus").toString()));
        List<PactInfoEntity> list = dao.getPactList(page,entity);
        page.setRecords(list);
//        page.setTotal(list.size());
        System.out.println(page);
        System.out.println(page.getPages());
        return new PageUtils(page);
    }
}
