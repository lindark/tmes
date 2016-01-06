package cc.jiuyi.sap.rfc;

import java.io.IOException;
import java.util.List;

import cc.jiuyi.entity.Dump;
import cc.jiuyi.entity.DumpDetail;
import cc.jiuyi.util.CustomerException;

public interface DumpRfc extends BaserfcService{
	/**
	 * 读取物料凭证
	 * @param lgort 库存地点
	 * @param bgdat 开始时间
	 * @param eddat 结束时间
	 * @return
	 * @throws IOException
	 * @throws CustomerException
	 */
	public List<Dump> findMaterialDocument(String lgort,String bgdat,String eddat) throws IOException,CustomerException;
	public List<DumpDetail> findMaterialDocumentByMblnr(String mblnr,String loginid) throws IOException,CustomerException;
}
