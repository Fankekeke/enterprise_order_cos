package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.CommodityInfo;
import cc.mrbird.febs.cos.dao.CommodityInfoMapper;
import cc.mrbird.febs.cos.entity.CommodityRebateInfo;
import cc.mrbird.febs.cos.entity.UserInfo;
import cc.mrbird.febs.cos.service.ICommodityInfoService;
import cc.mrbird.febs.cos.service.ICommodityRebateInfoService;
import cc.mrbird.febs.cos.service.IUserInfoService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 商品管理 实现层
 *
 * @author FanK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommodityInfoServiceImpl extends ServiceImpl<CommodityInfoMapper, CommodityInfo> implements ICommodityInfoService {

    private final ICommodityRebateInfoService commodityRebateInfoService;

    private final IUserInfoService userInfoService;

    /**
     * 分页获取商品信息
     *
     * @param page          分页对象
     * @param commodityInfo 商品信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectCommodityPage(Page<CommodityInfo> page, CommodityInfo commodityInfo) {
        return baseMapper.selectCommodityPage(page, commodityInfo);
    }

    /**
     * 根据用户获取商品信息【动态价格】
     *
     * @param commodityInfo 商品信息
     * @return 结果
     */
    @Override
    public List<CommodityInfo> selectCommodityByUser(CommodityInfo commodityInfo) {
        // 商品信息
        List<CommodityInfo> commodityInfoList = this.list(Wrappers.<CommodityInfo>lambdaQuery()
                .like(StrUtil.isNotEmpty(commodityInfo.getName()), CommodityInfo::getName, commodityInfo.getName()));

        // 商品折扣
        List<CommodityRebateInfo> rebateInfoList = commodityRebateInfoService.list();
        Map<Integer, CommodityRebateInfo> rebateMap = rebateInfoList.stream().collect(Collectors.toMap(CommodityRebateInfo::getCommodityId, e -> e));

        // 获取用户信息计算注册时常
        UserInfo user = userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUserId, commodityInfo.getUserId()));
        long month = DateUtil.betweenMonth(DateUtil.parseDate(user.getCreateDate()), new Date(), true);

        for (CommodityInfo commodity : commodityInfoList) {
            // 获取此商品折扣信息
            CommodityRebateInfo rebateInfo = rebateMap.get(commodity.getId());
            if (rebateInfo == null) {
                continue;
            }
            if (month >= rebateInfo.getLowRestriction()) {
                commodity.setSellPrice(rebateInfo.getLowRate());
            } else if (month >= rebateInfo.getSpecialRestriction()) {
                commodity.setSellPrice(rebateInfo.getSpecialPrice());
            }
        }
        return commodityInfoList;
    }
}
