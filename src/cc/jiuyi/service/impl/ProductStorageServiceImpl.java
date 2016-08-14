package cc.jiuyi.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.ProductStorageDao;
import cc.jiuyi.dao.WorkingBillDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Bom;
import cc.jiuyi.entity.Orders;
import cc.jiuyi.entity.ProcessRoute;
import cc.jiuyi.entity.ProductStorage;
import cc.jiuyi.entity.Products;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.BomService;
import cc.jiuyi.service.OrdersService;
import cc.jiuyi.service.ProcessRouteService;
import cc.jiuyi.service.ProductStorageService;
import cc.jiuyi.service.ProductsService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.ThinkWayUtil;

import org.springframework.stereotype.Service;
//import org.springmodules.cache.annotations.CacheFlush;
import org.apache.log4j.Logger;


/**
 * Service实现类 - 随工单
 */

@Service
public class ProductStorageServiceImpl extends
		BaseServiceImpl<ProductStorage, String> implements ProductStorageService{
	private static Logger log = Logger.getLogger(ProductStorageServiceImpl.class);  
	@Resource
	private ProductStorageDao productstoragedao;
	@Resource
	private OrdersService orderservice;
	@Resource
	private ProductsService productsservice;
	@Resource
	private ProcessRouteService processrouteservice;
	@Resource
	private BomService bomservice;
	@Resource
	private AdminService adminservice;
	@Resource
	public void setBaseDao(ProductStorageDao productStoragedao) {
		super.setBaseDao(productStoragedao);
	}

	@Override
	public Pager findPagerByjqGrid(Pager pager, Map map) {
		return productstoragedao.findPagerByjqGrid(pager, map);
	}

	public void savePS(ProductStorage ps){
		productstoragedao.addProductStorage(ps);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		productstoragedao.updateisdel(ids, oper);
	}

	@Override
	public List<ProductStorage> getListWorkingBillByDate(Admin admin) {
		// 找到当班随工单思路
		// 1. 获取 生产日期 
		// 2. 获取 班次
		// 3. 获取工作中心
		// 4. 根据工作中心 跟 当前身份的 单元上维护的 工作中心比较
		// 5. 显示出对应的随工单
		List<ProductStorage> workingbillList = new ArrayList<ProductStorage>();
		try{
		String productDate = admin.getProductDate();//生产日期
		String shift = admin.getShift();//班次
		String workcenter = admin.getTeam()==null?"" :admin.getTeam().getFactoryUnit()==null?"":admin.getTeam().getFactoryUnit().getWorkCenter();//获取当前登录身份的工作中心
		if("".equals(workcenter))
			return workingbillList;
		
		workingbillList = productstoragedao.findWorkingBill(workcenter,productDate,shift);
		}catch(Exception e){
			e.printStackTrace();
		}
		return workingbillList;
	}



	public void update(ProductStorage workingbill) {
		productstoragedao.update(workingbill);
	}
	public ProductStorage merge(ProductStorage entity) {
		return productstoragedao.merge(entity);
	}

	/**
	 * 查询随工单表中的id 和 产品名称maktx
	 */
	@Override
	public List<ProductStorage> getIdsAndNames() {
		return this.productstoragedao.getIdsAndNames();
	}

	@Override
	public List<ProductStorage> findListWorkingBill(Object[] productsid,
			String productDate, String shift) {
		return productstoragedao.findListWorkingBill(productsid, productDate, shift);
	}
	

	public void updateWorkingBill(ProductStorage workingbill) {
		productstoragedao.updateWorkingBill(workingbill);
	}

	/**
	 * 根据随工单编号 获取 下一条记录
	 */
	
	public ProductStorage getCodeNext(String productstorage,String productDate,String shift){
		ProductStorage ps=new ProductStorage();
		ps.setAufnr(productstorage);
		return ps;
	}
	@Override
	public ProductStorage getCodeNext(Admin admin, String workingbillCode,
			String productDate, String shift) {
		
		return null;
	}
	@Override
	public List<ProductStorage> getWorkingBillByProductsCode(String matnr) {
		return productstoragedao.getWorkingBillByProductsCode(matnr);
	}

	@Override
	public  void mergeWorkingBill(List<ProductStorage> workingbillList,
			List<Orders> orderList, List<ProcessRoute> processrouteList, List<Bom> bomList) {
		log.info("生产订单:"+orderList.size()+"-----"+"随工单:"+workingbillList.size()+"-----"+"工艺:"+processrouteList.size()+"-----"+"BOM:"+bomList.size());
		//生产订单
		for(int i=0;i<orderList.size();i++){
			Orders order = orderList.get(i);
			/***生产订单***/
			mergeorderdeal(order);//订单处理
			/**处理产品信息***/
			mergeproductdeal(order);//产品处理
			/**处理随工单信息***/
//			mergeworkingbilldeal(order,workingbillList);
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
 			List<Bom> bomList01 = bomservice.getBomListRFC(order.getAufnr(), maxversion,"sys");//根据生产订单和版本号获取Bom结合
			if(bomList00.size() >= bomList01.size()){//SAP 比MES 多
				for(int y=0;y<bomList00.size();y++){
					Bom bom00 = bomList00.get(y);
					boolean flag1 = false;
					String bom00str = ThinkWayUtil.null2String(order.getMatnr())+ThinkWayUtil.null2String(order.getGamng())+ThinkWayUtil.null2String(bom00.getMaterialCode())+ThinkWayUtil.null2o(bom00.getMaterialAmount()+ThinkWayUtil.null2String(bom00.getShift())+ThinkWayUtil.null2String(bom00.getIsDel()));//+ThinkWayUtil.null2String(bom00.getMaterialUnit());//产品编码+产品数量+BOM物料编码+BOM物料缩量+BOM物料单位
					for(int z=0;z<bomList01.size();z++){
						Bom bom01 = bomList01.get(z);
						String bom01str = ThinkWayUtil.null2String(order.getMatnr())+ThinkWayUtil.null2String(order.getGamng())+ThinkWayUtil.null2String(bom01.getMaterialCode())+ThinkWayUtil.null2o(bom01.getMaterialAmount()+ThinkWayUtil.null2String(bom01.getShift())+ThinkWayUtil.null2String(bom01.getIsDel()));//+bom01.getMaterialUnit();//产品编码+产品数量+BOM物料编码+BOM物料缩量+BOM物料单位
						if(bom00str.equals(bom01str)){//如果找到.flag1 设置为true,停止
							flag1 = true;
							break;
						}
						if(z==bomList01.size() -1 && flag1 == false){
							log.info("bom01str:"+bom01str);
						}
					}
					if(!flag1){//如果 SAP 在MES中有一条未匹配上，直接停止循环，BOM升级
						log.info("bom00str:"+bom00str);
						flag = false;
						break;
					}
				}
			}else{
				for(int y=0;y<bomList01.size();y++){
					Bom bom01 = bomList01.get(y);
					boolean flag1 = false;
					String bom01str = ThinkWayUtil.null2String(order.getMatnr())+ThinkWayUtil.null2String(order.getGamng())+ThinkWayUtil.null2String(bom01.getMaterialCode())+ThinkWayUtil.null2o(bom01.getMaterialAmount()+ThinkWayUtil.null2String(bom01.getShift())+ThinkWayUtil.null2String(bom01.getIsDel()));//+bom01.getMaterialUnit();//产品编码+产品数量+BOM物料编码+BOM物料缩量+BOM物料单位
					for(int z=0;z<bomList00.size();z++){
						Bom bom00 = bomList00.get(z);
						String bom00str = ThinkWayUtil.null2String(order.getMatnr())+ThinkWayUtil.null2String(order.getGamng())+ThinkWayUtil.null2String(bom00.getMaterialCode())+ThinkWayUtil.null2o(bom00.getMaterialAmount()+ThinkWayUtil.null2String(bom00.getShift())+ThinkWayUtil.null2String(bom00.getIsDel()));//+ThinkWayUtil.null2String(bom00.getMaterialUnit());//产品编码+产品数量+BOM物料编码+BOM物料缩量+BOM物料单位
						if(bom01str.equals(bom00str)){//如果找到.flag1 设置为true,停止
							flag1 = true;
							break;
						}
						if(z==bomList00.size() -1 && flag1 == false){
							log.info("bom00str:"+bom00str);
						}
					}
					if(!flag1){//如果 SAP 在MES中有一条未匹配上，直接停止循环，BOM升级
						log.info("bom01str:"+bom01str);
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
				bom00.setProductAmount(Double.parseDouble(orders.getGamng()));//将生产数量写入到Bom表中
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

	
	public List<ProductStorage> getListWorkingBillByDate(String productDate,String shift,String workcenter,String matnr){
		return productstoragedao.getListWorkingBillByDate(productDate,shift,workcenter,matnr);
	}

	/**
	 * 计算投入产出表逻辑
	 */
	public void updateWorkingInoutCalculate(List<WorkingBill> paramaterList) {
		
		
		
	}

	@Override
	public void updateWorkingInoutCalculate(List<ProductStorage> paramaterList,
			HashMap<String, Object> maps) {
		
	}

	@Override
	public List<ProductStorage> findListWorkingBill(
			String productDate, String shift) {
		return productstoragedao.findListWorkingBill(productDate, shift);
	}

	@Override
	public List<ProductStorage> getWorkingBillList(String workingBillId) {
		return productstoragedao.getWorkingBillList(workingBillId);
	}

	@Override
	public List<ProductStorage> getListWorkingBillByProductDate(String startdate,
			String enddate, String workcode) {
		return productstoragedao.getListWorkingBillByProductDate(startdate, enddate, workcode);
	}


}