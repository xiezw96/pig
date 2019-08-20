package com.pig4cloud.pig.device.core.master.protocol.szyn;

import cn.hutool.core.util.StrUtil;
import com.pig4cloud.pig.device.api.SaleMachineHealth;
import com.pig4cloud.pig.device.core.master.MachineProtocolParser;
import com.pig4cloud.pig.device.core.master.MasterMsgCached;
import com.pig4cloud.pig.device.core.msg.MachineMsg;
import com.pig4cloud.pig.device.core.msg.MqttMsgContext;
import com.pig4cloud.pig.device.core.msg.MsgConstants;
import com.pig4cloud.pig.device.core.msg.MsgContext;
import com.pig4cloud.pig.device.core.msg.aisle.cmd.down.AisleComandMsg;
import com.pig4cloud.pig.device.core.msg.aisle.cmd.down.CmdLayGoodsMsg;
import com.pig4cloud.pig.device.core.msg.aisle.cmd.down.CmdQASState;
import com.pig4cloud.pig.device.core.msg.aisle.cmd.down.OpenAisleMsg;
import com.pig4cloud.pig.device.core.msg.aisle.cmd.part.AsPart;
import com.pig4cloud.pig.device.core.msg.aisle.cmd.part.SimPart;
import com.pig4cloud.pig.device.core.msg.aisle.cmd.up.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @title: SZYNAisleMachineProtocolV1
 * @projectName eden
 * @description: 深圳宇楠格子柜门式MQTT协议解析
 */
@Component("szynMqttprotocol")
@Log
public class SZYNAisleMachineProtocolV1 implements MachineProtocolParser {

	//YN命令编码-出货
	static final int CMD_LAY_GOODS = 22 ;
	//YN命令编码-查询电机状态
	static final int CMD_AS_STATES = 24 ;
	//YN命令编码-补货复位
	static final int CMD_ME_RESETS = 28 ;
	//YN命令编码-查询订单状态
	static final int CMD_OD_STATE  = 30 ;
	//YN命令编码-查询网络状态
	static final int CMD_NET_STATE = 100 ;

	//YN命令编码-应答出货
	static final int CMD_R_LAY_GOODS = 23 ;
	//YN命令编码-应答查询电机状态
	static final int CMD_R_AS_STATES = 25 ;
	//YN命令编码-应答补货复位
	static final int CMD_R_ME_RESETS = 29 ;
	//YN命令编码-应答查询订单状态
	static final int CMD_R_OD_STATE  = 31 ;
	//YN命令编码-应答查询网络状态
	static final int CMD_R_NET_STATE = 101 ;

	@Autowired
	MasterMsgCached masterMsgCached;

	@Override
	public boolean isMatched(MsgContext ctx) {
		//TODO 精确拦截
		if(MsgConstants.PROTOCAL_TYPE_MQTT.equals(ctx.getIotProto()))
			return true;
		return false;
	}

	public byte[] parser(MachineMsg req) {
		if(req == null) return  null;

		SZYNMqttProtoBean yn = null;
		StringBuilder sb = new StringBuilder();

		switch (req.getOperCode()) {
			//1-出货指令
			case AisleComandMsg.TYPE_CMD_LAYGOODS:
				CmdLayGoodsMsg c1 = (CmdLayGoodsMsg)req;
				if(StrUtil.isEmpty(c1.getAsId()) || c1.getNum() < 1) {
					log.warning("+++消息：["+req+"],处理失败！");
					break;
				}
				yn = toYNBean(c1,CMD_LAY_GOODS);
				sb.append(c1.getAsId()).append("&").append(c1.getNum());
				break;
			//2-查询货道电机
			case AisleComandMsg.TYPE_CMD_QASTATE:
				CmdQASState c2 = (CmdQASState)req;
				if(StrUtil.isEmpty(c2.getAsId())) {
					log.warning("+++消息：["+req+"],处理失败！");
					break;
				}
				yn = toYNBean(c2,CMD_AS_STATES);
				//是否作用全货道
				if(CmdQASState.ASID_OF_ALL.equals(c2.getAsId())){
					sb.append("0");
				}else{
					sb.append(c2.getAsId());
				}
				break;
			//3-查询订单状态
			case AisleComandMsg.TYPE_CMD_ODSTATE:
				AisleComandMsg c3 = (AisleComandMsg)req;
				yn = toYNBean(c3,CMD_OD_STATE);
				sb.append("0");
				break;
			//4-机器复位
			case AisleComandMsg.TYPE_CMD_MERESET:
				AisleComandMsg c4 = (AisleComandMsg)req;
				yn = toYNBean(c4,CMD_ME_RESETS);
				sb.append("0");
				break;
			//5-查询网络状态
			case AisleComandMsg.TYPE_CMD_NETSTATE:
				AisleComandMsg c5 = (AisleComandMsg)req;
				yn = toYNBean(c5,CMD_NET_STATE);
				sb.append("0");
				break;
			//6-打开柜门
			case AisleComandMsg.TYPE_CMD_OPENAISLE:
				OpenAisleMsg c6 = (OpenAisleMsg)req;
				if(StrUtil.isEmpty(c6.getAisleId())) {
					log.warning("+++消息：["+req+"],处理失败！");
					break;
				}
				yn = toYNBean(c6,CMD_LAY_GOODS);
				sb.append(c6.getAisleId()).append("&1");
				break;
			//默认
			default:
				log.warning("+++目前不支持指令："+req.getOperCode());
		}

		if(yn == null) {
			return  null;
		}

		yn.setM(sb.toString());
		return yn.toJson().getBytes();
	}

	/**
	 * 消息至YN格式转换
	 * @param req
	 * @param cmdCode
	 * @return
	 */
	public SZYNMqttProtoBean toYNBean(AisleComandMsg req,int cmdCode){
		SZYNMqttProtoBean yn = new SZYNMqttProtoBean();
		yn.setC(cmdCode);
		yn.setT(req.getMachineCode());
		yn.setS(req.getTxId());
		yn.setF(req.getCtx().getServerId());
		if(MqttMsgContext.class.isAssignableFrom(req.getCtx().getClass())){
			MqttMsgContext ctx = (MqttMsgContext) req.getCtx();
			yn.setMi(ctx.getMqttMsgId());
		}
		return yn;
	}


	/**
	 * 设备上行格式解析
	 * @param result
	 * @return
	 */
	public MachineMsg format(byte[] result,MsgContext ctx) {
		if(result == null) {
			log.warning("++YN字节格式化，输入为空!");
			return null;
		}
		SZYNMqttProtoBean o = SZYNMqttProtoBean.toBean(result);
		//TODO 模拟指令转换 优化
		MachineMsg relMsg = masterMsgCached.popTxMsgCached(o.getS());
		//1.按请求指令判断情况
		if(relMsg != null) {
			//模拟打开货道指令
			if(AisleComandMsg.TYPE_CMD_OPENAISLE.equals(relMsg.getOperCode())){
				if(StrUtil.isEmpty(o.getM())) {
					log.warning("++解析应答出货数据出错,应答结果内容为空,具体:"+o);
					throw new RuntimeException("++解析应答出货数据出错,应答结果内容为空,具体:"+o);
				}
				String[] mmsgs = o.getM().split("&");
				if(mmsgs.length != 2) {
					log.warning("++解析应答出货数据出错,应答结果不满足C&R格式,具体:"+o);
					throw new RuntimeException("++解析应答出货数据出错,应答结果不满足C&R格式,具体:"+o);
				}
				ReqOpenAisleMsg r1 = new ReqOpenAisleMsg(MsgConstants.PROTOCAL_TYPE_MQTT);
				r1.setCtx(ctx);
				r1.setResult(mmsgs[1].equals("0")?ReqOpenAisleMsg.RESULT_OPEN_DOOR_SUCESS:Integer.parseInt(mmsgs[1]));
				toMachineMsg(o,r1);
				return r1;
			}
		}
		//2.按o.OperCode判断情况
		if(o != null) {
			MachineMsg rMsg = forMsgOperCode(o);
			rMsg.setCtx(ctx);
			return rMsg;
		}
		return null;
	}

	/**
	 * YN格式的命令码转成标准格式
	 * @param o
	 * @return
	 */
	public MachineMsg forMsgOperCode(SZYNMqttProtoBean o){
		AisleMachineReq req = null;
		String mc = o.getM();
		switch (o.getC()){
			//1-应答出货
			case CMD_R_LAY_GOODS:
				if(StrUtil.isEmpty(mc)) {
					log.warning("++解析应答出货数据出错,应答结果内容为空,具体:"+o);
					throw new RuntimeException("++解析应答出货数据出错,应答结果内容为空");
				}
				String[] mmsgs = mc.split("&");
				if(mmsgs.length != 2) {
					log.warning("++解析应答出货数据出错,应答结果不满足C&R格式,具体:"+o);
					throw new RuntimeException("++解析应答出货数据出错,应答结果不满足C&R格式");
				}
				ReqLayGoodsMsg r1 = new ReqLayGoodsMsg(MsgConstants.PROTOCAL_TYPE_MQTT);
				r1.setAisleId(mmsgs[0]);
				r1.setResultCode(mmsgs[1]);
				req = r1;
				break;
			//2-应答查询货道状态
			case CMD_R_AS_STATES:
				if(StrUtil.isEmpty(mc)){
					log.warning("++解析应答查询货道信息出错,应答结果不满足E1,E2...格式,具体:"+o);
					throw new RuntimeException("++解析应答查询货道信息出错,应答结果不满足E1,E2...格式");
				}
				ReqQASStateMsg r2 = new ReqQASStateMsg(MsgConstants.PROTOCAL_TYPE_MQTT);
				AsPart p = null;
				String[] arr = mc.split("&");
				if(arr.length == 1) {
					//单个情况下，不回填id (由于设备端未返回正确的货道标识)
					p = new AsPart();
					p.setCmState(Integer.parseInt(arr[0]));
					r2.addPart(p);
				}else{
					//全部查询情况下，按货道顺序填写
					for (int i=1; i<=arr.length; i++){
						p = new AsPart();
						p.setId(String.valueOf(i));
						p.setCmState(forAsState(arr[i-1]));
						r2.addPart(p);
					}
				}

				req = r2;
				break;
			//3-应答补货复位
			case CMD_R_ME_RESETS:
				req = new AisleMachineReq(AisleMachineReq.TYPE_CMD_R_MERESET,MsgConstants.PROTOCAL_TYPE_MQTT);
				break;
			//4-应答订单状态查询
			case CMD_R_OD_STATE:
				ReqOdStateMsg r4 = new ReqOdStateMsg(MsgConstants.PROTOCAL_TYPE_MQTT);
				r4.setOdExisted(mc.equals("1"));
				req = r4;
				break;
			//5-应答查询网络状态
			case CMD_R_NET_STATE:
				String[] arr5 = mc.split("&");
				if(arr5.length < 2) {
					log.warning("++解析应答查询网络信息出错,应答结果不满足E1,E2...格式,具体:"+o);
					throw new RuntimeException("++解析应答查询网络信息出错,应答结果不满足E1,E2...格式");
				}
				ReqQNetStateMsg r5 = new ReqQNetStateMsg(MsgConstants.PROTOCAL_TYPE_MQTT);
				r5.setSim(new SimPart());
				r5.getSim().setRssi(arr5[0]);
				r5.getSim().setIccId(arr5[1]);
				req = r5;
				break;
		}
		//转换其他公共信息
		if(req != null)
			toMachineMsg(o,req);

		return req;
	}

	/**
	 * 判断电机状态
	 * @param state
	 * @return
	 */
	public static Integer forAsState(String state){
		if(StrUtil.isBlank(state))
			return null;
		if("0".equals(state)) return SaleMachineHealth.AISLE_STATE_OPEN;
		if("2".equals(state)) return SaleMachineHealth.AISLE_STATE_NORAML;
		else return SaleMachineHealth.AISLE_STATE_EC_FAIL;
	}

	/**
	 * YN数据格式转换为标准数据格式
	 * @param o
	 * @param req
	 * @return
	 */
	public void toMachineMsg(SZYNMqttProtoBean o, AisleMachineReq req){
		req.setMachineCode(o.getF());
		req.setTxId(o.getS());
		if(MqttMsgContext.class.isAssignableFrom(req.getCtx().getClass())){
			MqttMsgContext ctx = (MqttMsgContext) req.getCtx();
			ctx.setMqttMsgId(o.getMi());
		}
		req.getCtx().setServerId(o.getT());
	}

}
