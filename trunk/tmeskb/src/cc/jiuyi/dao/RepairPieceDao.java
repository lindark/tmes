package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.RepairPiece;

/**
 * 返修--组件
 * @author lenovo
 *
 */
public interface RepairPieceDao extends BaseDao<RepairPiece, String>
{

    public Pager historyjqGrid(Pager pager, HashMap<String,String> map);
	
	public List<Object[]> historyExcelExport(HashMap<String,String> map);
	
	public Object sumAmount(String wdid,String wicode);
}
