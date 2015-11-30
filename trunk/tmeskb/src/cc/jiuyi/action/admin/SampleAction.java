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
import cc.jiuyi.entity.SampleRecord;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.CauseService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.SampleRecordService;
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
	private Admin admin;
	private String add;
	private String edit;
	private String show;
	private List<SampleRecord>list_samrecord;//缺陷记录
	private String sampletype;//抽检类型
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
	@Resource
	private SampleRecordService srService;//缺陷记录
	/**======================end 对象，变量，接口=*=================================*/
	
	/**======================方法start==========================================*/
	
	/**
	 * 查询选择的那条随工单
	 */
	public String list()
	{
		admin = adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());
		System.out.println(admin.getShift());
		this.workingbill=this.workingBillService.get(wbId);
		return LIST;
	}
	
	/**
	 * 增加前
	 * 1.需要根据随工单id查询其信息
	 */
	public String add()
	{
		this.workingbill=this.workingBillService.get(wbId);//获取随工单的信息
		this.list_cause=this.causeService.getBySample("1");//获取缺陷表中关于抽检的缺陷内容
		this.list_dict=this.dictService.getState("sampleType");//获取抽检类型
		this.add="add";
		return INPUT;
	}
	/**
	 * 增加
	 */
	public String save()
	{
		//保存抽检单信息:抽检单，缺陷ID，缺陷数量，1保存/2确认
		this.sampleService.saveInfo(sample,info,info2,my_id);
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
			pager=this.sampleService.getSamplePager(pager,wbId);
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
	 * 刷卡确认/刷卡撤销
	 * my_id=1--确认
	 * my_id=2--撤销
	 */
	public String confirmOrRevoke()
	{
		try
		{
			ids=info.split(",");
			String newstate="1";
			for(int i=0;i<ids.length;i++)
			{
				this.sample=this.sampleService.load(ids[i]);
				String state=sample.getState();
				//确认
				if("1".equals(my_id))
				{
					newstate="2";
					//已经确认的不能重复确认
					if("2".equals(state))
					{
						addActionError("已确认的无须再确认！");
						return ERROR;
					}
					//已经撤销的不能再确认
					if("3".equals(state))
					{
						addActionError("已撤销的无法再确认！");
						return ERROR;
					}
				}
				//撤销
				if("2".equals(my_id))
				{
					newstate="3";
					//已经撤销的不能再确认
					if("3".equals(state))
					{
						addActionError("已撤销的无法再撤销！");
						return ERROR;
					}
				}
			}
			List<Sample>list=this.sampleService.get(ids);
			this.sampleService.updateState(list,newstate);
			this.redirectionUrl="sample!list.action?wbId="+wbId;
			return SUCCESS;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 编辑前
	 */
	public String edit()
	{
		try
		{
			this.workingbill=this.workingBillService.get(wbId);//获取随工单的信息
			List<Cause> l_cause=this.causeService.getBySample("1");//获取缺陷表中关于抽检的缺陷内容
			this.list_dict=this.dictService.getState("sampleType");//获取抽检类型
			this.sample=this.sampleService.load(id);
			this.list_samrecord=this.srService.getBySampleId(id);//根据抽检id获取缺陷记录
			list_cause=new ArrayList<Cause>();
			for(int i=0;i<l_cause.size();i++)
			{
				Cause c=l_cause.get(i);
				for(int j=0;j<list_samrecord.size();j++)
				{
					SampleRecord sr=list_samrecord.get(j);
					if(c!=null&&sr!=null)
					{
						if(c.getId().equals(sr.getCauseId()))
						{
							c.setCauseNum(sr.getRecordNum());
						}
					}
				}
				this.list_cause.add(c);
			}
			this.edit="edit";
			return INPUT;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 修改
	 */
	public String update()
	{
		//保存抽检单信息:抽检单，缺陷ID，缺陷数量，1保存/2确认
		this.sampleService.updateInfo(sample,info,info2,my_id);
		this.redirectionUrl="sample!list.action?wbId="+this.sample.getWorkingBill().getId();
		return SUCCESS;
	}
	
	/**
	 * 查询一个，查看功能，不能编辑
	 */
	public String show()
	{
		this.workingbill=this.workingBillService.get(wbId);//获取随工单的信息
		this.sample=this.sampleService.load(id);
		this.sampletype=this.dictService.getByState("sampleType",sample.getState());//根据状态获取抽检类型
		this.list_samrecord=this.srService.getBySampleId(id);//根据抽检id获取缺陷记录
		this.show="show";
		return INPUT;
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

	public Admin getAdmin()
	{
		return admin;
	}

	public void setAdmin(Admin admin)
	{
		this.admin = admin;
	}

	public String getAdd()
	{
		return add;
	}

	public void setAdd(String add)
	{
		this.add = add;
	}

	public String getEdit()
	{
		return edit;
	}

	public void setEdit(String edit)
	{
		this.edit = edit;
	}

	public String getShow()
	{
		return show;
	}

	public void setShow(String show)
	{
		this.show = show;
	}

	public List<SampleRecord> getList_samrecord()
	{
		return list_samrecord;
	}

	public void setList_samrecord(List<SampleRecord> list_samrecord)
	{
		this.list_samrecord = list_samrecord;
	}

	public String getSampletype()
	{
		return sampletype;
	}

	public void setSampletype(String sampletype)
	{
		this.sampletype = sampletype;
	}
	
	/**==========================end "get/set"====================================*/
}
