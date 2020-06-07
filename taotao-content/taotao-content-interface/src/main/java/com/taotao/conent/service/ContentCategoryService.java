package com.taotao.conent.service;

import java.util.List;

import com.taotao.common.pojo.EasyUITreeNode;

public interface ContentCategoryService {
	//通过子节点的id查询该节点的子节点列表
	public List<EasyUITreeNode> getContentCategoryList(Long parentId);
}
