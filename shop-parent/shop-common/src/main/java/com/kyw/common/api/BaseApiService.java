package com.kyw.common.api;

import java.util.HashMap;
import java.util.Map;

import com.kyw.constants.BaseApiConstants;

public class BaseApiService {

	public Map<String,Object> setResultParamError(String msg){
		return setResult(BaseApiConstants.HHTP_400_CODE, msg, null);
	 
	}
	public Map<String,Object> setResultError(String msg){
		return setResult(BaseApiConstants.HHTP_500_CODE, msg, null);
		
	}
	public Map<String,Object> setResultSuccess(){
		return setResult(BaseApiConstants.HHTP_200_CODE, BaseApiConstants.HHTP_200_SUCCESS, null);
		
	}
	public Map<String,Object> setResultSuccess(String msg){
		return setResult(BaseApiConstants.HHTP_200_CODE, msg, null);
		
	}
	public Map<String,Object> setResultSuccessData(Object data){
		return setResult(BaseApiConstants.HHTP_200_CODE, BaseApiConstants.HHTP_200_SUCCESS, data);
		
	}
	
	/**
	 * 自定义返回结果集
	 * @param code 返回code值
	 * @param msg  返回的信息
	 * @param data  返回的数据
	 * @return
	 */
	public Map<String,Object> setResult(Integer code,String msg,Object data){
		Map<String,Object> result= new HashMap<>();
		result.put(BaseApiConstants.HHTP_CODE_NAME, code);
		result.put(BaseApiConstants.HHTP_MSG_NAME, msg);
		if(data!=null) {
			result.put(BaseApiConstants.HHTP_DATA_NAME, data);
		}
		return result;
	}
	
}
