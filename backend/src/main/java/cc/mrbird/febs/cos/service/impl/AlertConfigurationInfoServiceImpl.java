package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.AlertConfigurationInfo;
import cc.mrbird.febs.cos.dao.AlertConfigurationInfoMapper;
import cc.mrbird.febs.cos.service.IAlertConfigurationInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * 预警配置 实现层
 *
 * @author FanK
 */
@Service
public class AlertConfigurationInfoServiceImpl extends ServiceImpl<AlertConfigurationInfoMapper, AlertConfigurationInfo> implements IAlertConfigurationInfoService {

    /**
     * 分页获取报警配置
     *
     * @param page                   分页对象
     * @param alertConfigurationInfo 报警配置
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectAlertConfigurationPage(Page<AlertConfigurationInfo> page, AlertConfigurationInfo alertConfigurationInfo) {
        return baseMapper.selectAlertConfigurationPage(page, alertConfigurationInfo);
    }
}
