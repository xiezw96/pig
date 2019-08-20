package com.pig4cloud.pig.device.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.bytebuddy.implementation.bytecode.Throw;

/**
 * @title: AisleMachineOperException
 * @projectName eden
 * @description: TODO
 * @date 6/6/1911:38 AM
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
public class AisleMachineOperException extends RuntimeException{
	//异常类型
	public String operCode;

	//异常码
	public String code;

	public AisleMachineOperException() {
	}
	public AisleMachineOperException(String operCode ,String code ,String msg) {
		super(msg);
		this.operCode = operCode;
		this.code = code;
	}
	public AisleMachineOperException(String operCode , String code , String msg, Throwable t) {
		super(msg,t);
		this.operCode = operCode;
		this.code = code;
	}
	public AisleMachineOperException(String msg) {
		super(msg);
	}
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
