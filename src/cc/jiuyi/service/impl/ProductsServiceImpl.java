package cc.jiuyi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.dao.ProductsDao;
import cc.jiuyi.entity.Material;
import cc.jiuyi.entity.Process;
import cc.jiuyi.entity.Products;
import cc.jiuyi.entity.Product;
import cc.jiuyi.service.ProductsService;

/**
 * Service实现类 -产品管理
 * @author Reece
 *
 */

@Service
public class ProductsServiceImpl extends BaseServiceImpl<Products, String>implements ProductsService{

	@Resource
	private ProductsDao productsDao;
	
	@Resource
	public void setBaseDao(ProductsDao productsDao){
		super.setBaseDao(productsDao);
	}
	
	@Override
	public void delete(String id) {
		Products products = productsDao.load(id);
		this.delete(products);
	}

	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

	@Override
	public List<Products> getProductsList() {		
		return productsDao.getProductsList();
	}

	@Override
	public Pager getProductsPager(Pager pager, HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return productsDao.getProductsPager(pager, map);
	}

	@Override
	public Pager getProductsPager2(Pager pager, HashMap<String, String> map,String id) {
		// TODO Auto-generated method stub
		return productsDao.getProductsPager2(pager, map,id);
	}
	@Override
	public void updateisdel(String[] ids, String oper) {
		// TODO Auto-generated method stub
		productsDao.updateisdel(ids, oper);
		
	}

	@Override
	public boolean isExistByProductsCode(String productsCode) {
		// TODO Auto-generated method stub
		return productsDao.isExistByProductsCode(productsCode);
	}

	@Override
	public boolean isExistByMaterialGroup(String materialGroup) {
		
		return productsDao.isExistByMaterialGroup(materialGroup);
	}

	@Override
	public List<Material> getAllMaterial() {
		return productsDao.getAllMaterial();
	}

	@Override
	public List<Process> getAllProcess() {
		return productsDao.getAllProcess();
	}

	@Override
	public Products getProducts(String matnr) {
		// TODO Auto-generated method stub
		return productsDao.getProducts(matnr);
	}

	
	
}
