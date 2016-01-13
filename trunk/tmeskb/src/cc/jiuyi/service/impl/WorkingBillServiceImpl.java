package cc.jiuyi.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.dao.AdminDao;
import cc.jiuyi.dao.BrandDao;
import cc.jiuyi.dao.DictDao;
import cc.jiuyi.dao.MemberRankDao;
import cc.jiuyi.dao.WorkingBillDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Bom;
import cc.jiuyi.entity.Brand;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.MemberRank;
import cc.jiuyi.entity.Orders;
import cc.jiuyi.entity.Process;
import cc.jiuyi.entity.ProcessRoute;
import cc.jiuyi.entity.Products;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.BomService;
import cc.jiuyi.service.BrandService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.OrdersService;
import cc.jiuyi.service.ProcessRouteService;
import cc.jiuyi.service.ProductsService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.ArithUtil;
import cc.jiuyi.util.ThinkWayUtil;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Service;
//import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

/**
 * Service实现类 - 随工单
 */

@Service
public class WorkingBillServiceImpl extends
		BaseServiceImpl<WorkingBill, String> implements WorkingBillService {
	@Resource
	private WorkingBillDao workingbilldao;
	@Resource
	private OrdersService orderservice;
	@Resource
	private ProductsService productsservice;
	@Resource
	private ProcessRouteService processrouteservice;
	@Resource
	private BomService bomservice;

	@Resource
	public void setBaseDao(WorkingBillDao workingbilldao) {
		super.setBaseDao(workingbilldao);
	}

	@Override
	public Pager findPagerByjqGrid(Pager pager, Map map) {
		return workingbilldao.findPagerByjqGrid(pager, map);
	}

	

	@Override
	public void updateisdel(String[] ids, String oper) {
		workingbilldao.updateisdel(ids, oper);
	}

	@Override
	public List<WorkingBill> getListWorkingBillByDate(Admin admin) {
		// 找到当班随工单思路
		// 1. 获取 生产日期 
		// 2. 获取 班次
		// 3. 获取工作中心
		// 4. 根据工作中心 跟 当前身份的 单元上维护的 工作中心比较
		// 5. 显示出对应的随工单
		String productDate = admin.getProductDate();//生产日期
		String shift = admin.getShift();//班次
		String workcenter = admin.getDepartment().getTeam().getFactoryUnit().getWorkCenter();//获取当前登录身份的工作中心
		String steus = "PP01";//关键工序
		List<Orders> orderList = orderservice.findOrders(productDate);
		List<String> aufnrList = new ArrayList<String>();
		for(Orders orders : orderList){
			Integer maxversion = processrouteservice.getMaxVersion(orders.getId(), productDate);
			Orders orders1 = orderservice.findOrders(productDate, maxversion, steus, workcenter);
			if(orders1 == null)
				continue;
			aufnrList.add(orders1.getAufnr());
		}
		if(aufnrList.size() <=0){
			return null;
		}
		List<WorkingBill> workingbillList = workingbilldao.findWorkingBill(aufnrList,productDate,shift);
		
		return workingbillList;
	}

	@Cacheable(modelId = "caching")
	@Override
	public WorkingBill get(String id) {
		return workingbilldao.get(id);
	}

	@Cacheable(modelId = "flushing")
	public void update(WorkingBill workingbill) {
		workingbilldao.update(workingbill);
	}

	/**
	 * 查询随工单表中的id 和 产品名称maktx
	 */
	@Override
	public List<WorkingBill> getIdsAndNames() {
		return this.workingbilldao.getIdsAndNames();
	}

	@Override
	public List<WorkingBill> findListWorkingBill(Object[] productsid,
			String productDate, String shift) {
		return workingbilldao.findListWorkingBill(productsid, productDate, shift);
	}
	
	/**
	 * 根据随工单id 获取下一随工单
	 * @param workingBillid
	 * @return
	 */
	public WorkingBill getNextWorkingBill(String workingBillid) {
		WorkingBill workingbill = workingbilldao.get(workingBillid);
		String productdate = workingbill.getProductDate();//生产日期
		String workingcode = workingbill.getWorkingBillCode();//随工单id
		String laststr = workingcode.substring(workingcode.length()-1, workingcode.length());//获取最后一个值
		String beforestr = workingcode.substring(0,workingcode.length()-1);
		Integer last = Integer.parseInt(laststr)+1;
		laststr = last.toString();
		String str = beforestr + laststr;
		//workingbilldao.isExist("workingBillCode", value)
		return null;
	}

	@Cacheable(modelId = "flushing")
	public void updateWorkingBill(WorkingBill workingbill) {
		workingbilldao.updateWorkingBill(workingbill);
	}

	/**
	 * 根据随工单编号 获取 下一条记录
	 */
	@Cacheable(modelId = "caching")
	public WorkingBill getCodeNext(String workingbillCode){
		return workingbilldao.getCodeNext(workingbillCode);
	}

	@Override
	public List<WorkingBill> getWorkingBillByProductsCode(String matnr) {
		return workingbilldao.getWorkingBillByProductsCode(matnr);
	}

	@Override
	public synchronized void mergeWorkingBill(List<WorkingBill> workingbillList,
			List<Orders> orderList, List<ProcessRoute> processrouteList, List<Bom> bomList) {
		
		//生产订单
		for(int i=0;i<orderList.size();i++){
			Orders order = orderList.get(i);
			/***生产订单黑醋栗***/
			mergeorderdeal(order);//订单处理
			/**处理产品信息***/
			mergeproductdeal(order);//产品处理
			/**处理随工单信息***/
			mergeworkingbilldeal(order,workingbillList);
			/**处理BOM信息***/
			mergebomdeal(order,bomList);
			/***处理工艺路线***/
			mergeprocessroutedeal(order,processrouteList);
			
	}

	

}
	
	//订单处理
	public void mergeorderdeal(Orders order){
		Orders order1 = orderservice.get("aufnr",order.getAufnr());//生产订单号
		if(order1 == null){
			orderservice.save(order);
		}else{
			order.setId(order1.getId());
			orderservice.merge(order);
		}
		
	}
	
	//产品处理
	public void mergeproductdeal(Orders order){
		Products products = productsservice.get("productsCode",order.getMatnr());
		if(products==null){
			Products products1 = new Products();
			products1.setProductsCode(order.getMatnr());
			products1.setProductsName(order.getMaktx());
			products1.setIsDel("N");
			products1.setState("1");
			productsservice.save(products1);
		}else{
			products.setProductsName(order.getMaktx());
			productsservice.update(products);
		}
		
	}
	
	//随工单处理
	public void mergeworkingbilldeal(Orders order,List<WorkingBill> workingbillList){
		List<WorkingBill> workingbillList00 = new ArrayList<WorkingBill>();
		for(int y=0;y<workingbillList.size();y++){
			WorkingBill workingBill = workingbillList.get(y);
			if(workingBill.getAufnr().equals(order.getAufnr())){
				workingbillList00.add(workingBill);
			}
		}
		List<WorkingBill> workingbillList01 = workingbilldao.getList("aufnr", order.getAufnr());
		if(workingbillList00.size() >= workingbillList01.size()){//如果SAP的比MES 多
		 boolean flag = false;
		 for(int x=0;x<workingbillList00.size();x++){
			WorkingBill WorkingBill00 = workingbillList00.get(x);
			for(int z=0;z<workingbillList01.size();z++){
				WorkingBill WorkingBill01 = workingbillList01.get(z);
				if(WorkingBill01.getWorkingBillCode().equals(WorkingBill00.getWorkingBillCode())){
					this.updateWorkingBill(WorkingBill00);
					flag = true;
				}
				
			}
			if(!flag){ //没找到
				String id = this.save(WorkingBill00);
				WorkingBill00.setId(id);
				workingbillList01.add(WorkingBill00);
			}
		 }
		}else if(workingbillList00.size() < workingbillList01.size()){//如果MES比SAP多
			boolean flag = false;
			 for(int x=0;x<workingbillList01.size();x++){
				WorkingBill WorkingBill01 = workingbillList01.get(x);
				for(int z=0;z<workingbillList00.size();z++){
					WorkingBill WorkingBill00 = workingbillList00.get(z);
					if(WorkingBill01.getWorkingBillCode().equals(WorkingBill00.getWorkingBillCode())){
						this.updateWorkingBill(WorkingBill00);
						flag = true;
					}
				}
				if(!flag){ //没找到
					WorkingBill01.setIsdel("Y");
					this.update(WorkingBill01);
				}
			 }
		}
	}
	
	//处理BOM信息
	public void mergebomdeal(Orders order,List<Bom> bomList){
		List<Bom> bomList00 = new ArrayList<Bom>();//SAP
		for(int y=0;y<bomList.size();y++){
			Bom bom = bomList.get(y);
			if(bom.getRsnum().equals(order.getRsnum())){
				bomList00.add(bom);
			}
		}
		boolean flag = true;
		Integer maxversion = bomservice.getMaxVersion(order.getAufnr());//根据生产订单获取最高版本号
		if(maxversion == null){//如果没有找到，需要创建版本
			flag = false;
		}else{
			List<Bom> bomList01 = bomservice.getBomList(order.getAufnr(), maxversion);//根据生产订单和版本号获取Bom结合
			if(bomList00.size() >= bomList01.size()){//SAP 比MES 多
				for(int y=0;y<bomList00.size();y++){
					Bom bom00 = bomList00.get(y);
					boolean flag1 = false;
					String bom00str = ThinkWayUtil.null2String(order.getMatnr())+ThinkWayUtil.null2String(order.getGamng())+ThinkWayUtil.null2String(bom00.getMaterialCode())+ThinkWayUtil.null2o(bom00.getMaterialAmount());//+ThinkWayUtil.null2String(bom00.getMaterialUnit());//产品编码+产品数量+BOM物料编码+BOM物料缩量+BOM物料单位
					for(int z=0;z<bomList01.size();z++){
						Bom bom01 = bomList01.get(z);
						String bom01str = ThinkWayUtil.null2String(order.getMatnr())+ThinkWayUtil.null2String(order.getGamng())+ThinkWayUtil.null2String(bom01.getMaterialCode())+ThinkWayUtil.null2o(bom01.getMaterialAmount());//+bom01.getMaterialUnit();//产品编码+产品数量+BOM物料编码+BOM物料缩量+BOM物料单位
						if(bom00str.equals(bom01str)){//如果找到.flag1 设置为true,停止
							flag1 = true;
							break;
						}
					}
					if(!flag1){//如果 SAP 在MES中有一条未匹配上，直接停止循环，BOM升级
						flag = false;
						break;
					}
				}
			}else{
				for(int y=0;y<bomList01.size();y++){
					Bom bom01 = bomList01.get(y);
					boolean flag1 = false;
					String bom01str = ThinkWayUtil.null2String(order.getMatnr())+ThinkWayUtil.null2String(order.getGamng())+ThinkWayUtil.null2String(bom01.getMaterialCode())+ThinkWayUtil.null2o(bom01.getMaterialAmount());//+bom01.getMaterialUnit();//产品编码+产品数量+BOM物料编码+BOM物料缩量+BOM物料单位
					for(int z=0;z<bomList00.size();z++){
						Bom bom00 = bomList00.get(z);
						String bom00str = ThinkWayUtil.null2String(order.getMatnr())+ThinkWayUtil.null2String(order.getGamng())+ThinkWayUtil.null2String(bom00.getMaterialCode())+ThinkWayUtil.null2o(bom00.getMaterialAmount());//+ThinkWayUtil.null2String(bom00.getMaterialUnit());//产品编码+产品数量+BOM物料编码+BOM物料缩量+BOM物料单位
						if(bom01str.equals(bom00str)){//如果找到.flag1 设置为true,停止
							flag1 = true;
							break;
						}
					}
					if(!flag1){//如果 SAP 在MES中有一条未匹配上，直接停止循环，BOM升级
						flag = false;
						break;
					}
				}
			}
		}
		
		if(!flag){//升级版本
			for(int y=0;y<bomList00.size();y++){
				Bom bom00 = bomList00.get(y);
				if(maxversion == null)
					maxversion = 0;
				Orders orders = orderservice.get("aufnr",order.getAufnr());
				bom00.setVersion(maxversion+1);
				bom00.setOrders(orders);
				bomservice.save(bom00);
			}
		}
		
	}

	//处理工艺路线
	public void mergeprocessroutedeal(Orders order,List<ProcessRoute> processrouteList){
		List<ProcessRoute> processrouteList00 = new ArrayList<ProcessRoute>();//获得跟当前订单相关的工艺路线
		for(int y=0;y<processrouteList.size();y++){
			ProcessRoute processroute = processrouteList.get(y);
			if(processroute.getAufpl().equals(order.getAufpl())){
				processrouteList00.add(processroute);
			}
		}
		boolean flag = true;
		Integer maxversion = processrouteservice.getMaxVersion(order.getAufnr());
		if(maxversion == null){
			flag = false;
		}else{
			List<ProcessRoute> processrouteList01 = processrouteservice.getProcessRouteList(order.getAufnr(), maxversion);
			if(processrouteList00.size()>=processrouteList01.size()){//如果SAP 比MES 多
				for(int y=0;y<processrouteList00.size();y++){
					ProcessRoute processroute00 = processrouteList00.get(y);
					boolean flag1 = false;
					String process00str = ThinkWayUtil.null2String(order.getMatnr())+ThinkWayUtil.null2String(processroute00.getProcessCode())+ThinkWayUtil.null2String(processroute00.getWorkCenter())+ThinkWayUtil.null2String(processroute00.getSteus())+ThinkWayUtil.null2String(processroute00.getProcessName());//产品编码+工序编码+工作中心+控制码+工序名称
					for(int z=0;z<processrouteList01.size();z++){
						ProcessRoute processroute01 = processrouteList01.get(z);
						String process01str = ThinkWayUtil.null2String(order.getMatnr())+ThinkWayUtil.null2String(processroute01.getProcessCode())+ThinkWayUtil.null2String(processroute01.getWorkCenter())+ThinkWayUtil.null2String(processroute01.getSteus())+ThinkWayUtil.null2String(processroute01.getProcessName());//产品编码+工序编码+工作中心+控制码+工序名称
						if(process00str.equals(process01str)){//如果找到.flag1 设置为true,停止
							flag1 = true;
							break;
						}
					}
					if(!flag1){
						flag = false;
						break;
					}
				}
			}else{
				for(int y=0;y<processrouteList01.size();y++){
					ProcessRoute processroute01 = processrouteList01.get(y);
					boolean flag1 = false;
					String process01str = ThinkWayUtil.null2String(order.getMatnr())+ThinkWayUtil.null2String(processroute01.getProcessCode())+ThinkWayUtil.null2String(processroute01.getWorkCenter())+ThinkWayUtil.null2String(processroute01.getSteus())+ThinkWayUtil.null2String(processroute01.getProcessName());//产品编码+工序编码+工作中心+控制码+工序名称
					for(int z=0;z<processrouteList00.size();z++){
						ProcessRoute processroute00 = processrouteList00.get(z);
						String process00str = ThinkWayUtil.null2String(order.getMatnr())+ThinkWayUtil.null2String(processroute00.getProcessCode())+ThinkWayUtil.null2String(processroute00.getWorkCenter())+ThinkWayUtil.null2String(processroute00.getSteus())+ThinkWayUtil.null2String(processroute00.getProcessName());//产品编码+工序编码+工作中心+控制码+工序名称
						if(process00str.equals(process01str)){//如果找到.flag1 设置为true,停止
							flag1 = true;
							break;
						}
						
					}
					if(!flag1){
						flag = false;
						break;
					}
				}
			}
			
			
		}
		
		if(!flag){
			for(int y=0;y<processrouteList00.size();y++){
				ProcessRoute processroute = processrouteList00.get(y);
				if(maxversion == null)
					maxversion = 0;
				Orders orders = orderservice.get("aufnr",order.getAufnr());
				processroute.setVersion(maxversion+1);
				processroute.setOrders(orders);
				processrouteservice.save(processroute);
			}
		}
		
		
	}
	
}