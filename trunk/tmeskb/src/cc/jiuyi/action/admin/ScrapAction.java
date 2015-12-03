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
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.Material;
import cc.jiuyi.entity.Products;
import cc.jiuyi.entity.Scrap;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.ProductsService;
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

	/**======================对象，变量，接口 start=================================*/
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
	private List<Material>list_material;//物料
	private List<Dict>list_dict;//责任划分
	private String smdutytype;//报废信息--责任类型
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
	private ProductsService productService;//产品
	
	/**======================end 对象，变量，接口=*=================================*/
	
	/**===========================方法start=====================================*/
	
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
		try
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
				s1.setXcreater(s1.getCreater().getName());//提单人
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
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 新增前
	 */
	public String add()
	{
		this.workingbill=this.wbService.load(wbId);
		this.product=this.productService.getProducts(workingbill.getMatnr());//随工单对应的产品
		this.list_material=new ArrayList<Material>( this.product.getMaterial());//产品对应的物料(/组件)
		this.list_dict=this.dictService.getState("scrapMessageType");//责任类型
		return INPUT;
	}
	
	/**
	 * 查看
	 */
	public String show()
	{
		//this.smdutytype=ThinkWayUtil.getDictValueByDictKey(dictService, "scrapState", s1.getState());
		
		return INPUT;
	}
	/**==========================end 方法=======================================*/
	
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

	public List<Material> getList_material()
	{
		return list_material;
	}

	public void setList_material(List<Material> list_material)
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
	
	/**==========================end "get/set"=================================*/
}
