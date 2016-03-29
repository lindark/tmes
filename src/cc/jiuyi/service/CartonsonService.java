package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.CartonSon;

/**
 * 纸箱收货子表
 * 
 * @author lenovo
 * 
 */
public interface CartonsonService extends BaseService<CartonSon, String> {

	public Pager historyjqGrid(Pager pager, HashMap<String, String> map);

	public List<Object[]> historyExcelExport(HashMap<String, String> map);

}
