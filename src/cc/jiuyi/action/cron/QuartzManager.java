package cc.jiuyi.action.cron;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.scheduling.quartz.CronTriggerBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.stereotype.Component;

import cc.jiuyi.entity.FactoryUnit;
import cc.jiuyi.entity.FactoryUnitSyn;
import cc.jiuyi.service.FactoryUnitService;
import cc.jiuyi.service.FactoryUnitSynService;
import cc.jiuyi.util.SpringUtil;

@Component
public class QuartzManager implements BeanFactoryAware {
	private Logger log = Logger.getLogger(QuartzManager.class);
	private Scheduler scheduler;
	private static BeanFactory beanFactory = null;
	private FactoryUnitService factoryUnitService;
	@SuppressWarnings("unused")
	public void reScheduleJob() throws Exception, ParseException {
		// ͨ����ѯ���ݿ���ƻ����������üƻ�����
		System.out.println("reScheduleJob---->"+new Date());
		
		factoryUnitService = (FactoryUnitService)SpringUtil.getBean("factoryUnitServiceImpl");
		List<FactoryUnit> factoryUnitList = factoryUnitService.getAll();
	
		List<FactoryUnit> quartzList = new ArrayList<FactoryUnit>();//�������ֶ�������һ��
		for(FactoryUnit f : factoryUnitList){
			configQuatrz(f);
		}
	}

	public  boolean configQuatrz(FactoryUnit tbcq) {
		boolean result = false;
		CronTriggerBean trigger = null;
		try {
				// ����ʱ��ͨ����̬ע���scheduler�õ�trigger
				trigger = (CronTriggerBean) scheduler.getTrigger(tbcq.getTriggername(), Scheduler.DEFAULT_GROUP);
			// ����ƻ������Ѵ���������޸ķ���
			if (trigger != null) {
				change(tbcq, trigger);
			} else {
				// ����ƻ����񲻴��ڲ������ݿ��������״̬Ϊ����ʱ,�򴴽��ƻ�����
				if (tbcq.getState().equals("1")) {
					this.createCronTriggerBean(tbcq);
				}
			}
			result = true;
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
		}

		return result;
	}

	public  void change(FactoryUnit tbcq, CronTriggerBean trigger)
			throws Exception {
		System.out.println("----------"+tbcq.getMethodArguments()[0]+"-----------");
		// �������Ϊ����
		if (tbcq.getIsSync().equals("1")) {
			// �жϴ�DB��ȡ�õ�����ʱ������ڵ�quartz�߳��е�����ʱ���Ƿ����
			// �����ȣ����ʾ�û���û�������趨���ݿ��е�����ʱ�䣬�����������Ҫ����rescheduleJob
			if (!trigger.getCronExpression().equalsIgnoreCase(
					tbcq.getCronexpression())) {
				trigger.setCronExpression(tbcq.getCronexpression());
				scheduler.rescheduleJob(tbcq.getTriggername(),
						Scheduler.DEFAULT_GROUP, trigger);
				log.info(new Date() + ": ����" + tbcq.getTriggername() + "�ƻ�����");
				System.out.println(new Date() + ": ����" + tbcq.getTriggername() + "�ƻ�����");
			}
		} else {
			// ������
			scheduler.pauseTrigger(trigger.getName(), trigger.getGroup());// ֹͣ������
			scheduler.unscheduleJob(trigger.getName(), trigger.getGroup());// �Ƴ�������
			scheduler.deleteJob(trigger.getJobName(), trigger.getJobGroup());// ɾ������
			log.info(new Date() + ": ɾ��" + tbcq.getTriggername() + "�ƻ�����");
			
			System.out.println(new Date() + ": ɾ��" + tbcq.getTriggername() + "�ƻ�����");
		}

	}

	/**
	 * ����/��Ӽƻ�����
	 * 
	 * @param tbcq
	 *            �ƻ��������ö���
	 * @throws Exception
	 */
	public void createCronTriggerBean(FactoryUnit tbcq) throws Exception {
		// �½�һ������Spring�Ĺ���Job��
		MethodInvokingJobDetailFactoryBean mjdfb = new MethodInvokingJobDetailFactoryBean();
		
		mjdfb.setName(tbcq.getJobdetailname());// ����Job����
		// ��������������ΪSpring�Ķ����Bean����� getBean����
		if (tbcq.getIsspringbean().equals("1")) {
			mjdfb.setTargetObject(beanFactory.getBean(tbcq.getTargetobject()));// ����������
		} else {
			// ����ֱ��new����
			mjdfb.setTargetObject(Class.forName(tbcq.getTargetobject())
					.newInstance());// ����������
		}
		mjdfb.setTargetMethod(tbcq.getMethodname());// �������񷽷�
		mjdfb.setArguments(tbcq.getMethodArguments());//���÷�������
		mjdfb.setConcurrent(tbcq.getConcurrent().equals("0") ? false : true); // �����Ƿ񲢷���������
		mjdfb.afterPropertiesSet();// ������Job���ύ���ƻ�������
		// ��Spring�Ĺ���Job��תΪQuartz����Job��
		JobDetail jobDetail = new JobDetail();
		jobDetail = (JobDetail) mjdfb.getObject();
		jobDetail.setName(tbcq.getJobdetailname());
		scheduler.addJob(jobDetail, true); // ��Job��ӵ�������
		// ��һ������Spring��ʱ����
		CronTriggerBean c = new CronTriggerBean();
		
		c.setCronExpression(tbcq.getCronexpression());// ����ʱ����ʽ
		c.setName(tbcq.getTriggername());// ��������
		c.setJobDetail(jobDetail);// ע��Job
		c.setJobName(tbcq.getJobdetailname());// ����Job����
		scheduler.scheduleJob(c);// ע�뵽������
		scheduler.rescheduleJob(tbcq.getTriggername(), Scheduler.DEFAULT_GROUP,
				c);// ˢ�¹�����
		log.info(new Date() + ": �½�" + tbcq.getTriggername() + "�ƻ�����");
		System.out.println(new Date() + ": �½�" + tbcq.getTriggername() + "�ƻ�����");
	}

	public void resh(Scheduler scheduler) throws ParseException, Exception{
			this.scheduler= scheduler;
		reScheduleJob();
	}
	
	public Scheduler getScheduler() {
		return scheduler;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	/*
	 * public ApplicationContext getApc() { return apc; }
	 * 
	 * public void setApc(ApplicationContext apc) { this.apc = apc; }
	 */
	public void setBeanFactory(BeanFactory factory) throws BeansException {
		this.beanFactory = factory;

	}

	public BeanFactory getBeanFactory() {
		return beanFactory;
	}
   
}
