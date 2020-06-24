package com.taotao.search.test;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrjTest {
	
	@Test
	public void add() throws Exception{
		//1创建solrserver  创建连接  需要指定地址
		SolrServer solrService = new HttpSolrServer("http://47.92.48.144:8080/solr");
		//2创建solrinputdocument
		SolrInputDocument document = new SolrInputDocument();
		//3向文档中添加域
		document.addField("id", "test001");
		document.addField("item_title", "这是一个测试");
		//4将文档提交到索引库中
		solrService.add(document);
		//5提交
		solrService.commit();
	}
	
	@Test
	public void testquery() throws Exception{
		//1创建solrserver对象
		SolrServer solrService = new HttpSolrServer("http://47.92.48.144:8080/solr");
		//2创建solrquery对象，设置各种过滤条件，主查询条件  排序
		SolrQuery query = new SolrQuery();
		//3设置查询条件
		query.setQuery("mingzi");
		query.addFilterQuery("item_price:[0 TO 30000000]");
		query.set("df", "item_title");
		//4执行查询
		QueryResponse response = solrService.query(query);
		//5获取结果集
		SolrDocumentList results = response.getResults();
		System.out.println("查询的总的记录数"+results.getNumFound());
		//6遍历结果集  打印
		for(SolrDocument solrDocument:results) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("item_title"));
		}
	}
}
