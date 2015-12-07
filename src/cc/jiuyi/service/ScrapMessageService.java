package cc.jiuyi.service;

import cc.jiuyi.entity.ScrapMessage;

/**
 * 报废信息
 * @author gaoyf
 *
 */
public interface ScrapMessageService extends BaseService<ScrapMessage, String>
{

	/**
	 * 根据scrap表id和物料表id查询
	 * @param id
	 * @param id2
	 * @return
	 */
	ScrapMessage getBySidAndMid(String sid, String mid);
}
