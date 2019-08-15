package com.zheng.order.dao;


import com.zheng.order.entity.DaasSnDTO;

import java.util.List;
import java.util.Map;

/**
 * ORDER SERVICE API FOR 
 * ORDER STATUS,Delivery/Shipping STATUS
 * Deliver Tracking Package
 * CTO/Feature Information
 *
 */
public interface OrderApiDAO {

	public int countByOrders(Map params);

	public List<DaasSnDTO> findByOrders(Map params);
}
