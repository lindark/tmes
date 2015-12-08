package cc.jiuyi.sap.rfc;

import java.io.IOException;
import java.util.List;

import cc.jiuyi.entity.DailyWork;
import cc.jiuyi.util.CustomerException;

public interface DailyWorkRfc extends BaserfcService{
	/**
	 * 单个报工
	 * @param orderid
	 * @param step
	 * @param menge
	 * @throws IOException
	 * @throws CustomerException
	 */
	public void SetDailyWork(String orderid,String step,String menge)throws IOException, CustomerException;
	/**
	 * 批量报工
	 * @param dailywork
	 * @return
	 * @throws IOException
	 * @throws CustomerException
	 */
	public List<DailyWork> BatchSetDailyWork(List<DailyWork> dailywork) throws IOException, CustomerException;
	public List<DailyWork> BatchSetDailyWorkCancel(List<DailyWork> dailywork) throws IOException, CustomerException;
}
