package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.StoreRecordInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * 库房记录 mapper层
 *
 * @author FanK
 */
public interface StoreRecordInfoMapper extends BaseMapper<StoreRecordInfo> {

    /**
     * 分页获取库房记录信息
     *
     * @param page            分页对象
     * @param storeRecordInfo 库房记录信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectStoreRecordPage(Page<StoreRecordInfo> page, @Param("storeRecordInfo") StoreRecordInfo storeRecordInfo);
}
