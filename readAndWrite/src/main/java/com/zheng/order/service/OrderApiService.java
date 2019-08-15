package com.zheng.order.service;

import com.zheng.constant.Constants;
import com.zheng.order.dao.OrderApiDAO;
import com.zheng.mybatis.plugin.PageInfo;
import com.zheng.util.StringUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OrderApiService {

	@Autowired
    OrderApiDAO orderApiDAO;
    public PageInfo findByOrders(Map<String, Object> params) {
        PageInfo pageInfo = new PageInfo();
        int pageSize = (Integer) params.get("pageSize");
        int page = (Integer) params.get("pageindex");
        params.put("startRowNum", (page -1) * pageSize);

        int totalCount = orderApiDAO.countByOrders(params);
        if (totalCount == 0) {
            return null;
        }

        List salesOrders = orderApiDAO.findByOrders(params);
        pageInfo.setCurrentPage(page);
        pageInfo.setShowCount(pageSize);
        pageInfo.setTotalPage((totalCount + pageSize - 1) / pageSize);
        pageInfo.setTotalCount(totalCount);
        pageInfo.setTotalResult(totalCount);
        pageInfo.setResult(salesOrders);
        return pageInfo;
    }
    
}
