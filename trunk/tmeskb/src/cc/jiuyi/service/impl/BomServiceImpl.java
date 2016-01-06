package cc.jiuyi.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.BomDao;
import cc.jiuyi.entity.Bom;
import cc.jiuyi.service.BomService;

/**
 * Service实现类 - Bom
 */

@Service
public class BomServiceImpl extends BaseServiceImpl<Bom, String> implements BomService {

	@Resource
	private BomDao bomDao;

	@Resource
	public void setBaseDao(BomDao bomDao) {
		super.setBaseDao(bomDao);
	}

	@Override
	public void mergeBom(List<Bom> bomList, String productid) {
		Integer maxversion = bomDao.getMaxVersionByid(productid);// 当前最大版本
		if (maxversion == null)
			maxversion = 0;
		maxversion++; // 最大版本+1
		for (int i = 0; i < bomList.size(); i++) {
			Bom bom = (Bom) bomList.get(i);
			if (bom == null)
				continue;
			bom.setVersion(maxversion);
			bomDao.save(bom);
		}
	}

	@Override
	public List<Bom> getBomByProductCode(String productCode,String materialCode,Integer version) {
		
		return bomDao.getBomByProductCode(productCode,materialCode,version);
	}

	@Override
	public Integer getMaxVersionBycode(String productCode) {
		return bomDao.getMaxVersionBycode(productCode);
	}

	@Override
	public Integer getMaxVersionByid(String productid) {
		return bomDao.getMaxVersionByid(productid);
	}

	@Override
	public List<Bom> getListByid(String productid, Integer version) {
		return bomDao.getListByid(productid, version);
	}
	
	@Override
	public List<Bom> getListBycode(String productcode, Integer version) {
		return bomDao.getListBycode(productcode, version);
	}
	
	@Override
	public List<Bom> getBomListByMaxVersion(Integer version) {
		return bomDao.getBomListByMaxVersion(version);
	}

	@Override
	public Pager findPagerByjqGrid(Pager pager, HashMap<String, String> map) {
		return bomDao.findPagerByjqGrid(pager, map);
	}

	/**
	 * 根据产品id和随工单中的bom版本号查询bom表
	 */
	public List<Bom>list_bom(String pid,String version)
	{
		return this.bomDao.getByPidAndWversion(pid,version);
	}
}