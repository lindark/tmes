package cc.jiuyi.action.admin;

import java.io.IOException;
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
import cc.jiuyi.entity.Bom;
import cc.jiuyi.entity.Cause;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.Products;
import cc.jiuyi.entity.Scrap;
import cc.jiuyi.entity.ScrapBug;
import cc.jiuyi.entity.ScrapLater;
import cc.jiuyi.entity.ScrapMessage;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.sap.rfc.ScrapRfc;
import cc.jiuyi.sap.rfc.impl.ScrapRfcImpl;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.BomService;
import cc.jiuyi.service.CauseService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.ScrapMessageService;
import cc.jiuyi.service.ScrapService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 报废
 * @author gaoyf
 *
 */
@ParentPackage("admin")
public class ScrapAction extends BaseAdminAction
{
	private static final long serialVersionUID = -1318544796737785311L;

	/**========================variable,object,interface  start========================*/
	/**
	 * 对象或变量
	 */
	private Scrap scrap;//报废单
	private Admin admin;
	private String wbId;//随工单id
	private WorkingBill workingbill;//随工单
	private String add;//新增时
	private String edit;//编辑时
	private String show;//查看时
	private Products product;//产品
	private List<Bom>list_material;//物料
	private List<Dict>list_dict;//责任划分
	private String smdutytype;//报废信息--责任类型
	private List<Cause>list_cause;//缺陷
	private List<ScrapLater>list_scraplater;//报废后产出
	private List<ScrapMessage>list_scrapmsg;//报废信息
	private List<ScrapBug>list_scrapbug;//报废原因
	private String my_id;//1刷卡保存2刷卡确认
	private String info;
	private String cardnumber;
	/**
	 * service接口
	 */
	@Resource
	private ScrapService scrapService;//报废单
	@Resource
	private AdminService adminService;
	@Resource
	private WorkingBillService wbService;//随工单
	@Resource
	private DictService dictService;//字典表
	@Resource
	private CauseService causeService;//缺陷
	@Resource
	private ScrapMessageService smService;//报废信息表
	@Resource
	private BomService bomservice;
	
	/**========================end  variable,object,interface==========================*/
	
	/**========================method  start======================================*/
	
	/**
	 * 当前随工单的报废数单数据
	 */
	public String list()
	{
		this.admin=this.adminService.getLoginAdmin();
		this.workingbill=this.wbService.get(wbId);
		return LIST;
	}

	/**
	 * jqGrid查询
	 * scrap_list.ftl
	 */
	public String ajlist()
	{
		if(pager==null)
		{
			pager=new Pager();
		}
		pager.setOrderType(OrderType.desc);
		pager.setOrderBy("modifyDate");
		//查询条件
		if(pager.is_search()==true&&filters!=null)
		{
			JSONObject filt=JSONObject.fromObject(filters);
			Pager pg1=new Pager();
			Map<Object,Object> map=new HashMap<Object, Object>();
			map.put("rules", jqGridSearchDetailTo.class);
			pg1=(Pager) JSONObject.toBean(filt,Pager.class,map);
			pager.setRules(pg1.getRules());
			pager.setGroupOp(pg1.getGroupOp());
		}
		pager=this.scrapService.getScrapPager(pager,wbId);
		@SuppressWarnings("unchecked")
		List<Scrap> scraplist=pager.getList();
		List<Scrap> scraplist2=new ArrayList<Scrap>();
		for(int i=0;i<scraplist.size();i++)
		{
			Scrap s1=scraplist.get(i);
			s1.setXstate(ThinkWayUtil.getDictValueByDictKey(dictService, "scrapState", s1.getState()));//状态
			if(s1.getCreater()!=null)
			{
				s1.setXcreater(s1.getCreater().getName());//提单人
			}
			if(s1.getConfirmation()!=null)
			{
				s1.setXconfirmation(s1.getConfirmation().getName());//确认人
			}
			scraplist2.add(s1);
		}
		pager.setList(scraplist2);
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Scrap.class));//排除有关联关系的属性字段 
		JSONArray jsonArray=JSONArray.fromObject(pager,jsonConfig);
		return this.ajaxJson(jsonArray.get(0).toString());
	}
	
	/**
	 * 新增前  --modify weitao
	 */
	public String add()
	{
		this.workingbill=this.wbService.get(wbId);
		String aufnr = workingbill.getWorkingBillCode().substring(0,workingbill.getWorkingBillCode().length()-2);
		//Date productDate = ThinkWayUtil.formatStringDate(workingbill.getProductDate());
		list_material = bomservice.findBom(aufnr, workingbill.getProductDate());
		//this.list_material=new ArrayList<Bom>( this.product.getMaterial());//产品对应的物料(/组件)
		this.list_dict=this.dictService.getState("scrapMessageType");//责任类型
		this.list_cause=this.causeService.getBySample("4");//报废原因内容
		this.add="add";
		return INPUT;
	}
	
	/**
	 * 新增保存
	 */
	public String creditsave()
	{
		//保存新增数据，返回主表新增数据的ID
		this.scrapService.saveInfo(scrap,list_scrapmsg,list_scrapbug,list_scraplater,my_id,cardnumber);//保存		
		this.redirectionUrl="scrap!list.action?wbId="+this.scrap.getWorkingBill().getId();
		return this.ajaxJsonSuccessMessage("您的操作已成功!");
	}
	
	/**
	 * 新增确认
	 */
	public String creditsubmit()
	{
		//保存新增数据，返回主表新增数据的ID
		String scrapid=this.scrapService.saveInfo(scrap,list_scrapmsg,list_scrapbug,list_scraplater,my_id,cardnumber);//保存		
		//确认需要与SAP交互
		Scrap s=this.scrapService.get(scrapid);//根据ID获取刚新增的数据
		List<Scrap>list1=new ArrayList<Scrap>();
		list1.add(s);
		//List<ScrapLater>list2=new ArrayList<ScrapLater>(s.getScrapLaterSet());//报废产出表数据
		List<ScrapLater>list2=this.scrapService.getSlBySid(scrapid);//报废产出表数据
		this.redirectionUrl="scrap!list.action?wbId="+this.scrap.getWorkingBill().getId();
		if(list2.size()>0)
		{
			return xconfirm(list1,"2",1);
		}
		return this.ajaxJsonSuccessMessage("您的操作已成功!");	
	}
	
	/**
	 * 编辑前 --modify weitao
	 */
	public String edit()
	{
		this.list_material=new ArrayList<Bom>();
		this.workingbill=this.wbService.get(wbId);
		String aufnr = workingbill.getWorkingBillCode().substring(0,workingbill.getWorkingBillCode().length()-2);
		//Date productDate = ThinkWayUtil.formatStringDate(workingbill.getProductDate());
		List<Bom> l_material = bomservice.findBom(aufnr, workingbill.getProductDate());
		for(int i=0;i<l_material.size();i++)
		{
			Bom m=l_material.get(i);
			if(m!=null)
			{
				ScrapMessage sm=this.smService.getBySidAndMid(id,m.getMaterialCode());//根据scrap表id和物料表id查询报废信息
				if(sm!=null)
				{
					m.setXsmreson(sm.getSmreson());//原因
					m.setXmenge(sm.getMenge());//数量
					m.setXsmduty(sm.getSmduty());//责任划分
					m.setXsmid(sm.getId());
					List<ScrapBug> l_sbug=new ArrayList<ScrapBug>(sm.getScrapBug());//获取一个物料对应的报废原因
					String sbids="",sbnums="";
					for(int j=0;j<l_sbug.size();j++)
					{
						ScrapBug sb=l_sbug.get(j);
						if(sb!=null)
						{
							sbids=sbids+sb.getCauseId()+",";
							sbnums=sbnums+sb.getSbbugNum()+",";
						}
					}
					m.setXsbids(sbids);//缺陷ids
					m.setXsbnums(sbnums);//缺陷数量
				}
				list_material.add(m);
			}
		}
		this.list_dict=this.dictService.getState("scrapMessageType");//责任类型
		this.list_cause=this.causeService.getBySample("4");//报废原因内容
		this.scrap=this.scrapService.get(id);
		this.list_scraplater=new ArrayList<ScrapLater>(this.scrap.getScrapLaterSet());//报废后产出
		this.edit="edit";
		return INPUT;
	}
	/**
	 * 修改保存
	 */
	public String creditupdate()
	{
		this.scrapService.updateInfo(scrap,list_scrapmsg,list_scrapbug,list_scraplater,my_id);
		this.redirectionUrl="scrap!list.action?wbId="+this.scrap.getWorkingBill().getId();
		return this.ajaxJsonSuccessMessage("您的操作已经成功");
	}
	
	/**
	 * 修改确认
	 */
	public String creditreply()
	{
		this.scrapService.updateInfo(scrap,list_scrapmsg,list_scrapbug,list_scraplater,my_id);
		Scrap s=this.scrapService.get(scrap.getId());//根据ID获取刚新增的数据
		List<Scrap>list1=new ArrayList<Scrap>();
		list1.add(s);
		List<ScrapLater>list2=new ArrayList<ScrapLater>(s.getScrapLaterSet());//报废产出表数据
		if(list2.size()>0)
		{
			xconfirm(list1,"2",1);
		}
		this.redirectionUrl="scrap!list.action?wbId="+this.scrap.getWorkingBill().getId();
		return this.ajaxJsonSuccessMessage("你的操作已成功!");
	}
	
	
	/**
	 * 查看
	 */
	public String show()
	{
		this.list_scrapmsg=new ArrayList<ScrapMessage>();//初始化
		this.scrap=this.scrapService.get(id);
		this.workingbill=this.wbService.get(wbId);
		List<ScrapMessage>l_scrapmsg=new ArrayList<ScrapMessage>(this.scrap.getScrapMsgSet());//报废信息
		if(l_scrapmsg.size()>0)
		{
			for(int i=0;i<l_scrapmsg.size();i++)
			{
				ScrapMessage sm=l_scrapmsg.get(i);
				sm.setXsmduty(this.dictService.getByState("scrapMessageType",sm.getSmduty()));
				this.list_scrapmsg.add(sm);
			}
		}
		this.list_scraplater=new ArrayList<ScrapLater>(this.scrap.getScrapLaterSet());//报废后产出
		this.show="show";
		return INPUT;
	}
	
	/**
	 * list页面
	 * 刷卡确认
	 */
	public String creditapproval()
	{
		ids = info.split(",");
		for (int i = 0; i < ids.length; i++)
		{
			this.scrap = this.scrapService.get(ids[i]);
			String state = scrap.getState();
			//报废后产出表是否为空
			List<ScrapLater>list2=new ArrayList<ScrapLater>(scrap.getScrapLaterSet());
			if(list2.size()==0)
			{
				return ajaxJsonErrorMessage("有'报废后产出表'数据为空,不能确认!");
			}
			// 已经确认的不能重复确认
			if ("2".equals(state))
			{
				return ajaxJsonErrorMessage("已确认的无须再确认！");
			}
			// 已经撤销的不能再确认
			if ("3".equals(state))
			{
				return ajaxJsonErrorMessage("已撤销的无法再确认！");
			}	
		}
		List<Scrap> list = this.scrapService.get(ids);
		return xconfirm(list,"2",1);
	}
	/**
	 * list页面
	 * 刷卡撤销
	 */
	public String creditundo()
	{
		ids = info.split(",");
		for (int i = 0; i < ids.length; i++)
		{
			this.scrap = this.scrapService.get(ids[i]);
			String state = scrap.getState();
			// 已经撤销的不能再确认
			if ("3".equals(state))
			{
				return ajaxJsonErrorMessage("已撤销的无法再撤销！");
			}	
		}
		List<Scrap> list = this.scrapService.get(ids);
		return xconfirm(list,"3",2);
	}
	
	/**
	 * list页面
	 * xmyid=1 确认操作=退料  
	 * xmyid=2 撤销操作=领料
	 */
	public String xconfirm(List<Scrap>xlist_scrap,String newstate,int xmyid)
	{
		try
		{
			ScrapRfc scrapRfc=new ScrapRfcImpl();
			for (int i = 0; i < xlist_scrap.size(); i++)
			{
				Scrap s=xlist_scrap.get(i);
				/**确认操作*/
				if(xmyid==1)
				{
					//报废后产出--根据主表获取对应从表的数据
					List<ScrapLater>list1=this.scrapService.getSlBySid(s.getId());
					s.setMove_type("262");
					if(list1.size()>0)
					{
						/**有产后数据,和SAP交互*/
						//调用SAP，返回一个List数据，判断检索是否通过
						Scrap s_sapreturn=scrapRfc.ScrappedCrt("X",s,list1);
						if("E".equalsIgnoreCase(s_sapreturn.getE_type()))
						{
							return ajaxJsonErrorMessage(s.getE_message());
						}
						else
						{
							//调用SAP，执行数据交互，返回List，并判断数据交互中是否成功，成功的更新本地数据库，失败的则不保存
							Scrap s_sapreturn2=scrapRfc.ScrappedCrt("",s,list1);
							if("E".equalsIgnoreCase(s_sapreturn.getE_type()))
							{
								return ajaxJsonErrorMessage(s.getE_message());
							}
							else
							{
								/**与SAP交互没有问题,更新本地数据库*/
								this.scrapService.updateMyData(s_sapreturn2,newstate,cardnumber,1);
							}
						}
					}
					else
					{
						/**没有产后数据,修改状态*/
						this.scrapService.updateMyData(s,newstate,cardnumber,2);
					}
				}
				/**撤销操作*/
				else if(xmyid==2)
				{
					/**撤销未确认的*/
					if("1".equals(s.getState()))
					{
						this.scrapService.updateMyData(s, "3",cardnumber,2);
					}
					/**撤销已确认的*/
					if("2".equals(s.getState()))
					{
						//报废后产出--根据主表获取对应从表的数据
						List<ScrapLater>list1=this.scrapService.getSlBySid(s.getId());
						s.setMove_type("261");
						
						if(list1.size()>0)
						{
							/**有产后数据,和SAP交互*/
							//调用SAP，返回一个List数据，判断检索是否通过
							Scrap s_sapreturn=scrapRfc.ScrappedCrt("X",s,list1);
							if("E".equalsIgnoreCase(s_sapreturn.getE_type()))
							{
								return ajaxJsonErrorMessage(s.getE_message());
							}
							else
							{
								//调用SAP，执行数据交互，返回List，并判断数据交互中是否成功，成功的更新本地数据库，失败的则不保存
								Scrap s_sapreturn2=scrapRfc.ScrappedCrt("",s,list1);
								if("E".equalsIgnoreCase(s_sapreturn.getE_type()))
								{
									return ajaxJsonErrorMessage(s.getE_message());
								}
								else
								{
									/**与SAP交互没有问题,更新本地数据库*/
									this.scrapService.updateMyData(s_sapreturn2,newstate,cardnumber,1);
								}
							}
						}
						else
						{
							/**没有产后数据,修改状态*/
							this.scrapService.updateMyData(s,newstate,cardnumber,2);
						}
					}
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return ajaxJsonErrorMessage("IO出现异常，请联系系统管理员");
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxJsonErrorMessage("系统出现问题，请联系系统管理员");
		}
		return this.ajaxJsonSuccessMessage("您的操作已成功!");
	}
	/**========================end  method======================================*/
	
	/**=========================="get/set"  start==============================*/
	public Scrap getScrap()
	{
		return scrap;
	}

	public void setScrap(Scrap scrap)
	{
		this.scrap = scrap;
	}

	public Admin getAdmin()
	{
		return admin;
	}

	public void setAdmin(Admin admin)
	{
		this.admin = admin;
	}

	public String getWbId()
	{
		return wbId;
	}

	public void setWbId(String wbId)
	{
		this.wbId = wbId;
	}

	public WorkingBill getWorkingbill()
	{
		return workingbill;
	}

	public void setWorkingbill(WorkingBill workingbill)
	{
		this.workingbill = workingbill;
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

	public Products getProduct()
	{
		return product;
	}

	public void setProduct(Products product)
	{
		this.product = product;
	}

	public List<Bom> getList_material()
	{
		return list_material;
	}

	public void setList_material(List<Bom> list_material)
	{
		this.list_material = list_material;
	}

	public List<Dict> getList_dict()
	{
		return list_dict;
	}

	public void setList_dict(List<Dict> list_dict)
	{
		this.list_dict = list_dict;
	}

	public String getSmdutytype()
	{
		return smdutytype;
	}

	public void setSmdutytype(String smdutytype)
	{
		this.smdutytype = smdutytype;
	}

	public List<Cause> getList_cause()
	{
		return list_cause;
	}

	public void setList_cause(List<Cause> list_cause)
	{
		this.list_cause = list_cause;
	}

	public List<ScrapLater> getList_scraplater()
	{
		return list_scraplater;
	}

	public void setList_scraplater(List<ScrapLater> list_scraplater)
	{
		this.list_scraplater = list_scraplater;
	}

	public List<ScrapMessage> getList_scrapmsg()
	{
		return list_scrapmsg;
	}

	public void setList_scrapmsg(List<ScrapMessage> list_scrapmsg)
	{
		this.list_scrapmsg = list_scrapmsg;
	}

	public List<ScrapBug> getList_scrapbug()
	{
		return list_scrapbug;
	}

	public void setList_scrapbug(List<ScrapBug> list_scrapbug)
	{
		this.list_scrapbug = list_scrapbug;
	}

	public String getMy_id()
	{
		return my_id;
	}

	public void setMy_id(String my_id)
	{
		this.my_id = my_id;
	}

	public String getInfo()
	{
		return info;
	}

	public void setInfo(String info)
	{
		this.info = info;
	}

	public String getCardnumber()
	{
		return cardnumber;
	}

	public void setCardnumber(String cardnumber)
	{
		this.cardnumber = cardnumber;
	}

	
	
	/**==========================end "get/set"=================================*/
}
