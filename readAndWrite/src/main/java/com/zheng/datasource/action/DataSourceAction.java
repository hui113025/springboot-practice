package com.zheng.datasource.action;

import com.alibaba.fastjson.JSONObject;
import com.zheng.datasource.DatabaseContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/datasource")
public class DataSourceAction {

	@RequestMapping(value = "/setup")
	@ResponseBody
	public String setup(HttpServletRequest request) {
		JSONObject ret=new JSONObject();
		String datasource=request.getParameter("datasource");
		DatabaseContextHolder.setCustomerType(datasource);
		ret.put("result","datasource setup success!");
		return ret.toJSONString();
	}
	
}
