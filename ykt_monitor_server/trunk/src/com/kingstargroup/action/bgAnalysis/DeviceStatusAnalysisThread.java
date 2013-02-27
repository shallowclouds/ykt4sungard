package com.kingstargroup.action.bgAnalysis;

/**
 * Copyright (C), 2000-2005, Kingstar Co., Ltd. 
 * File name: DeviceStatusAnalysisThread.java
 * Description: 设备状态监控
 * Modify History（或Change Log）:  
 * 操作类型（创建、修改等）   操作日期       操作者             操作内容简述
 * 创建  				 2005-10-31    何林青             设备状态监控
 * 修改					2006-07-20     何林青    			修改日志记录方式
 * <p>
 *
 * @author      何林青
 * @version     1.0
 * @since       1.0
 */
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.xml.utils.FastStringBuffer;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kingstargroup.SqlStatementDictionary;
import com.kingstargroup.commons.AnalyzeThreadInterface;
import com.kingstargroup.commons.HibernateSessionFactory;
import com.kingstargroup.form.Devicestatus;
import com.kingstargroup.form.MapAlertTable;
import com.kingstargroup.form.MapAnalysisPoint;
import com.kingstargroup.form.MapDeviceTable;
import com.kingstargroup.form.MapDeviceTableId;
import com.kingstargroup.form.MapParamTable;
import com.kingstargroup.form.MapStateDic;
import com.kingstargroup.form.TDevice;
import com.kingstargroup.form.TDevmonitor;
import com.kingstargroup.utils.CommonSendMailUtil;
import com.kingstargroup.utils.CommonSendSMGUtil;
import com.kingstargroup.utils.DateUtil;
import com.kingstargroup.utils.GetApplicationResources;
import com.kingstargroup.utils.StringUtil;

public class DeviceStatusAnalysisThread extends Thread implements
		AnalyzeThreadInterface {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(DeviceStatusAnalysisThread.class);

	// flag of stop this thread
	private static boolean requestStop = false;

	// record analysis time
	private String recordTime = "";

	// default analysis is to analysis all from
	private String startTime = "20050101000000";

	// default analysis is to analysis to now
	private String endTime = DateUtil.getNow();

	// default analysis cycle is one hour
	private double cycle = 10.0;

	// inteval param
	private int doubleCycle = 0;

	// alarm contact
	private String contactName = "";

	// record the record of analysis result
	private int mailRecord = 0;

	// whether to record
	private boolean recordOrNot = true;

	// whether to mail
	private boolean mailOrNot = false;

	// whether to SMG
	private boolean smgOrNot = false;

	// mail content' bufferString
	private FastStringBuffer buffer = null;

	// SMG content's Sting
	private String SmgMessage = "";

	// phone number list with delimeter ";"
	private String phoneNumber = "";

	// phone contact name list with delimeter ";"
	private String phoneName = "";

	// mail address list with delimeter ";"
	private String mailList = "";

	private int accelateParam = 0;

	private String preAnalysisTime = "";
	
	private HashMap errinfoHashmap = new HashMap();
	
	private HashMap preErrHashmap = new HashMap();
	
	private StringBuffer errcodeStr = new StringBuffer();

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		while (!requestStop) {
			buffer = new FastStringBuffer(100);
			mailRecord = 0;
			anaylize();
		}
	}

	public void initParam() {
		if (logger.isInfoEnabled()) {
			logger.info("**************设备状态分析线程初始化**************"); //$NON-NLS-1$
		}
		try {
			Session session = HibernateSessionFactory.currentSession();
			Query queryTime = session
					.createQuery("from MapAnalysisPoint point where point.analysisname=:analysisName");
			queryTime.setString("analysisName", "DEVICE_EXCEPTION");
			Iterator iterTime = queryTime.iterate();
			if (iterTime.hasNext()) {
				MapAnalysisPoint mapAnalysisPoint = (MapAnalysisPoint) iterTime
						.next();
				this.startTime = mapAnalysisPoint.getAnalysispoint();
			}

			if (!"20050101000000".equals(this.startTime)) {
				this.endTime = DateUtil.getNow();
				this.startTime = DateUtil.getNewTime(endTime, -1 * doubleCycle);
			}

		} catch (HibernateException e) {
			logger.error("设备状态分析线程初始化失败！", e); //$NON-NLS-1$
		} finally {
			HibernateSessionFactory.closeSession();
		}
		if (logger.isInfoEnabled()) {
			logger.info("**************设备状态分析线程初始化成功**************"); //$NON-NLS-1$
		}
	}

	/**
	 * @param session
	 */
	private void getParameter(Session session) {
		phoneNumber = "";
		phoneName = "";
		mailList = "";
		contactName = "";
		recordOrNot = true;
		mailOrNot = false;
		smgOrNot = false;
		// monitor param
		Query queryOrNot = session
				.createQuery("from MapAlertTable alert where alert.parameter='DeviceAbnormal'");
		Iterator iterOrNot = queryOrNot.iterate();
		if (iterOrNot.hasNext()) {
			MapAlertTable alertTable = (MapAlertTable) iterOrNot.next();
			if (0==alertTable.getNote()) {
				recordOrNot = false;
			}
			if (1==alertTable.getMailalert()) {
				mailOrNot = true;
			}
			if (1==alertTable.getPhonealert()) {
				smgOrNot = true;
			}
		}

		Query queryCycle = session
				.createQuery("from MapParamTable param where param.parameter=:paramRefer");
		queryCycle.setString("paramRefer", "HeartInterval");
		Iterator iterParam = queryCycle.iterate();
		if (iterParam.hasNext()) {
			MapParamTable mapParamTable = (MapParamTable) iterParam.next();
			this.cycle = mapParamTable.getValue();
		}
		String s = String.valueOf(cycle * 60);
		this.doubleCycle = Integer.parseInt(s.substring(0, s.indexOf('.')));
		this.doubleCycle = 60;
		
		errinfoHashmap.clear();
		preErrHashmap.clear();
		
		//把设备的错误信息和编码缓存
		errcodeStr.delete(0, errcodeStr.length());
		errcodeStr.append(
				SqlStatementDictionary
				.getIntance()
				.getSQL(
						"com.kingstargroup.action.bgAnalysis.DeviceStatusAnalysisThread.AbDeviceResult"));
		Query errCodeQuery = session.createQuery("from MapStateDic where errornot=1");
		List errCodeList = errCodeQuery.list();
		if(errCodeList==null||errCodeList.size()==0){
			errcodeStr.append(" and a.beatstatus='1' ");
		}else{
		Iterator errCodeIter = errCodeList.iterator();
		MapStateDic mapStateDic = (MapStateDic)errCodeIter.next();
		errcodeStr.append(" and (")
		.append(" a.beatstatus='")
		.append(mapStateDic.getStateid())
		.append("'");
		errinfoHashmap.put(mapStateDic.getStateid(), mapStateDic.getErrorcode());
		preErrHashmap.put(mapStateDic.getErrorcode(), "");
		
		
		while (errCodeIter.hasNext()) {
			mapStateDic = (MapStateDic)errCodeIter.next();
			errcodeStr.append(" or a.beatstatus='")
			.append(mapStateDic.getStateid())
			.append("'");
			
			errinfoHashmap.put(mapStateDic.getStateid(), mapStateDic.getErrorcode());
			preErrHashmap.put(mapStateDic.getErrorcode(), "");
		}
		
		
		errcodeStr.append(")");
		}

		// // contact list
		// Query queryContact = session
		// .createQuery("from MapContactMonitorTable monitor where
		// monitor.monitorno='DEVICE_EXCEPTION'");
		// Iterator iterContact = queryContact.iterate();
		// while (iterContact.hasNext()) {
		// MapContactMonitorTable contactMonitor = (MapContactMonitorTable)
		// iterContact
		// .next();
		// String nowDayTime = DateUtil.getNow("HHmmss");
		//
		// Query queryPhone = session
		// .createQuery("from MapContactTable a where
		// a.contactname=:contactName and a.starttime<=:starttime and
		// a.endtime>=:endtime");
		// queryPhone.setString("contactName", contactMonitor
		// .getContactname());
		// queryPhone.setString("starttime", nowDayTime);
		// queryPhone.setString("endtime", nowDayTime);
		// Iterator iterPhone = queryPhone.iterate();
		// if (iterPhone.hasNext()) {
		// MapContactTable contactTable = (MapContactTable) iterPhone
		// .next();
		// int dayofWeek = DateUtil.getDayofWeek();
		// if ((1 == dayofWeek)
		// && ("1".equals(contactTable.getWeekdays()
		// .substring(6, 7)))) {
		// phoneNumber += contactTable.getCellphone() + ";";
		// mailList += contactTable.getEmail() + ";";
		// contactName += contactMonitor.getContactname() + ",";
		// } else if ("1".equals(contactTable.getWeekdays().substring(
		// dayofWeek - 2, dayofWeek - 1))) {
		// phoneNumber += contactTable.getCellphone() + ";";
		// mailList += contactTable.getEmail() + ";";
		// contactName += contactMonitor.getContactname() + ",";
		// }
		// }
		// }
	}

	public void anaylize() {
		if (logger.isInfoEnabled()) {
			logger.info("设备状态分析线程开始分析任务"); //$NON-NLS-1$
		}
		Transaction tx = null;
		try{
		try {
			Session session = HibernateSessionFactory.currentSession();
			tx = session.beginTransaction();
			this.accelateParam = Integer
					.parseInt(BgMonitor.getIntance().accelateParam);
			endTime = DateUtil.getNewTime(endTime, accelateParam * 60);
			if (logger.isInfoEnabled()) {
				logger
						.info("设备状态分析线程开始分析时间:" + DateUtil.getNow("yyyy/MM/dd/HH:mm:ss")); //$NON-NLS-1$
			}
			if (1 > DateUtil.checkTimeSequence(endTime, DateUtil.getNow())) {
				if (logger.isInfoEnabled()) {
					logger
							.info("设备状态分析线程分析时间段：" + startTime + " -- "
									+ endTime);
				}
			} else {
				endTime = DateUtil.getNow();
				if (logger.isInfoEnabled()) {
					logger
							.info("设备状态分析线程分析时间段：" + startTime + " -- "
									+ endTime);
				}
			}
			getParameter(session);
			// record prev record time
			preAnalysisTime = recordTime;
			String devid_t = "";
			String deverr_idstr = "";

			recordTime = DateUtil.getNow();
			BgMonitor
					.monitorThreadStatusReport(
							"com.kingstargroup.action.bgAnalysis.DeviceStatusAnalysisThread",
							"设备状态分析线程", DateUtil.getNow("yyyy/MM/dd/HH:mm:ss"),
							String.valueOf(this.doubleCycle), "运行中...");
			// Query query = session.getNamedQuery("AbDeviceResult");
			

			
			Query query = session.createSQLQuery(errcodeStr.toString())
					.addEntity("dev", TDevice.class);
			List list = query.list();
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				TDevice device = (TDevice) iter.next();
				if (true == recordOrNot) {
					TDevmonitor devMonitor = (TDevmonitor) session.get(TDevmonitor.class,new Long(device.getDeviceid()));
					MapDeviceTable mapDeviceTable = new MapDeviceTable();
					MapDeviceTableId deviceTableKey = new MapDeviceTableId();
					deviceTableKey.setAnalysistime(recordTime);
					deviceTableKey.setDeviceid(device.getDeviceid());
	
					Query devquery = session.createSQLQuery(
							"select dev.devphyid devphy999Id from YKT_CUR.T_DEVICE dev "
									+ "where dev.deviceid="
									+ device.getFdeviceid()).addScalar(
							"devphy999Id", Hibernate.STRING);
					List devList = devquery.list();
					Iterator devIter = devList.iterator();
					String fdevphyId = "";
					if (devIter.hasNext()) {
						fdevphyId = devIter.next().toString();
					}
					Query queryContact = session
							.createQuery("select a.contactname from MapContactMonitorTable a "
									+ "where a.monitorno= :monitorNo");
					queryContact.setString("monitorNo", fdevphyId);
					List listContact = queryContact.list();
					contactName = "";
					for (int i = 0; i < listContact.size(); i++) {
						contactName += listContact.get(i).toString() + ",";
					}
					if (!"".equals(contactName)) {
						contactName = contactName.substring(0, contactName
								.lastIndexOf(","));
					}
					mapDeviceTable.setContactName(contactName);// to get
					// contact
					// according to
					// fdevphy_id					
					mapDeviceTable.setAreacode(device.getAreacode());
					mapDeviceTable.setCardverno(device.getCardverno());
					mapDeviceTable.setContactName(contactName);
					mapDeviceTable.setCpu(Double.parseDouble("0"));
					mapDeviceTable.setDevicename(device.getDevicename());
					mapDeviceTable.setDeviceno(device.getDeviceno());

					mapDeviceTable.setDevphyid(device.getDevphyid());
					mapDeviceTable.setDevtypecode(device.getDevtypecode());
					mapDeviceTable.setDevusage(device.getDevusage());
					mapDeviceTable.setErrorReason(Long.parseLong("11"));
					mapDeviceTable.setFdevphyid(fdevphyId);
					mapDeviceTable.setHarddisk(Double.parseDouble("0"));
					mapDeviceTable.setId(deviceTableKey);
					mapDeviceTable.setIp(device.getIp());
					mapDeviceTable.setMemory(Double.parseDouble("0"));
					mapDeviceTable.setRunstatus(device.getRunstatus());
					mapDeviceTable.setStatus(device.getStatus());
					mapDeviceTable.setSysid(device.getSysid());
					
					mapDeviceTable.setDevicetime(devMonitor.getDevtime());
					mapDeviceTable.setServertime(devMonitor.getOptime());
					

					String errCode = "101"+errinfoHashmap.get(String.valueOf(devMonitor.getBeatstatus()));
					mapDeviceTable.setErrorReason(Long.parseLong(errCode));
					//记录这次每种设备异常的设备物理id
					devid_t += device.getDevphyid()+",";
					preErrHashmap.put(errCode, (preErrHashmap.get(errCode)==null?"":preErrHashmap.get(errCode)) + device.getDevphyid()+",");

//					session.save(mapDeviceTable);
					
					
					deverr_idstr += device.getDeviceid()+",";
					Query querystatus = session.createQuery("from Devicestatus where deviceid=:deviceid and (devstatus=1 or devstatus=2 or devstatus=3)");
					querystatus.setLong("deviceid", device.getDeviceid());
					List liststatus = querystatus.list();
					if(liststatus==null||liststatus.size()==0){
						//新设备异常
						Devicestatus devicestatus  = new Devicestatus();
						devicestatus.setContactname(contactName);
						devicestatus.setDeviceid(device.getDeviceid());
						devicestatus.setDevicename(device.getDevicename());
						devicestatus.setDevphyid(device.getDevphyid());
						devicestatus.setDevstatus(1);
						devicestatus.setDevtypecode(device.getDevtypecode());
//						devicestatus.setDevtype("终端设备");
						devicestatus.setErrlasttime(0);
						devicestatus.setErrstarttime(recordTime);
						session.save(devicestatus);		
					}else{
						Iterator iterstatus = liststatus.iterator();					
						while (iterstatus.hasNext()) {
							Devicestatus devicestatus = (Devicestatus) iterstatus.next();
							String nowtime = DateUtil.getNow();
							long intev = DateUtil.getIntervalTime(nowtime, devicestatus.getErrstarttime())/1000;
							devicestatus.setErrlasttime(intev);
							devicestatus.setDevstatus(1);			
							session.save(devicestatus);						
						}
					}	
				}
				mailRecord++;
			}
			// analysis time record
			if (!"".equals(preAnalysisTime)) {
				Iterator setIter = preErrHashmap.keySet().iterator();				
				while(setIter.hasNext()){
					String errCode = setIter.next().toString();
					String sql = SqlStatementDictionary
					.getIntance()
					.getSQL(
							"com.kingstargroup.action.bgAnalysis.DeviceStatusAnalysisThread.PreDeviceResult")+" and a.ERROR_REASON=101"+errCode;
					Query queryPrev = session.createSQLQuery(sql)
					.addEntity("a", MapDeviceTable.class);
					queryPrev.setString("analysisTime", preAnalysisTime);
					List listpre = queryPrev.list();
					Iterator iterPre = listpre.iterator();
					while (iterPre.hasNext()) {
						MapDeviceTable device = (MapDeviceTable) iterPre.next();
						if (devid_t.indexOf(device.getDevphyid()) == -1) {
							// 原来的好了
							MapDeviceTable newdevice = new MapDeviceTable();
							BeanUtils.copyProperties(newdevice, device);
							newdevice.getId().setAnalysistime(recordTime);
							newdevice.setErrorReason(Long.parseLong("151"+errCode));
//							session.save(newdevice);
						}
					}
				}
			}
			//新任务之设备状态
			Query querychange = session.createQuery("from Devicestatus where deviceid not in("+deverr_idstr+"-1) and (devstatus=1 or devstatus=2 or devstatus=3)");
			List liststatus = querychange.list();
			Iterator iterstatus = liststatus.iterator();
			while(iterstatus.hasNext()){
				Devicestatus devicestatus = (Devicestatus) iterstatus.next();
				if(devicestatus.getDevstatus()==1){
					devicestatus.setErrendtime(recordTime);
					devicestatus.setDevstatus(2);			
					String nowtime = DateUtil.getNow();
					long intev = DateUtil.getIntervalTime(nowtime, devicestatus.getErrstarttime())/1000;
					devicestatus.setErrlasttime(intev);	
					session.save(devicestatus);	
				}else if(devicestatus.getDevstatus()==2){
					devicestatus.setDevstatus(0);			
					session.save(devicestatus);	
				}else{
					devicestatus.setDevstatus(0);			
					String nowtime = DateUtil.getNow();
					long intev = DateUtil.getIntervalTime(nowtime, devicestatus.getErrstarttime())/1000;
					devicestatus.setErrlasttime(intev);	
					session.save(devicestatus);	
				}
				
			}

			// record timepoint analysis
			Object tempAnalysis = session.get(MapAnalysisPoint.class,
					"DEVICE_EXCEPTION");
			MapAnalysisPoint analysisPoint = null;
			if (tempAnalysis == null) {
				analysisPoint = new MapAnalysisPoint();
				analysisPoint.setAnalysisname("DEVICE_EXCEPTION");
				analysisPoint.setAnalysispoint(endTime);
				session.save(analysisPoint);
			} else {
				analysisPoint = (MapAnalysisPoint) tempAnalysis;
				analysisPoint.setAnalysispoint(endTime);
				session.update(analysisPoint);
			}
			tx.commit();

		} catch (HibernateException e) {
			logger.error("设备状态分析线程出现以下异常：", e); //$NON-NLS-1$

			if (tx != null) {
				try {
					tx.rollback();
				} catch (Exception e1) {
					// TODO: handle exception
					e1.printStackTrace();
				}
			}
		} catch (Exception e) {
			logger.error("设备状态分析线程出现以下异常：", e); //$NON-NLS-1$
			if (tx != null) {
				try {
					tx.rollback();
				} catch (Exception e1) {
					// TODO: handle exception
					e1.printStackTrace();
				}
			}
		} finally {
			HibernateSessionFactory.closeSession();
		}
		
		}catch (Exception e) {
			e.printStackTrace();
		}

		// sleep thread follows the setting
		try {
			long sleepTime = Long.parseLong(String.valueOf(doubleCycle * 1000))
					- DateUtil.getIntervalTime(DateUtil.getNow(), recordTime);
			if (logger.isInfoEnabled()) {
				logger
						.info("设备状态分析线程结束进入休眠状态!"
								+ " 休眠开始时间:" + DateUtil.getNow("yyyy/MM/dd/HH:mm:ss") + ", 休眠时间:" + sleepTime + " 毫秒"); //$NON-NLS-1$
			}
			this.startTime = this.endTime;
			this.endTime = DateUtil.getNewTime(this.endTime, doubleCycle);
			if (sleepTime <= 0) {
				sleep(600000);
			} else {
				sleep(sleepTime);
			}
		} catch (InterruptedException e) {
			logger.error("设备状态分析线程出现以下异常：可能被强行停止！ ", e); //$NON-NLS-1$
		} catch (Exception e) {
			logger.error("设备状态分析线程出现以下异常：可能被强行停止！ ", e); //$NON-NLS-1$
		}
	}

	public void terminate() {
		if (this.isAlive()) {
			logger.warn("设备状态分析线程状态被请求，此时线程状态：活动线程!"); //$NON-NLS-1$
			requestStop = true;
		} else {
			logger.warn("设备状态分析线程状态被请求，此时线程状态：死亡线程!"); //$NON-NLS-1$
			this.destroy();

		}
	}

	public boolean isTerminate() {
		return this.isAlive();
	}

	public void sendMail() {
		// TODO Auto-generated method stub
		if ((true == mailOrNot) && (!"".equals(mailList))
				&& (0 != buffer.size())) {
			if (logger.isInfoEnabled()) {
				logger.info("设备状态分析线程发送邮件给联系人:" + contactName);
			}
			String tmpStr = "";
			if (buffer.size() > 5000) {
				tmpStr = buffer.getString(0, 5000);
			}
			tmpStr = buffer.toString();

			String[] tempList = null;
			tempList = mailList.split(";");
			for (int i = 0; i < tempList.length; i++) {
				if (!"".equals(tempList)) {
					CommonSendMailUtil.sendMail(GetApplicationResources
							.getInstance().getString(
									"com.kingstar.MonitorSys.SUBJECT_SERIAL"),
							contactName, tempList[i], StringUtil
									.wholeMailGenerate(contactName, DateUtil
											.reFormatTime(recordTime),
											mailRecord, tmpStr,
											"DEVICE_EXCEPTION"));
				}
			}
		}
	}

	public void sendSMG() {
		if ((true == smgOrNot) && (!"".equals(phoneNumber)) && mailRecord > 0) {
			if (logger.isInfoEnabled()) {
				logger.info("设备状态分析线程发送短信给联系人:" + contactName);
			}
			String[] tempList = null;
			String[] nameList = null;
			tempList = phoneNumber.split(";");
			nameList = phoneName.split(";");
			// String tempPhone = phoneNumber.substring(0, phoneNumber
			// .lastIndexOf(";"));
			for (int i = 0; i < tempList.length; i++) {
				if (!"".equals(tempList[i])) {
					SmgMessage = StringUtil.wholeSMGGenerate(nameList[i],
							DateUtil.reFormatTime(recordTime), mailRecord,
							"DEVICE_EXCEPTION");
					CommonSendSMGUtil.sendSMG(tempList[i], SmgMessage);
				}
			}
		}
	}

}
