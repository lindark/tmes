package cc.jiuyi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.dao.MaterialDao;
import cc.jiuyi.entity.Material;
import cc.jiuyi.entity.Products;
import cc.jiuyi.service.MaterialService;

/**
 * Service实现类 -物料基本数据
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
	public Pager getMaterialPager(Pager pager, HashMap<String, String> map) {
		return materialDao.getMaterialPager(pager, map);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
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
	
	@Override
	@Cacheable(modelId="caching")
	public Material get(String id) {
		return materialDao.get(id);
	}
	@Override
	@Cacheable(modelId="caching")
	public Material get(String propertyName, Object value) {
		return materialDao.get(propertyName, value);
	}
	
	@Override
	@CacheFlush(modelId="flushing")
	public void update(Material entity) {
		materialDao.update(entity);
	}
	
	@Override
	@CacheFlush(modelId="flushing")
	public Material merge(Material entity) {
		// TODO Auto-generated method stub
		return super.merge(entity);
	}
	
	
	public void mergeMaterialList(List<Material> materialList){
		for(int i = 0; i < materialList.size();i++){
			Material material = materialList.get(i);
			Material material1 = this.get("materialCode",material.getMaterialCode());
			if(material1 == null){
				this.save(material);
			}else{
				material.setId(material1.getId());
				this.merge(material);
			}
		}
		
	}
	
	/**
	 * 根据物料id查询是否存在
	 */
	public boolean getByCode(String code)
	{
		return this.materialDao.getByCode(code);
	}
}
