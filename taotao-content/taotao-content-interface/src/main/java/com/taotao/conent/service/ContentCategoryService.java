package com.taotao.conent.service;

import java.util.List;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;

public interface ContentCategoryService {
	//通过子节点的id查询该节点的子节点列表
	public List<EasyUITreeNode> getContentCategoryList(Long parentId);
	//增加内容分类
	/**
	 * 
	 * @param parentId 父节点的id
	 * @param name	新增节点的名称
	 * @return
	 */
	public TaotaoResult createContentCategory(Long parentId, String name);
}
