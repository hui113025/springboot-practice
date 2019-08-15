package com.zheng.constant;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Constants {
	private static final Log logger = LogFactory.getLog(Constants.class);

	public enum OrderStatus {
		Cancelled(1, "Cancelled"),
		Order_Received(2 ,"Order Received"),
		Released_to_Manufacturing(3 ,"Released to Manufacturing"),
		Non_shipppable(4 ,"Non shipppable"),
		Shipped(5 ,"Shipped"),
		Delivered(6 ,"Delivered"),
		Paritally_Shipped(7 ,"Paritally Shipped"),
		Partially_Delivered(8 ,"Partially Delivered"),
		Order_Processing(9 ,"Order Processing"),
		Customer_Action(10 ,"Customer Action");

		private int key;
		private String name;

		OrderStatus(int key, String name) {
			this.key = key;
			this.name = name;
		}

		public int getKey() {
			return key;
		}

		public void setKey(int key) {
			this.key = key;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

        public static String getEnumName(int key) {
            for (OrderStatus o : OrderStatus.values()) {
                if (o.getKey() == key) {
                    return o.getName();
                }
            }
            return null;
        }
	}

}
