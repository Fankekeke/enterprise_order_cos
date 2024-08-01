package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.CommodityRebateInfo;
import cc.mrbird.febs.cos.dao.CommodityRebateInfoMapper;
import cc.mrbird.febs.cos.service.ICommodityRebateInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * 商品折扣设置 实现层
 *
 * @author FanK
 */
@Service
public class CommodityRebateInfoServiceImpl extends ServiceImpl<CommodityRebateInfoMapper, CommodityRebateInfo> implements ICommodityRebateInfoService {

    /**
     * 分页获取商品信息
     *
     * @param page                分页对象
     * @param commodityRebateInfo 商品信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectRebatePage(Page<CommodityRebateInfo> page, CommodityRebateInfo commodityRebateInfo) {
        return baseMapper.selectRebatePage(page, commodityRebateInfo);
    }
}
