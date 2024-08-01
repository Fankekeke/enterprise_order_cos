package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.OrderPutInfo;
import cc.mrbird.febs.cos.dao.OrderPutInfoMapper;
import cc.mrbird.febs.cos.service.IOrderPutInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * 库房入库 实现层
 *
 * @author FanK
 */
@Service
public class OrderPutInfoServiceImpl extends ServiceImpl<OrderPutInfoMapper, OrderPutInfo> implements IOrderPutInfoService {

    /**
     * 分页获取库房入库信息
     *
     * @param page         分页对象
     * @param orderPutInfo 库房入库信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectOrderPutPage(Page<OrderPutInfo> page, OrderPutInfo orderPutInfo) {
        return baseMapper.selectOrderPutPage(page, orderPutInfo);
    }
}
