package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.OrderPutInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * 库房入库 service层
 *
 * @author FanK
 */
public interface IOrderPutInfoService extends IService<OrderPutInfo> {

    /**
     * 分页获取库房入库信息
     *
     * @param page         分页对象
     * @param orderPutInfo 库房入库信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectOrderPutPage(Page<OrderPutInfo> page, OrderPutInfo orderPutInfo);
}
