package cc.jiuyi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cc.jiuyi.action.cron.WorkingBillJob;
import cc.jiuyi.dao.PickDetailDao;
import cc.jiuyi.dao.WorkingInoutDao;
import cc.jiuyi.entity.Bom;
import cc.jiuyi.entity.HandOverProcess;
import cc.jiuyi.entity.Process;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.entity.WorkingInout;
import cc.jiuyi.service.BomService;
import cc.jiuyi.service.HandOverProcessService;
import cc.jiuyi.service.ProcessService;
import cc.jiuyi.service.WorkingInoutService;
import cc.jiuyi.util.ArithUtil;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * Service实现类 -投入产出表
 * @author Reece
 *
 */

@Service
public class WorkingInoutServiceImpl extends BaseServiceImpl<WorkingInout, String>implements WorkingInoutService{
	public static Logger log = Logger.getLogger(WorkingInoutServiceImpl.class);
	
	@Resource
	private WorkingInoutDao workingInoutDao;
	@Resource
	private ProcessService processservice;
	@Resource
	private BomService bomservice;
	@Resource
	private HandOverProcessService handoverprocessservice;
	
	@Resource
	public void setBaseDao(WorkingInoutDao workingInoutDao){
		super.setBaseDao(workingInoutDao);
	}
	
	@Override
	public boolean isExist(String workingBillId, String materialCode) {
		return workingInoutDao.isExist(workingBillId, materialCode);
	}

	@Override
	public WorkingInout findWorkingInout(String workingBillId,String materialCode) {
		return workingInoutDao.findWorkingInout(workingBillId, materialCode);
	}

	@Override
	public List<WorkingInout> findPagerByWorkingBillInout(
			HashMap<String, String> map) {
		
		
		return jsonstr;
	}

	/**
	 * 获取数据
	 * 首页中点击其中一个随工单后显示其投入产出数据
	 */
	public List<WorkingInout> findWbinoutput(String wbid)
	{
		return this.workingInoutDao.findWbinoutput(wbid);
	}
}
