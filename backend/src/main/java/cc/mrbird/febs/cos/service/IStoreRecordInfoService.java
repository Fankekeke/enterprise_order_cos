package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.StoreRecordInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * 库房记录 service层
 *
 * @author FanK
 */
public interface IStoreRecordInfoService extends IService<StoreRecordInfo> {

    /**
     * 分页获取库房记录信息
     *
     * @param page            分页对象
     * @param storeRecordInfo 库房记录信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectStoreRecordPage(Page<StoreRecordInfo> page, StoreRecordInfo storeRecordInfo);
}
