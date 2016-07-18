package cc.jiuyi.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.MaterialDao;
import cc.jiuyi.entity.FactoryUnit;
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
		return materialDao.isExistByMaterialCode(materialCode);
	}

	@Override
	public List<Products> getProductsList() {
		return materialDao.getProductsList();
	}
	
	@Override
	public List<Material> getMantrBom(String matnr) {
		return materialDao.getMantrBom(matnr);
	}

	@Override
	public List<Material> getMantrBom(Object[] matnrs) {
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
		return super.merge(entity);
	}
	
	
	public void mergeMaterialList(List<Material> materialList){
		for(int i = 0; i < materialList.size();i++){
			Material material = materialList.get(i);
//			Material material1 = this.get("materialCode",material.getMaterialCode());
			Material material1 = this.getByNum(material.getMaterialCode(), material.getFactoryunit());
			if(material1 == null){
				this.save(material);
			}else{
				material.setId(material1.getId());
				this.merge(material);
			}
		}
		
	}
	
	/**
	 * 根据物料号查询是否存在
	 */
	public boolean getByCode(String code,FactoryUnit factoryunit)
	{
		return this.materialDao.getByCode(code,factoryunit);
	}

	/**
	 * 根据物料id查询是否存在
	 */
	public boolean getByCode(String code)
	{
		return this.materialDao.getByCode(code);
	}


	/**
	 * 根据物料编码查询
	 */
	public Material getByNum(String materialCode)
	{
		return this.materialDao.getByNum(materialCode);
	}

	/**
	 * 根据物料编码查询
	 */
	public Material getByNum(String materialCode,FactoryUnit factoryunit)
	{
		return this.materialDao.getByNum(materialCode,factoryunit);
	}


	@Override
	public List<Material> getMaterialList(String materialCode,
			FactoryUnit factoryunit) {
		return this.materialDao.getMaterialList(materialCode,factoryunit);
	}
}
