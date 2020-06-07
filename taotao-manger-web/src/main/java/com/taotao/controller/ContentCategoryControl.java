package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.conent.service.ContentCategoryService;


/**
 * 内容分类的处理controller
 * @author Hsir1990
 *
 */
@Controller
public class ContentCategoryControl {
	@Autowired
	private ContentCategoryService service;
	/**
	 * url : '/content/category/list',
		animate: true,
		method : "GET",
		参数: id
	 */
	@RequestMapping(value="/content/category/list",method=RequestMethod.GET)
	@ResponseBody
	public List<EasyUITreeNode> getContentCategoryList(@RequestParam(value="id",defaultValue="0") Long parentId){
		//1.引入服务
		//2.注入服务
		//3调用
		return service.getContentCategoryList(parentId);
	}}
