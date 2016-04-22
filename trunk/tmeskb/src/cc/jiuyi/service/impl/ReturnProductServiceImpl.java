package cc.jiuyi.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.ReturnProductDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.EndProduct;
import cc.jiuyi.entity.ReturnProduct;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.ReturnProductService;

/**
 * Service实现类 -成品入库
 */

@Service
public class ReturnProductServiceImpl extends BaseServiceImpl<ReturnProduct, String> implements ReturnProductService {

	@Resource
	ReturnProductDao  returnProductDao;
	@Resource
	AdminService  adminService;
	
	@Resource
	public void setBaseDao(ReturnProductDao returnProductDao) {
		super.setBaseDao(returnProductDao);
	}
	
	@Override
	public void saveReturnProduct(List<ReturnProduct> returnProductList, String info, Admin admin) {
		if(returnProductList!=null){
			
			String produtDate = null;
			String shift = null;
			String factoryCode = null;
			String factoryDesp = null;
				produtDate = admin.getProductDate();
				shift = admin.getShift();
				if(admin.getTeam()!=null && admin.getTeam().getFactoryUnit()!=null){
					factoryCode = admin.getTeam().getFactoryUnit().getFactoryUnitCode();
					factoryDesp = admin.getTeam().getFactoryUnit().getFactoryUnitName();
				}
			
			for(ReturnProduct rp : returnProductList){
				if(rp.getStockMout()!=null && !"".equals(rp.getStockMout())){
					rp.setCreateUser(admin.getUsername());
					rp.setCreateName(admin.getName());
					rp.setReceiveRepertorySite(info);
					rp.setState("1");
					rp.setProductDate(produtDate);
					rp.setShift(shift);
					rp.setFactoryCode(factoryCode);
					rp.setFactoryDesp(factoryDesp);
					save(rp);
				}
			}
		}
		
	}

	@Override
	public void updateApprovalReturnProduct(String[] ids, Admin admin) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateEidtReturnProduct(String id, Admin admin,
			ReturnProduct returnProduct, String info) {
		
		String produtDate = null;
		String shift = null;
		String factoryCode = null;
		String factoryDesp = null;
			produtDate = admin.getProductDate();
			shift = admin.getShift();
			if(admin.getTeam()!=null && admin.getTeam().getFactoryUnit()!=null){
				factoryCode = admin.getTeam().getFactoryUnit().getFactoryUnitCode();
				factoryDesp = admin.getTeam().getFactoryUnit().getFactoryUnitName();
			}
		
		ReturnProduct rp = get(id);
		BeanUtils.copyProperties(returnProduct, rp, new String[] {"id","state"});
		rp.setCreateUser(admin.getUsername());
		rp.setCreateName(admin.getName());
		rp.setReceiveRepertorySite(info);
		rp.setProductDate(produtDate);
		rp.setShift(shift);
		rp.setFactoryCode(factoryCode);
		rp.setFactoryDesp(factoryDesp);
		update(rp);
		
	}

	@Override
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map) {
		return returnProductDao.historyjqGrid(pager, map);
	}
	@Override
	public Pager jqGrid(Pager pager,Admin admin) {
		return returnProductDao.jqGrid(pager,admin);
	}
	@Override
	public List<ReturnProduct> historyExcelExport(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return returnProductDao.historyExcelExport(map);
	}



}
