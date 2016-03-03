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

import cc.jiuyi.dao.HandOverProcessDao;
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
	private HandOverProcessDao handoverprocessdao;
	
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
		return workingInoutDao.findPagerByWorkingBillInout(map);
	}

	public JSONArray showInoutJsonData(String[] strlen,String[] lavenlen){
		List<String> nameobj = new ArrayList<String>();
		List<String> labelobj = new ArrayList<String>();
		List<String> indexobj = new ArrayList<String>();
		
		String[] propertyNames = {"isDel","state"};
		String[] propertyValues={"N","1"};
		List<Process> processList00 = processservice.getList(propertyNames, propertyValues);
		
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
		/**处理接上班(正常)**/
		for(int i=0;i<processList00.size();i++){
			Process process = processList00.get(i);
			String label = "接上班"+process.getProcessName()+"(正常)";
			String name ="GXJSBZC_"+process.getId();
			String index="GXJSBZC_"+process.getId();
			indexobj.add(index);
			nameobj.add(name);
			labelobj.add(label);
		}
		/**处理接上班 (正常)end**/
		/**处理接上班(返修)**/
		for(int i=0;i<processList00.size();i++){
			Process process = processList00.get(i);
			String label = "接上班"+process.getProcessName()+"(返修)";
			String name ="GXJSBFX_"+process.getId();
			String index="GXJSBFX_"+process.getId();
			indexobj.add(index);
			nameobj.add(name);
			labelobj.add(label);
		}
		/**处理接上班 (返修)end**/
		nameobj.add(strlen[5]);labelobj.add(lavenlen[5]);indexobj.add(strlen[5]);//领用数
		
		/**处理交下班(正常)**/
		for(int i=0;i<processList00.size();i++){
			Process process = processList00.get(i);
			String label = "交下班"+process.getProcessName()+"(正常)";
			String name ="GXJXBZC_"+process.getId();
			String index="GXJXBZC_"+process.getId();
			indexobj.add(index);
			nameobj.add(name);
			labelobj.add(label);
		}
		/**处理交下班(正常)end**/
		
		/**处理交下班(返修)**/
		for(int i=0;i<processList00.size();i++){
			Process process = processList00.get(i);
			String label = "交下班"+process.getProcessName()+"(返修)";
			String name ="GXJXBFX_"+process.getId();
			String index="GXJXBFX_"+process.getId();
			indexobj.add(index);
			nameobj.add(name);
			labelobj.add(label);
		}
		/**处理交下班 (返修)end**/
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
		nameobj.add(strlen[26]);labelobj.add(lavenlen[26]);indexobj.add(strlen[26]);//当班报工数
		nameobj.add(strlen[18]);labelobj.add(lavenlen[18]);indexobj.add(strlen[18]);//一次合格率
		nameobj.add(strlen[19]);labelobj.add(lavenlen[19]);indexobj.add(strlen[19]);//投入总数量
		nameobj.add(strlen[20]);labelobj.add(lavenlen[20]);indexobj.add(strlen[20]);//产出总数量
		nameobj.add(strlen[21]);labelobj.add(lavenlen[21]);indexobj.add(strlen[21]);//数量差异
		nameobj.add(strlen[22]);labelobj.add(lavenlen[22]);indexobj.add(strlen[22]);//计划达成率
		nameobj.add(strlen[27]);labelobj.add(lavenlen[27]);indexobj.add(strlen[27]);//单据状态
		
		
		
		
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

	public JSONArray showInoutJsonData1(String[] strlen,String[] lavenlen){
		List<String> nameobj = new ArrayList<String>();
		List<String> labelobj = new ArrayList<String>();
		List<String> indexobj = new ArrayList<String>();
		
		String[] propertyNames = {"isDel","state"};
		String[] propertyValues={"N","1"};
		List<Process> processList00 = processservice.getList(propertyNames, propertyValues);
		
		//nameobj.add(strlen[12]);labelobj.add(lavenlen[12]);indexobj.add(strlen[12]);//生产日期
		//nameobj.add(strlen[13]);labelobj.add(lavenlen[13]);indexobj.add(strlen[13]);//班次
		//nameobj.add(strlen[14]);labelobj.add(lavenlen[14]);indexobj.add(strlen[14]);//生产订单号
		//nameobj.add(strlen[23]);labelobj.add(lavenlen[23]);indexobj.add(strlen[23]);//物料编码
		//nameobj.add(strlen[24]);labelobj.add(lavenlen[24]);indexobj.add(strlen[24]);//物料描述
		//nameobj.add(strlen[1]);labelobj.add(lavenlen[1]);indexobj.add(strlen[1]);//子件编码
		nameobj.add(strlen[25]);labelobj.add(lavenlen[25]);indexobj.add(strlen[25]);//组件描述
		//nameobj.add(strlen[15]);labelobj.add(lavenlen[15]);indexobj.add(strlen[15]);//组件单位用量
		//nameobj.add(strlen[2]);labelobj.add(lavenlen[2]);indexobj.add(strlen[2]);//计划数量
		//nameobj.add(strlen[0]);labelobj.add(lavenlen[0]);indexobj.add(strlen[0]);//随工单编号
		/**处理接上班(正常)**/
		for(int i=0;i<processList00.size();i++){
			Process process = processList00.get(i);
			String label = "接上班"+process.getProcessName()+"(正常)";
			String name ="GXJSBZC_"+process.getId();
			String index="GXJSBZC_"+process.getId();
			indexobj.add(index);
			nameobj.add(name);
			labelobj.add(label);
		}
		/**处理接上班 (正常)end**/
		/**处理接上班(返修)**/
		for(int i=0;i<processList00.size();i++){
			Process process = processList00.get(i);
			String label = "接上班"+process.getProcessName()+"(返修)";
			String name ="GXJSBFX_"+process.getId();
			String index="GXJSBFX_"+process.getId();
			indexobj.add(index);
			nameobj.add(name);
			labelobj.add(label);
		}
		/**处理接上班 (返修)end**/
		nameobj.add(strlen[5]);labelobj.add(lavenlen[5]);indexobj.add(strlen[5]);//领用数
		
		/**处理交下班(正常)**/
		for(int i=0;i<processList00.size();i++){
			Process process = processList00.get(i);
			String label = "交下班"+process.getProcessName()+"(正常)";
			String name ="GXJXBZC_"+process.getId();
			String index="GXJXBZC_"+process.getId();
			indexobj.add(index);
			nameobj.add(name);
			labelobj.add(label);
		}
		/**处理交下班(正常)end**/
		
		/**处理交下班(返修)**/
		for(int i=0;i<processList00.size();i++){
			Process process = processList00.get(i);
			String label = "交下班"+process.getProcessName()+"(返修)";
			String name ="GXJXBFX_"+process.getId();
			String index="GXJXBFX_"+process.getId();
			indexobj.add(index);
			nameobj.add(name);
			labelobj.add(label);
		}
		/**处理交下班 (返修)end**/
		nameobj.add(strlen[9]);labelobj.add(lavenlen[9]);indexobj.add(strlen[9]);//报废数
		nameobj.add(strlen[19]);labelobj.add(lavenlen[19]);indexobj.add(strlen[19]);//投入总数量
		nameobj.add(strlen[3]);labelobj.add(lavenlen[3]);indexobj.add(strlen[3]);//接上班零头数
		nameobj.add(strlen[4]);labelobj.add(lavenlen[4]);indexobj.add(strlen[4]);//接上班异常零头数
		//nameobj.add(strlen[6]);labelobj.add(lavenlen[6]);indexobj.add(strlen[6]);//倍数
		nameobj.add(strlen[7]);labelobj.add(lavenlen[7]);indexobj.add(strlen[7]);//入库数
		nameobj.add(strlen[8]);labelobj.add(lavenlen[8]);indexobj.add(strlen[8]);//交下班零头数
		nameobj.add(strlen[17]);labelobj.add(lavenlen[17]);indexobj.add(strlen[17]);//交下班异常零头数
		nameobj.add(strlen[20]);labelobj.add(lavenlen[20]);indexobj.add(strlen[20]);//产出总数量
		nameobj.add(strlen[10]);labelobj.add(lavenlen[10]);indexobj.add(strlen[10]);//成型异常表面维修数
		nameobj.add(strlen[11]);labelobj.add(lavenlen[11]);indexobj.add(strlen[11]);//成型维修表面返回数
		//nameobj.add(strlen[16]);labelobj.add(lavenlen[16]);indexobj.add(strlen[16]);//当班检验合格数
		//nameobj.add(strlen[26]);labelobj.add(lavenlen[26]);indexobj.add(strlen[26]);//当班报工数
		//nameobj.add(strlen[18]);labelobj.add(lavenlen[18]);indexobj.add(strlen[18]);//一次合格率
		nameobj.add(strlen[21]);labelobj.add(lavenlen[21]);indexobj.add(strlen[21]);//数量差异
		//nameobj.add(strlen[22]);labelobj.add(lavenlen[22]);indexobj.add(strlen[22]);//计划达成率
		//nameobj.add(strlen[27]);labelobj.add(lavenlen[27]);indexobj.add(strlen[27]);//单据状态
		
		
		
		
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
				
				map.put(strlen[0], workingbill.getWorkingBillCode());
				map.put(strlen[1], workinginout.getMaterialCode());
				map.put(strlen[2], workingbill.getPlanCount());
				map.put(strlen[23], workingbill.getMatnr());//产品编码
				map.put(strlen[24], workingbill.getMaktx());//产品描述
				map.put(strlen[25],workinginout.getMaterialName());//组件描述
				map.put(strlen[26],workingbill.getDailyWorkTotalAmount());//当班报工数量
				map.put(strlen[27],workingbill.getIsHand().equals("Y")?"交接完成":"未交接完成");//单据状态
				map.put(strlen[3], workingbill.getAfteroddamount());//接上班零头数
				map.put(strlen[4], workingbill.getAfterunoddamount());//接上班异常零头数
				map.put(strlen[5], workinginout.getRecipientsAmount());//领用数
				map.put(strlen[7],workingbill.getTotalSingleAmount());//入库数
				map.put(strlen[8],workingbill.getBeforeoddamount());//交下班零头数
				map.put(strlen[17],workingbill.getBeforeunoddamount());//交下班异常零头数
				map.put(strlen[9],workinginout.getScrapNumber());//报废数
				map.put(strlen[10],workingbill.getTotalRepairAmount());//返修数量
				map.put(strlen[11],workingbill.getTotalRepairinAmount());//返修收货数量
				map.put(strlen[12],workingbill.getProductDate());//生产日期
				map.put(strlen[13],workingbill.getWorkingBillCode().substring(workingbill.getWorkingBillCode().length()-2,workingbill.getWorkingBillCode().length()));//班次
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
				
				Double dwyl = ArithUtil.round(ArithUtil.div(bomamount, workingbill.getPlanCount()), 2);//单位用量
				map.put(strlen[15],dwyl);//组件单位用量 = BOM需求数量  / 随工单计划数量 保留2位小数
				Double afteroddamount = ThinkWayUtil.null2o(workingbill.getAfteroddamount());//接上班零头数
				Double afterunoddamount = ThinkWayUtil.null2o(workingbill.getAfterunoddamount());//接上班异常零头数
				Double totalsingleamount = ThinkWayUtil.null2o(workingbill.getTotalSingleAmount());//入库数
				Double beforeoddamount = ThinkWayUtil.null2o(workingbill.getBeforeoddamount());//交下班零头数
				Double beforeunoddamount = ThinkWayUtil.null2o(workingbill.getBeforeunoddamount());//交下班异常零头数
				Double dbjyhgs = ArithUtil.sub(ArithUtil.sub(ArithUtil.add(ArithUtil.add(afteroddamount, afterunoddamount),totalsingleamount),beforeoddamount),beforeunoddamount);//当班检验合格数 = 接上班零头数 + 接上班异常零头数 + 入库数 - 交下班零头数 - 交下班异常零头数
				map.put(strlen[16],dbjyhgs);//当班检验合格数
				map.put(strlen[18],"");//一次合格率 TODO 此处计算一次合格率，现在无法计算
				
				Double trzsl = 0.00d;
				Double cczsl = 0.00d;
				trzsl = ArithUtil.add(trzsl, ThinkWayUtil.null2o(workinginout.getRecipientsAmount()));//领用数
				trzsl = ArithUtil.sub(trzsl, ThinkWayUtil.null2o(workinginout.getScrapNumber()));//报废数
				
				cczsl = ArithUtil.add(cczsl,ThinkWayUtil.null2o(workingbill.getTotalSingleAmount()));//入库数
				cczsl = ArithUtil.add(cczsl,ThinkWayUtil.null2o(workingbill.getTotalRepairinAmount()));//返修收货数量
				cczsl = ArithUtil.round(ArithUtil.mul(cczsl, dwyl),2);
				
				for(int y=0;y<jsonarray.size();y++){
					JSONObject json = (JSONObject) jsonarray.get(y);
					String name = json.getString("name");
					int firstls = StringUtils.indexOf(name, "GXJSBZC_");
					int firstls00 = StringUtils.indexOf(name, "GXJXBZC_");
					if(firstls >= 0){//如果找到，表示是接上班工序交接
						String processid = StringUtils.substringAfter(name, "GXJSBZC_");//获取接上班ID
						String[] propertyNames = {"processid","afterworkingbill.id","materialCode"};
						String[] propertyValues={processid,workinginout.getWorkingbill().getId(),workinginout.getMaterialCode()};
						HandOverProcess handoverprocess = handoverprocessdao.get(propertyNames, propertyValues);
						Double zcjjsl = 0.00d;
						Double fxjjsl = 0.00d;
						if(handoverprocess != null){
							zcjjsl = handoverprocess.getAmount();//正常交接数量
							fxjjsl = handoverprocess.getRepairAmount();//返修交接数量
							map.put("GXJSBZC_"+processid,"0" );//正常交接数量
							map.put("GXJSBFX_"+processid, "0");//返修交接数量
						}
						map.put("GXJSBZC_"+processid,zcjjsl);//正常交接数量
						map.put("GXJSBFX_"+processid,fxjjsl);//返修交接数量
						
						trzsl = ArithUtil.add(trzsl, ThinkWayUtil.null2o(zcjjsl));//投入:正常交接数量
						trzsl = ArithUtil.add(trzsl, ThinkWayUtil.null2o(fxjjsl));//投入:返修交接数量
					}
					if(firstls00>=0){//交下班
						String processid = StringUtils.substringAfter(name, "GXJXBZC_");//获取交下班ID
						String[] propertyNames = {"processid","beforworkingbill.id","materialCode"};
						String[] propertyValues={processid,workinginout.getWorkingbill().getId(),workinginout.getMaterialCode()};
						HandOverProcess handoverprocess = handoverprocessdao.get(propertyNames, propertyValues);
						Double zcjjsl = 0.00d;
						Double fxjjsl = 0.00d;
						if(handoverprocess != null){
							zcjjsl = handoverprocess.getAmount();//正常交接数量
							fxjjsl = handoverprocess.getRepairAmount();//返修交接数量
						}
						map.put("GXJXBZC_"+processid,zcjjsl);//正常交接数量
						map.put("GXJXBFX_"+processid,fxjjsl);//返修交接数量
						trzsl = ArithUtil.sub(trzsl,ThinkWayUtil.null2o(zcjjsl));//投入:正常交接数量
						trzsl = ArithUtil.sub(trzsl,ThinkWayUtil.null2o(fxjjsl));//投入:返修交接数量
					}
					//int firstls1 = StringUtils
					
				}
				
				map.put(strlen[19],trzsl);//投入总数量 = 领用数 + 接上班正常和返修数量
				map.put(strlen[20],cczsl);//产出总数量 = (入库数 + 返修收货数量)*单位用量  保留2位小数
				map.put(strlen[21],ArithUtil.sub(trzsl, cczsl));//数量差异= 投入总数量 - 产出总数量
				Double jhdcl = ArithUtil.round(ArithUtil.div(dbjyhgs, workingbill.getPlanCount())*100,2);//计划达成率
				map.put(strlen[22],jhdcl+"%");//计划达成率 = 当班检验合格数 / 计划数  
				jsonstr.add(map);
		}
		}catch(Exception e){
			log.error(e);
			e.printStackTrace();
		}
		
		
		return jsonstr;
	}
}
