package cc.jiuyi.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import cc.jiuyi.dao.DictDao;
import cc.jiuyi.dao.EndProductDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.EndProduct;
import cc.jiuyi.entity.Pick;
import cc.jiuyi.entity.UnitConversion;
import cc.jiuyi.sap.rfc.EndProductRfc;
import cc.jiuyi.service.EndProductService;
import cc.jiuyi.service.UnitConversionService;

/**
 * Service实现类 -成品入库
 */

@Service
public class EndProductServiceImpl extends BaseServiceImpl<EndProduct, String> implements EndProductService {
	@Resource
	 EndProductDao  endProductDao;
	@Resource
	private UnitConversionService unitConversionService;
	@Resource
	public void setBaseDao(EndProductDao endProductDao) {
		super.setBaseDao(endProductDao);
	}

	@Override
	public void saveEndProduct(List<EndProduct> endProductList,String info,Admin admin) {
		if(endProductList!=null){
			for(EndProduct ed : endProductList){
				if(ed.getStockBoxMout()!=null && !"".equals(ed.getStockBoxMout())){
					ed.setCreateUser(admin.getUsername());
					ed.setCreateName(admin.getName());
					ed.setReceiveRepertorySite(info);
					ed.setState("1");
					UnitConversion ucs = unitConversionService.get("matnr", ed.getMaterialCode());
					if(ucs.getConversationRatio()==null || "".equals(ucs.getConversationRatio())){
							ucs.setConversationRatio(0.0);
						}
						BigDecimal dcl = new BigDecimal(ed.getStockBoxMout());
						BigDecimal dcu = new BigDecimal(ucs.getConversationRatio());
							BigDecimal dc = dcl.multiply(dcu);
							ed.setStockMout(dc.doubleValue());
					save(ed);
				}
			}
		}
		
	}

	@Override
	public void updateApprovalEndProduct(String[] ids, Admin admin) {
		
		
		//update(ed);
	}

	@Override
	public void updateEidtEndProduct(String id, Admin admin,EndProduct endProduct,String info) {
		EndProduct ep = get(id);
		BeanUtils.copyProperties(endProduct, ep, new String[] {"id","state"});
		ep.setCreateUser(admin.getUsername());
		ep.setCreateName(admin.getName());
		ep.setReceiveRepertorySite(info);
		UnitConversion ucs = unitConversionService.get("matnr", ep.getMaterialCode());
		if(ucs.getConversationRatio()==null || "".equals(ucs.getConversationRatio())){
				ucs.setConversationRatio(0.0);
			}
			BigDecimal dcl = new BigDecimal(ep.getStockBoxMout());
			BigDecimal dcu = new BigDecimal(ucs.getConversationRatio());
				BigDecimal dc = dcl.multiply(dcu);
				ep.setStockMout(dc.doubleValue());
		update(ep);
		
	}
	
}