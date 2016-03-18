package cc.jiuyi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.BomDao;
import cc.jiuyi.entity.Bom;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.entity.Orders;
import cc.jiuyi.service.BomService;
import cc.jiuyi.service.OrdersService;

/**
 * Service实现类 - Bom
 */

@Service
public class BomServiceImpl extends BaseServiceImpl<Bom, String> implements BomService {

	@Resource
	private BomDao bomDao;
	@Resource
	private OrdersService orderservice;

	@Resource
	public void setBaseDao(BomDao bomDao) {
		super.setBaseDao(bomDao);
	}


	@Override
	public Pager findPagerByjqGrid(Pager pager, HashMap<String, String> map) {
		return bomDao.findPagerByjqGrid(pager, map);
	}


	@Override
	public List<Bom> findBom(String aufnr,String productDate,String workingBillCode) {
		String workingbilllast = StringUtils.substring(workingBillCode, workingBillCode.length()-2,workingBillCode.length());
		
		Orders orders = orderservice.get("aufnr",aufnr);
		Integer maxversion = bomDao.getMaxversion(orders.getId(),productDate);
		if(maxversion == null)
			maxversion=0;
		List<Bom> bomList = bomDao.getBomList(aufnr, maxversion,workingbilllast);
		if(bomList == null || bomList.size() <=0)
			bomList = bomDao.getBomList(aufnr, maxversion);
		return bomList;
	}
	
	public List<Bom> findBom(String aufnr,String productDate,String materialCode,String workingBillCode){
		String workingbilllast = StringUtils.substring(workingBillCode, workingBillCode.length()-2,workingBillCode.length());
		Orders orders = orderservice.get("aufnr",aufnr);//获取生产订单号
		//Integer maxversion = bomDao.getMaxversion(orders.getId(),productDate);
		Integer maxversion = bomDao.getMaxversion(workingBillCode.substring(workingBillCode.length()-2,workingBillCode.length()),orders.getId(),materialCode);
		if(maxversion == null)
			maxversion=0;
		List<Bom> bomList = bomDao.getBomList(aufnr, maxversion,materialCode,workingbilllast);
		if(bomList ==null || bomList.size() <=0)
			bomList = bomDao.getBomList1(aufnr, maxversion, materialCode);
		return bomList;
	}


	@Override
	public Integer getMaxVersion(String aufnr) {
		return bomDao.getMaxVersion(aufnr);
	}


	@Override
	public List<Bom> getBomList(String aufnr, Integer maxversion) {
		return bomDao.getBomList(aufnr, maxversion);
	}
	
	/**
	 * jqGrid:(根据:子件编码/名称,随工单)查询
	 */
	public Pager getPieceByCondition(Pager pager,HashMap<String, String> map,WorkingBill wb)
	{
		String aufnr = wb.getWorkingBillCode().substring(0,wb.getWorkingBillCode().length()-2);
		String productDate = wb.getProductDate();
		String workingBillCode=wb.getWorkingBillCode();
		String workingbilllast = StringUtils.substring(workingBillCode, workingBillCode.length()-2,workingBillCode.length());
		Orders orders = orderservice.get("aufnr",aufnr);
		Integer maxversion = bomDao.getMaxVersion(orders.getMatnr(), productDate);
		if(maxversion!=null)
		{
			if(workingbilllast!=null)
			{
				return this.bomDao.getPieceByCondition(pager,map, aufnr, maxversion,workingbilllast);
			}
			return this.bomDao.getPieceByCondition(pager,map, aufnr, maxversion);
		}
		return null;
	}


	@Override
	public Integer getMaxversion(String orderId, String productDate) {
		return bomDao.getMaxversion(orderId, productDate);
	}
	
	@Override
	public String getMaterialName(String materialCode) {
		return bomDao.getMaterialName(materialCode);
	}


	/**
	 * 根据订单号,生产日期,以"5"开关的查询
	 */
	public Bom getBomByConditions(String aufnr, String productDate, String num,String workingBillCode)
	{
		String workingbilllast = StringUtils.substring(workingBillCode, workingBillCode.length()-2,workingBillCode.length());
		Orders orders = orderservice.get("aufnr",aufnr);
		Integer maxversion = bomDao.getMaxversion(orders.getId(),productDate);
		List<Bom>list=new ArrayList<Bom>();
		List<Bom>list2=new ArrayList<Bom>();
		list=bomDao.getBomList(aufnr, maxversion,workingbilllast);
		if(list.size()>0)
		{
			for(int i=0;i<list.size();i++)
			{
				Bom bom = list.get(i);
				if(bom.getMaterialCode().startsWith("5"))
				{
					return bom;
				}	
			}
		}
		else
		{
			list2=bomDao.getBomList(aufnr, maxversion);
			if(list2.size()>0)
			{
				for(int i=0;i<list2.size();i++)
				{
					Bom bom = list2.get(i);
					if(bom.getMaterialCode().startsWith("5"))
					{
						return bom;
					}	
				}
			}
		}
		return null;
	}


	@Override
	public Pager findPagerByOrders(Pager pager, HashMap<String, String> map,
			List<String> idList) {
		// TODO Auto-generated method stub
		return bomDao.findPagerByOrders(pager, map,idList);
	}
	
}