package cc.jiuyi.service;

import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Scrap;
import cc.jiuyi.entity.ScrapBug;
import cc.jiuyi.entity.ScrapLater;
import cc.jiuyi.entity.ScrapMessage;
/**
 * 报废
 * @author gaoyf
 *
 */
public interface ScrapService extends BaseService<Scrap, String>
{

	/**
	 * jqGrid查询
	 * @param pager
	 * @param wbId
	 * @return
	 */
	public Pager getScrapPager(Pager pager, String wbId);

	/**
	 * 新增
	 * @param scrap
	 * @param list_scrapmsg
	 * @param list_scrapbug
	 * @param list_scraplater
	 * @param my_id
	 */
	public void saveInfo(Scrap scrap, List<ScrapMessage> list_scrapmsg,
			List<ScrapBug> list_scrapbug, List<ScrapLater> list_scraplater,
			String my_id);

	/**
	 * 修改
	 * @param scrap
	 * @param list_scrapmsg
	 * @param list_scrapbug
	 * @param list_scraplater
	 * @param my_id
	 */
	public void updateInfo(Scrap scrap, List<ScrapMessage> list_scrapmsg,
			List<ScrapBug> list_scrapbug, List<ScrapLater> list_scraplater,
			String my_id);

	/**
	 * 确认或撤销
	 * @param list
	 * @param newstate
	 */
	public void updateState(List<Scrap> list, String newstate);

}
