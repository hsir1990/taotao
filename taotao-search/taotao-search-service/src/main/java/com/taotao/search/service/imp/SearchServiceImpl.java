package com.taotao.search.service.imp;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.SearchResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.dao.SearchDao;
import com.taotao.search.mapper.SearchItemMapper;
import com.taotao.search.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {
	@Autowired
	private SearchItemMapper mapper;
	@Autowired
	private SolrServer solrserver;
	@Autowired
	private SearchDao searchdao;
	
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

	@Override
	public SearchResult search(String queryString, Integer page, Integer rows) throws Exception {
		//1创建solrquery对象
		SolrQuery query = new SolrQuery();
		//2设置主查询条件
		if(StringUtils.isNotBlank(queryString)) {
			query.setQuery(queryString);
		}else {
			query.setQuery("*.*");
		}
		//2.1设置过滤条件  设置分页
		if(page==null)page=1;
		if(rows==null)rows=60;
		query.setStart((page-1)*rows);
		query.setRows(rows);
		//2.2设置默认的搜索域
		query.setHighlight(true);
		query.setHighlightSimplePre("<em style=\"color:red\">");
		query.setHighlightSimplePost("</em>");   
		query.addHighlightField("item_title");//设置高亮显示区域
		//3调用dao的方法，返回的是SearchResult只包含总记录数和商品的列表
		SearchResult search = searchdao.search(query);
		//4设置SearchResult的总页数
		long pageCount=0L;
		pageCount=search.getRecordCount()/rows;
		if(search.getRecordCount()%rows > 0) {
			pageCount++;
		}
		search.setPageCount(pageCount);
		//5返回
		return search;
	}

}
