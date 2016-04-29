package cc.jiuyi.action.admin;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.quartz.Scheduler;
import org.quartz.impl.StdScheduler;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cc.jiuyi.action.cron.QuartzManager;
import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.entity.FactoryUnit;
import cc.jiuyi.entity.FactoryUnitSyn;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.FactoryUnitSynService;
import cc.jiuyi.util.QuartzManagerUtil;
import cc.jiuyi.util.SpringUtil;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 后台Action类-单元同步管理
 */

@ParentPackage("admin")
public class FactoryUnitSynAction extends BaseAdminAction  implements ApplicationContextAware{
	private static final long serialVersionUID = 6239172315637426392L;
	private static ApplicationContext context = null;
	private FactoryUnitSyn factoryUnitSyn;
	
	
	@Resource
	private FactoryUnitSynService factoryUnitSynService;
	@Resource
	private DictService dictService;
	
	public String list(){
		return LIST;
	}

	public String ajlist(){


		HashMap<String, String> map = new HashMap<String, String>();

		if (pager==null)
		{
			pager=new Pager();
		}
		if(pager.getOrderBy()==null||"".equals(pager.getOrderBy()))
		{
			pager.setOrderType(OrderType.desc);
			pager.setOrderBy("modifyDate");
		}
		if (pager.is_search() == true && filters != null)
		{// 需要查询条件
			JSONObject filt = JSONObject.fromObject(filters);
			Pager pager1 = new Pager();
			Map m = new HashMap();
			m.put("rules", jqGridSearchDetailTo.class);
			pager1 = (Pager) JSONObject.toBean(filt, Pager.class, m);
			pager.setRules(pager1.getRules());
			pager.setGroupOp(pager1.getGroupOp());
		}

		if (pager.is_search() == true && Param != null)
		{// 普通搜索功能
			// 此处处理普通查询结果 Param 是表单提交过来的json 字符串,进行处理。封装到后台执行
			JSONObject obj = JSONObject.fromObject(Param);
			if (obj.get("factoryUnitCode") != null)
			{
				String factoryUnitCode = obj.getString("factoryUnitCode").toString();
				map.put("factoryUnitCode", factoryUnitCode);
			}
			if (obj.get("factoryUnitName") != null)
			{
				String factoryUnitName = obj.getString("factoryUnitName").toString();
				map.put("factoryUnitName", factoryUnitName);
			}
		}

		pager = factoryUnitSynService.getFactoryUnitSynPager(pager, map);
		List<FactoryUnitSyn> factoryUnitSynList = pager.getList();
		List<FactoryUnitSyn> lst = new ArrayList<FactoryUnitSyn>();
		for (int i = 0; i < factoryUnitSynList.size(); i++)
		{
			FactoryUnitSyn factoryUnitSyn = (FactoryUnitSyn) factoryUnitSynList.get(i);
			factoryUnitSyn.setStateRemark(ThinkWayUtil.getDictValueByDictKey(dictService, "factoryUnitSynState", factoryUnitSyn.getState()));
			lst.add(factoryUnitSyn);
		}
		pager.setList(lst);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(FactoryUnit.class));// 排除有关联关系的属性字段
		JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
		System.out.println(jsonArray.get(0).toString());
		return ajaxJson(jsonArray.get(0).toString());

	
	}
	public String add(){
		return INPUT;
	}
	public String save() throws ParseException, Exception{
		factoryUnitSyn.setIsDel("N");
		factoryUnitSyn.setTriggername(factoryUnitSyn.getFactoryUnitCode());
		//factoryUnitSyn.setCronexpression("0 0/5 * * * ?");
		factoryUnitSyn.setJobdetailname(factoryUnitSyn.getFactoryUnitCode());
		factoryUnitSyn.setTargetobject("cc.jiuyi.action.cron.WorkingBillJobAll");
		factoryUnitSyn.setMethodname("start1");
		String[]  arguments = {factoryUnitSyn.getFactoryUnitCode()};
		factoryUnitSyn.setMethodArguments(arguments);
		factoryUnitSyn.setConcurrent("1");
		//factoryUnitSyn.setState("1");
		factoryUnitSyn.setReadme("readme");
		factoryUnitSyn.setIsspringbean("0");
		factoryUnitSynService.save(factoryUnitSyn);
		reshSyn();//重启quartz 任务
		redirectionUrl="factory_unit_syn!list.action";
		return SUCCESS;	
	}
	public String edit(){
		factoryUnitSyn  = factoryUnitSynService.get(id);
		return INPUT;
	}
	public String update() throws ParseException, Exception{
		FactoryUnitSyn  fus =  factoryUnitSynService.get(id);
		fus.setState(factoryUnitSyn.getState());
		fus.setCronexpression(factoryUnitSyn.getCronexpression());
		fus.setFactoryUnitCode(factoryUnitSyn.getFactoryUnitCode());
		fus.setFactoryUnitName(factoryUnitSyn.getFactoryUnitName());
		factoryUnitSynService.update(fus);
		reshSyn();//重启quartz 任务
		redirectionUrl="factory_unit_syn!list.action";
		return SUCCESS;
	}
	public String delete(){
		FactoryUnitSyn funs = factoryUnitSynService.get(id);
		funs.setIsDel("Y");
		funs.setState("2");
		factoryUnitSynService.update(funs);
		//factoryUnitSynService.delete(funs);
		redirectionUrl = "factory_unit_syn!list.action";
		return ajaxJsonSuccessMessage("删除成功！");
	}

	public List<FactoryUnitSyn> getAll(){
		List<FactoryUnitSyn> factoryUnitSynList = factoryUnitSynService.getAll();
		return  factoryUnitSynList;
	 }
	
	public String checkFactoryUnitCode(){
		String factoryUnitCode=factoryUnitSyn.getFactoryUnitCode();
		FactoryUnitSyn funs = factoryUnitSynService.get("factoryUnitCode", factoryUnitCode);
		if(funs==null){
			return ajaxText("true");
		}else{
			if(id!=null && !"".equals(id)){
				if(funs.getId().equals(id)){
					return ajaxText("true");
				}else{
					return ajaxText("false");
				}
			}else{
				return ajaxText("false");
			}
		}
	}
	
	
	public void reshSyn() throws ParseException, Exception{
		Scheduler scheduler = (StdScheduler)SpringUtil.getBean("schedulerManager");
		//Scheduler scheduler = schedulerFactoryBean.getScheduler();
		QuartzManager q = new QuartzManager();
		q.resh(scheduler);

        // 读取容器中的QUARTZ总管类
       /* Scheduler scheduler = (StdScheduler)SpringUtil.getBean("schedulerManager");
        //停止正在运行的JOB
        List<FactoryUnitSyn> FactoryUnitSynList = factoryUnitSynService.getAll();
        for(FactoryUnitSyn f : FactoryUnitSynList){
        	 scheduler.interrupt(f.getTriggername(), Scheduler.DEFAULT_GROUP);
        }
        // 重启任务
        scheduler.resumeTrigger("quartzManagerTrigger", Scheduler.DEFAULT_GROUP);*/
		//QuartzManagerUtil.startJobs();
	}
	public FactoryUnitSyn getFactoryUnitSyn() {
		return factoryUnitSyn;
	}

	public void setFactoryUnitSyn(FactoryUnitSyn factoryUnitSyn) {
		this.factoryUnitSyn = factoryUnitSyn;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		context = applicationContext;
		
	}
	public synchronized static Object getBean(String beanName) {
		return context.getBean(beanName);
	}
}
