package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.LogisticsInfo;
import cc.mrbird.febs.cos.entity.OrderInfo;
import cc.mrbird.febs.cos.entity.UserInfo;
import cc.mrbird.febs.cos.dao.UserInfoMapper;
import cc.mrbird.febs.cos.service.ILogisticsInfoService;
import cc.mrbird.febs.cos.service.IOrderInfoService;
import cc.mrbird.febs.cos.service.IUserInfoService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
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
 * 用户管理 实现层
 *
 * @author FanK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

    private IOrderInfoService orderInfoService;

    private ILogisticsInfoService logisticsInfoService;

    /**
     * 分页获取用户信息
     *
     * @param page     分页对象
     * @param userInfo 用户信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectUserPage(Page<UserInfo> page, UserInfo userInfo) {
        return baseMapper.selectUserPage(page, userInfo);
    }

    /**
     * 获取用户详情
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> selectUserDetail(Integer userId) {
        // 返回数据
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>() {
            {
                put("user,", null);
                put("order", Collections.emptyList());
            }
        };
        UserInfo userInfo = this.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUserId, userId));
        if (userInfo == null) {
            return result;
        }

        result.put("user", userInfo);
        // 用户订单信息
        List<OrderInfo> orderInfoList = orderInfoService.list(Wrappers.<OrderInfo>lambdaQuery().eq(OrderInfo::getUserId, userInfo.getId()));
        if (CollectionUtil.isEmpty(orderInfoList)) {
            return result;
        }

        // 获取未收货订单状态
        List<OrderInfo> notCheckOrder = orderInfoList.stream().filter(e -> "4".equals(e.getStatus())).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(notCheckOrder)) {
            return result;
        }

        // 获取物流信息
        List<Integer> orderIds = notCheckOrder.stream().map(OrderInfo::getId).collect(Collectors.toList());
        List<LogisticsInfo> logisticsList = logisticsInfoService.list(Wrappers.<LogisticsInfo>lambdaQuery().eq(LogisticsInfo::getCurrentLogistics, "1")
                .in(LogisticsInfo::getOrderId, orderIds));
        Map<Integer, String> logisticsMap = logisticsList.stream().collect(Collectors.toMap(LogisticsInfo::getOrderId, LogisticsInfo::getRemark));

        for (OrderInfo orderInfo : notCheckOrder) {
            if (StrUtil.isEmpty(logisticsMap.get(orderInfo.getId()))) {
                continue;
            }
            orderInfo.setAddress(logisticsMap.get(orderInfo.getId()));
        }
        result.put("order", notCheckOrder);
        return result;
    }
}
