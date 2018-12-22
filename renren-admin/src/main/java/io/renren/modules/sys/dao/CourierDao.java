package io.renren.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.renren.modules.sys.entity.CourierEntity;
import io.renren.modules.sys.vo.CourierVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 快递员信息表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-12-15 12:06:08
 */
public interface CourierDao extends BaseMapper<CourierEntity> {

    /**
     * 查询分页列表
     * @param page
     * @param courierVo
     * @return
     */
    List<CourierVo> selectMyPage(Page<CourierVo> page, CourierVo courierVo);

    /**
     * 更新导入信息
     * @param batchId
     */
    void updateByBatch(@Param("batchId") String batchId, @Param("pactId") String pactId);

    /**
     * 查询
     * @param ids
     * @return
     */
    List<CourierVo> selectByIds(@Param("ids") List<Integer> ids);
}
