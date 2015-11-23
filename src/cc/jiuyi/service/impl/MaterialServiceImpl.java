package cc.jiuyi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.dao.MaterialDao;
import cc.jiuyi.entity.Material;
import cc.jiuyi.entity.Products;
import cc.jiuyi.service.MaterialService;

/**
 * Service实现类 -产品Bom管理
 * @author Reece
 *
 */

@Service
public class MaterialServiceImpl extends BaseServiceImpl<Material, String>implements MaterialService{

	@Resource
	private MaterialDao materialDao;
	
	@Resource
	public void setBaseDao(MaterialDao materialDao){
		super.setBaseDao(materialDao);
	}
	

	@Override
	public List<Material> getMaterialList() {		
		return materialDao.getMaterialList();
	}

	@Override
	public Pager getMaterialPager(Pager pager, HashMap<String, String> map,String productsName) {
		// TODO Auto-generated method stub
		return materialDao.getMaterialPager(pager, map,productsName);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		// TODO Auto-generated method stub
		materialDao.updateisdel(ids, oper);
		
	}

	@Override
	public boolean isExistByMaterialCode(String materialCode) {
		// TODO Auto-generated method stub
		return materialDao.isExistByMaterialCode(materialCode);
	}

	@Override
	public List<Products> getProductsList() {
		// TODO Auto-generated method stub
		return materialDao.getProductsList();
	}
	
	@Override
	public List<Material> getMantrBom(String matnr) {
		// TODO Auto-generated method stub
		return materialDao.getMantrBom(matnr);
	}

	@Override
	public List<Material> getMantrBom(Object[] matnrs) {
		// TODO Auto-generated method stub
		return materialDao.getMantrBom(matnrs);
	}

	
}
