package cc.jiuyi.action.admin;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.struts2.convention.annotation.ParentPackage;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Cause;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.Sample;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.CauseService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.SampleService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 抽检
 * @author gaoyf
 *
 */
@ParentPackage("admin")
public class SampleAction extends BaseAdminAction
{
	private static final long serialVersionUID = 7706687725550333117L;
	
	/**======================对象，变量，接口 start=================================*/
	/**
	 * 对象或变量
	 */
	private Sample sample;//抽检
	private String wbId;//随工单id
	private WorkingBill workingbill;//随工单
	private String my_id;//自定义ID
	private List<Cause> list_cause;//缺陷
	private List<Dict> list_dict;//抽检类型
	private String info;
	private String info2;
	/**
	 * service接口
	 */
	@Resource
	private SampleService sampleService;//抽检
	@Resource
	private WorkingBillService workingBillService;//随工单
	@Resource
	private DictService dictService;//字典表
	@Resource
	private CauseService causeService;//缺陷表
	@Resource
	private AdminService adminService;
	
	/**======================end 对象，变量，接口=*=================================*/
	
	/**======================方法start==========================================*/
	
	/**
	 * 查询选择的那条随工单
	 */
	public String list()
	{
		this.workingbill=this.workingBillService.get(wbId);
		return LIST;
	}
	
	/**
	 * 增加前
	 * 1.需要根据随工单id查询其信息
	 */
	public String add()
	{
		this.workingbill=this.workingBillService.get(wbId);
		this.list_cause=this.causeService.getBySample("1");//获取缺陷表中关于抽检的内容
		this.list_dict=this.dictService.getState("sampleType");//获取缺陷类型
		return INPUT;
	}
	/**
	 * 增加
	 */
	public String save()
	{
		Admin admin=this.adminService.getLoginAdmin();
		//保存抽检单信息:抽检单，缺陷ID，缺陷数量，1保存/2确认
		this.sampleService.saveInfo(sample,info,info2,my_id,admin);
		this.redirectionUrl="sample!list.action?wbId="+this.sample.getWorkingBill().getId();
		return SUCCESS;
	}
	
	/**
	 * jqGrid查询
	 * sample_list.ftl页面
	 */
	public String ajlist()
	{
		try
		{
			if(pager==null)
			{
				pager=new Pager();
				pager.setOrderType(OrderType.asc);
				pager.setOrderBy("orderList");
			}
			//jqGrid条件查询
			if(pager.is_search()==true&&filters!=null)
			{
				JSONObject filt=JSONObject.fromObject(filters);
				Pager pg1=new Pager();
				Map<Object,Object> mp=new HashMap<Object, Object>();
				mp.put("rules", jqGridSearchDetailTo.class);
				pg1=(Pager) JSONObject.toBean(filt,Pager.class,mp);
				pager.setRules(pg1.getRules());
				pager.setGroupOp(pg1.getGroupOp());
			}
			pager=this.sampleService.getSamplePager(pager);
			@SuppressWarnings("unchecked")
			List<Sample>samplelist=pager.getList();
			List<Sample>samplelist2=new ArrayList<Sample>();
			for(int i=0;i<samplelist.size();i++)
			{
				Sample s1=samplelist.get(i);
				s1.setXstate(ThinkWayUtil.getDictValueByDictKey(dictService, "sampleState", s1.getState()));//状态描述--页面显示
				s1.setXsampler(s1.getSampler().getName());//抽检人xsampler
				if(s1.getComfirmation()!=null)
				{
					s1.setXcomfirmation(s1.getComfirmation().getName());//确认人xcomfirmation
				}
				System.out.println(s1.getCreateDate());
				s1.setXproductnum(s1.getWorkingBill().getMatnr());//产品编号xproductnum
				s1.setXproductname(s1.getWorkingBill().getMaktx());//产品名称xproductnames
				s1.setXsampletype(ThinkWayUtil.getDictValueByDictKey(dictService, "sampleType", s1.getSampleType()));//抽检类型xsampletype
				samplelist2.add(s1);
			}
			pager.setList(samplelist2);
			JsonConfig jsonConfig=new JsonConfig();
			jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
			jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Sample.class));//排除有关联关系的属性字段  
			JSONArray jsonArray=JSONArray.fromObject(pager,jsonConfig);
			return this.ajaxJson(jsonArray.get(0).toString());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * my_id=1--确认操作
	 * my_id=2--撤销操作
	 */
	public String myaction()
	{
		return null;
	}
	/**==========================end 方法========================================*/
	
	/**=========================="get/set"  start===============================*/
	
	public Sample getSample()
	{
		return sample;
	}
	public void setSample(Sample sample)
	{
		this.sample = sample;
	}

	public WorkingBill getWorkingbill()
	{
		return workingbill;
	}

	public void setWorkingbill(WorkingBill workingbill)
	{
		this.workingbill = workingbill;
	}

	public String getWbId()
	{
		return wbId;
	}

	public void setWbId(String wbId)
	{
		this.wbId = wbId;
	}

	public String getMy_id()
	{
		return my_id;
	}

	public void setMy_id(String my_id)
	{
		this.my_id = my_id;
	}

	public List<Cause> getList_cause()
	{
		return list_cause;
	}

	public void setList_cause(List<Cause> list_cause)
	{
		this.list_cause = list_cause;
	}

	public List<Dict> getList_dict()
	{
		return list_dict;
	}

	public void setList_dict(List<Dict> list_dict)
	{
		this.list_dict = list_dict;
	}

	public String getInfo()
	{
		return info;
	}

	public void setInfo(String info)
	{
		this.info = info;
	}

	public String getInfo2()
	{
		return info2;
	}

	public void setInfo2(String info2)
	{
		this.info2 = info2;
	}
	
	/**==========================end "get/set"====================================*/
}
