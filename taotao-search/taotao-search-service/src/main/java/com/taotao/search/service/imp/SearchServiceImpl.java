package com.taotao.search.service.imp;

import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.mapper.SearchItemMapper;
import com.taotao.search.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {
	@Autowired
	private SearchItemMapper mapper;
	@Autowired
	private SolrServer solrserver;
	
	@Override
	public TaotaoResult importAllSearchItems() throws Exception{
		//1注入mapper
		//2调用mapper的方法，查询所有的商品的数据
		List<SearchItem> searchItemList = mapper.getSearchItemList();
		//3通过solrj将数据写入到索引库中
			//3.1穿件httpsolrserver
			//3.2创建solrinputdocument
		for(SearchItem searchItem : searchItemList) {
			SolrInputDocument document = new SolrInputDocument();
			document.addField("id", searchItem.getId().toString());//这里是字符串需要转换
			document.addField("item_title", searchItem.getTitle());
			document.addField("item_sell_point", searchItem.getSell_point());
			document.addField("item_price", searchItem.getPrice());
			document.addField("item_image", searchItem.getImage());
			document.addField("item_category_name", searchItem.getCategory_name());
			document.addField("item_desc", searchItem.getItem_desc());
			//添加索引库
			solrserver.add(document);
		}
		//提交
		solrserver.commit();
		return null;
	}

}
