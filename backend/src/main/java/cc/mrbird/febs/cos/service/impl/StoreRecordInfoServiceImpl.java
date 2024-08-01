package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.StoreRecordInfo;
import cc.mrbird.febs.cos.dao.StoreRecordInfoMapper;
import cc.mrbird.febs.cos.service.IStoreRecordInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * 库房记录 实现层
 *
 * @author FanK
 */
@Service
public class StoreRecordInfoServiceImpl extends ServiceImpl<StoreRecordInfoMapper, StoreRecordInfo> implements IStoreRecordInfoService {

    /**
     * 分页获取库房记录信息
     *
     * @param page            分页对象
     * @param storeRecordInfo 库房记录信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectStoreRecordPage(Page<StoreRecordInfo> page, StoreRecordInfo storeRecordInfo) {
        return baseMapper.selectStoreRecordPage(page, storeRecordInfo);
    }
}
