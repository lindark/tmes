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
import cc.jiuyi.entity.Order;
import cc.jiuyi.entity.Process;
import cc.jiuyi.entity.ProcessRoute;
import cc.jiuyi.entity.Products;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.BrandService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.OrderService;
import cc.jiuyi.service.ProcessRouteService;
import cc.jiuyi.service.ProductsService;
import cc.jiuyi.service.WorkingBillService;

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
	private OrderService orderservice;
	@Resource
	private ProductsService productsservice;
	@Resource
	private ProcessRouteService processrouteservice;

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
			List<Order> orderList, List<ProcessRoute> processrouteList, List<Bom> bomList) {
		
		//生产订单
		for(int i=0;i<orderList.size();i++){
			Order order = orderList.get(i);
			Order order1 = orderservice.get("aufnr",order.getAufnr());//生产订单号
			if(order1 == null){
				orderservice.save(order);
			}else{
				order.setId(order1.getId());
				orderservice.merge(order);
			}
			Products products = productsservice.get("productsCode",order.getMatnr());
			if(products==null){
				Products products1 = new Products();
				products1.setProductsCode(order.getMatnr());
				products1.setProductsName(order.getMaktx());
				products1.setIsDel("N");
				productsservice.save(products1);
			}else{
				products.setProductsName(order.getMaktx());
				productsservice.update(products);
			}
			List<WorkingBill> workingbillList00 = new ArrayList<WorkingBill>();
			for(int y=0;y<workingbillList.size();y++){
				WorkingBill workingBill = workingbillList.get(i);
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
					this.save(WorkingBill00);
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
		
		//工艺路线
//		for(int i=0;i<processrouteList.size();i++){
//			//processrouteservice
//		}
//		
		//Bom

		
		
		
	}

	




}