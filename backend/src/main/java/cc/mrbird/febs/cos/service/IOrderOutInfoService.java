package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.OrderOutInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * 库房出库 service层
 *
 * @author FanK
 */
public interface IOrderOutInfoService extends IService<OrderOutInfo> {

    /**
     * 分页获取库房出库信息
     *
     * @param page         分页对象
     * @param orderOutInfo 库房出库信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectOrderOutPage(Page<OrderOutInfo> page, OrderOutInfo orderOutInfo);
}
