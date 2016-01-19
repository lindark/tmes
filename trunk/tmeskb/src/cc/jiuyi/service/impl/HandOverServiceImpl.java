package cc.jiuyi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.dao.HandOverDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.HandOver;
import cc.jiuyi.entity.HandOverProcess;
import cc.jiuyi.entity.OddHandOver;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.HandOverProcessService;
import cc.jiuyi.service.HandOverService;
import cc.jiuyi.service.OddHandOverService;
import cc.jiuyi.service.WorkingBillService;

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
	private WorkingBillService workingbillservice;
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
			OddHandOver oddHandOver = oddHandOverService.get(handoverprocess.getId());
			if(oddHandOver!=null){
				oddHandOver.setMblnr(mblnr1);
				oddHandOverService.update(oddHandOver);
			}
			
		}
		
	}
	
	@Override
	public void update(HandOver entity) {
		if(entity.getState().equals("3")){//刷卡确认,表示此处需要处理投入产出
			List<HandOver> handoverlist = new ArrayList<HandOver>();
			handoverlist.add(entity);
			updateWorkingInoutCalculate(handoverlist, null);
		}
		super.update(entity);
	}

	@Override
	public void updateWorkingInoutCalculate(List<HandOver> paramaterList,
			HashMap<String, Object> maps) {
		for(int i=0 ;i< paramaterList.size();i++){
			HandOver handover = paramaterList.get(i);
			List<OddHandOver> list = new ArrayList<OddHandOver>(handover.getOddHandOver());
			for(OddHandOver oddhandover : list){
				Double actualbommount = oddhandover.getActualBomMount();//正常零头数
				Double unbommount = oddhandover.getUnBomMount();//异常零头数
				String workingbillCode = oddhandover.getAfterWorkingCode();
				WorkingBill workingbill = workingbillservice.get("workingBillCode",workingbillCode);
				workingbill.setAfteroddamount(actualbommount);
				workingbill.setAfterunoddamount(unbommount);
				workingbillservice.update(workingbill);
			}
		}
		
		
	}

	




}