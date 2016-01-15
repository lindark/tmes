package cc.jiuyi.service;

import java.util.List;

import cc.jiuyi.entity.Abnormal;

/**
 * Service接口 - 用于计算投入产出信息
 */
public interface WorkingInoutCalculateBase<T>{

	/**
	 * 计算 投入产出信息方法
	 * @param list
	 */
	public void updateWorkingInoutCalculate(List<T> paramaterList);
	
	
}
