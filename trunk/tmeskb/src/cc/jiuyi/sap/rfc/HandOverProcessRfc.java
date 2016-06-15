package cc.jiuyi.sap.rfc;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import cc.jiuyi.entity.HandOverProcess;
import cc.jiuyi.entity.ProcessHandover;
import cc.jiuyi.entity.ProcessHandoverSon;
import cc.jiuyi.util.CustomerException;

public interface HandOverProcessRfc extends BaserfcService {
	public String BatchHandOver(List<HandOverProcess> list,String testrun,String loginid) throws IOException,CustomerException;
	public ProcessHandover BatchProcessHandOver(ProcessHandover processHandover,String testrun,String loginid) throws IOException,CustomerException;
	public ProcessHandover RevokedProcessHandOver(ProcessHandover processHandover,String testrun,String loginid) throws IOException,CustomerException;
}
