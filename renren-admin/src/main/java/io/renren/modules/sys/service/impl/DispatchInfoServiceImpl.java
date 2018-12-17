package io.renren.modules.sys.service.impl;

import io.renren.common.utils.Tools;
import io.renren.modules.sys.entity.CityInfoEntity;
import io.renren.modules.sys.entity.CourierEntity;
import io.renren.modules.sys.entity.PactInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.DispatchInfoDao;
import io.renren.modules.sys.entity.DispatchInfoEntity;
import io.renren.modules.sys.service.DispatchInfoService;
import org.springframework.transaction.annotation.Transactional;


@Service("dispatchInfoService")
public class DispatchInfoServiceImpl extends ServiceImpl<DispatchInfoDao, DispatchInfoEntity> implements DispatchInfoService {

    @Autowired
    private DispatchInfoDao dao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<DispatchInfoEntity> page = this.selectPage(
                new Query<DispatchInfoEntity>(params).getPage(),
                new EntityWrapper<DispatchInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils getDispatchList(Map<String, Object> params) {
        DispatchInfoEntity entity = new DispatchInfoEntity();
        Page page = new Query<DispatchInfoEntity>(params).getPage();
        if (params.get("courierName") != null && Tools.notEmpty(params.get("courierName").toString()))
            entity.setCourierName(params.get("courierName").toString());
        if (params.get("month") != null && Tools.notEmpty(params.get("month").toString()))
            entity.setMonth(params.get("month").toString());
        List<DispatchInfoEntity> list = dao.getDispatchList(page,entity);
        page.setRecords(list);
        return new PageUtils(page);
    }

    /**
     * 导入运营数据
     * @param entity
     * @return
     */
    @Override
    @Transactional
    public int importData(DispatchInfoEntity entity) {
        if (Tools.isEmpty(entity.getCourierName()) || Tools.isEmpty(entity.getErpId()))
            throw new RuntimeException("配送员姓名和erp编号不能为空");
        //根据erp编号和姓名查询配送员Id
        CourierEntity courierVo = dao.getCourierInfo(entity.getCourierName(),entity.getErpId());
        if(courierVo == null)
            throw new RuntimeException("姓名："+entity.getCourierName()+"编号："+entity.getErpId()+" 的配送员不存在，请先导入配送员");
        entity.setCourierId(courierVo.getId());
//        if (Tools.isEmpty(entity.getCityName()))
//            throw new RuntimeException("城市名不能为空");
//        //根据城市名查询城市信息
//        CityInfoEntity cityVo = dao.getCityInfo(entity.getCityName());
        //插入数据信息
        int count = dao.insertDispatch(entity);
        return count;
    }
}
