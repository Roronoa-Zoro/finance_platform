package com.lp.finance.platform.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.framework.service.impl.SuperServiceImpl;
import com.lp.finance.platform.dao.FinanceProductMapper;
import com.lp.finance.platform.entity.FinanceProductEntity;
import com.lp.finance.platform.service.FinanceProductService;

@Service
public class FinanceProductServiceImpl extends SuperServiceImpl<FinanceProductMapper, FinanceProductEntity> 
			implements FinanceProductService {


}
