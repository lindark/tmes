package cc.jiuyi.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.RepairinDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Bom;
import cc.jiuyi.entity.Products;
import cc.jiuyi.entity.Repairin;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.BomService;
import cc.jiuyi.service.MaterialService;
import cc.jiuyi.service.OrdersService;
import cc.jiuyi.service.ProductsService;
import cc.jiuyi.service.RepairinService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.ArithUtil;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * Service实现类 返修收货
 */
@Service
public class RepairinServiceImpl extends BaseServiceImpl<Repairin, String>
		implements RepairinService {
	@Resource
	private RepairinDao repairinDao;
	@Resource
	private WorkingBillService workingbillService;
	@Resource
	private AdminService adminservice;

	@Resource
	public void setBaseDao(RepairinDao repairinDao) {
		super.setBaseDao(repairinDao);
	}
	@Resource
	private ProductsService productsService;
	@Resource
	private BomService bomService;
	@Resource
	private MaterialService mService;//物料表
	@Resource
	private OrdersService ordersservice;
	
	@Override
	public Pager findPagerByjqGrid(Pager pager, HashMap<String, String> map,
			String workingbillId) {
		return repairinDao.findPagerByjqGrid(pager, map, workingbillId);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		repairinDao.updateisdel(ids, oper);
	}

	@Override
	/**
	 * 考虑线程同步
	 */
	public synchronized void updateState(List<Repairin> list, String statu,
			String workingbillid, String cardnumber) {
		//Admin admin = adminservice.getByCardnum(cardnumber);
		WorkingBill workingbill = workingbillService.get(workingbillid);
		Integer totalamount = workingbill.getTotalRepairinAmount();
		for (int i = 0; i < list.size(); i++) {
			Repairin repairin = list.get(i);
			if (statu.equals("1")) {
				totalamount = repairin.getReceiveAmount() + totalamount;
			}
			if (statu.equals("3") && repairin.getState().equals("1")) {
				totalamount -= repairin.getReceiveAmount();
			}
			//repairin.setConfirmUser(admin);
			//repairin.setState(statu);
			//repairinDao.update(repairin);
		}
		workingbill.setTotalRepairinAmount(totalamount);
		workingbillService.update(workingbill);
	}

	@Override
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map) {
		return repairinDao.historyjqGrid(pager, map);
	}

	/**
	 * 与SAP交互   退料262  905
	 * list 主表数据   wbid随工单id
	 * @return
	 * @author gyf
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getSAPMap(List<Repairin> list, WorkingBill wb,String cardnumber)
	{
		Admin admin = adminservice.getByCardnum(cardnumber);
		Products p=this.productsService.getByPcode(wb.getMatnr());//根据产品编号查询产品
		//Date productDate = ThinkWayUtil.formatStringDate(wb.getProductDate());//生产日期
		String aufnr = wb.getWorkingBillCode().substring(0,wb.getWorkingBillCode().length()-2);
		List<Bom> list_bom=bomService.findBom(aufnr, wb.getProductDate());
		String workingBillCode=wb.getWorkingBillCode();
		List<Bom>listbom=new ArrayList<Bom>();
		//物料表是否存在
		for(int i=0;i<list_bom.size();i++)
		{
			Bom b=list_bom.get(i);
			//根据物料id查询是否存在
			if(this.mService.getByCode(b.getMaterialCode()))
			{
				listbom.add(b);
			}
		}
		List<Map<Object,Object>>list1=new ArrayList<Map<Object,Object>>();
		List<Map<Object,Object>>list2=new ArrayList<Map<Object,Object>>();
		if(listbom.size()>0)
		{
			for(int i=0;i<list.size();i++)
			{
				Map<Object,Object>m=new HashMap<Object, Object>();
				Map<Object,Object>m2=new HashMap<Object, Object>();
				Repairin r=list.get(i);
				int n=r.getReceiveAmount();//收获数量
				for(int j=0;j<listbom.size();j++)
				{
					Bom b=listbom.get(j);
					//组件总数量=返修数量/产品数量 *组件数量
					String count=""+ArithUtil.mul(ArithUtil.div(n, b.getProductAmount()), b.getMaterialAmount());
					m.put("MATNR", b.getMaterialCode());//物料编码
					m.put("ZSFSL", count);//数量
					m.put("ITEM_TEXT", workingBillCode.substring(workingBillCode.length()-2));//项目文本 选填
					m.put("XUH", r.getId());
					list1.add(m);
				}
				m2.put("WERKS", admin.getDepartment().getTeam().getFactoryUnit().getWorkShop().getFactory().getFactoryCode());//工厂
				m2.put("LGORT", admin.getDepartment().getTeam().getFactoryUnit().getWarehouse());//库存地点
				m2.put("ZTEXT", workingBillCode.substring(workingBillCode.length()-2));//抬头文本
				m2.put("XUH", r.getId());//序号
				list2.add(m2);
			}
		}
		List l=new ArrayList();
		l.add(list1);
		l.add(list2);
		return l;
	}
	/**
	 * 与SAP交互没有问题,更新本地数据库
	 */
	public void updateMyData(Map<Object,Object>map,String cardnumber)
	{
		Repairin r=this.repairinDao.get(map.get("XUH").toString());//根据id查询
		Admin admin=this.adminservice.getByCardnum(cardnumber);
		r.setConfirmUser(admin);//确认人
		r.setMblnr(map.get("EX_MBLNR").toString());//凭证号
		r.setState("1");//状态改为已确认
		r.setModifyDate(new Date());//更新日期
		this.repairinDao.update(r);
	}
}
