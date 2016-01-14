package cc.jiuyi.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.dao.HandOverDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.HandOver;
import cc.jiuyi.entity.HandOverProcess;
import cc.jiuyi.entity.OddHandOver;
import cc.jiuyi.service.HandOverProcessService;
import cc.jiuyi.service.HandOverService;
import cc.jiuyi.service.OddHandOverService;

/**
 * Service实现类 - 品牌
 */

@Service
public class HandOverServiceImpl extends BaseServiceImpl<HandOver, String> implements HandOverService {

	@Resource
	private HandOverProcessService handoverprocessservice;
	@Resource
	private OddHandOverService oddHandOverService;
	@Resource
	public void setBaseDao(HandOverDao handoverdao) {
		super.setBaseDao(handoverdao);
	}

	@Override
	public void saveandgx(Admin admin,List<HandOverProcess> handoverprocessList,List<OddHandOver> oddHandOverList) {
		HandOver handover = new HandOver();
		handover.setState("2");
		handover.setSubmitadmin(admin);
		String id = super.save(handover);
		for(HandOverProcess handoverprocess : handoverprocessList){
			handover.setId(id);
			handoverprocess.setHandover(handover);
			handoverprocessservice.update(handoverprocess);
		}
		for(OddHandOver oddHandOver : oddHandOverList){
			handover.setId(id);
			oddHandOver.setHandOver(handover);
			oddHandOverService.update(oddHandOver);
		}
		
		
		
	}
	
	public void updateHand(List<HandOverProcess> handoverprocessList,String mblnr1,String handoverId,Admin admin){
		
		for(HandOverProcess handoverprocess : handoverprocessList){
			handoverprocess.setMblnr(mblnr1);
			handoverprocessservice.update(handoverprocess);
		}
		
	}
	




}