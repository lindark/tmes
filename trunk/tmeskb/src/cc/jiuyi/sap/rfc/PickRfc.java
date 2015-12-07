package cc.jiuyi.sap.rfc;

import java.io.IOException;
import java.util.List;

import cc.jiuyi.entity.Pick;
import cc.jiuyi.entity.PickDetail;
import cc.jiuyi.util.CustomerException;

public interface PickRfc extends BaserfcService{
	public String MaterialDocumentCrt(Pick pick,List<PickDetail> pickdetail) throws IOException,CustomerException;
	public List<Pick> BatchMaterialDocumentCrt(List<Pick> pick,List<PickDetail> pickdetail) throws IOException,CustomerException;
}
