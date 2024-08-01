package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.OrderPutInfo;
import cc.mrbird.febs.cos.dao.OrderPutInfoMapper;
import cc.mrbird.febs.cos.service.IOrderPutInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 库房入库 实现层
 *
 * @author FanK
 */
@Service
public class OrderPutInfoServiceImpl extends ServiceImpl<OrderPutInfoMapper, OrderPutInfo> implements IOrderPutInfoService {

}
