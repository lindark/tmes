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
import cc.jiuyi.service.ReturnProductService;

/**
 * Service实现类 -成品入库
 */

@Service
public class ReturnProductServiceImpl extends BaseServiceImpl<ReturnProduct, String> implements ReturnProductService {

	@Resource
	ReturnProductDao  returnProductDao;
	
	@Resource
	public void setBaseDao(ReturnProductDao returnProductDao) {
		super.setBaseDao(returnProductDao);
	}
	
	@Override
	public void saveReturnProduct(List<ReturnProduct> returnProductList, String info, Admin admin) {
		if(returnProductList!=null){
			for(ReturnProduct rp : returnProductList){
				if(rp.getStockMout()!=null && !"".equals(rp.getStockMout())){
					rp.setCreateUser(admin.getUsername());
					rp.setCreateName(admin.getName());
					rp.setReceiveRepertorySite(info);
					rp.setState("1");
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
		ReturnProduct rp = get(id);
		BeanUtils.copyProperties(returnProduct, rp, new String[] {"id","state"});
		rp.setCreateUser(admin.getUsername());
		rp.setCreateName(admin.getName());
		rp.setReceiveRepertorySite(info);
		update(rp);
		
	}

	@Override
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map) {
		return returnProductDao.historyjqGrid(pager, map);
	}

	@Override
	public List<ReturnProduct> historyExcelExport(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return returnProductDao.historyExcelExport(map);
	}



}
