package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.OrderPutInfo;
import cc.mrbird.febs.cos.dao.OrderPutInfoMapper;
import cc.mrbird.febs.cos.entity.StoreRecordInfo;
import cc.mrbird.febs.cos.service.IOrderPutInfoService;
import cc.mrbird.febs.cos.service.IStoreRecordInfoService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 库房入库 实现层
 *
 * @author FanK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderPutInfoServiceImpl extends ServiceImpl<OrderPutInfoMapper, OrderPutInfo> implements IOrderPutInfoService {

    private final IStoreRecordInfoService storeRecordInfoService;

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

    /**
     * 添加库房入库
     *
     * @param orderPutInfo 入库信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addOrderPut(OrderPutInfo orderPutInfo) {
        // 获取入库信息
        List<StoreRecordInfo> storeRecordList = JSONUtil.toList(orderPutInfo.getStoreRecord(), StoreRecordInfo.class);
        // 创建时间
        orderPutInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        // 入库单号
        orderPutInfo.setCode("PUT-" + System.currentTimeMillis());
        // 计算总金额
        for (StoreRecordInfo storeRecordInfo : storeRecordList) {
            // 入库
            storeRecordInfo.setType("1");
            storeRecordInfo.setOrderNumber(orderPutInfo.getCode());
            storeRecordInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
            storeRecordInfo.setTotalPrice(NumberUtil.mul(storeRecordInfo.getPrice(), storeRecordInfo.getNum()));
        }
        BigDecimal totalPrice = storeRecordList.stream().map(StoreRecordInfo::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        orderPutInfo.setTotalPrice(totalPrice);

        // 获取库房库存
        List<String> commodityCodeList = storeRecordList.stream().map(StoreRecordInfo::getCommodityCode).collect(Collectors.toList());
        List<StoreRecordInfo> storeRecords = storeRecordInfoService.list(Wrappers.<StoreRecordInfo>lambdaQuery().eq(StoreRecordInfo::getType, "0")
                .in(StoreRecordInfo::getCommodityCode, commodityCodeList));
        Map<String, StoreRecordInfo> storeMap = storeRecords.stream().collect(Collectors.toMap(StoreRecordInfo::getCommodityCode, e -> e));

        // 更新库房库存
        List<StoreRecordInfo> toUpdateList = new ArrayList<>();
        for (StoreRecordInfo storeRecordInfo : storeRecordList) {
            // 获取此商品库存
            StoreRecordInfo currentStoreRecordInfo = storeMap.get(storeRecordInfo.getCommodityCode());
            if (currentStoreRecordInfo == null) {
                continue;
            }
            // 更新此商品库存
            currentStoreRecordInfo.setNum(currentStoreRecordInfo.getNum() + storeRecordInfo.getNum());
            toUpdateList.add(currentStoreRecordInfo);
        }
        storeRecordInfoService.updateBatchById(toUpdateList);

        // 添加入库记录
        storeRecordInfoService.saveBatch(storeRecordList);

        // 保存入库单
        return this.save(orderPutInfo);
    }
}
