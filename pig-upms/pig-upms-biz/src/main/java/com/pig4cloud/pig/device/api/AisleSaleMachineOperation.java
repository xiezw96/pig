package com.pig4cloud.pig.device.api;

/**
 * @description: 柜门式的售货机器操作接口。 注：设备远程交互接口交互过程可能发一系列执行时异常，需要根据需要进行捕获处理。
 * @author:
 * @date:
 */
public interface AisleSaleMachineOperation {

	/**************************  业务及交易下发接口   ********************************************/
	/**
	 * 指定特定机器及货道出货
	 * @param orderId
	 * @param machineCode
	 * @param aisle
	 * @param num
	 * @return
	 */
	public void layTheGoods(String orderId,String machineCode,String aisle,int num) throws AisleMachineOperException;

	/**
	 * 根据订单号检查货品出货情况 ,注意：从出货到查询时间间隔限制为1分钟以内（每台机器可缓存订单状态有限，具体根据机器型号来定）
	 * @param orderId
	 * @param machineCode
	 * @return
	 */
	public boolean checkTheOrder(String orderId, String machineCode) throws AisleMachineOperException;

	/**
	 * 补货复位机器
	 * @param machineCode
	 * @return
	 */
	public void resetAfterReplenish(String machineCode) throws AisleMachineOperException;

	/**************************  管理类下发接口   ********************************************/
	/**
	 * 打开机器所有货道补取货门
	 * @param machineCode
	 * @return 打开失败货道标识列表
	 * @throws AisleMachineOperException 除了常规异常外，有些设备无法支持该指令，则会抛出异常
	 */
	public String[] openAllTheDoor(String machineCode) throws AisleMachineOperException;

	/**
	 * 打开特定货道的补取货门
	 * @param machineCode
	 * @param aisle
	 * @return
	 * @throws AisleMachineOperException 除了常规异常外，有些设备无法支持该指令，则会抛出异常
	 */
	public boolean openTheAsileDoor(String machineCode, String aisle) throws AisleMachineOperException;

	/**
	 * 查设备是否联网
	 * @param machineCode
	 * @param doRealTimeTest 是否执行实时硬件指令测试,true-是
	 * @return
	 * @throws AisleMachineOperException
	 */
	public boolean checkIfOnline(String machineCode,boolean doRealTimeTest) throws AisleMachineOperException;

	/**
	 * 获取机器特定货道状态
	 * @param machineCode
	 * @param aisle
	 * @param doRealTimeTest 是否执行实时硬件指令测试,true-是
	 * @return
	 * @throws AisleMachineOperException
	 */
	public int getAsileStatus(String machineCode,String aisle,boolean doRealTimeTest) throws AisleMachineOperException;

	/**
	 * 检查机器健康状况，包含：货道电机情况、信号信息
	 * @param machineCode
	 * @param doRealTimeTest 否执行实时硬件指令测试,true-是
	 * @return
	 * @throws AisleMachineOperException
	 */
	public SaleMachineHealth checkHealthOfMachine(String machineCode,boolean doRealTimeTest) throws AisleMachineOperException;


	/************************** 数据上报接口  ******************************************/
}
