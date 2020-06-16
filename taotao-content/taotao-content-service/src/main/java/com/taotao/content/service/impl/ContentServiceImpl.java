package com.taotao.content.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.druid.support.json.JSONUtils;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.util.JsonUtils;
import com.taotao.conent.service.ContentService;
import com.taotao.content.jedis.JedisClient;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private JedisClient client;
	@Value("${CONTENT_KEY}")
	private String CONTENT_KEY;
	
	@Autowired
	private TbContentMapper mapper;
	@Override
	public TaotaoResult saveContent(TbContent content) {
		//1.注入mapper
		//2补全其他的属性
		content.setCreated(new Date());
		content.setUpdated(content.getCreated());
		//3插入内容表中
		mapper.insertSelective(content);
		
		
		//当添加内容的时候，需要清空此内容下所属的分类下的所有的缓存
		try {
			client.hdel(CONTENT_KEY, content.getCategoryId()+"");
			System.out.println("当插入时，清空缓存！");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return TaotaoResult.ok();
	}
	@Override
	public List<TbContent> getContentListByCatId(Long categoryId) {
		//添加缓存不能影响正常的业务逻辑
		
		try {
			//判断redis中是否有数据，如果有  直接从redis中获取 返回
			String jsonStr = client.hget(CONTENT_KEY, categoryId+"");//从redis数据库中获取的内容分类下的所有的内容
			//如果存在，证明有缓存
			if(StringUtils.isNotBlank(jsonStr)) {
				System.out.println("这里有缓存了");
				return JsonUtils.jsonToList(jsonStr, TbContent.class);
			}
		} catch (Exception e1) {
			
			e1.printStackTrace();
		}
		
		//1注入mapper
		//2创建example
		TbContentExample example = new TbContentExample();
		//3设置查询的条件
		example.createCriteria().andCategoryIdEqualTo(categoryId);//select * from tbcontent where category_id=1
		//4执行查询
		List<TbContent> list = mapper.selectByExample(example);
		//返回
		
		//将数据写入到redis数据库中
		
		//注入jedisclient
		//调用方法写入redis   key  calue
		try {
			System.out.println("没有缓存");
			client.hset(CONTENT_KEY, categoryId+"", JsonUtils.objectToJson(list));
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return list;
	}

}
