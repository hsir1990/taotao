package com.taotao.search.mapper;
/**
 * 定义Mapper 关联查询3张表  查询搜索时的商品数据
 * @author Administrator
 *
 */

import java.util.List;

import com.taotao.common.pojo.SearchItem;

public interface SearchItemMapper {
	//查询所有的商品的数据
	public List<SearchItem> getSearchItemList();
}
