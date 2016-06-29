package cc.jiuyi.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.HandOverProcessDao;
import cc.jiuyi.dao.OddHandOverDao;
import cc.jiuyi.dao.PickDetailDao;
import cc.jiuyi.dao.ProcessHandoverDao;
import cc.jiuyi.dao.WorkingInoutDao;
import cc.jiuyi.entity.Bom;
import cc.jiuyi.entity.PickDetail;
import cc.jiuyi.entity.Process;
import cc.jiuyi.entity.ProcessHandover;
import cc.jiuyi.entity.ProcessHandoverSon;
import cc.jiuyi.entity.Repair;
import cc.jiuyi.entity.RepairPiece;
import cc.jiuyi.entity.Repairin;
import cc.jiuyi.entity.RepairinPiece;
import cc.jiuyi.entity.TempKaoqin;
import cc.jiuyi.entity.UnitConversion;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.entity.WorkingInout;
import cc.jiuyi.service.BomService;
import cc.jiuyi.service.ProcessService;
import cc.jiuyi.service.TempKaoqinService;
import cc.jiuyi.service.UnitConversionService;
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
	private OddHandOverDao oddHandOverDao;
	@Resource
	private PickDetailDao pickdetaildao;
	@Resource
	private ProcessHandoverDao processHandoverDao;
	@Resource
	private UnitConversionService unitConversionService;
	@Resource
	private TempKaoqinService tempKaoqinService;
	
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
		return workingInoutDao.newFindPagerByWorkingBillInout(map);
	}

	public JSONArray showInoutJsonData(String[] strlen,String[] lavenlen){
		List<String> nameobj = new ArrayList<String>();
		List<String> labelobj = new ArrayList<String>();
		List<String> indexobj = new ArrayList<String>();
		
		String[] propertyNames = {"isDel","state"};
		String[] propertyValues={"N","1"};
		List<Process> processList00 = processservice.getList(propertyNames, propertyValues);
		
		nameobj.add(strlen[29]);labelobj.add(lavenlen[29]);indexobj.add(strlen[29]);//单元
		//nameobj.add(strlen[30]);labelobj.add(lavenlen[29]);indexobj.add(strlen[30]);//班组编码
		nameobj.add(strlen[31]);labelobj.add(lavenlen[31]);indexobj.add(strlen[31]);//班组名称
		nameobj.add(strlen[12]);labelobj.add(lavenlen[12]);indexobj.add(strlen[12]);//生产日期
		nameobj.add(strlen[13]);labelobj.add(lavenlen[13]);indexobj.add(strlen[13]);//班次
		nameobj.add(strlen[14]);labelobj.add(lavenlen[14]);indexobj.add(strlen[14]);//生产订单号
		nameobj.add(strlen[23]);labelobj.add(lavenlen[23]);indexobj.add(strlen[23]);//物料编码
		nameobj.add(strlen[24]);labelobj.add(lavenlen[24]);indexobj.add(strlen[24]);//物料描述
		nameobj.add(strlen[1]);labelobj.add(lavenlen[1]);indexobj.add(strlen[1]);//子件编码
		nameobj.add(strlen[25]);labelobj.add(lavenlen[25]);indexobj.add(strlen[25]);//组件描述
		nameobj.add(strlen[15]);labelobj.add(lavenlen[15]);indexobj.add(strlen[15]);//组件单位用量
		nameobj.add(strlen[2]);labelobj.add(lavenlen[2]);indexobj.add(strlen[2]);//计划数量
		//nameobj.add(strlen[0]);labelobj.add(lavenlen[0]);indexobj.add(strlen[0]);//随工单编号
		nameobj.add(strlen[5]);labelobj.add(lavenlen[5]);indexobj.add(strlen[5]);//领用数
		nameobj.add(strlen[33]);labelobj.add(lavenlen[33]);indexobj.add(strlen[33]);//应出勤人数
		nameobj.add(strlen[34]);labelobj.add(lavenlen[34]);indexobj.add(strlen[34]);//实出勤人数
		/**处理接上班(正常)**//*
		for(int i=0;i<processList00.size();i++){
			Process process = processList00.get(i);
			String label = "接上班"+process.getProcessName()+"(正常)";
			String name ="GXJSBZC_"+process.getId();
			String index="GXJSBZC_"+process.getId();
			indexobj.add(index);
			nameobj.add(name);
			labelobj.add(label);
		}
		*//**处理接上班 (正常)end**//*
		*//**处理接上班(返修)**//*
		for(int i=0;i<processList00.size();i++){
			Process process = processList00.get(i);
			String label = "接上班"+process.getProcessName()+"(返修)";
			String name ="GXJSBFX_"+process.getId();
			String index="GXJSBFX_"+process.getId();
			indexobj.add(index);
			nameobj.add(name);
			labelobj.add(label);
		}
		*//**处理接上班 (返修)end**/
		
		
		/**处理交下班(正常)**//*
		for(int i=0;i<processList00.size();i++){
			Process process = processList00.get(i);
			String label = "交下班"+process.getProcessName()+"(正常)";
			String name ="GXJXBZC_"+process.getId();
			String index="GXJXBZC_"+process.getId();
			indexobj.add(index);
			nameobj.add(name);
			labelobj.add(label);
		}
		*//**处理交下班(正常)end**//*
		
		*//**处理交下班(返修)**//*
		for(int i=0;i<processList00.size();i++){
			Process process = processList00.get(i);
			String label = "交下班"+process.getProcessName()+"(返修)";
			String name ="GXJXBFX_"+process.getId();
			String index="GXJXBFX_"+process.getId();
			indexobj.add(index);
			nameobj.add(name);
			labelobj.add(label);
		}
		*//**处理交下班 (返修)end**/
		/**处理接上班**/
		//boolean flagProcess1 = false;//判断工序是否都为0，如不为0，在首页投入产出表中显示
		//boolean flagProcess2 = false;//判断工序返修是否都为0，如不为0，在首页投入产出表中显示
		for(int i=0;i<processList00.size();i++){
			Process process = processList00.get(i);
			String label = "接/"+process.getProcessName();
			String name ="GXJSBZC_"+process.getId();
			String index="GXJSBZC_"+process.getId();
			indexobj.add(index);
			nameobj.add(name);
			labelobj.add(label);
			
			process = processList00.get(i);
			label = "接/"+process.getProcessName()+"/返";
			name ="GXJSBFX_"+process.getId();
			index="GXJSBFX_"+process.getId();
			indexobj.add(index);
			nameobj.add(name);
			labelobj.add(label);
		}
		/**处理接上班end**/
		/**处理交下班**/
		for(int i=0;i<processList00.size();i++){
			Process process = processList00.get(i);
			String label = "交/"+process.getProcessName();
			String name ="GXJXBZC_"+process.getId();
			String index="GXJXBZC_"+process.getId();
			indexobj.add(index);
			nameobj.add(name);
			labelobj.add(label);
			
			process = processList00.get(i);
			label = "交/"+process.getProcessName()+"/返";
			name ="GXJXBFX_"+process.getId();
			index="GXJXBFX_"+process.getId();
			indexobj.add(index);
			nameobj.add(name);
			labelobj.add(label);
		}
		/**处理交下班end**/
		nameobj.add(strlen[9]);labelobj.add(lavenlen[9]);indexobj.add(strlen[9]);//报废数
		nameobj.add(strlen[3]);labelobj.add(lavenlen[3]);indexobj.add(strlen[3]);//接上班零头数
		nameobj.add(strlen[4]);labelobj.add(lavenlen[4]);indexobj.add(strlen[4]);//接上班异常零头数
		//nameobj.add(strlen[6]);labelobj.add(lavenlen[6]);indexobj.add(strlen[6]);//倍数
		nameobj.add(strlen[7]);labelobj.add(lavenlen[7]);indexobj.add(strlen[7]);//入库数
		nameobj.add(strlen[8]);labelobj.add(lavenlen[8]);indexobj.add(strlen[8]);//交下班零头数
		nameobj.add(strlen[17]);labelobj.add(lavenlen[17]);indexobj.add(strlen[17]);//交下班异常零头数
		nameobj.add(strlen[10]);labelobj.add(lavenlen[10]);indexobj.add(strlen[10]);//成型异常表面维修数
		nameobj.add(strlen[11]);labelobj.add(lavenlen[11]);indexobj.add(strlen[11]);//成型维修表面返回数
		nameobj.add(strlen[16]);labelobj.add(lavenlen[16]);indexobj.add(strlen[16]);//当班检验合格数
//		nameobj.add(strlen[26]);labelobj.add(lavenlen[26]);indexobj.add(strlen[26]);//当班报工数
		nameobj.add(strlen[18]);labelobj.add(lavenlen[18]);indexobj.add(strlen[18]);//一次合格率
		nameobj.add(strlen[19]);labelobj.add(lavenlen[19]);indexobj.add(strlen[19]);//投入总数量
		nameobj.add(strlen[20]);labelobj.add(lavenlen[20]);indexobj.add(strlen[20]);//产出总数量
		nameobj.add(strlen[21]);labelobj.add(lavenlen[21]);indexobj.add(strlen[21]);//数量差异
		nameobj.add(strlen[22]);labelobj.add(lavenlen[22]);indexobj.add(strlen[22]);//计划达成率
		nameobj.add(strlen[27]);labelobj.add(lavenlen[27]);indexobj.add(strlen[27]);//单据状态
		nameobj.add(strlen[26]);labelobj.add(lavenlen[26]);indexobj.add(strlen[26]);//当班报工数
		nameobj.add(strlen[28]);labelobj.add(lavenlen[28]);indexobj.add(strlen[28]);//校验差异
		nameobj.add(strlen[33]);labelobj.add(lavenlen[33]);indexobj.add(strlen[33]);//应出勤人数
		nameobj.add(strlen[34]);labelobj.add(lavenlen[34]);indexobj.add(strlen[34]);//实出勤人数
		
		
		JSONArray jsonarray = new JSONArray();
		for(int i=0;i<nameobj.size();i++){ 
			JSONObject jsonobject = new JSONObject();
			jsonobject.put("name", nameobj.get(i));
			jsonobject.put("index", indexobj.get(i));
			jsonobject.put("label", labelobj.get(i));
			jsonobject.put("width", 150);
			jsonobject.put("fixed", true);
			jsonarray.add(jsonobject);
		}
		
		
		
		
		return jsonarray;
	}
	/**
	 * 页面投入产出报表显示方法
	 */
	public JSONArray showInoutJsonData1(String[] strlen,String[] lavenlen){
		List<String> nameobj = new ArrayList<String>();
		List<String> labelobj = new ArrayList<String>();
		List<String> indexobj = new ArrayList<String>();
		
		String[] propertyNames = {"isDel","state"};
		String[] propertyValues={"N","1"};
		List<Process> processList00 = processservice.getList(propertyNames, propertyValues);
		
		nameobj.add(strlen[25]);labelobj.add(lavenlen[25]);indexobj.add(strlen[25]);//组件描述
		nameobj.add(strlen[5]);labelobj.add(lavenlen[5]);indexobj.add(strlen[5]);//领用数
		/**处理接上班**/
		//boolean flagProcess1 = false;//判断工序是否都为0，如不为0，在首页投入产出表中显示
		//boolean flagProcess2 = false;//判断工序返修是否都为0，如不为0，在首页投入产出表中显示
		for(int i=0;i<processList00.size();i++){
			Process process = processList00.get(i);
			String label = "接/"+process.getProcessName();
			String name ="GXJSBZC_"+process.getId();
			String index="GXJSBZC_"+process.getId();
			indexobj.add(index);
			nameobj.add(name);
			labelobj.add(label);
			
			process = processList00.get(i);
			label = "接/"+process.getProcessName()+"/返";
			name ="GXJSBFX_"+process.getId();
			index="GXJSBFX_"+process.getId();
			indexobj.add(index);
			nameobj.add(name);
			labelobj.add(label);
		}
		/**处理接上班end**/
		/**处理交下班**/
		for(int i=0;i<processList00.size();i++){
			Process process = processList00.get(i);
			String label = "交/"+process.getProcessName();
			String name ="GXJXBZC_"+process.getId();
			String index="GXJXBZC_"+process.getId();
			indexobj.add(index);
			nameobj.add(name);
			labelobj.add(label);
			
			process = processList00.get(i);
			label = "交/"+process.getProcessName()+"/返";
			name ="GXJXBFX_"+process.getId();
			index="GXJXBFX_"+process.getId();
			indexobj.add(index);
			nameobj.add(name);
			labelobj.add(label);
		}
		/**处理交下班end**/
		
		
		nameobj.add(strlen[19]);labelobj.add(lavenlen[19]);indexobj.add(strlen[19]);//投入总数量
		nameobj.add(strlen[15]);labelobj.add(lavenlen[15]);indexobj.add(strlen[15]);//单位用量
		nameobj.add(strlen[7]);labelobj.add(lavenlen[7]);indexobj.add(strlen[7]);//入库数
		nameobj.add(strlen[9]);labelobj.add(lavenlen[9]);indexobj.add(strlen[9]);//组件报废数
		nameobj.add(strlen[8]);labelobj.add(lavenlen[8]);indexobj.add(strlen[8]);//交下班零头数
		nameobj.add(strlen[17]);labelobj.add(lavenlen[17]);indexobj.add(strlen[17]);//交下班异常零头数
		nameobj.add(strlen[3]);labelobj.add(lavenlen[3]);indexobj.add(strlen[3]);//接上班零头数
		nameobj.add(strlen[4]);labelobj.add(lavenlen[4]);indexobj.add(strlen[4]);//接上班异常零头数
		nameobj.add(strlen[10]);labelobj.add(lavenlen[10]);indexobj.add(strlen[10]);//返修发货
		nameobj.add(strlen[11]);labelobj.add(lavenlen[11]);indexobj.add(strlen[11]);//返修收货
		nameobj.add(strlen[20]);labelobj.add(lavenlen[20]);indexobj.add(strlen[20]);//产出总数量
		nameobj.add(strlen[21]);labelobj.add(lavenlen[21]);indexobj.add(strlen[21]);//数量差异
		nameobj.add(strlen[16]);labelobj.add(lavenlen[16]);indexobj.add(strlen[16]);//生产数
		nameobj.add(strlen[26]);labelobj.add(lavenlen[26]);indexobj.add(strlen[26]);//当班报工数
		nameobj.add(strlen[28]);labelobj.add(lavenlen[28]);indexobj.add(strlen[28]);//校验差异
		nameobj.add(strlen[33]);labelobj.add(lavenlen[33]);indexobj.add(strlen[33]);//应出勤人数
		nameobj.add(strlen[34]);labelobj.add(lavenlen[34]);indexobj.add(strlen[34]);//实出勤人数
		
		JSONArray jsonarray = new JSONArray();
		for(int i=0;i<nameobj.size();i++){ 
			JSONObject jsonobject = new JSONObject();
			jsonobject.put("name", nameobj.get(i));
			jsonobject.put("index", indexobj.get(i));
			jsonobject.put("label", labelobj.get(i));
			//jsonobject.put("width", 150);
			//jsonobject.put("fixed", true);
			jsonarray.add(jsonobject);
		}
		
		
		
		
		return jsonarray;
	}
	
	
	
	

	/**
	 * 投入产出报表
	 */
	public Pager listjqGrid(Pager pager,HashMap<String,String> mapcheck,JSONArray jsonarray,String[] strlen) {
		
		List result = new ArrayList<JSONObject>();
		try{
			List<WorkingInout> workingInoutList=new ArrayList<WorkingInout>();
			
			pager = workingInoutDao.listjqGrid(pager, mapcheck);
			workingInoutList=pager.getList();
			for(int i=0;i<workingInoutList.size();i++){
				JSONObject map = new JSONObject();
				WorkingInout workinginout = workingInoutList.get(i);
				WorkingBill workingbill = workinginout.getWorkingbill();
				String aufnr = workingbill.getAufnr();
				
				String productDate = workingbill.getProductDate();
				String shift = workingbill.getShift();
				if(shift.length()>1){
					shift = shift.substring((shift.length()-1),shift.length());
				}
				String factoryUnitCode;
				if(workingbill.getTeam()==null||workingbill.getTeam().getFactoryUnit()==null){
					map.put(strlen[33],0);//如果没有相关联的班组或单元，则该投入产出表中，应出勤人数为0
					map.put(strlen[34],0);//如果没有相关联的班组或单元，则该投入产出表中，实出勤人数为0
				}else{
					factoryUnitCode = workingbill.getTeam().getFactoryUnit().getFactoryUnitCode();
					List<TempKaoqin> needTempKaoqinList = tempKaoqinService.getWorkNumList(productDate, shift, factoryUnitCode, "");//应出勤人数
					List<TempKaoqin> actualTempKaoqinList = tempKaoqinService.getWorkNumList(productDate, shift, factoryUnitCode, "2");//实出勤人数
					map.put(strlen[33],needTempKaoqinList.size());
					map.put(strlen[34],actualTempKaoqinList.size());
				}
				/** 接上班 */
			//	List<OddHandOver> oddHandOverListBefore1 = oddHandOverDao.getList("afterWorkingCode", workingbill.getWorkingBillCode());
				String[] propertyNamesLTJSB={"afterWorkingBillCode","isdel"};
				String[] propertyValuesLTJSB={workingbill.getWorkingBillCode(),"N"};
				List<ProcessHandover> processHandoverListLTJSB = processHandoverDao.getList(propertyNamesLTJSB, propertyValuesLTJSB);
				//List<OddHandOver> oddHandOverListBefore = new ArrayList<OddHandOver>();
				BigDecimal actualAmountLTJSB = new BigDecimal(0);
				BigDecimal unAmountLTJSB = new BigDecimal(0);
				for(int k=0;k<processHandoverListLTJSB.size();k++){
					ProcessHandover ph = processHandoverListLTJSB.get(k);
					if("零头数交接".equals(ph.getProcessHandoverTop().getType()) && "2".equals(ph.getProcessHandoverTop().getState())){
						actualAmountLTJSB = actualAmountLTJSB.add(new BigDecimal(ThinkWayUtil.null2o(ph.getActualHOMount())));
						unAmountLTJSB = unAmountLTJSB.add(new BigDecimal(ThinkWayUtil.null2o(ph.getUnHOMount())));
					}
				}
			//	for(int k=0;k<oddHandOverListBefore1.size();k++){
				//	OddHandOver oddHandOver = oddHandOverListBefore1.get(k);
			//		if(oddHandOver.getIsdel().equals("N")){
						//if(oddHandOver.getProcessHandover()!=null && oddHandOver.getProcessHandover().getProcessHandoverTop()!=null && !"2".equals(oddHandOver.getProcessHandover().getProcessHandoverTop().getState()))
						//continue;
				//		BigDecimal NewActualAmount = new BigDecimal(ThinkWayUtil.null2o(oddHandOver.getActualBomMount()));
				//		BigDecimal NewUnAmount = new BigDecimal(ThinkWayUtil.null2o(oddHandOver.getUnBomMount()));
				//		actualAmount = actualAmount.add(NewActualAmount);
				//		unAmount = unAmount.add(NewUnAmount);
						//oddHandOverListBefore.add(oddHandOver);
			//		}
			//	}
		//		Double afteroddamount =0.0d;
			//	Double afterunoddamount=0.0d;
				//if(oddHandOverListBefore!=null && oddHandOverListBefore.size()>0){
			//		afteroddamount = ThinkWayUtil.null2o(oddHandOverListBefore.get(0).getActualBomMount());//接上班零头数
			//		afteroddamount = actualAmount.doubleValue();//接上班零头数
			//		afterunoddamount = ThinkWayUtil.null2o(oddHandOverListBefore.get(0).getUnBomMount());//接上班异常零头数
			//		afterunoddamount = unAmount.doubleValue();//接上班异常零头数
			//	}
				map.put(strlen[3], actualAmountLTJSB);//接上班零头数
				map.put(strlen[4], unAmountLTJSB);//接上班异常零头数
				
				/** 交下班 */
			//	List<OddHandOver> oddHandOverListAfter1 = oddHandOverDao.getList("beforeWokingCode", workingbill.getWorkingBillCode());
			//	List<OddHandOver> oddHandOverListAfter = new ArrayList<OddHandOver>();
				String[] propertyNamesLTJXB={"workingBillCode","isdel"};
				String[] propertyValuesLTJXB={workingbill.getWorkingBillCode(),"N"};
				List<ProcessHandover> processHandoverListLTJXB = processHandoverDao.getList(propertyNamesLTJXB, propertyValuesLTJXB);
				BigDecimal actualAmountLTJXB = new BigDecimal(0);
				BigDecimal unAmountLTJXB = new BigDecimal(0);
			//	for(int k=0;k<processHandoverListLTJXB.size();k++){
			//		OddHandOver oddHandOver = oddHandOverListAfter1.get(k);
			//		if(oddHandOver.getIsdel().equals("N")){
						//if(oddHandOver.getProcessHandover()!=null && oddHandOver.getProcessHandover().getProcessHandoverTop()!=null && !"2".equals(oddHandOver.getProcessHandover().getProcessHandoverTop().getState()))
						//continue;
			//			BigDecimal NewActualAmount = new BigDecimal(ThinkWayUtil.null2o(oddHandOver.getActualBomMount()));
			//			BigDecimal NewUnAmount = new BigDecimal(ThinkWayUtil.null2o(oddHandOver.getUnBomMount()));
			//			actualAmount = actualAmount.add(NewActualAmount);
			//			unAmount = unAmount.add(NewUnAmount);
			//			oddHandOverListAfter.add(oddHandOver);
		//			}
			//	}
				for(int k=0;k<processHandoverListLTJXB.size();k++){
					ProcessHandover ph = processHandoverListLTJXB.get(k);
					if("零头数交接".equals(ph.getProcessHandoverTop().getType()) && "2".equals(ph.getProcessHandoverTop().getState())){
						actualAmountLTJXB = actualAmountLTJXB.add(new BigDecimal(ThinkWayUtil.null2o(ph.getActualHOMount())));
						unAmountLTJXB = unAmountLTJXB.add(new BigDecimal(ThinkWayUtil.null2o(ph.getUnHOMount())));
					}
				}
				map.put(strlen[8],actualAmountLTJXB);//交下班零头数
				map.put(strlen[17],unAmountLTJXB);//交下班异常零头数
			//	Double beforeoddamount = 0.0d;
			//	Double beforeunoddamount = 0.0d;
		//		if(oddHandOverListAfter!=null && oddHandOverListAfter.size()>0){
				//	beforeoddamount = ThinkWayUtil.null2o(oddHandOverListAfter.get(0).getActualBomMount());//交下班零头数
		//			beforeoddamount = actualAmount.doubleValue();//交下班零头数
				//	beforeunoddamount = ThinkWayUtil.null2o(oddHandOverListAfter.get(0).getUnBomMount());//交下班异常零头数
		//			beforeunoddamount = unAmount.doubleValue();//交下班异常零头数
		//		}
				
				map.put(strlen[29], workingbill.getWorkcenter());//单元
				map.put(strlen[0], workingbill.getWorkingBillCode());
				map.put(strlen[1], workinginout.getMaterialCode());
				map.put(strlen[2], workingbill.getPlanCount());
				map.put(strlen[23], workingbill.getMatnr());//产品编码
				map.put(strlen[24], workingbill.getMaktx());//产品描述
				
				String materialname = workinginout.getMaterialName();
				if(ThinkWayUtil.null2String(materialname).equals("")){//处理不知道什么问题导致物料描述没有的问题
					materialname = bomservice.getMaterialName(workinginout.getMaterialCode());
				}
				
				map.put(strlen[25],materialname);//组件描述
				map.put(strlen[26],workingbill.getDailyWorkTotalAmount());//当班报工数量
				map.put(strlen[27],workingbill.getIsHand().equals("Y")?"交接完成":"未交接完成");//单据状态
				
				
				List<PickDetail> pickdetailList = pickdetaildao.finddetailByapp(workingbill.getId(), "2");//"2" 表示 确认状态数据
				Double recipientsamount = 0.00d;
				for(int y=0;y<pickdetailList.size();y++){
					PickDetail pickdetail1 = pickdetailList.get(y);
					if(pickdetail1.getMaterialCode().equals(workinginout.getMaterialCode())){
						if("261".equals(pickdetail1.getPickType())){//领料
							//sum += Double.parseDouble(pickdetail1.getPickAmount());
							recipientsamount = ArithUtil.add(Double.parseDouble(pickdetail1.getPickAmount()), recipientsamount);
						}else{//退料
							//sum -= Double.parseDouble(pickdetail1.getPickAmount());
							recipientsamount = ArithUtil.sub(recipientsamount,Double.parseDouble(pickdetail1.getPickAmount()));
						}
					}
				}
				if(workinginout.getScrapNumber()==null){
					workinginout.setScrapNumber(0.0d);
				}
				map.put(strlen[5], recipientsamount);//领用数
				map.put(strlen[7],workingbill.getTotalSingleAmount());//入库数
			
				map.put(strlen[9],workinginout.getScrapNumber());//报废数
				Set<Repair> repairSet = workingbill.getRepair();
				BigDecimal fxfh = new BigDecimal(0);
				if(repairSet!=null){
					for(Repair r : repairSet){
						if("1".equals(r.getState())){
							Set<RepairPiece> repairPieceSet = r.getRpieceSet();
							if(repairPieceSet!=null){
								for(RepairPiece rp : repairPieceSet){
									if(rp.getRpcode().equals(workinginout.getMaterialCode())){
										fxfh = fxfh.add(new BigDecimal(rp.getRpcount()==null?"0":rp.getRpcount()));
									}
								}
							}
						}
					}
					map.put(strlen[10],fxfh);//返修发货数量
				}else{
					//map.put(strlen[10],workingbill.getTotalRepairAmount());//返修发货数量
					map.put(strlen[10],fxfh);//返修发货数量
				}
				
				Set<Repairin> repairinSet = workingbill.getRepairin();
				BigDecimal fxsh = new BigDecimal(0);
				if(repairinSet!=null){
					for(Repairin r : repairinSet){
						if("1".equals(r.getState())){
							Set<RepairinPiece> repairinPieceSet = r.getRpieceSet();
							if(repairinPieceSet!=null){
								for(RepairinPiece rp : repairinPieceSet){
									if(rp.getRpcode().equals(workinginout.getMaterialCode())){
										fxsh = fxsh.add(new BigDecimal(rp.getRpcount()==null?"0":rp.getRpcount()));
									}
								}
							}
						}
					}
					map.put(strlen[11],fxsh);//返修收货数量
				}else{
					//map.put(strlen[11],workingbill.getTotalRepairinAmount());//返修收货数量
					map.put(strlen[11],fxsh);//返修收货数量
				}
				
				
				
				
				map.put(strlen[12],workingbill.getProductDate());//生产日期
				map.put(strlen[13],workingbill.getShift());//班次
				map.put(strlen[14],workingbill.getAufnr());//生产订单号
				List<Bom> bomList = bomservice.findBom(aufnr, workingbill.getProductDate(), workinginout.getMaterialCode(), workingbill.getWorkingBillCode());
				Double bomamount = 0.00d;
				for(Bom bom :bomList){
					bomamount +=bom.getMaterialAmount();
				}
				Double mount = 0.0d;
				if(bomamount<=0){
					mount = 0d;
				}else{
					mount = ArithUtil.round(ArithUtil.div(workingbill.getPlanCount(),bomamount),2);
				}
				map.put(strlen[6],mount);//倍数 = 随工单计划数量 / bom数量  保留2位小数
				Double dwyl = 0.0;
				//Double dwyl = ArithUtil.round(ArithUtil.div(bomamount, workingbill.getPlanCount()), 2);//单位用量
				String s = workinginout.getMaterialCode().substring(0, 1);
				if("5".equals(s)){
					List<UnitConversion> unitConversionList = unitConversionService.getList("matnr",workingbill.getMatnr());
					if(unitConversionList!=null && unitConversionList.size()>0){
						if(unitConversionList.get(0).getConversationRatio()!=null && !unitConversionList.get(0).getConversationRatio().equals("")){
							if(new BigDecimal(0).compareTo(new BigDecimal(unitConversionList.get(0).getConversationRatio()))!=0){
								dwyl = ArithUtil.round(ArithUtil.div(1, unitConversionList.get(0).getConversationRatio()), 4);
							}
						}
					}
				}else{
					if(new BigDecimal(0).compareTo(new BigDecimal(workingbill.getPlanCount()))!=0){
						dwyl = ArithUtil.round(ArithUtil.div(bomamount, workingbill.getPlanCount()), 2);//单位用量
					}
					//map.put(strlen[15],dwyl);//组件单位用量 = BOM需求数量  / 随工单计划数量 保留2位小数
				}
				map.put(strlen[15],dwyl);//组件单位用量 = MES中基本信息--计量单位转换中的1/兑换比例
			
				Double totalsingleamount = ThinkWayUtil.null2o(workingbill.getTotalSingleAmount());//入库数
				
				
				
//				Double dbjyhgs = ArithUtil.sub(ArithUtil.sub(ArithUtil.add(ArithUtil.add(actualAmountLTJSB.doubleValue(), unAmountLTJSB.doubleValue()),totalsingleamount),actualAmountLTJXB.doubleValue()),unAmountLTJXB.doubleValue());//当班检验合格数 = 接上班零头数 + 接上班异常零头数 + 入库数 - 交下班零头数 - 交下班异常零头数
				BigDecimal WarehousingNum = new BigDecimal(ThinkWayUtil.null2o(workingbill.getTotalSingleAmount()));
				BigDecimal qualifiedNum = WarehousingNum.add(actualAmountLTJXB).add(unAmountLTJXB).subtract(actualAmountLTJSB).subtract(unAmountLTJSB).add(fxfh).subtract(fxsh);//生产数=入库+交/零头+交/零头/返-接/零头-接/零头/返+返修发货-返修收货
				Double dbjyhgs = qualifiedNum.doubleValue();
				map.put(strlen[16],dbjyhgs);//当班检验合格数
				map.put(strlen[18],"");//一次合格率 TODO 此处计算一次合格率，现在无法计算
				
				Double trzsl = 0.00d;
				Double cczsl = 0.00d;
				trzsl = ArithUtil.add(trzsl, ThinkWayUtil.null2o(recipientsamount));//领用数
				//trzsl = ArithUtil.add(trzsl, ThinkWayUtil.null2o(workinginout.getScrapNumber()));//报废数
				
			//	cczsl = ArithUtil.add(cczsl,ThinkWayUtil.null2o(workingbill.getTotalSingleAmount()));//入库数
//				cczsl = ArithUtil.add(cczsl,ThinkWayUtil.null2o(workingbill.getTotalRepairinAmount()));//返修收货数量  modify 
//				cczsl = ArithUtil.round(ArithUtil.mul(cczsl, dwyl),2);
				
				for(int y=0;y<jsonarray.size();y++){
					JSONObject json = (JSONObject) jsonarray.get(y);
					String name = json.getString("name");
					int firstls = StringUtils.indexOf(name, "GXJSBZC_");
					int firstls00 = StringUtils.indexOf(name, "GXJXBZC_");
					if(firstls >= 0){//如果找到，表示是接上班工序交接
						String processid = StringUtils.substringAfter(name, "GXJSBZC_");//获取接上班ID
				//		String[] propertyNames = {"processid","afterworkingbill.id","materialCode"};
						String[] propertyNames = {"processid","afterworkingbill.id","matnr","isdel"};
						String[] propertyValues={processid,workinginout.getWorkingbill().getId(),workinginout.getWorkingbill().getMatnr(),"N"};
						/*HandOverProcess handoverprocess = null;
						List<HandOverProcess> handoverprocessList = handoverprocessdao.getList(propertyNames, propertyValues);
						if(handoverprocessList!=null && handoverprocessList.size()>1){
							for(HandOverProcess hod : handoverprocessList){
								if(hod.getAfterworkingbill().getId().equals(hod.getAfterworkingbill().getId())){
									handoverprocess = hod;
									break;
								}
							}
						}else{
							handoverprocess = handoverprocessdao.get(propertyNames, propertyValues);
						}*/
						List<ProcessHandover> processHandoverList = processHandoverDao.getList(propertyNames, propertyValues);
						Double zcjjsl = 0.00d;
						Double fxjjsl = 0.00d;
//						if(handoverprocess != null){
//							zcjjsl = handoverprocess.getAmount();//正常交接数量
//							fxjjsl = handoverprocess.getRepairAmount();//返修交接数量
						if(processHandoverList != null && processHandoverList.size()>0){
							//BigDecimal amout = new BigDecimal(0);
							//BigDecimal repairAmout = new BigDecimal(0);
							//BigDecimal mout = new BigDecimal(0);
							//BigDecimal repairMout = new BigDecimal(0);
							for(ProcessHandover processHandover:processHandoverList){
								if("2".equals(processHandover.getProcessHandoverTop().getState())){
									Set<ProcessHandoverSon> processHandoverSon = processHandover.getProcessHandoverSonSet();
									for(ProcessHandoverSon p:processHandoverSon){
										//BigDecimal bomAmount = new BigDecimal(p.getBomAmount());
										//BigDecimal cqamount = new BigDecimal(p.getCqamount());
										//BigDecimal repairNumber = new BigDecimal(p.getRepairNumber());
										//BigDecimal cqrepairamount = new BigDecimal(p.getCqrepairamount());
										//amout = amout.add(bomAmount).add(cqamount);
										//repairAmout = repairAmout.add(repairNumber).add(cqrepairamount);
										if(p.getBomCode().equals(workinginout.getMaterialCode())){
											zcjjsl = new BigDecimal(zcjjsl).add(new BigDecimal(p.getBomAmount()==null?"0":p.getBomAmount())).setScale(3, RoundingMode.HALF_UP).doubleValue();
											fxjjsl = new BigDecimal(fxjjsl).add(new BigDecimal(p.getRepairNumber()==null?"0":p.getRepairNumber())).setScale(3, RoundingMode.HALF_UP).doubleValue();
										}								
									}
								}
								//mout = mout.add(amout);
								//repairMout = repairMout.add(repairAmout);
								//zcjjsl = mout.doubleValue();
								//fxjjsl = repairMout.doubleValue();
							}
							//map.put("GXJSBZC_"+processid, "0");//正常交接数量
							//map.put("GXJSBFX_"+processid,  "0");//返修交接数量
						}
						map.put("GXJSBZC_"+processid,zcjjsl);//正常交接数量
						map.put("GXJSBFX_"+processid,fxjjsl);//返修交接数量!=						
						trzsl = ArithUtil.add(trzsl, ThinkWayUtil.null2o(zcjjsl));//投入:正常交接数量
						trzsl = ArithUtil.add(trzsl, ThinkWayUtil.null2o(fxjjsl));//投入:返修交接数量
					}
					if(firstls00>=0){//交下班
						String processid = StringUtils.substringAfter(name, "GXJXBZC_");//获取交下班ID
			//			String[] propertyNames = {"processid","beforworkingbill.id","materialCode"};
						String[] propertyNames = {"processid","workingBill.id","matnr","isdel"};
						String[] propertyValues={processid,workinginout.getWorkingbill().getId(),workinginout.getWorkingbill().getMatnr(),"N"};
			//			HandOverProcess handoverprocess = handoverprocessdao.get(propertyNames, propertyValues);
						List<ProcessHandover> processHandoverList = processHandoverDao.getList(propertyNames, propertyValues);
						Double zcjjsl = 0.00d;
						Double fxjjsl = 0.00d;
//						if(handoverprocess != null){
//							zcjjsl = handoverprocess.getAmount();//正常交接数量
//							fxjjsl = handoverprocess.getRepairAmount();//返修交接数量
//						}
						if(processHandoverList != null && processHandoverList.size()>0){
							//BigDecimal amout = new BigDecimal(0);
							//BigDecimal repairAmout = new BigDecimal(0);
							//BigDecimal mout = new BigDecimal(0);
							//BigDecimal repairMout = new BigDecimal(0);
							for(ProcessHandover processHandover:processHandoverList){
								if("2".equals(processHandover.getProcessHandoverTop().getState())){
									Set<ProcessHandoverSon> processHandoverSon = processHandover.getProcessHandoverSonSet();
									for(ProcessHandoverSon p:processHandoverSon){
										//BigDecimal bomAmount = new BigDecimal(p.getBomAmount());
										//BigDecimal cqamount = new BigDecimal(p.getCqamount());
										//BigDecimal repairNumber = new BigDecimal(p.getRepairNumber());
										//BigDecimal cqrepairamount = new BigDecimal(p.getCqrepairamount());
										//amout = amout.add(bomAmount).add(cqamount);
										//repairAmout = repairAmout.add(repairNumber).add(cqrepairamount);
										if(p.getBomCode().equals(workinginout.getMaterialCode())){
											zcjjsl = new BigDecimal(zcjjsl).add(new BigDecimal(p.getBomAmount()==null?"0":p.getBomAmount())).setScale(3, RoundingMode.HALF_UP).doubleValue();
											fxjjsl = new BigDecimal(fxjjsl).add(new BigDecimal(p.getRepairNumber()==null?"0":p.getRepairNumber())).setScale(3, RoundingMode.HALF_UP).doubleValue();
										}
									}
								}
								//mout = mout.add(amout);
								//repairMout = repairMout.add(repairAmout);
								//zcjjsl = mout.doubleValue();
								//fxjjsl = repairMout.doubleValue();
							}
							//map.put("GXJXBZC_"+processid, "0");//正常交接数量
							//map.put("GXJXBFX_"+processid,  "0");//返修交接数量
						}

						map.put("GXJXBZC_"+processid,zcjjsl);//正常交接数量
						map.put("GXJXBFX_"+processid,fxjjsl);//返修交接数量
						trzsl = ArithUtil.sub(trzsl,ThinkWayUtil.null2o(zcjjsl));//投入:正常交接数量
						trzsl = ArithUtil.sub(trzsl,ThinkWayUtil.null2o(fxjjsl));//投入:返修交接数量
					}
					//int firstls1 = StringUtils
					
				}
				
				map.put(strlen[19],trzsl);//投入总数量 = 领用数 + 接上班正常和返修数量
				
				cczsl = ArithUtil.add(cczsl,ThinkWayUtil.null2o(workingbill.getTotalSingleAmount()));//入库数
				Double repairAmount = 0.00d;
				//repairAmount = ArithUtil.add(repairAmount,ThinkWayUtil.null2o(workingbill.getTotalRepairAmount()));//成型异常表面维修数 -出去
				repairAmount = ArithUtil.add(repairAmount,fxfh.doubleValue());//成型异常表面维修数 -出去
				Double repairinAmount = 0.00d;
				//repairinAmount = ArithUtil.add(repairinAmount,ThinkWayUtil.null2o(workingbill.getTotalRepairinAmount()));//成型维修返回数 -回来
				repairinAmount = ArithUtil.add(repairinAmount,fxsh.doubleValue());//成型维修返回数 -回来
				Double zjbfs = 0.00d;
				zjbfs = ArithUtil.add(zjbfs, ThinkWayUtil.null2o(workinginout.getScrapNumber()));//报废数
				String str = workinginout.getMaterialCode().substring(0, 1);
				if(!"5".equals(str)){
					//(成品入库数+交下班零头数+交下班零头返修数+成型异常表面维修数-成型维修返回数-接上班零头数-接上班零头返修数)*单位用量+组件报废数
//					cczsl = (new BigDecimal(cczsl).add(actualAmountLTJXB).add(unAmountLTJXB).add(new BigDecimal(repairAmount)).subtract(new BigDecimal(repairinAmount)).subtract(actualAmountLTJSB).subtract(unAmountLTJSB)).multiply(new BigDecimal(dwyl)).add(new BigDecimal(zjbfs)).setScale(2, RoundingMode.HALF_UP).doubleValue();
					//产出汇总公式修改=（入库+交/零头+交/零头/返-接/零头-接/零头/返）*组件用量+返修发货-返修收货+组件报废
//					cczsl = ((new BigDecimal(cczsl).add(actualAmountLTJXB).add(unAmountLTJXB).subtract(actualAmountLTJSB).subtract(unAmountLTJSB)).multiply(new BigDecimal(dwyl)).add(fxfh).subtract(fxsh).add(new BigDecimal(zjbfs))).setScale(2, RoundingMode.HALF_UP).doubleValue();
					cczsl = (new BigDecimal(cczsl).add(actualAmountLTJXB).add(unAmountLTJXB).subtract(actualAmountLTJSB).subtract(unAmountLTJSB)).doubleValue();
					cczsl = (new BigDecimal(cczsl).multiply(new BigDecimal(dwyl)).doubleValue());
					cczsl = (new BigDecimal(cczsl).add(fxfh).subtract(fxsh).add(new BigDecimal(zjbfs))).setScale(2, RoundingMode.HALF_UP).doubleValue();
				}else{
					cczsl = new BigDecimal(cczsl).multiply(new BigDecimal(dwyl)).setScale(2, RoundingMode.HALF_UP).doubleValue();
				}
					
				//cczsl = (cczsl+beforeoddamount+beforeunoddamount+repairAmount-repairinAmount -afteroddamount-afterunoddamount)*dwyl+zjbfs;
				map.put(strlen[20],cczsl);//产出总数量 = (入库数 + 返修收货数量)*单位用量  保留2位小数  /**modify:产出总数量=入库数**/ 
				map.put(strlen[21],ArithUtil.sub(trzsl, cczsl));//数量差异= 投入总数量 - 产出总数量
				Double jhdcl = 0d;
				if(new BigDecimal(0).compareTo(new BigDecimal(workingbill.getPlanCount()))!=0){
					jhdcl= ArithUtil.round(ArithUtil.div(dbjyhgs, workingbill.getPlanCount())*100,2);//计划达成率
				}
				map.put(strlen[22],jhdcl+"%");//计划达成率 = 当班检验合格数 / 计划数  
				BigDecimal cost = new BigDecimal(0);
				//if(cost.compareTo(new BigDecimal(ThinkWayUtil.null2o(workingbill.getTotalSingleAmount())))!=0 && cost.compareTo(new BigDecimal(ThinkWayUtil.null2o(recipientsamount)))!=0 && cost.compareTo(new BigDecimal(ThinkWayUtil.null2o(trzsl)))!=0){
					
				//}
				Double bgs = 0.00d;
				bgs = ThinkWayUtil.null2o(workingbill.getDailyWorkTotalAmount());
				map.put(strlen[26],bgs);//当班报工数
				Double jycl = 0.00d;
				Double rks = ThinkWayUtil.null2o(workingbill.getTotalSingleAmount());//入库数
				//beforeoddamount  //交下班零头
				//bgs					//报工数
				//afteroddamount   //接上班零头数
				//repairinAmount  //返修收货
				jycl = (new BigDecimal(rks).add(actualAmountLTJXB)).subtract( (new BigDecimal(bgs).add(actualAmountLTJSB).add(new BigDecimal(repairinAmount)))).doubleValue();
				map.put(strlen[28],jycl);//校验差异    公式=（入库数+交下班零头）-（报工数+接上班零头+返修收货）
				map.put(strlen[31],workingbill.getTeam()==null?"":workingbill.getTeam().getTeamName());//班组名称
				result.add(map);
		}
		}catch(Exception e){
			log.error(e);
			e.printStackTrace();
		}
		
		pager.setList(result);
		
		return pager;
	}

	
	
	
	
	/**
	 * 投入产出报表
	 */
	public JSONArray findInoutByJsonData(JSONArray jsonarray,HashMap<String,String> mapcheck,String[] strlen,int my_id) {
		
		
		JSONArray jsonstr = new JSONArray();
		try{
			List<WorkingInout> workingInoutList=new ArrayList<WorkingInout>();
			if(my_id==1)
			{
				workingInoutList = this.findPagerByWorkingBillInout(mapcheck);
			}
			else if(my_id==2)
			{
				workingInoutList = this.workingInoutDao.findWbinoutput(mapcheck.get("wbid"));
			}
			for(int i=0;i<workingInoutList.size();i++){
				JSONObject map = new JSONObject();
				WorkingInout workinginout = workingInoutList.get(i);
				WorkingBill workingbill = workinginout.getWorkingbill();
				String aufnr = workingbill.getAufnr();
				
				String productDate = workingbill.getProductDate();
				String shift = workingbill.getShift();
				if(shift.length()>1){
					shift = shift.substring((shift.length()-1),shift.length());
				}
				String factoryUnitCode;
				if(workingbill.getTeam()==null||workingbill.getTeam().getFactoryUnit()==null){
					map.put(strlen[33],0);//如果没有相关联的班组或单元，则该投入产出表中，应出勤人数为0
					map.put(strlen[34],0);//如果没有相关联的班组或单元，则该投入产出表中，实出勤人数为0
				}else{
					factoryUnitCode = workingbill.getTeam().getFactoryUnit().getFactoryUnitCode();
					List<TempKaoqin> needTempKaoqinList = tempKaoqinService.getWorkNumList(productDate, shift, factoryUnitCode, "");//应出勤人数
					List<TempKaoqin> actualTempKaoqinList = tempKaoqinService.getWorkNumList(productDate, shift, factoryUnitCode, "2");//实出勤人数
					map.put(strlen[33],needTempKaoqinList.size());
					map.put(strlen[34],actualTempKaoqinList.size());
				}
				/** 接上班 */
				String[] propertyNamesLTJSB={"afterWorkingBillCode","isdel"};
				String[] propertyValuesLTJSB={workingbill.getWorkingBillCode(),"N"};
				List<ProcessHandover> processHandoverListLTJSB = processHandoverDao.getList(propertyNamesLTJSB, propertyValuesLTJSB);
				BigDecimal actualAmountLTJSB = new BigDecimal(0);
				BigDecimal unAmountLTJSB = new BigDecimal(0);
				for(int k=0;k<processHandoverListLTJSB.size();k++){
					ProcessHandover ph = processHandoverListLTJSB.get(k);
					if("零头数交接".equals(ph.getProcessHandoverTop().getType()) && "2".equals(ph.getProcessHandoverTop().getState())){
						actualAmountLTJSB = actualAmountLTJSB.add(new BigDecimal(ThinkWayUtil.null2o(ph.getActualHOMount())));
						unAmountLTJSB = unAmountLTJSB.add(new BigDecimal(ThinkWayUtil.null2o(ph.getUnHOMount())));
					}
				}
				map.put(strlen[3], actualAmountLTJSB);//接上班零头数
				map.put(strlen[4], unAmountLTJSB);//接上班异常零头数
				
				/** 交下班 */
				String[] propertyNamesLTJXB={"workingBillCode","isdel"};
				String[] propertyValuesLTJXB={workingbill.getWorkingBillCode(),"N"};
				List<ProcessHandover> processHandoverListLTJXB = processHandoverDao.getList(propertyNamesLTJXB, propertyValuesLTJXB);
				BigDecimal actualAmountLTJXB = new BigDecimal(0);
				BigDecimal unAmountLTJXB = new BigDecimal(0);
				for(int k=0;k<processHandoverListLTJXB.size();k++){
					ProcessHandover ph = processHandoverListLTJXB.get(k);
					if("零头数交接".equals(ph.getProcessHandoverTop().getType()) && "2".equals(ph.getProcessHandoverTop().getState())){
						actualAmountLTJXB = actualAmountLTJXB.add(new BigDecimal(ThinkWayUtil.null2o(ph.getActualHOMount())));
						unAmountLTJXB = unAmountLTJXB.add(new BigDecimal(ThinkWayUtil.null2o(ph.getUnHOMount())));
					}
				}
				map.put(strlen[8],actualAmountLTJXB);//交下班零头数
				map.put(strlen[17],unAmountLTJXB);//交下班异常零头数
				
				map.put(strlen[29], workingbill.getWorkcenter());//单元
				map.put(strlen[0], workingbill.getWorkingBillCode());
				map.put(strlen[1], workinginout.getMaterialCode());
				map.put(strlen[2], workingbill.getPlanCount());
				map.put(strlen[23], workingbill.getMatnr());//产品编码
				map.put(strlen[24], workingbill.getMaktx());//产品描述
				
				String materialname = workinginout.getMaterialName();
				if(ThinkWayUtil.null2String(materialname).equals("")){//处理不知道什么问题导致物料描述没有的问题
					materialname = bomservice.getMaterialName(workinginout.getMaterialCode());
				}
				
				map.put(strlen[25],materialname);//组件描述
				map.put(strlen[26],workingbill.getDailyWorkTotalAmount());//当班报工数量
				map.put(strlen[27],workingbill.getIsHand().equals("Y")?"交接完成":"未交接完成");//单据状态
				
				
				List<PickDetail> pickdetailList = pickdetaildao.finddetailByapp(workingbill.getId(), "2");//"2" 表示 确认状态数据
				Double recipientsamount = 0.00d;
				for(int y=0;y<pickdetailList.size();y++){
					PickDetail pickdetail1 = pickdetailList.get(y);
					if(pickdetail1.getMaterialCode().equals(workinginout.getMaterialCode())){
						if("261".equals(pickdetail1.getPickType())){//领料
							//sum += Double.parseDouble(pickdetail1.getPickAmount());
							recipientsamount = ArithUtil.add(Double.parseDouble(pickdetail1.getPickAmount()), recipientsamount);
						}else{//退料
							//sum -= Double.parseDouble(pickdetail1.getPickAmount());
							recipientsamount = ArithUtil.sub(recipientsamount,Double.parseDouble(pickdetail1.getPickAmount()));
						}
					}
				}
				if(workinginout.getScrapNumber()==null){
					workinginout.setScrapNumber(0.0d);
				}
				map.put(strlen[5], recipientsamount);//领用数
				map.put(strlen[7],workingbill.getTotalSingleAmount());//入库数
			
				map.put(strlen[9],workinginout.getScrapNumber());//报废数
				Set<Repair> repairSet = workingbill.getRepair();
				BigDecimal fxfh = new BigDecimal(0);
				if(repairSet!=null){
					for(Repair r : repairSet){
						if("1".equals(r.getState())){
							Set<RepairPiece> repairPieceSet = r.getRpieceSet();
							if(repairPieceSet!=null){
								for(RepairPiece rp : repairPieceSet){
									if(rp.getRpcode().equals(workinginout.getMaterialCode())){
										fxfh = fxfh.add(new BigDecimal(rp.getRpcount()==null?"0":rp.getRpcount()));
									}
								}
							}
						}
					}
					map.put(strlen[10],fxfh);//返修发货数量
				}else{
					//map.put(strlen[10],workingbill.getTotalRepairAmount());//返修发货数量
					map.put(strlen[10],fxfh);//返修发货数量
				}
				
				Set<Repairin> repairinSet = workingbill.getRepairin();
				BigDecimal fxsh = new BigDecimal(0);
				if(repairinSet!=null){
					for(Repairin r : repairinSet){
						if("1".equals(r.getState())){
							Set<RepairinPiece> repairinPieceSet = r.getRpieceSet();
							if(repairinPieceSet!=null){
								for(RepairinPiece rp : repairinPieceSet){
									if(rp.getRpcode().equals(workinginout.getMaterialCode())){
										fxsh = fxsh.add(new BigDecimal(rp.getRpcount()==null?"0":rp.getRpcount()));
									}
								}
							}
						}
					}
					map.put(strlen[11],fxsh);//返修发货数量
				}else{
					//map.put(strlen[11],workingbill.getTotalRepairinAmount());//返修收货数量
					map.put(strlen[11],fxsh);//返修发货数量
				}
				map.put(strlen[12],workingbill.getProductDate());//生产日期
				map.put(strlen[13],workingbill.getShift());//班次
				map.put(strlen[14],workingbill.getAufnr());//生产订单号
				List<Bom> bomList = bomservice.findBom(aufnr, workingbill.getProductDate(), workinginout.getMaterialCode(), workingbill.getWorkingBillCode());
				Double bomamount = 0.00d;
				for(Bom bom :bomList){
					bomamount +=bom.getMaterialAmount();
				}
				Double mount = 0.0d;
				if(bomamount<=0){
					mount = 0d;
				}else{
					mount = ArithUtil.round(ArithUtil.div(workingbill.getPlanCount(),bomamount),2);
				}
				map.put(strlen[6],mount);//倍数 = 随工单计划数量 / bom数量  保留2位小数
				Double dwyl = 0.0;
				//Double dwyl = ArithUtil.round(ArithUtil.div(bomamount, workingbill.getPlanCount()), 2);//单位用量
				String s = workinginout.getMaterialCode().substring(0, 1);
				if("5".equals(s)){
					List<UnitConversion> unitConversionList = unitConversionService.getList("matnr",workingbill.getMatnr());
					if(unitConversionList!=null && unitConversionList.size()>0){
						if(unitConversionList.get(0).getConversationRatio()!=null && !unitConversionList.get(0).getConversationRatio().equals("")){
							if(new BigDecimal(0).compareTo(new BigDecimal(unitConversionList.get(0).getConversationRatio()))!=0){
								dwyl = ArithUtil.round(ArithUtil.div(1, unitConversionList.get(0).getConversationRatio()), 4);
							}
						}
					}
				}else{
					if(new BigDecimal(0).compareTo(new BigDecimal(workingbill.getPlanCount()))!=0){
						dwyl = ArithUtil.round(ArithUtil.div(bomamount, workingbill.getPlanCount()), 2);//单位用量
					}
					//map.put(strlen[15],dwyl);//组件单位用量 = BOM需求数量  / 随工单计划数量 保留2位小数
				}
				map.put(strlen[15],dwyl);//组件单位用量 = MES中基本信息--计量单位转换中的1/兑换比例
			
				Double totalsingleamount = ThinkWayUtil.null2o(workingbill.getTotalSingleAmount());//入库数
				
				
				
		//		Double dbjyhgs = ArithUtil.sub(ArithUtil.sub(ArithUtil.add(ArithUtil.add(actualAmountLTJSB.doubleValue(), unAmountLTJSB.doubleValue()),totalsingleamount),actualAmountLTJXB.doubleValue()),unAmountLTJXB.doubleValue());//当班检验合格数 = 接上班零头数 + 接上班异常零头数 + 入库数 - 交下班零头数 - 交下班异常零头数
				BigDecimal WarehousingNum = new BigDecimal(ThinkWayUtil.null2o(workingbill.getTotalSingleAmount()));
				BigDecimal qualifiedNum = WarehousingNum.add(actualAmountLTJXB).add(unAmountLTJXB).subtract(actualAmountLTJSB).subtract(unAmountLTJSB).add(fxfh).subtract(fxsh);//生产数=入库+交/零头+交/零头/返-接/零头-接/零头/返+返修发货-返修收货
				Double dbjyhgs = qualifiedNum.doubleValue();
				map.put(strlen[16],dbjyhgs);//当班检验合格数
				map.put(strlen[18],"");//一次合格率 TODO 此处计算一次合格率，现在无法计算
				
				Double trzsl = 0.00d;
				Double cczsl = 0.00d;
				trzsl = ArithUtil.add(trzsl, ThinkWayUtil.null2o(recipientsamount));//领用数
				//trzsl = ArithUtil.add(trzsl, ThinkWayUtil.null2o(workinginout.getScrapNumber()));//报废数
						
				for(int y=0;y<jsonarray.size();y++){
					JSONObject json = (JSONObject) jsonarray.get(y);
					String name = json.getString("name");
					int firstls = StringUtils.indexOf(name, "GXJSBZC_");
					int firstls00 = StringUtils.indexOf(name, "GXJXBZC_");
					if(firstls >= 0){//如果找到，表示是接上班工序交接
						String processid = StringUtils.substringAfter(name, "GXJSBZC_");//获取接上班ID
						String[] propertyNames = {"processid","afterworkingbill.id","matnr","isdel"};
						String[] propertyValues={processid,workinginout.getWorkingbill().getId(),workinginout.getWorkingbill().getMatnr(),"N"};

						List<ProcessHandover> processHandoverList = processHandoverDao.getList(propertyNames, propertyValues);
						Double zcjjsl = 0.00d;
						Double fxjjsl = 0.00d;
						if(processHandoverList != null && processHandoverList.size()>0){
							for(ProcessHandover processHandover:processHandoverList){
								if("2".equals(processHandover.getProcessHandoverTop().getState())){
									Set<ProcessHandoverSon> processHandoverSon = processHandover.getProcessHandoverSonSet();
									for(ProcessHandoverSon p:processHandoverSon){
										if(p.getBomCode().equals(workinginout.getMaterialCode())){
											zcjjsl = new BigDecimal(zcjjsl).add(new BigDecimal(p.getBomAmount()==null?"0":p.getBomAmount())).setScale(3, RoundingMode.HALF_UP).doubleValue();
											fxjjsl = new BigDecimal(fxjjsl).add(new BigDecimal(p.getRepairNumber()==null?"0":p.getRepairNumber())).setScale(3, RoundingMode.HALF_UP).doubleValue();
										}								
									}
								}
							}
						}
						map.put("GXJSBZC_"+processid,zcjjsl);//正常交接数量
						map.put("GXJSBFX_"+processid,fxjjsl);//返修交接数量!=						
						trzsl = ArithUtil.add(trzsl, ThinkWayUtil.null2o(zcjjsl));//投入:正常交接数量
						trzsl = ArithUtil.add(trzsl, ThinkWayUtil.null2o(fxjjsl));//投入:返修交接数量
					}
					if(firstls00>=0){//交下班
						String processid = StringUtils.substringAfter(name, "GXJXBZC_");//获取交下班ID
						String[] propertyNames = {"processid","workingBill.id","matnr","isdel"};
						String[] propertyValues={processid,workinginout.getWorkingbill().getId(),workinginout.getWorkingbill().getMatnr(),"N"};
						List<ProcessHandover> processHandoverList = processHandoverDao.getList(propertyNames, propertyValues);
						Double zcjjsl = 0.00d;
						Double fxjjsl = 0.00d;
						if(processHandoverList != null && processHandoverList.size()>0){
							for(ProcessHandover processHandover:processHandoverList){
								if("2".equals(processHandover.getProcessHandoverTop().getState())){
									Set<ProcessHandoverSon> processHandoverSon = processHandover.getProcessHandoverSonSet();
									for(ProcessHandoverSon p:processHandoverSon){
										if(p.getBomCode().equals(workinginout.getMaterialCode())){
											zcjjsl = new BigDecimal(zcjjsl).add(new BigDecimal(p.getBomAmount()==null?"0":p.getBomAmount())).setScale(3, RoundingMode.HALF_UP).doubleValue();
											fxjjsl = new BigDecimal(fxjjsl).add(new BigDecimal(p.getRepairNumber()==null?"0":p.getRepairNumber())).setScale(3, RoundingMode.HALF_UP).doubleValue();
										}
									}
								}
							}
						}

						map.put("GXJXBZC_"+processid,zcjjsl);//正常交接数量
						map.put("GXJXBFX_"+processid,fxjjsl);//返修交接数量
						trzsl = ArithUtil.sub(trzsl,ThinkWayUtil.null2o(zcjjsl));//投入:正常交接数量
						trzsl = ArithUtil.sub(trzsl,ThinkWayUtil.null2o(fxjjsl));//投入:返修交接数量
					}
				}
				
				map.put(strlen[19],trzsl);//投入总数量 = 领用数 + 接上班正常和返修数量
				
				cczsl = ArithUtil.add(cczsl,ThinkWayUtil.null2o(workingbill.getTotalSingleAmount()));//入库数
				Double repairAmount = 0.00d;
				//repairAmount = ArithUtil.add(repairAmount,ThinkWayUtil.null2o(workingbill.getTotalRepairAmount()));//成型异常表面维修数 -出去
				repairAmount = ArithUtil.add(repairAmount,fxfh.doubleValue());//成型异常表面维修数 -出去
				Double repairinAmount = 0.00d;
				//repairinAmount = ArithUtil.add(repairinAmount,ThinkWayUtil.null2o(workingbill.getTotalRepairinAmount()));//成型维修返回数 -回来
				repairinAmount = ArithUtil.add(repairinAmount,fxsh.doubleValue());//成型维修返回数 -回来
				Double zjbfs = 0.00d;
				zjbfs = ArithUtil.add(zjbfs, ThinkWayUtil.null2o(workinginout.getScrapNumber()));//报废数
				String str = workinginout.getMaterialCode().substring(0, 1);
				if(!"5".equals(str)){
					//(成品入库数+交下班零头数+交下班零头返修数+成型异常表面维修数-成型维修返回数-接上班零头数-接上班零头返修数)*单位用量+组件报废数
//					cczsl = (new BigDecimal(cczsl).add(actualAmountLTJXB).add(unAmountLTJXB).add(new BigDecimal(repairAmount)).subtract(new BigDecimal(repairinAmount)).subtract(actualAmountLTJSB).subtract(unAmountLTJSB)).multiply(new BigDecimal(dwyl)).add(new BigDecimal(zjbfs)).setScale(2, RoundingMode.HALF_UP).doubleValue();
					//产出汇总公式修改=（入库+交/零头+交/零头/返-接/零头-接/零头/返）*组件用量+返修发货-返修收货+组件报废
//					cczsl = ((new BigDecimal(cczsl).add(actualAmountLTJXB).add(unAmountLTJXB).subtract(actualAmountLTJSB).subtract(unAmountLTJSB)).multiply(new BigDecimal(dwyl)).add(fxfh).subtract(fxsh).add(new BigDecimal(zjbfs))).setScale(2, RoundingMode.HALF_UP).doubleValue();
					cczsl = (new BigDecimal(cczsl).add(actualAmountLTJXB).add(unAmountLTJXB).subtract(actualAmountLTJSB).subtract(unAmountLTJSB)).doubleValue();
					cczsl = (new BigDecimal(cczsl).multiply(new BigDecimal(dwyl)).doubleValue());
					cczsl = (new BigDecimal(cczsl).add(fxfh).subtract(fxsh).add(new BigDecimal(zjbfs))).setScale(2, RoundingMode.HALF_UP).doubleValue();
				}else{
					cczsl = new BigDecimal(cczsl).multiply(new BigDecimal(dwyl)).setScale(2, RoundingMode.HALF_UP).doubleValue();
				}
					
				map.put(strlen[20],cczsl);//产出总数量 = (入库数 + 返修收货数量)*单位用量  保留2位小数  /**modify:产出总数量=入库数**/ 
				map.put(strlen[21],ArithUtil.sub(trzsl, cczsl));//数量差异= 投入总数量 - 产出总数量
				Double jhdcl = 0d;
				if(new BigDecimal(0).compareTo(new BigDecimal(workingbill.getPlanCount()))!=0){
					jhdcl= ArithUtil.round(ArithUtil.div(dbjyhgs, workingbill.getPlanCount())*100,2);//计划达成率
				}
				map.put(strlen[22],jhdcl+"%");//计划达成率 = 当班检验合格数 / 计划数  
				BigDecimal cost = new BigDecimal(0);
				Double bgs = 0.00d;
				bgs = ThinkWayUtil.null2o(workingbill.getDailyWorkTotalAmount());
				map.put(strlen[26],bgs);//当班报工数
				Double jycl = 0.00d;
				Double rks = ThinkWayUtil.null2o(workingbill.getTotalSingleAmount());//入库数
				//beforeoddamount  //交下班零头
				//bgs					//报工数
				//afteroddamount   //接上班零头数
				//repairinAmount  //返修收货
				jycl = (new BigDecimal(rks).add(actualAmountLTJXB)).subtract( (new BigDecimal(bgs).add(actualAmountLTJSB).add(new BigDecimal(repairinAmount)))).doubleValue();
				map.put(strlen[28],jycl);//校验差异    公式=（入库数+交下班零头）-（报工数+接上班零头+返修收货）
				map.put(strlen[31],workingbill.getTeam()==null?"":workingbill.getTeam().getTeamName());//班组名称
				jsonstr.add(map);
		}
		}catch(Exception e){
			log.error(e);
			e.printStackTrace();
		}
		
		
		return jsonstr;
	}
	
	/**
	 * 投入产出报表
	 */
	public JSONArray findInoutByJsonData3(JSONArray jsonarray,HashMap<String,String> mapcheck,String[] strlen) {
		
		JSONArray jsonstr = new JSONArray();
		try{
			List<WorkingInout> workingInoutList=new ArrayList<WorkingInout>();
			workingInoutList = this.workingInoutDao.findWbinoutput(mapcheck.get("wbid"));
			for(int i=0;i<workingInoutList.size();i++){
				JSONObject map = new JSONObject();
				WorkingInout workinginout = workingInoutList.get(i);
				WorkingBill workingbill = workinginout.getWorkingbill();
				String aufnr = workingbill.getAufnr();
				
				/** 接上班 */
				String[] propertyNamesLTJSB={"afterWorkingBillCode","isdel"};
				String[] propertyValuesLTJSB={workingbill.getWorkingBillCode(),"N"};
				List<ProcessHandover> processHandoverListLTJSB = processHandoverDao.getList(propertyNamesLTJSB, propertyValuesLTJSB);
				BigDecimal actualAmountLTJSB = new BigDecimal(0);
				BigDecimal unAmountLTJSB = new BigDecimal(0);
				for(int k=0;k<processHandoverListLTJSB.size();k++){
					ProcessHandover ph = processHandoverListLTJSB.get(k);
					if("零头数交接".equals(ph.getProcessHandoverTop().getType())  && "2".equals(ph.getProcessHandoverTop().getState())){
						actualAmountLTJSB = actualAmountLTJSB.add(new BigDecimal(ThinkWayUtil.null2o(ph.getActualHOMount())));
						unAmountLTJSB = unAmountLTJSB.add(new BigDecimal(ThinkWayUtil.null2o(ph.getUnHOMount())));
					}
				}
				
				/** 交下班 */
				String[] propertyNamesLTJXB={"workingBillCode","isdel"};
				String[] propertyValuesLTJXB={workingbill.getWorkingBillCode(),"N"};
				List<ProcessHandover> processHandoverListLTJXB = processHandoverDao.getList(propertyNamesLTJXB, propertyValuesLTJXB);
				BigDecimal actualAmountLTJXB = new BigDecimal(0);
				BigDecimal unAmountLTJXB = new BigDecimal(0);
				for(int k=0;k<processHandoverListLTJXB.size();k++){
					ProcessHandover ph = processHandoverListLTJXB.get(k);
					if("零头数交接".equals(ph.getProcessHandoverTop().getType())  && "2".equals(ph.getProcessHandoverTop().getState())){
						actualAmountLTJXB = actualAmountLTJXB.add(new BigDecimal(ThinkWayUtil.null2o(ph.getActualHOMount())));
						unAmountLTJXB = unAmountLTJXB.add(new BigDecimal(ThinkWayUtil.null2o(ph.getUnHOMount())));
					}
				}
				
				List<PickDetail> pickdetailList = pickdetaildao.finddetailByapp(workingbill.getId(), "2");//"2" 表示 确认状态数据
				Double recipientsamount = 0.00d;
				for(int y=0;y<pickdetailList.size();y++){
					PickDetail pickdetail1 = pickdetailList.get(y);
					if(pickdetail1.getMaterialCode().equals(workinginout.getMaterialCode())){
						if("261".equals(pickdetail1.getPickType())){//领料
							recipientsamount = ArithUtil.add(Double.parseDouble(pickdetail1.getPickAmount()), recipientsamount);
						}else{//退料
							recipientsamount = ArithUtil.sub(recipientsamount,Double.parseDouble(pickdetail1.getPickAmount()));
						}
					}
				}
				if(workinginout.getScrapNumber()==null){
					workinginout.setScrapNumber(0.0d);
				}
				List<Bom> bomList = bomservice.findBom(aufnr, workingbill.getProductDate(), workinginout.getMaterialCode(), workingbill.getWorkingBillCode());
				Double bomamount = 0.00d;
				for(Bom bom :bomList){
					bomamount +=bom.getMaterialAmount();
				}
			
				Double dwyl = 0.0;
				String s = workinginout.getMaterialCode().substring(0, 1);
				if("5".equals(s)){
					List<UnitConversion> unitConversionList = unitConversionService.getList("matnr",workingbill.getMatnr());
					if(unitConversionList!=null && unitConversionList.size()>0){
						if(unitConversionList.get(0).getConversationRatio()!=null && !unitConversionList.get(0).getConversationRatio().equals("")){
							if(new BigDecimal(0).compareTo(new BigDecimal(unitConversionList.get(0).getConversationRatio()))!=0){
								dwyl = ArithUtil.round(ArithUtil.div(1, unitConversionList.get(0).getConversationRatio()), 4);
							}
						}
					}
				}else{
					if(new BigDecimal(0).compareTo(new BigDecimal(workingbill.getPlanCount()))!=0){
						dwyl = ArithUtil.round(ArithUtil.div(bomamount, workingbill.getPlanCount()), 2);//单位用量
					}
				}
				
				Double trzsl = 0.00d;
				Double cczsl = 0.00d;
				trzsl = ArithUtil.add(trzsl, ThinkWayUtil.null2o(recipientsamount));//领用数
				for(int y=0;y<jsonarray.size();y++){
					JSONObject json = (JSONObject) jsonarray.get(y);
					String name = json.getString("name");
					int firstls = StringUtils.indexOf(name, "GXJSBZC_");
					int firstls00 = StringUtils.indexOf(name, "GXJXBZC_");
					if(firstls >= 0){//如果找到，表示是接上班工序交接
						String processid = StringUtils.substringAfter(name, "GXJSBZC_");//获取接上班ID
						String[] propertyNames = {"processid","afterworkingbill.id","matnr","isdel"};
						String[] propertyValues={processid,workinginout.getWorkingbill().getId(),workinginout.getWorkingbill().getMatnr(),"N"};
						List<ProcessHandover> processHandoverList = processHandoverDao.getList(propertyNames, propertyValues);
						Double zcjjsl = 0.00d;
						Double fxjjsl = 0.00d;
						if(processHandoverList != null && processHandoverList.size()>0){
							for(ProcessHandover processHandover:processHandoverList){
								if("2".equals(processHandover.getProcessHandoverTop().getState())){
									Set<ProcessHandoverSon> processHandoverSon = processHandover.getProcessHandoverSonSet();
									for(ProcessHandoverSon p:processHandoverSon){
										if(p.getBomCode().equals(workinginout.getMaterialCode())){
											zcjjsl = new BigDecimal(zcjjsl).add(new BigDecimal(p.getBomAmount()==null?"0":p.getBomAmount())).setScale(3, RoundingMode.HALF_UP).doubleValue();
											fxjjsl = new BigDecimal(fxjjsl).add(new BigDecimal(p.getRepairNumber()==null?"0":p.getRepairNumber())).setScale(3, RoundingMode.HALF_UP).doubleValue();
										}								
									}
								}
							}
						}
						trzsl = ArithUtil.add(trzsl, ThinkWayUtil.null2o(zcjjsl));//投入:正常交接数量
						trzsl = ArithUtil.add(trzsl, ThinkWayUtil.null2o(fxjjsl));//投入:返修交接数量
					}
					if(firstls00>=0){//交下班
						String processid = StringUtils.substringAfter(name, "GXJXBZC_");//获取交下班ID
						String[] propertyNames = {"processid","workingBill.id","matnr","isdel"};
						String[] propertyValues={processid,workinginout.getWorkingbill().getId(),workinginout.getWorkingbill().getMatnr(),"N"};
						List<ProcessHandover> processHandoverList = processHandoverDao.getList(propertyNames, propertyValues);
						Double zcjjsl = 0.00d;
						Double fxjjsl = 0.00d;
						if(processHandoverList != null && processHandoverList.size()>0){
							for(ProcessHandover processHandover:processHandoverList){
								if("2".equals(processHandover.getProcessHandoverTop().getState())){
									Set<ProcessHandoverSon> processHandoverSon = processHandover.getProcessHandoverSonSet();
									for(ProcessHandoverSon p:processHandoverSon){
										if(p.getBomCode().equals(workinginout.getMaterialCode())){
											zcjjsl = new BigDecimal(zcjjsl).add(new BigDecimal(p.getBomAmount()==null?"0":p.getBomAmount())).setScale(3, RoundingMode.HALF_UP).doubleValue();
											fxjjsl = new BigDecimal(fxjjsl).add(new BigDecimal(p.getRepairNumber()==null?"0":p.getRepairNumber())).setScale(3, RoundingMode.HALF_UP).doubleValue();
										}
									}
								}
							}
						}

						trzsl = ArithUtil.sub(trzsl,ThinkWayUtil.null2o(zcjjsl));//投入:正常交接数量
						trzsl = ArithUtil.sub(trzsl,ThinkWayUtil.null2o(fxjjsl));//投入:返修交接数量
					}
				}
				cczsl = ArithUtil.add(cczsl,ThinkWayUtil.null2o(workingbill.getTotalSingleAmount()));//入库数
				Set<Repair> repairSet = workingbill.getRepair();
				BigDecimal fxfh = new BigDecimal(0);
				if(repairSet!=null){
					for(Repair r : repairSet){
						if("1".equals(r.getState())){
							Set<RepairPiece> repairPieceSet = r.getRpieceSet();
							if(repairPieceSet!=null){
								for(RepairPiece rp : repairPieceSet){
									if(rp.getRpcode().equals(workinginout.getMaterialCode())){
										fxfh = fxfh.add(new BigDecimal(rp.getRpcount()==null?"0":rp.getRpcount()));
									}
								}
							}
						}
					}
					map.put(strlen[10],fxfh);//返修发货数量
				}else{
					//map.put(strlen[10],workingbill.getTotalRepairAmount());//返修发货数量
					map.put(strlen[10],fxfh);//返修发货数量
				}
				
				Set<Repairin> repairinSet = workingbill.getRepairin();
				BigDecimal fxsh = new BigDecimal(0);
				if(repairinSet!=null){
					for(Repairin r : repairinSet){
						if("1".equals(r.getState())){
							Set<RepairinPiece> repairinPieceSet = r.getRpieceSet();
							if(repairinPieceSet!=null){
								for(RepairinPiece rp : repairinPieceSet){
									if(rp.getRpcode().equals(workinginout.getMaterialCode())){	
										fxsh = fxsh.add(new BigDecimal(rp.getRpcount()==null?"0":rp.getRpcount()));
									}
								}
							}
						}
					}
					map.put(strlen[11],fxsh);//返修发货数量
				}else{
					//map.put(strlen[11],workingbill.getTotalRepairinAmount());//返修收货数量
					map.put(strlen[11],fxsh);//返修发货数量
				}
				Double repairAmount = 0.00d;
				//repairAmount = ArithUtil.add(repairAmount,ThinkWayUtil.null2o(workingbill.getTotalRepairAmount()));//成型异常表面维修数 -出去
				repairAmount = ArithUtil.add(repairAmount,fxfh.doubleValue());//成型异常表面维修数 -出去
				Double repairinAmount = 0.00d;
				//repairinAmount = ArithUtil.add(repairinAmount,ThinkWayUtil.null2o(workingbill.getTotalRepairinAmount()));//成型维修返回数 -回来
				repairinAmount = ArithUtil.add(repairinAmount,fxsh.doubleValue());//成型维修返回数 -回来
				Double zjbfs = 0.00d;
				zjbfs = ArithUtil.add(zjbfs, ThinkWayUtil.null2o(workinginout.getScrapNumber()));//报废数
				String str = workinginout.getMaterialCode().substring(0, 1);
				if(!"5".equals(str)){
//					cczsl = (new BigDecimal(cczsl).add(actualAmountLTJXB).add(unAmountLTJXB).add(new BigDecimal(repairAmount)).subtract(new BigDecimal(repairinAmount)).subtract(actualAmountLTJSB).subtract(unAmountLTJSB)).multiply(new BigDecimal(dwyl)).add(new BigDecimal(zjbfs)).setScale(2, RoundingMode.HALF_UP).doubleValue();
					//产出汇总公式修改=（入库+交/零头+交/零头/返-接/零头-接/零头/返）*组件用量+返修发货-返修收货+组件报废
//					cczsl = ((new BigDecimal(cczsl).add(actualAmountLTJXB).add(unAmountLTJXB).subtract(actualAmountLTJSB).subtract(unAmountLTJSB)).multiply(new BigDecimal(dwyl)).add(fxfh).subtract(fxsh).add(new BigDecimal(zjbfs))).setScale(2, RoundingMode.HALF_UP).doubleValue();
					cczsl = (new BigDecimal(cczsl).add(actualAmountLTJXB).add(unAmountLTJXB).subtract(actualAmountLTJSB).subtract(unAmountLTJSB)).doubleValue();
					cczsl = (new BigDecimal(cczsl).multiply(new BigDecimal(dwyl)).doubleValue());
					cczsl = (new BigDecimal(cczsl).add(fxfh).subtract(fxsh).add(new BigDecimal(zjbfs))).setScale(2, RoundingMode.HALF_UP).doubleValue();
				}else{
					cczsl = new BigDecimal(cczsl).multiply(new BigDecimal(dwyl)).setScale(2, RoundingMode.HALF_UP).doubleValue();
				}
				map.put(strlen[21],ArithUtil.sub(trzsl, cczsl));//数量差异= 投入总数量 - 产出总数量
				jsonstr.add(map);
		}
		}catch(Exception e){
			log.error(e);
			e.printStackTrace();
		}
		
		
		return jsonstr;
	}
	
	
}
