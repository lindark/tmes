package cc.jiuyi.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.LocatHandOverHeaderDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.FactoryUnit;
import cc.jiuyi.entity.LocatHandOver;
import cc.jiuyi.entity.LocatHandOverHeader;
import cc.jiuyi.service.LocatHandOverHeaderService;
import cc.jiuyi.service.LocatHandOverService;
/**
 * Service实现类 线边仓交接主表
 */
@Service
public class LocatHandOverHeaderServiceImpl extends BaseServiceImpl<LocatHandOverHeader, String>
		implements LocatHandOverHeaderService {
	
	@Resource
	LocatHandOverHeaderDao locatHandOverHeaderDao;
	@Resource
	public void setBaseDao( LocatHandOverHeaderDao  locatHandOverHeaderDao) {
		super.setBaseDao(locatHandOverHeaderDao);
	}
	@Resource
	LocatHandOverService locatHandOverService;
	@Override
	public Pager jqGrid(Pager pager,Admin admin) {
		return this.locatHandOverHeaderDao.jqGrid(pager,admin);
	}
	@Override
	public void saveLocatHandOver(Admin admin,List<LocatHandOver> locatHandOverList,Admin admin1 ) {
		FactoryUnit factoryUnit = admin1.getTeam()==null?null:admin1.getTeam().getFactoryUnit()==null?null:admin1.getTeam().getFactoryUnit();
		
		LocatHandOverHeader locatHandOverHeader = new LocatHandOverHeader();
		locatHandOverHeader.setSubmitUser(admin.getName());
		locatHandOverHeader.setSubmitUserCard(admin.getCardNumber());
		locatHandOverHeader.setShift(admin1.getShift());
		locatHandOverHeader.setProductDate(admin1.getProductDate());
		locatHandOverHeader.setFactoryUnitCode(factoryUnit.getFactoryUnitCode());
		locatHandOverHeader.setFactoryUnitDesp(factoryUnit.getFactoryUnitName());
		locatHandOverHeader.setIsDel("N");
		locatHandOverHeader.setState("1");
		locatHandOverHeader.setLgpla(locatHandOverList.get(0).getLgpla());
		locatHandOverHeader.setLocationCode(locatHandOverList.get(0).getLocationCode());
		this.locatHandOverHeaderDao.save(locatHandOverHeader);
		for(LocatHandOver lho : locatHandOverList){
			lho.setIsDel("N");
			lho.setLocatHandOverHeader(locatHandOverHeader);
			locatHandOverService.save(lho);
		}
	}
	@Override
	public void updateLocatHandOver(Admin admin, String[] ids) {
		List<LocatHandOverHeader> locatHandOverheaderList = locatHandOverHeaderDao.get(ids);
		for(LocatHandOverHeader lho : locatHandOverheaderList){
			lho.setState("2");
			lho.setConfirmUser(admin.getName());
			lho.setConfirmUserCard(admin.getCardNumber());
			locatHandOverHeaderDao.update(lho);
		}
	}
	
	@Override
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map) {
		return locatHandOverHeaderDao.historyjqGrid(pager, map);
	}
	
	@Override
	public List<Object[]> historyExcelExport(HashMap<String, String> map) {
		return locatHandOverHeaderDao.historyExcelExport(map);
	}
}
