package cc.jiuyi.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.ScrapLater;
import cc.jiuyi.entity.UpDown;
import cc.jiuyi.util.CustomerException;

/**
 * 上架/下架
 * @author weitao
 *
 */
public interface UpDownServcie extends BaseService<UpDown, String>{

	
	public List<HashMap<String,String>> upmaterList(String werks,String lgort,String matnr,String lgpla,String maktx) throws IOException, CustomerException;
	
	public void save(List<UpDown> updownList);
	
	public Pager findByPager(Pager pager,Admin admin,List<String> list);
	
	public Pager searchByPager(Pager pager,Admin admin,List<String> list,HashMap<String, String> map);
		
	
	public Pager historyjqGrid(Pager pager, HashMap<String,String> map);
	
    public List<UpDown> historyExcelExport(HashMap<String,String> map);
    
    /**
     * 需要提供3个参数,查询汇总值
     * @param updown  1. updown.type  2.updown.productdate 3. updown.shift
     * @return
     */
    public List<Object[]> findUpdowngroupby(UpDown updown);

}
