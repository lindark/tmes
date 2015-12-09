package cc.jiuyi.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.dao.AbnormalDao;
import cc.jiuyi.dao.AbnormalLogDao;
import cc.jiuyi.dao.CreditCardDao;
import cc.jiuyi.entity.Abnormal;
import cc.jiuyi.entity.AbnormalLog;
import cc.jiuyi.entity.CreditCard;
import cc.jiuyi.service.AbnormalLogService;
import cc.jiuyi.service.AbnormalService;
import cc.jiuyi.service.CreditCardService;

/**
 * Service实现类 - 异常日志
 */

@Service
public class CreditCardServiceImpl extends BaseServiceImpl<CreditCard, String> implements CreditCardService{

	@Resource
	private CreditCardDao creditcarddao;
	
	@Resource
	public void setBaseDao(CreditCardDao creditcarddao) {
		super.setBaseDao(creditcarddao);
	}
	
	public CreditCard get(Date createdate){
		return creditcarddao.get(createdate);
		
	}
}
