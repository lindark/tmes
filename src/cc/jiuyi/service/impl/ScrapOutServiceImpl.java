package cc.jiuyi.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.ScrapOutDao;
import cc.jiuyi.entity.ScrapOut;
import cc.jiuyi.service.ScrapOutService;

/**
 * Service实现类 -报废产出对照表
 * @author Reece
 *
 */

@Service
public class ScrapOutServiceImpl extends BaseServiceImpl<ScrapOut, String>implements ScrapOutService{

	@Resource
	private ScrapOutDao scrapOutDao;
	
	@Resource
	public void setBaseDao(ScrapOutDao scrapOutDao){
		super.setBaseDao(scrapOutDao);
	}

	@Override
	public List<ScrapOut> getExistScrapOutList() {
		return scrapOutDao.getExistScrapOutList();
	}

	@Override
	public Pager getScrapOutPager(Pager pager, HashMap<String, String> map) {
		return scrapOutDao.getScrapOutPager(pager, map);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		scrapOutDao.updateisdel(ids, oper);		
	}

	@Override
	public void mergeScrapOutList(List<ScrapOut> outlList) {
		for (int i = 0; i < outlList.size(); i++) {
			ScrapOut scrapOut = outlList.get(i);
			ScrapOut scrapOut1 = this.get("materialCode",scrapOut.getMaterialCode());
			if(scrapOut1 == null){
				this.save(scrapOut);
			}else{
				scrapOut.setId(scrapOut1.getId());
				this.merge(scrapOut);
			}
		}
	}

	/**
	 * 根据物料编码查询产品编码
	 */
	public ScrapOut getByMaterialCode(String materialCode)
	{
		return this.scrapOutDao.getByMaterialCode(materialCode);
	}

	@Override
	public List<ScrapOut> getByMaterialCode1(String materialCode) {
		return this.scrapOutDao.getByMaterialCode1(materialCode);
	}
}
	
