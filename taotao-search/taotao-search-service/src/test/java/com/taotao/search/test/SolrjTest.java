package com.taotao.search.test;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
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
	public void testquery() {
		
	}
}
