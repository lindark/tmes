package cc.jiuyi.dao;

import cc.jiuyi.entity.RepairinPiece;

/**
 * 返修收货--组件
 * @author lenovo
 *
 */
public interface RepairinPieceDao extends BaseDao<RepairinPiece, String>
{
	public Object[] sumAmount(String wdid,String wicode);
}
