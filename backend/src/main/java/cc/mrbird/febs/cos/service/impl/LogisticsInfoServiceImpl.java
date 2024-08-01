package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.LogisticsInfo;
import cc.mrbird.febs.cos.dao.LogisticsInfoMapper;
import cc.mrbird.febs.cos.service.ILogisticsInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 配送物流信息 实现层
 *
 * @author FanK
 */
@Service
public class LogisticsInfoServiceImpl extends ServiceImpl<LogisticsInfoMapper, LogisticsInfo> implements ILogisticsInfoService {

}
