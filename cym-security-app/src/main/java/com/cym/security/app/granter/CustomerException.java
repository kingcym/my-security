package com.cym.security.app.granter;

/**
 * 自定义异常
 * @author cym
 *@date 2017/12/06
 */
public class CustomerException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 异常对应返回码
	 */
	public Integer code;
	/**
	 * 异常对应返回信息
	 */
	public String  msg;
	public CustomerException() {
        super();
    }
    public CustomerException(String msg) {
        super(msg);
        this.msg = msg;
    }
 
    public CustomerException(Integer code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	

}
