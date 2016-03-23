package cc.jiuyi.sap.rfc;

import java.io.IOException;
import java.util.HashMap;
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
	
	/**
	 * 根据物料读取批次
	 * @param werks 工厂
	 * @param lgort 库存地点 
	 * @param matnr 物料编码
	 * @param lgpla 配送地点库位
	 * @return HashMap 的 List 集合
	 * @throws IOException  IO异常信息,请捕获并抛到页面
	 */
	public List<HashMap<String,String>> findMaterial(String werks,String lgort,String matnr,String lgpla,String maktx) throws IOException,CustomerException ;
	
	/**
	 * 库位下架
	 * @param arrList
	 * @return 返回 S 表示成功，其他均为失败
	 * @throws IOException
	 * @throws CustomerException
	 */
	public String saveMaterial(List<HashMap<String,String>> arrList) throws IOException, CustomerException;
	
	/**
	 * 更新物料数据
	 * @param testrun
	 * @param maplist
	 * @return
	 */
	public List<HashMap<String,String>> updateMaterial(String testrun,List<HashMap<String,String>> maplist)throws IOException, CustomerException;
}
