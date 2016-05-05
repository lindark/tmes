package cc.jiuyi.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.ProcessHandoverTop;

/**
 * Dao接口 - 工序交接
 */
@Repository
public interface ProcessHandoverTopDao extends BaseDao<ProcessHandoverTop,String> {
	public List<ProcessHandoverTop> getPHT(Admin admin);
	public List<ProcessHandoverTop> getReN(Admin admin);
}
