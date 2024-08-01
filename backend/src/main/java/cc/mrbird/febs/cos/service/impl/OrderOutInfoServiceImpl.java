package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.OrderOutInfo;
import cc.mrbird.febs.cos.dao.OrderOutInfoMapper;
import cc.mrbird.febs.cos.service.IOrderOutInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * 库房出库 实现层
 *
 * @author FanK
 */
@Service
public class OrderOutInfoServiceImpl extends ServiceImpl<OrderOutInfoMapper, OrderOutInfo> implements IOrderOutInfoService {

    /**
     * 分页获取库房出库信息
     *
     * @param page         分页对象
     * @param orderOutInfo 库房出库信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectOrderOutPage(Page<OrderOutInfo> page, OrderOutInfo orderOutInfo) {
        return baseMapper.selectOrderOutPage(page, orderOutInfo);
    }

    /**
     * 添加库房出库
     *
     * @param orderOutInfo 出库信息
     * @return 结果
     */
    @Override
    public boolean addOrderOut(OrderOutInfo orderOutInfo) {
        return false;
    }
}
