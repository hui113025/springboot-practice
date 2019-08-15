package com.zheng.order.action;

import com.zheng.mybatis.plugin.PageInfo;
import com.zheng.order.service.OrderApiService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/order")
public class OrderApiAction {
    private static final Log logger = LogFactory.getLog(OrderApiAction.class);

    @Autowired
	OrderApiService orderApiService;

	@RequestMapping(value = "daas/v1", method = RequestMethod.POST)
	public PageInfo searchDaasServiceNow(@RequestBody Map<String, Object> paramJson) {
		PageInfo pageInfo = null;
		try {
			pageInfo = orderApiService.findByOrders(paramJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageInfo;
	}
}
