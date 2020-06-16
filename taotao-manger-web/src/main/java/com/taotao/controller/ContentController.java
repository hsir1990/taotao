package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.conent.service.ContentService;
import com.taotao.pojo.TbContent;

/**
 * 处理内容相关
 * @author Administrator
 *
 */
@Controller
public class ContentController {
	@Autowired
	private ContentService contentservice;
	
	@RequestMapping(value="/content/save",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult saveContent(TbContent tConntent) {
		//1引入服务
		//2注入服务
		//3调用
		return contentservice.saveContent(tConntent);
		
	}
}
