package cc.jiuyi.service;

import java.util.List;

import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.ProcessHandoverTop;

/**
 * Service接口 - 工序交接
 */
public interface ProcessHandoverTopService extends BaseService<ProcessHandoverTop, String> {
	public List<ProcessHandoverTop> getPHT(Admin admin);
}
