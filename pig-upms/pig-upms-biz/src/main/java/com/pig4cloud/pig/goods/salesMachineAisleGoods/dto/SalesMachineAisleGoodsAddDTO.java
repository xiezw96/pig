package com.pig4cloud.pig.goods.salesMachineAisleGoods.dto;

import com.pig4cloud.pig.order.order.dto.GoodsDetailDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * <p>Title: SalesMachineAisleGoodsAddDTO</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年06月18日
 * @since 1.8
 */
@Data
@ApiModel("货道补货保存实体")
public class SalesMachineAisleGoodsAddDTO {
	private Integer id;
	// 修改时添加数量
	private Integer num;
	List<GoodsDetailDTO> goods;
}
