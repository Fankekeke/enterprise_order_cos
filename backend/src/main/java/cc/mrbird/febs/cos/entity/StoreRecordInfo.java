package cc.mrbird.febs.cos.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 库房记录
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class StoreRecordInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 商品ID
     */
    private String commodityCode;

    /**
     * 数量
     */
    private Integer num;

    /**
     * 类型（0.库存 1.入库 2.出库）
     */
    private String type;

    /**
     * 出库或入库单号
     */
    private String orderNumber;

    /**
     * 出库或入库价格
     */
    private BigDecimal price;

    /**
     * 创建时间
     */
    private String createDate;


}
