package cc.jiuyi.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

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
		Integer maxversion = bomDao.getMaxVersion(productid);// 当前最大版本
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

}