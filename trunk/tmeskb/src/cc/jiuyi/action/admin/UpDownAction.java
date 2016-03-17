package cc.jiuyi.action.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.ParentPackage;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Locationonside;
import cc.jiuyi.entity.UpDown;
import cc.jiuyi.sap.rfc.UpDownRfc;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.UpDownServcie;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.ThinkWayUtil;


/**
 * 后台Action类 - 文章
 */

@ParentPackage("admin")
public class UpDownAction extends BaseAdminAction {

	private static final long serialVersionUID = -7909258717886487018L;
	private static Logger log = Logger.getLogger(UpDownAction.class);  
	@Resource
	private UpDownServcie updownservice;
	@Resource
	private AdminService adminservice;
	@Resource
	private UpDownRfc undownrfc;
	@Resource
	private DictService dictservice;
	
	private Pager pager;
	private UpDown updown;
	private String type;//类型
	private String lgpla;//发出仓位
	private String lgplaun;//接收仓位
	private String loginid;
	private String cardnumber;
	private List<Locationonside> locationonsideList;
	private List<UpDown> updownList;
	
	public String add(){
		//检查数据完整性
		Admin admin = adminservice.getLoginAdmin();
		admin = adminservice.get(admin.getId());
		if(admin.getProductDate() == null || admin.getShift() == null){
			addActionError("生产日期和班次必须绑定后才可以使用");
			return ERROR;
		}
		
		if("up".equals(type)){//上架
			this.lgpla = "R-00";
		}else if("down".equals(type)){//下架
			this.lgplaun="R-00";
		}else if("updown".equals(type)){
			
		}else{
			addActionError("请输入正确的类型!");
			return ERROR;
		}
		
		if(ThinkWayUtil.null2String(this.lgpla).equals("")){
			return INPUT;
		}
		//业务处理
		String werks = admin.getDepartment().getTeam().getFactoryUnit().getWorkShop().getFactory().getFactoryCode();//工厂
		String lgort = admin.getDepartment().getTeam().getFactoryUnit().getWarehouse();//库存地点
		String matnr = "";
		//String lgpla = this.lgpla ==null?"":this.lgpla;//仓位
		String lgpla = this.lgpla;
		try{
			List<HashMap<String,String>> hashList = updownservice.upmaterList(werks, lgort, matnr, lgpla);
			locationonsideList = new ArrayList<Locationonside>();
			for(int i=0;i<hashList.size();i++){
				HashMap<String,String> hashmap = hashList.get(i);
				Locationonside locationonside = new Locationonside();
				locationonside.setLocationCode(lgort);//库存地点
				locationonside.setMaterialCode(hashmap.get("matnr"));//物料编码
				locationonside.setMaterialName(hashmap.get("maktx"));//物料描述
				locationonside.setCharg(hashmap.get("charg"));//批次
				locationonside.setAmount(hashmap.get("verme"));//数量
				locationonsideList.add(locationonside);
			}
		}catch(IOException e){
			e.printStackTrace();
			log.error(e);
			addActionError("IO操作失败");
			return ERROR;
		}catch(CustomerException e){
			e.printStackTrace();
			log.error(e);
			addActionError(e.getMsgDes());
			return ERROR;
		}
		
		return INPUT;
	}
	
	/**
	 * 刷卡保存
	 * @return
	 */
	public String creditsave(){
		//检查数据完整性
		
		Admin admin = adminservice.get(loginid);
		Admin admin1 = adminservice.getByCardnum(cardnumber);
		if(admin.getProductDate() == null || admin.getShift() == null){
			return ajaxJsonErrorMessage("生产日期和班次必须绑定后才可以使用");
		}
		if(updownList==null || updownList.size()<=0){
			return ajaxJsonErrorMessage("请至少填写一行数据");
		}
		if(lgpla == null){
			return ajaxJsonErrorMessage("发出仓位必须填写");
		}
		if(lgplaun ==null){
			return ajaxJsonErrorMessage("目的地仓位必须填写");
		}
		//数据类型定义
		HashMap<String,String> hash = new HashMap<String,String>();
		List<HashMap<String,String>> hashList = new ArrayList<HashMap<String,String>>();
		//业务处理
		String werks = admin.getDepartment().getTeam().getFactoryUnit().getWorkShop().getFactory().getFactoryCode();
		String lgort = admin.getDepartment().getTeam().getFactoryUnit().getWarehouse();
		String flag = "";
		if(type.equals("up"))
			flag = "0";
		else if(type.equals("down"))
			flag = "2";
		else if(type.equals("updown")){
			flag = "3";
		}else
			return ajaxJsonErrorMessage("请正确填写类型!");
		
		hash.put("werks", werks);
		hash.put("lgort", lgort);
		hash.put("flag", flag);
		
		for(int i=0;i<updownList.size();i++){
			HashMap<String,String> hashmap = new HashMap<String,String>();
			UpDown updown = updownList.get(i);
			if(ThinkWayUtil.null2o(updown.getDwnum()) <= 0){
				updownList.remove(i);
				i--;
				continue;
			}
			hashmap.put("matnr", updown.getMatnr());//物料号
			hashmap.put("charg", updown.getCharg());//批次
			hashmap.put("lgpla", lgpla);//发出仓位
			hashmap.put("dwnum", ""+updown.getDwnum());
			hashmap.put("nlpla", lgplaun);//目的地仓位
			hashList.add(hashmap);
		}
		//接口调用
		try {
			List<HashMap<String,String>> maplist = undownrfc.undown(hash, hashList);
			for(int i=0;i<updownList.size();i++){
				UpDown updown = updownList.get(i);
				for(int y=0;y<maplist.size();y++){
					HashMap<String,String> map = maplist.get(y);
					String matnr = map.get("matnr");//物料号
					String charg = map.get("charg");//批次
					String tanum = map.get("tanum");//转储单号
					String tapos = map.get("tapos");//转出单行项目号
					if(ThinkWayUtil.null2String(updown.getMatnr()).equals(ThinkWayUtil.null2String(matnr)) &&
					   ThinkWayUtil.null2String(updown.getCharg()).equals(ThinkWayUtil.null2String(charg))){
						updown.setTanum(tanum);
						updown.setTapos(tapos);
					}
				}
				updown.setUplgpla(lgpla);
				updown.setDownlgpla(lgplaun);
				updown.setType(type);
				updown.setAppvaladmin(admin1);//保存人
				updown.setProductDate(admin.getProductDate());//将生产日期和班次写入
				updown.setShift(admin.getShift());
				updownList.set(i, updown);
			}
			updownservice.save(updownList);
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e);
			return ajaxJsonErrorMessage("IO操作失败!");
		} catch (CustomerException e) {
			e.printStackTrace();
			log.error(e.getMsgDes());
			return ajaxJsonErrorMessage(e.getMsgDes());
		}
		
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}
	
	public String list(){
		
		return LIST;
	}
	
	public String ajlist(){
		
		if (pager.getOrderBy().equals("")) {
			pager.setOrderType(OrderType.desc);
			pager.setOrderBy("modifyDate");
		}
		Admin admin = adminservice.getLoginAdmin();
		admin = adminservice.get(admin.getId());
		pager = updownservice.findByPager(pager,admin);
		
		List<UpDown> updownList = pager.getList();
		List<UpDown> updownList1 = new ArrayList<UpDown>();
		for(int i=0;i<updownList.size();i++){
			UpDown updown = updownList.get(i);
			updown.setTypex(ThinkWayUtil.getDictValueByDictKey(dictservice, "updown", updown.getType()));
			updown.setShiftx(ThinkWayUtil.getDictValueByDictKey(dictservice, "kaoqinClasses", updown.getShift()));
			updown.setAdminname(updown.getAppvaladmin().getName());
			updownList1.add(updown);
		}
		pager.setList(updownList1);
		JsonConfig jsonConfig=new JsonConfig();   
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(UpDown.class));//排除有关联关系的属性字段  
		JSONArray jsonArray = JSONArray.fromObject(pager,jsonConfig);
		System.out.println(jsonArray.get(0).toString());
		return ajaxJson(jsonArray.get(0).toString());
	}
	
	//查看
	public String view(){
		updown = updownservice.load(id);
		updown.setTypex(ThinkWayUtil.getDictValueByDictKey(dictservice, "updown", updown.getType()));
		return VIEW;
	}
	
	

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLgpla() {
		return lgpla;
	}

	public void setLgpla(String lgpla) {
		this.lgpla = lgpla;
	}

	public List<Locationonside> getLocationonsideList() {
		return locationonsideList;
	}

	public void setLocationonsideList(List<Locationonside> locationonsideList) {
		this.locationonsideList = locationonsideList;
	}

	public UpDown getUpdown() {
		return updown;
	}

	public void setUpdown(UpDown updown) {
		this.updown = updown;
	}

	public String getLoginid() {
		return loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public List<UpDown> getUpdownList() {
		return updownList;
	}

	public void setUpdownList(List<UpDown> updownList) {
		this.updownList = updownList;
	}

	public String getLgplaun() {
		return lgplaun;
	}

	public void setLgplaun(String lgplaun) {
		this.lgplaun = lgplaun;
	}

	public String getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}


	
	
	
}