package cc.mrbird.febs.cos.controller;

import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.AlipayBean;
import cc.mrbird.febs.cos.entity.OrderInfo;
import cc.mrbird.febs.cos.service.IOrderInfoService;
import cc.mrbird.febs.cos.service.PayService;
import com.alipay.api.AlipayApiException;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 支付宝支付接口 控制层
 *
 * @author FanK
 */
@RestController
@RequestMapping("/cos/pay")
public class PayController {

    @Resource
    private PayService payService;

    @Resource
    private IOrderInfoService orderInfoService;

    /**
     * 支付宝支付回调
     *
     * @param orderCode 订单编号
     * @return 结果
     */
    @PostMapping(value = "/rollback")
    public R alipayRollback(String orderCode) {
        // 获取订单信息
        OrderInfo orderInfo = orderInfoService.getOne(Wrappers.<OrderInfo>lambdaQuery().eq(OrderInfo::getCode, orderCode));
        if (orderInfo == null) {
            return R.ok(false);
        }
        if ("0".equals(orderInfo.getType())) {
            orderInfo.setStatus("3");
        } else if ("1".equals(orderInfo.getType()) && "0".equals(orderInfo.getStatus())) {
            orderInfo.setStatus("1");
        } else if ("1".equals(orderInfo.getType()) && "1".equals(orderInfo.getStatus())) {
            orderInfo.setStatus("3");
        }
        return R.ok(orderInfoService.updateById(orderInfo));
    }

    /**
     * 阿里支付
     *
     * @param outTradeNo  订单编号
     * @param subject     备注
     * @param totalAmount 付款金额
     * @param body        主体
     * @return 结果
     * @throws AlipayApiException 异常信息
     */
    @PostMapping(value = "/alipay")
    public R alipay(String outTradeNo, String subject, String totalAmount, String body) throws AlipayApiException {
        AlipayBean alipayBean = new AlipayBean();
        alipayBean.setOut_trade_no(outTradeNo);
        alipayBean.setSubject(subject);
        alipayBean.setTotal_amount(totalAmount);
        alipayBean.setBody(body);
        String result = payService.aliPay(alipayBean);
        return R.ok(result);
    }

}
