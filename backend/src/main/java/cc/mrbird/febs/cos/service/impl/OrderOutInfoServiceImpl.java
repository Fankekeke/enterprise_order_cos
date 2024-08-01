package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.OrderOutInfo;
import cc.mrbird.febs.cos.dao.OrderOutInfoMapper;
import cc.mrbird.febs.cos.service.IOrderOutInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 库房出库 实现层
 *
 * @author FanK
 */
@Service
public class OrderOutInfoServiceImpl extends ServiceImpl<OrderOutInfoMapper, OrderOutInfo> implements IOrderOutInfoService {

}
