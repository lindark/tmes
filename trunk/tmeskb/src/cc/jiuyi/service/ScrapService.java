package cc.jiuyi.service;

import java.util.HashMap;
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
	public String saveInfo(Scrap scrap, List<ScrapMessage> list_scrapmsg,
			List<ScrapBug> list_scrapbug, List<ScrapLater> list_scraplater,
			String my_id,String cardNumber);

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
	 * 取出所有未确认的报废单
	 */
	public List<Scrap> getUnCheckList();

	/**
	 * 1.与SAP交互没有问题,更新本地数据库
	 * 2.确认或撤销
	 */
	public void updateMyData(Scrap s,String newstate,String cardnumber,int my_id);

	/**
	 * 根据主表id获取产出后表数据
	 * @param scrapid
	 * @return
	 */
	public List<ScrapLater> getSlBySid(String scrapid);

	/**
	 * 如果没有SAP交互把状态先改为已确认及确认人
	 * @param scrapid
	 */
	public void updateState(String scrapid,String cardnumber,List<ScrapMessage>list_sm);

	
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map);
	
	public void updateNewUndo(Scrap s,String newstate,String cardnumber,int my_id);
}
