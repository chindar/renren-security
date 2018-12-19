package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.CompanyInfoEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * 公司信息表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-12-15 12:06:08
 */
public interface CompanyInfoDao extends BaseMapper<CompanyInfoEntity> {

    List<CompanyInfoEntity> getCompanyList(RowBounds var1, CompanyInfoEntity params);
}
