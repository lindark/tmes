package cc.jiuyi.sap.rfc;

import java.io.IOException;
import java.util.List;

import cc.jiuyi.entity.Pick;
import cc.jiuyi.entity.PickDetail;
import cc.jiuyi.util.CustomerException;

public interface PickRfc extends BaserfcService{
	public String MaterialDocumentCrt(String testrun,Pick pick,List<PickDetail> pickdetail) throws IOException,CustomerException;
	public List<Pick> BatchMaterialDocumentCrt(String testrun,List<Pick> pick,List<PickDetail> pickdetail) throws IOException,CustomerException;
}
