package com.taotao.conent.service;
/**
 * 内容处理的接口
 * @author Administrator
 *
 */

import java.util.List;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

public interface ContentService {
	/**
	 * 插入内容表
	 * @param content
	 * @return
	 */
	public TaotaoResult saveContent(TbContent content);
	/**
	 * 根据内容分类的ID，查询其下的内容列表
	 * @param categoryId
	 * @return
	 */
	public List<TbContent> getContentListByCatId(Long categoryId);
}
