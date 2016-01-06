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
	public List getListWorkingBillByDate(Admin admin) {
		return workingbilldao.getListWorkingBillByDate(admin);
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
	public void mergeWorkingBill(List<WorkingBill> workingbillList,
			List<Orders> orderList, List<ProcessRoute> processrouteList, List<Bom> bomList) {
		
		//生产订单
		for(int i=0;i<orderList.size();i++){
			Orders order = orderList.get(i);
			Orders order1 = orderservice.get("aufnr",order.getAufnr());//生产订单号
			if(order1 == null){
				orderservice.save(order);
			}else{
				order.setId(order1.getId());
				orderservice.merge(order);
			}
			
			/**处理产品信息***/
			Products products = productsservice.get("productsCode",order.getMatnr());
			String productsid="";
			if(products==null){
				Products products1 = new Products();
				products1.setProductsCode(order.getMatnr());
				products1.setProductsName(order.getMaktx());
				products1.setIsDel("N");
				productsid = productsservice.save(products1);
			}else{
				products.setProductsName(order.getMaktx());
				productsservice.update(products);
				productsid = products.getId();
			}
			
			/**处理随工单信息***/
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
			
			/**处理BOM信息***/
			Integer maxVersion = bomservice.getMaxVersionBycode(order.getMatnr());
			List<Bom> bomList00 = new ArrayList<Bom>();
			for(int y=0;y<bomList.size();y++){
				Bom bom = bomList.get(y);
				if(bom.getRsnum().equals(order.getRsnum())){
					bomList00.add(bom);
				}
			}
			
			List<Bom> bomList01 = bomservice.getList("rsnum",order.getRsnum());
			if(bomList00.size() != bomList01.size()){//如果双方不相等，升级版本
					for(int y=0;y<bomList00.size();y++){
						Bom bom = bomList00.get(y);
						if(maxVersion == null){//如果没有找到
							bom.setVersion(1);
						}else{//如果找到
							bom.setVersion(maxVersion+1);
						}
						Products products1 = new Products();
						products1.setId(productsid);
						bom.setProducts(products1);
						Double oneAmount = ArithUtil.div(bom.getMaterialAmount(), Double.parseDouble(order.getGamng()));//获取一个多少数量
						oneAmount = ArithUtil.round1(oneAmount,4);
						bom.setProductAmount(1d);//一个产品
						bom.setMaterialAmount(oneAmount);//一个产品需要的数量
						bomservice.save(bom);
						for(WorkingBill workingbill : workingbillList01){
							int res = workingbill.getProductDate().compareTo(ThinkWayUtil.SystemDate());
							if(res>=0){//如果随工单日期比系统日期大，应该更新bom 版本
								workingbill.setBomversion(maxVersion);
								workingbilldao.update(workingbill);
							}
						}
					}
				
		}else{
			boolean flag = true;
			for(int x=0;x<bomList00.size();x++){//用SAP在MES中找
				boolean flag1 = false;
				Bom bom00 = bomList00.get(x);
				for(int z=0;z<bomList01.size();z++){
					Bom bom01 = bomList01.get(z);
					if(bom01.getMaterialCode().equals(bom00.getMaterialCode()) || bom01.getMaterialAmount() != bom00.getMaterialAmount()){//如果找到。 跳出一层循环
						flag1 = true;
						break;
					}
				}
				if(flag1 == false){//如有发现有一条没找到。需要升级版本，跳出循环
					flag = false;
					break;
				}
			}
			boolean flag2 = true;
			for(int x=0;x<bomList01.size();x++){//MES 在SAP中找
				boolean flag3 = false;
				Bom bom01 = bomList01.get(x);
				for(int z=0;z<bomList00.size();z++){
					Bom bom00 = bomList00.get(z);
					if(bom00.getMaterialCode().equals(bom01.getMaterialCode()) || bom01.getMaterialAmount() != bom00.getMaterialAmount()){//
						flag3 = true;
						break;
					}
				}
				if(flag3 == false){//如果发现有一条没找到。需要升级版本，跳出循环
					flag2 = false;
					break;
				}
			}
			
			if(flag==false || flag2==false){//如果两方相比，如果都发现有不对的地方，升级版本
				for(int y=0;y<bomList00.size();y++){
					Bom bom = bomList00.get(y);
					if(maxVersion == null){//如果没有找到
						bom.setVersion(1);
					}else{//如果找到
						bom.setVersion(maxVersion+1);
					}
					Products products1 = new Products();
					products1.setId(productsid);
					bom.setProducts(products1);
					Double oneAmount = ArithUtil.div(bom.getMaterialAmount(), Double.parseDouble(order.getGamng()));//获取一个多少数量
					oneAmount = ArithUtil.round1(oneAmount,4);
					bom.setProductAmount(1d);//一个产品
					bom.setMaterialAmount(oneAmount);//一个产品需要的数量
					bomservice.save(bom);
					for(WorkingBill workingbill : workingbillList01){
						int res = workingbill.getProductDate().compareTo(ThinkWayUtil.SystemDate());
						if(res>=0){//如果随工单日期比系统日期大，应该更新bom 版本
							workingbill.setBomversion(maxVersion);
							workingbilldao.update(workingbill);
						}
					}
				}
			}else{//给随工单分配版本
				for(WorkingBill workingbill : workingbillList01){
					int res = workingbill.getProductDate().compareTo(ThinkWayUtil.SystemDate());
					if(res>=0){//如果随工单日期比系统日期大，应该更新bom 版本
						workingbill.setBomversion(maxVersion);
						workingbilldao.update(workingbill);
					}
				}
			}
		}
		
		/***处理工艺路线***/
			Integer maxVersion1 = processrouteservice.getMaxVersionBycode(order.getMatnr());
			List<ProcessRoute> processrouteList00 = new ArrayList<ProcessRoute>();
			for(int y=0;y<processrouteList.size();y++){
				ProcessRoute processroute = processrouteList.get(y);
				if(processroute.getAufpl().equals(order.getAufpl())){
					processrouteList00.add(processroute);
				}
			}
			List<ProcessRoute> processrouteList01 = processrouteservice.getList("aufpl", order.getAufpl());
			if(processrouteList00.size() != processrouteList01.size()){//如果双方不相等，升级版本
				for(int y=0;y<processrouteList00.size();y++){
					ProcessRoute processroute = processrouteList00.get(y);
					if(maxVersion1 == null){//如果没有找到
						processroute.setVersion(1);
					}else{//如果找到
						processroute.setVersion(maxVersion1+1);
					}
					Products products1 = new Products();
					products1.setId(productsid);
					processroute.setProducts(products1);
					processrouteservice.save(processroute);
					for(WorkingBill workingbill : workingbillList01){
						int res = workingbill.getProductDate().compareTo(ThinkWayUtil.SystemDate());
						if(res>=0){//如果随工单日期比系统日期大，应该更新bom 版本
							workingbill.setProcessversion(maxVersion1);
							workingbilldao.update(workingbill);
						}
					}
				}
			}else{
				boolean flag = true;
				for(int x=0;x<processrouteList00.size();x++){//用SAP在MES中找
					boolean flag1 = false;
					ProcessRoute processroute00 = processrouteList00.get(x);
					for(int z=0;z<processrouteList01.size();z++){
						ProcessRoute processroute01 = processrouteList01.get(z);
						if(processroute01.getProcessCode().equals(processroute00.getProcessCode())){//如果找到。 跳出一层循环
							flag1 = true;
							break;
						}
					}
					if(flag1 == false){//如有发现有一条没找到。需要升级版本，跳出循环
						flag = false;
						break;
					}
				}
				boolean flag2 = true;
				for(int x=0;x<processrouteList01.size();x++){//MES 在SAP中找
					boolean flag3 = false;
					ProcessRoute processroute01 = processrouteList01.get(x);
					for(int z=0;z<processrouteList00.size();z++){
						ProcessRoute processroute00 = processrouteList00.get(z);
						if(processroute00.getProcessCode().equals(processroute01.getProcessCode())){//
							flag3 = true;
							break;
						}
					}
					if(flag3 == false){//如果发现有一条没找到。需要升级版本，跳出循环
						flag2 = false;
						break;
					}
				}
				
				if(flag==false || flag2==false){//如果两方相比，如果都发现有不对的地方，升级版本
					for(int y=0;y<processrouteList00.size();y++){
						ProcessRoute processroute = processrouteList00.get(y);
						if(maxVersion1 == null){//如果没有找到
							processroute.setVersion(1);
						}else{//如果找到
							processroute.setVersion(maxVersion1+1);
						}
						Products products1 = new Products();
						products1.setId(productsid);
						processroute.setProducts(products1);
						processrouteservice.save(processroute);
						for(WorkingBill workingbill : workingbillList01){
							int res = workingbill.getProductDate().compareTo(ThinkWayUtil.SystemDate());
							if(res>=0){//如果随工单日期比系统日期大，应该更新bom 版本
								workingbill.setBomversion(maxVersion1);
								workingbilldao.update(workingbill);
							}
						}
					}
				}else{//给随工单分配版本
					for(WorkingBill workingbill : workingbillList01){
						int res = workingbill.getProductDate().compareTo(ThinkWayUtil.SystemDate());
						if(res>=0){//如果随工单日期比系统日期大，应该更新bom 版本
							workingbill.setProcessversion(maxVersion1);
							workingbilldao.update(workingbill);
						}
					}
				}
			}
	}

	

}


}