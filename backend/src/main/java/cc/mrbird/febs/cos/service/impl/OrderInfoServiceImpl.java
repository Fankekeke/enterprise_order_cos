package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.*;
import cc.mrbird.febs.cos.dao.OrderInfoMapper;
import cc.mrbird.febs.cos.service.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
}
