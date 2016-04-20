package cc.jiuyi.service.impl;



import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.UpDownDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.UpDown;
import cc.jiuyi.sap.rfc.DumpRfc;
import cc.jiuyi.sap.rfc.LocationonsideRfc;
import cc.jiuyi.service.UpDownServcie;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * Service实现类 -上架/下架
 * @author weitao
 *
 */

@Service
public class UpDownServiceImpl extends BaseServiceImpl<UpDown, String>implements UpDownServcie{
	
	@Resource
	private DumpRfc dumprfc;
	@Resource
	private UpDownDao updowndao;
	
	@Resource
	public void setBaseDao(UpDownDao updowndao) {
		super.setBaseDao(updowndao);
	}
	
	
	
	public List<HashMap<String,String>> upmaterList(String werks,String lgort,String matnr,String lgpla,String maktx) throws IOException, CustomerException{//上/下架物料清单
		
		List<HashMap<String,String>> hashList = dumprfc.findMaterial(werks, lgort, matnr, lgpla,maktx);
		return hashList;
	}
	
	public void save(List<UpDown> updownList){
		for(UpDown updown : updownList){
			updowndao.save(updown);
		}
	}
	
	public Pager findByPager(Pager pager,Admin admin,List<String> list){
		return updowndao.findByPager(pager, admin,list);
	}


	public Pager searchByPager(Pager pager,Admin admin,List<String> list,HashMap<String, String> map){
		return updowndao.searchByPager(pager, admin,list,map);
	}
	

	@Override
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map) {
		return updowndao.historyjqGrid(pager, map);
	}



	@Override
	public List<UpDown> historyExcelExport(HashMap<String, String> map) {
		return updowndao.historyExcelExport(map);
	}



	@Override
	public List<Object[]> findUpdowngroupby(UpDown updown) {
		return updowndao.findUpdowngroupby(updown);
	}
	
}
