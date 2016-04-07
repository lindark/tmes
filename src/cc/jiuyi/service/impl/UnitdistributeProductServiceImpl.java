package cc.jiuyi.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.FaultReasonDao;
import cc.jiuyi.dao.UnitdistributeProductDao;
import cc.jiuyi.entity.FaultReason;
import cc.jiuyi.entity.UnitdistributeProduct;
import cc.jiuyi.service.FaultReasonService;
import cc.jiuyi.service.UnitdistributeProductService;

/**
 * Service实现类 -单元分配产品
 *
 */

@Service
public class UnitdistributeProductServiceImpl extends BaseServiceImpl<UnitdistributeProduct, String>implements UnitdistributeProductService{

	@Resource
	private UnitdistributeProductDao unitdistributeProductDao;
	
	@Resource
	public void setBaseDao(UnitdistributeProductDao unitdistributeProductDao){
		super.setBaseDao(unitdistributeProductDao);
	}

	@Override
	public Pager getUnitProductPager(Pager pager, HashMap<String, String> map) {
		return unitdistributeProductDao.getUnitProductPager(pager,map);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		unitdistributeProductDao.updateisdel(ids, oper);
		
	}

	@Override
	public List<UnitdistributeProduct> getProductList(String unitCode) {
		// TODO Auto-generated method stub
		return unitdistributeProductDao.getProductList(unitCode);
	}

	/**
	 * 查询所有工位
	 */
	public List<UnitdistributeProduct> getAllList()
	{
		return this.unitdistributeProductDao.getAllList();
	}

	@Override
	public UnitdistributeProduct getUnitdistributeProduct(HashMap<String, String> map) {
		return this.unitdistributeProductDao.getUnitdistributeProduct(map);
	}

	/**
	 * 根据单元id和物料编码查询是否已存在
	 */
	public UnitdistributeProduct getByConditions(String fuid,String materialCode)
	{
		return this.unitdistributeProductDao.getByConditions(fuid,materialCode);
	}

}
