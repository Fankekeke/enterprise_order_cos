package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.LogisticsInfo;
import cc.mrbird.febs.cos.entity.OrderInfo;
import cc.mrbird.febs.cos.entity.UserInfo;
import cc.mrbird.febs.cos.service.ILogisticsInfoService;
import cc.mrbird.febs.cos.service.IOrderInfoService;
import cc.mrbird.febs.cos.service.IUserInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 订单管理 控制层
 *
 * @author FanK
 */
@RestController
@RequestMapping("/cos/order-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderInfoController {

    private final IOrderInfoService orderInfoService;

    private final IUserInfoService userInfoService;

    private final ILogisticsInfoService logisticsInfoService;

    /**
     * 分页获取订单信息
     *
     * @param page      分页对象
     * @param orderInfo 订单信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<OrderInfo> page, OrderInfo orderInfo) {
        return R.ok(orderInfoService.selectOrderPage(page, orderInfo));
    }

    /**
     * 订单发货
     *
     * @param orderId 订单ID
     * @return 结果
     */
    @GetMapping("/ship")
    public R orderShip(Integer orderId) {
        // 更新订单状态
        orderInfoService.update(Wrappers.<OrderInfo>lambdaUpdate().set(OrderInfo::getStatus, "4").eq(OrderInfo::getId, orderId));

        // 添加物流信息
        LogisticsInfo logistics = new LogisticsInfo();
        logistics.setOrderId(orderId);
        logistics.setRemark("商品已发货....");
        logistics.setCreateDate(DateUtil.formatDateTime(new Date()));
        logistics.setCurrentLogistics(1);
        return R.ok(logisticsInfoService.save(logistics));
    }

    /**
     * 查询订单信息详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(orderInfoService.selectOrderDetail(id));
    }

    /**
     * 查询订单信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(orderInfoService.list());
    }

    /**
     * 新增订单信息
     *
     * @param orderInfo 订单信息
     * @return 结果
     */
    @PostMapping
    public R save(OrderInfo orderInfo) {
        // 订单编号
        orderInfo.setCode("OD-" + System.currentTimeMillis());
        // 创建时间
        orderInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        // 获取用户信息
        UserInfo user = userInfoService.getById(orderInfo.getUserId());
        if (user != null) {
            orderInfo.setUserId(user.getId());
        }
        return R.ok(orderInfoService.save(orderInfo));
    }

    /**
     * 修改订单信息
     *
     * @param orderInfo 订单信息
     * @return 结果
     */
    @PutMapping
    public R edit(OrderInfo orderInfo) {
        return R.ok(orderInfoService.updateById(orderInfo));
    }

    /**
     * 删除订单信息
     *
     * @param ids ids
     * @return 订单信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(orderInfoService.removeByIds(ids));
    }
}
