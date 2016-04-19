package cc.jiuyi.action.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;












import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.ParentPackage;











import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Bom;
import cc.jiuyi.entity.ProcessHandover;
import cc.jiuyi.entity.ProcessHandoverSon;
import cc.jiuyi.entity.ProcessHandoverTop;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.sap.rfc.HandOverProcessRfc;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.BomService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.ProcessHandoverService;
import cc.jiuyi.service.ProcessHandoverTopService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.CommonUtil;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.ThinkWayUtil;


/**
 * 后台Action类-工序交接
 */
@ParentPackage("admin")
public class ProcessHandoverAction extends BaseAdminAction {

	private static final long serialVersionUID = -7711733851456884703L;
	private static Logger log = Logger.getLogger(AdminAction.class);  
	
	
	private Admin admin;
	private List<WorkingBill> workingbillList;
	private List<Bom> bomList; 
	private ProcessHandover processHandover;
	private ProcessHandoverTop processHandoverTop;
	public List<ProcessHandover> processHandoverList; 
	public List<ProcessHandoverSon> processHandoverSonList; 
	private String loginid;
	private String show;
	
	@Resource
	private AdminService adminService;
	@Resource
	private WorkingBillService workingbillservice;
	@Resource
	private BomService bomService;
	@Resource
	private ProcessHandoverService processHandoverService;
	@Resource
	private ProcessHandoverTopService processHandoverTopService;
	@Resource
	private DictService dictService;
	@Resource
	private HandOverProcessRfc handoverprocessrfc;
	
	/**
	 * 列表
	 * @return
	 */
	public String list(){
		
		return LIST;
	}
	
	/**
	 * aj列表
	 * @return
	 */
	public String ajlist(){
		HashMap<String, String> map = new HashMap<String, String>();

		if (pager.getOrderBy().equals("")) {
			pager.setOrderType(OrderType.desc);
			pager.setOrderBy("modifyDate");
		}
		if (pager.is_search() == true && filters != null) {// 需要查询条件
			JSONObject filt = JSONObject.fromObject(filters);
			Pager pager1 = new Pager();
			Map m = new HashMap();
			m.put("rules", jqGridSearchDetailTo.class);
			pager1 = (Pager) JSONObject.toBean(filt, Pager.class, m);
			pager.setRules(pager1.getRules());
			pager.setGroupOp(pager1.getGroupOp());
		}
		pager = processHandoverService.jqGrid(pager);
		List<ProcessHandoverTop> processHandoverTopList = pager.getList();
		List<ProcessHandoverTop> lst = new ArrayList<ProcessHandoverTop>();
		for (int i = 0; i < processHandoverTopList.size(); i++) {
			ProcessHandoverTop processHandoverTop = (ProcessHandoverTop) processHandoverTopList.get(i);
			processHandoverTop.setXstate(ThinkWayUtil.getDictValueByDictKey(dictService, "processHandoverTopState",processHandoverTop.getState()));
			processHandoverTop.setXshift(ThinkWayUtil.getDictValueByDictKey(dictService, "kaoqinClasses", processHandoverTop.getShift()));
			processHandoverTop.setXcreateUser(processHandoverTop.getCreateUser()==null?"":processHandoverTop.getCreateUser().getName());
			processHandoverTop.setXconfirmUser(processHandoverTop.getConfimUser()==null?"":processHandoverTop.getConfimUser().getName());
			lst.add(processHandoverTop);
		}
		pager.setList(lst);
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
		jsonConfig.setExcludes(ThinkWayUtil
				.getExcludeFields(ProcessHandoverTop.class));// 排除有关联关系的属性字段
		JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
		return ajaxJson(jsonArray.get(0).toString());
	}
	
	/**
	 * 添加
	 * @return
	 */
	public String add(){
		try{
			bomList = new ArrayList<Bom>();
			admin = adminService.getLoginAdmin();
			admin = adminService.get(admin.getId());
			processHandoverTop = new ProcessHandoverTop();
			/*String uuid = CommonUtil.getUUID();
			processHandoverTop.setId(uuid);*/
			processHandoverList = new ArrayList<ProcessHandover>();
			
			
			processHandoverSonList = new ArrayList<ProcessHandoverSon>();
			if(admin.getProductDate() != null && admin.getShift() != null){
				workingbillList = workingbillservice.getListWorkingBillByDate(admin);
				if(workingbillList!=null && workingbillList.size()>0){
					Set<ProcessHandover> processHandoverSet = new HashSet<ProcessHandover>();
					for(int i=0;i<workingbillList.size();i++){
						WorkingBill wb = workingbillList.get(i);
						if(wb.getProcessHandover()!=null){
							addActionError("当日工序交接已提交或已确认");
							return ERROR;
						}
					}
					Collections.sort(workingbillList);
					for(int i=0;i<workingbillList.size();i++){
						
						WorkingBill wb = workingbillList.get(i);
						String s = Integer.toString(i);
						ProcessHandover processHandover1 = new ProcessHandover();
						/*uuid = CommonUtil.getUUID();
						processHandover1.setId(uuid);*/
						processHandover1.setProcessHandoverTop(processHandoverTop);
						processHandover1.setWorkingBill(wb);
						processHandover1.setAufnr(wb.getAufnr());
						processHandover1.setWorkingBillCode(wb.getWorkingBillCode());
						processHandover1.setPlanCount(wb.getPlanCount()==null?"":wb.getPlanCount().toString());
						processHandover1.setMatnr(wb.getMatnr());
						processHandover1.setMaktx(wb.getMaktx());
						WorkingBill wbnext = workingbillservice.getCodeNext(admin,wb.getWorkingBillCode(),admin.getProductDate(),admin.getShift());
						if(wbnext!=null){
							//workingbillList.get(i).setAfterworkingBillCode(wbnext.getWorkingBillCode());
							processHandover1.setAfterWorkingBillCode(wbnext.getWorkingBillCode());
						}
						//获取Bom
						String aufnr = wb.getWorkingBillCode().substring(0,wb.getWorkingBillCode().length()-2);
						List<Bom> bomLists = bomService.findBom(aufnr, wb.getProductDate(),wb.getWorkingBillCode());
						if(bomLists != null && bomLists.size()>0){
							Set<ProcessHandoverSon> processHandoverSonSet = new HashSet<ProcessHandoverSon>();
							for(Bom b : bomLists){
								ProcessHandoverSon processHandoverSon1 = new ProcessHandoverSon();
								/*uuid = CommonUtil.getUUID();
								processHandoverSon1.setId(uuid);*/
								b.setBeforeWorkingCode(wb.getWorkingBillCode());
								if(wbnext!=null){
									b.setAfterWorkingCode(wbnext.getWorkingBillCode());
								}
								processHandoverSon1.setProcessHandover(processHandover1);
								processHandoverSon1.setProductAmount(b.getProductAmount()==null?"":b.getProductAmount().toString());
								processHandoverSon1.setBomCode(b.getMaterialCode());
								processHandoverSon1.setBomDesp(b.getMaterialName());
								processHandoverSon1.setMaterialAmount(b.getMaterialAmount()==null?"":b.getMaterialAmount().toString());
								processHandoverSon1.setBeforeWorkingCode(wb.getWorkingBillCode());
								processHandoverSonSet.add(processHandoverSon1);
								bomList.add(b);
							}
							processHandover1.setProcessHandoverSonSet(processHandoverSonSet);
						}
						processHandoverSet.add(processHandover1);
					}
					processHandoverTop.setProcessHandOverSet(processHandoverSet);
				}
			}else{
				addActionError("请绑定生产日期和班次");
				return ERROR;
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error(e);
		}
		return INPUT;
	}
	
	/**
	 * 编辑
	 * @return
	 */
	public String edit(){
		admin = adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());
		processHandoverTop = processHandoverTopService.get(id);
		return INPUT;
	}
	/**
	 * 刷卡保存
	 * @return
	 */
	public String creditsubmit(){
		processHandoverService.saveProcessHandover(processHandoverTop,processHandoverList,processHandoverSonList,loginid);
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}
	/**
	 * 刷卡保存 update
	 * @return
	 */
	public String creditupdate() {
		//processHandoverTop = processHandoverTopService.get(id);
		processHandoverService.updateProcessHandover(processHandoverTop,processHandoverList,processHandoverSonList,loginid);
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}
	/**
	 * 刷卡确认 update
	 * @return
	 */
	public String creditapproval(){
		try {
			processHandoverTop = processHandoverTopService.get(id);
			String budat = null;
			for(ProcessHandover p : processHandoverTop.getProcessHandOverSet()){
				if(p.getProductAmount()!=null && !"".equals(p.getProductAmount())){
					ProcessHandover ProcessHandover = handoverprocessrfc.BatchProcessHandOver(p, "",loginid);
					if(budat==null)
					//budat = ProcessHandover.getBudat();
					//p.setBudat(ProcessHandover.getBudat());
					p.setE_message(ProcessHandover.getE_message());
					p.setE_type(ProcessHandover.getE_type());
					p.setMblnr(ProcessHandover.getMblnr());
					processHandoverService.update(p);
				}
			}
			processHandoverTop.setBudat(budat);
			processHandoverTopService.update(processHandoverTop);
		} catch (IOException e) {
			e.printStackTrace();
			return ajaxJsonErrorMessage("IO出现异常，请联系系统管理员");
		} catch (CustomerException e) {
			e.printStackTrace();
			return ajaxJsonErrorMessage(e.getMsgDes());
		}//执行
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}
	
	/**
	 * 查看
	 * @return
	 */
	public String view(){
		processHandoverTop = processHandoverTopService.get(id);
		if(show==null)
		show = "show";
		return INPUT;
	}
	
	public String delete(){
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}
	
	public Admin getAdmin() {
		return admin;
	}



	public void setAdmin(Admin admin) {
		this.admin = admin;
	}



	public List<WorkingBill> getWorkingbillList() {
		return workingbillList;
	}



	public void setWorkingbillList(List<WorkingBill> workingbillList) {
		this.workingbillList = workingbillList;
	}



	public List<Bom> getBomList() {
		return bomList;
	}



	public void setBomList(List<Bom> bomList) {
		this.bomList = bomList;
	}



	public ProcessHandover getProcessHandover() {
		return processHandover;
	}



	public void setProcessHandover(ProcessHandover processHandover) {
		this.processHandover = processHandover;
	}

	public ProcessHandoverTop getProcessHandoverTop() {
		return processHandoverTop;
	}

	public void setProcessHandoverTop(ProcessHandoverTop processHandoverTop) {
		this.processHandoverTop = processHandoverTop;
	}

	public List<ProcessHandover> getProcessHandoverList() {
		return processHandoverList;
	}

	public void setProcessHandoverList(List<ProcessHandover> processHandoverList) {
		this.processHandoverList = processHandoverList;
	}

	public List<ProcessHandoverSon> getProcessHandoverSonList() {
		return processHandoverSonList;
	}

	public void setProcessHandoverSonList(
			List<ProcessHandoverSon> processHandoverSonList) {
		this.processHandoverSonList = processHandoverSonList;
	}

	public String getLoginid() {
		return loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public String getShow() {
		return show;
	}

	public void setShow(String show) {
		this.show = show;
	}
	
	
}
