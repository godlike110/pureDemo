package com.waimai.ops.controller;

import com.waimai.ops.model.AccessData;
import com.waimai.ops.utils.CommonUtil;

public class BaseController {

	protected AccessData getAccessData() {
		return (AccessData) CommonUtil.getThreadLocalValue();
	}

}
