package com.taotao.search.service;

import com.taotao.common.pojo.TaotaoResult;

public interface SearchService {
	//导入所有的商品数据到索引库中
	public TaotaoResult importAllSearchItems() throws Exception;
}
