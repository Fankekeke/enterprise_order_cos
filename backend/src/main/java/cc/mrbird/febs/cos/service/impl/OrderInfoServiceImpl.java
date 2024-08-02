package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.cos.dao.StoreRecordInfoMapper;
import cc.mrbird.febs.cos.entity.*;
import cc.mrbird.febs.cos.dao.OrderInfoMapper;
import cc.mrbird.febs.cos.service.*;
import cn.hutool.core.collection.CollectionUtil;
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
 * 订单管理 实现层
 *
 * @author FanK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements IOrderInfoService {

    private final IAddressInfoService addressInfoService;

    private final ICommodityInfoService commodityInfoService;

    private final ILogisticsInfoService logisticsInfoService;

    private final IUserInfoService userInfoService;

    private final IOrderDetailService orderDetailService;

    private final IBulletinInfoService bulletinInfoService;

    private final StoreRecordInfoMapper storeRecordInfoMapper;



    /**
     * 分页获取订单信息
     *
     * @param page      分页对象
     * @param orderInfo 订单信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectOrderPage(Page<OrderInfo> page, OrderInfo orderInfo) {
        return baseMapper.selectOrderPage(page, orderInfo);
    }

    /**
     * 查询订单详情
     *
     * @param orderId 订单编号
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> selectOrderDetail(Integer orderId) {
        // 返回数据
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>() {
            {
                put("order", null);
                put("detail", Collections.emptyList());
                put("user", null);
                put("logistics", Collections.emptyList());
                put("address", null);
            }
        };

        // 订单信息
        OrderInfo orderInfo = this.getById(orderId);
        result.put("order", orderInfo);

        // 订单详情
        List<OrderDetail> orderDetailList = orderDetailService.list(Wrappers.<OrderDetail>lambdaQuery().eq(OrderDetail::getOrderId, orderId));
        List<String> commodityCodeList = orderDetailList.stream().map(OrderDetail::getCommodityCode).collect(Collectors.toList());
        // 商品信息
        List<CommodityInfo> commodityInfoList = commodityInfoService.list(Wrappers.<CommodityInfo>lambdaQuery().in(CommodityInfo::getCode, commodityCodeList));
        Map<String, CommodityInfo> commodityInfoMap = commodityInfoList.stream().collect(Collectors.toMap(CommodityInfo::getCode, e -> e));
        for (OrderDetail orderDetail : orderDetailList) {
            orderDetail.setCommodity(commodityInfoMap.get(orderDetail.getCommodityCode()));
        }
        result.put("detail", orderDetailList);

        // 用户信息
        UserInfo userInfo = userInfoService.getById(orderInfo.getUserId());
        result.put("user", userInfo);

        // 物流信息
        List<LogisticsInfo> logisticsInfoList = logisticsInfoService.list(Wrappers.<LogisticsInfo>lambdaQuery().eq(LogisticsInfo::getOrderId, orderId));
        result.put("logistics", logisticsInfoList);

        // 收货地址
        if (orderInfo.getAddressId() != null) {
            AddressInfo addressInfo = addressInfoService.getById(orderInfo.getAddressId());
            result.put("address", addressInfo);
        }
        return result;
    }

    /**
     * 用户添加订单
     *
     * @param orderInfo 订单信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean orderAdd(OrderInfo orderInfo) throws FebsException {
        // 获取订单详情
        List<OrderDetail> orderDetailList = JSONUtil.toList(orderInfo.getOrderDetail(), OrderDetail.class);
        if (CollectionUtil.isEmpty(orderDetailList)) {
            throw new FebsException("订单详情不能为空");
        }

        // 订单总金额
        BigDecimal totalPrice = orderDetailList.stream().map(e -> NumberUtil.mul(e.getNum(), e.getPrice())).reduce(BigDecimal.ZERO, BigDecimal::add);
        orderInfo.setTotalPrice(totalPrice);

        // 如果为预付款
        if ("1".equals(orderInfo.getType())) {
            // 预付款金额为30%
            orderInfo.setSubsistPrice(NumberUtil.mul(orderInfo.getTotalPrice(), 0.3));
            orderInfo.setOwePrice(NumberUtil.sub(totalPrice, orderInfo.getSubsistPrice()));
        }
        this.save(orderInfo);
        orderDetailList.forEach(e -> e.setOrderId(orderInfo.getId()));
        return orderDetailService.saveBatch(orderDetailList);
    }

    /**
     * 管理员首页数据统计
     *
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> selectHomeData() {
        // 返回数据
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();

        // 总订单
        result.put("registerNum", 0);
        // 总收益
        result.put("orderPrice", 0);
        // 出库数量
        result.put("outNum", 0);
        // 入库数量
        result.put("putNum", 0);

        int year = DateUtil.year(new Date());
        int month = DateUtil.month(new Date()) + 1;

        // 本月出库数量
        List<StoreRecordInfo> monthOutRecordList = storeRecordInfoMapper.selectRecordBySlot(year, month, "2");
        result.put("monthOutNum", monthOutRecordList.size());
        // 本月出库收益
        result.put("monthOutPrice", monthOutRecordList.stream().map(StoreRecordInfo::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add));

        // 本年出库数量
        List<StoreRecordInfo> yearOutRecordList = storeRecordInfoMapper.selectRecordBySlot(year, null, "2");
        result.put("yearOutNum", yearOutRecordList.size());
        // 本年出库收益
        result.put("yearOutPrice", yearOutRecordList.stream().map(StoreRecordInfo::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add));

        // 本月入库数量
        List<StoreRecordInfo> monthPutRecordList = storeRecordInfoMapper.selectRecordBySlot(year, month, "1");
        result.put("monthPutNum", monthPutRecordList.size());
        // 本月入库支出
        result.put("monthPutPrice", monthPutRecordList.stream().map(StoreRecordInfo::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add));

        // 本年入库数量
        List<StoreRecordInfo> yearPutRecordList = storeRecordInfoMapper.selectRecordBySlot(year, month, "1");
        result.put("yearPutNum", yearPutRecordList.size());
        // 本年入库支出
        result.put("yearPutPrice", yearPutRecordList.stream().map(StoreRecordInfo::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add));


        // 公告信息
        result.put("bulletin", bulletinInfoService.list(Wrappers.<BulletinInfo>lambdaQuery().eq(BulletinInfo::getRackUp, 1)));

        // 近十天内出库统计
        result.put("orderNumWithinDays", baseMapper.selectOrderNumWithinDays("2"));
        // 近十天内出库收益统计
        result.put("orderPriceWithinDays", baseMapper.selectOrderPriceWithinDays("2"));

        // 近十天内入库统计
        result.put("putNumWithinDays", baseMapper.selectOrderNumWithinDays("1"));
        // 近十天内入库收益统计
        result.put("putPriceWithinDays", baseMapper.selectOrderPriceWithinDays("1"));
        return result;
    }
}
