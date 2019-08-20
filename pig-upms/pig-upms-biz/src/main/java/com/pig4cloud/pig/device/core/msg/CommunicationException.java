package com.pig4cloud.pig.device.core.msg;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @title: CommunicationException
 * @projectName eden
 * @description: TODO
 */
@Setter
@Getter
@ToString
public class CommunicationException extends RuntimeException implements Serializable {
	private static final long serialVersionUID = 1L;
	//其他未定义异常
	public  static final int  REASON_CODE_UNDEFINED_EXCEPTION = -1;
	//超时异常
	public  static final int REASON_CODE_TIMEOUT = 10001;

	private int reasonCode;

	public CommunicationException(int reasonCode,String message,Throwable cause){
		super(message,cause);
		this.reasonCode = reasonCode;
	}

}
