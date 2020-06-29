package com.taotao.search.test;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrCloudTest {
	@Test
	public void testAdd() throws SolrServerException, IOException {
		//1创建solrserver 集群的实现类
		//指定zookeeper集群的节点列表字符串
		CloudSolrServer cloudSolrServer = new CloudSolrServer("47.92.48.144:2181,47.92.48.144:2182,47.92.48.144:2183");
		//2设置默认的搜索的collection 默认的索引库（不是core所对应的，是指整个collection索引集合）
		cloudSolrServer.setDefaultCollection("collection2");
		//3创建solrinputdocument对象
		SolrInputDocument document = new SolrInputDocument();
		//4添加域到文档
		document.addField("id", "testclodid");
		document.addField("item_title", "今天天气好");
		//5将文档提交到索引库中
		cloudSolrServer.add(document);
		//6提交
		cloudSolrServer.commit();
	}
}
