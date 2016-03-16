package cc.jiuyi.service.impl;


import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.DeptpickDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Deptpick;
import cc.jiuyi.service.DeptpickService;

/**
 * Service实现类 -部门领料
 */

@Service
public class DeptpickServiceImpl extends BaseServiceImpl<Deptpick, String> implements DeptpickService {
	
	
	@Resource
	private DeptpickDao  deptpickdao;
	@Resource
	public void setBaseDao(DeptpickDao deptpickdao) {
		super.setBaseDao(deptpickdao);
	}
	
	public void saveDeptpickList(List<Deptpick> deptpickList){
		
		for(Deptpick deptpick : deptpickList){
			if(deptpick.getId() !=null){
				Deptpick dp = this.get(deptpick.getId());
				dp.setStockMount(deptpick.getStockMount());//数量
				dp.setCreateUser(deptpick.getCreateUser());//创建人
				dp.setCostcenter(deptpick.getCostcenter());//成本中心
				dp.setState(deptpick.getState());//状态
				dp.setMovetype(deptpick.getMovetype());//移动类型
				dp.setProductDate(deptpick.getProductDate());//生产日期
				dp.setShift(deptpick.getShift());//班次
				dp.setDepartmentName(deptpick.getDepartmentName());
				dp.setDepartid(deptpick.getDepartid());
				this.update(dp);
			}else
				this.save(deptpick);
		}
		
	}

	public void updateDeptpickList(List<Deptpick> deptpickList,Admin admin) {
		
		for(Deptpick deptpick : deptpickList){
			deptpick.setComfirmUser(admin);
			deptpick.setState("undone");
			this.update(deptpick);
		}
	}

	@Override
	public Pager findByPager(Pager pager, Admin admin) {
		return deptpickdao.findByPager(pager, admin);
	}

	@Override
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return deptpickdao.historyjqGrid(pager, map);
	}

	@Override
	public List<Deptpick> historyExcelExport(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return deptpickdao.historyExcelExport(map);
	}
	
	
}
