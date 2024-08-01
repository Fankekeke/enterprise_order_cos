package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.StockAlertInfo;
import cc.mrbird.febs.cos.dao.StockAlertInfoMapper;
import cc.mrbird.febs.cos.service.IStockAlertInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 库存预警 实现层
 *
 * @author FanK
 */
@Service
public class StockAlertInfoServiceImpl extends ServiceImpl<StockAlertInfoMapper, StockAlertInfo> implements IStockAlertInfoService {

}
