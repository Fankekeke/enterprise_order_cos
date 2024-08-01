package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.OrderOutInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * 库房出库 mapper层
 *
 * @author FanK
 */
public interface OrderOutInfoMapper extends BaseMapper<OrderOutInfo> {

    /**
     * 分页获取库房出库信息
     *
     * @param page         分页对象
     * @param orderOutInfo 库房出库信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectOrderOutPage(Page<OrderOutInfo> page, @Param("orderOutInfo") OrderOutInfo orderOutInfo);
}
