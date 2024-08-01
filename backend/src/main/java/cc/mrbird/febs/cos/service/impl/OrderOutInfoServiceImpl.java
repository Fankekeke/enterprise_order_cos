package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.OrderOutInfo;
import cc.mrbird.febs.cos.dao.OrderOutInfoMapper;
import cc.mrbird.febs.cos.entity.StoreRecordInfo;
import cc.mrbird.febs.cos.service.IOrderOutInfoService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

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
        // 获取出库信息
        List<StoreRecordInfo> storeRecordList = JSONUtil.toList(orderOutInfo.getStoreRecord(), StoreRecordInfo.class);
        // 出库单号
        orderOutInfo.setCode("OUT-" + System.currentTimeMillis());
        // 创建时间
        orderOutInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        // 计算总金额
        for (StoreRecordInfo storeRecordInfo : storeRecordList) {
            // 出库
            storeRecordInfo.setType("2");
            storeRecordInfo.setOrderNumber(orderOutInfo.getCode());
            storeRecordInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
            storeRecordInfo.setTotalPrice(NumberUtil.mul(storeRecordInfo.getPrice(), storeRecordInfo.getNum()));
        }
        BigDecimal totalPrice = storeRecordList.stream().map(StoreRecordInfo::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        orderOutInfo.setTotalPrice(totalPrice);
        return false;
    }
}
