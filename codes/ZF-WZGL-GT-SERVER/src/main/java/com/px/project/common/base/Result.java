package com.px.project.common.base;

/**
 * @author 品讯科技
 * @version V1.0
 * @Package com.px.pay.common.base
 * @ClassName:
 * @Description:
 * @time 2019/02/25
 */
public class Result {
    /**
     * 响应code
     */
    private String code;

    /**
     * 响应中文信息
     */
    private String msg;

    /**
     * 响应英文信息
     */
    private String enMsg;

    /**
     * 响应状态, true:成功、 false:失败
     */
    private Boolean status;

    private Integer promptType;

    /**
     * 返回结果参数
     */
    private Object data;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getEnMsg() {
        return enMsg;
    }

    public void setEnMsg(String enMsg) {
        this.enMsg = enMsg;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }


    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getPromptType() {
        return promptType;
    }

    public void setPromptType(Integer promptType) {
        this.promptType = promptType;
    }



    @Override
    public String toString() {
        return "Result{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", enMsg='" + enMsg + '\'' +
                ", status=" + status +
                ", promptType=" + promptType +
                ", data=" + data +
                '}';
    }

    /**
     *
     * @Title: SUCCESS
     * @Description: 成功返回
     * @author 品讯科技
     * @param data 接口回参
     * @return
     * @throws
     */
    public static Result SUCCESS(Object data){
        Result rs = new Result();
        rs.setCode(ReturnMsgEnum.OK.getCode());
        rs.setMsg(ReturnMsgEnum.OK.getMsg());
        rs.setEnMsg(ReturnMsgEnum.OK.toString());
        rs.setStatus(ReturnMsgEnum.OK.getStatus());
        rs.setData(data);
        return rs;
    }


    /**
     * @Title: SUCCESSLESS
     * @Description: 成功返回，缺少data信息
     * @author 品讯科技
     * @return
     * @throws
     */
    public static Result SUCCESS(){
        Result rs = new Result();
        rs.setCode(ReturnMsgEnum.OK.getCode());
        rs.setMsg(ReturnMsgEnum.OK.getMsg());
        rs.setEnMsg(ReturnMsgEnum.OK.toString());
        return rs;
    }

    /**
    *
    * @Title: DATAERR
    * @Description: 返回数据错误
    * @author 品讯科技
    * @param
    * @return
    * @throws
    */
   public static Result DATAERR(){
       Result rs = new Result();
       rs.setCode(ReturnMsgEnum.FAILURE.getCode());
       rs.setMsg(ReturnMsgEnum.FAILURE.getMsg());
       rs.setEnMsg(ReturnMsgEnum.FAILURE.toString());
       return rs;
   }


   /**
   *
   * @Title: PARAMERR
   * @Description: 参数传递错误
   * @author 品讯科技
   * @param data 详细错误信息
   * @return
   * @throws
   */
  public static Result PARAMERR(String data){
      Result rs = new Result();
      rs.setCode(ReturnMsgEnum.FAILURE.getCode());
      rs.setMsg(data);
      rs.setEnMsg(ReturnMsgEnum.FAILURE.toString());
      rs.setStatus(ReturnMsgEnum.FAILURE.getStatus());
      return rs;
  }


  /**
  *
  * @Title: ERROR
  * @Description: 服务器逻辑处理错误
  * @author 品讯科技
  * @return
  * @throws
  */
 public static Result ERROR(){
     Result rs = new Result();
     rs.setCode(ReturnMsgEnum.FAILURE.getCode());
     rs.setMsg(ReturnMsgEnum.FAILURE.getMsg());
     rs.setEnMsg(ReturnMsgEnum.FAILURE.toString());
     rs.setStatus(ReturnMsgEnum.FAILURE.getStatus());
     return rs;
 }

    public static Result buildResult(ReturnMsg msg) {
        Result rs = new Result();
        rs.setCode(msg.getCode());
        rs.setMsg(msg.getMsg());
        rs.setEnMsg(msg.toString());
        rs.setPromptType(msg.promptType());
        rs.setStatus(msg.getStatus());
        return rs;
    }




    public static Result buildResult(ReturnMsg msg, Object data) {
        Result rs = new Result();
        rs.setCode(msg.getCode());
        rs.setMsg(msg.getMsg());
        rs.setEnMsg(msg.toString());
        rs.setPromptType(msg.promptType());
        rs.setStatus(msg.getStatus());
        rs.setData(data);
        return rs;
    }

    public static Result buildResult(ReturnMsg msg, String msgStr) {
        Result rs = new Result ();
        rs.setCode(msg.getCode());
        rs.setMsg(msgStr);
        rs.setEnMsg(msg.toString());
        rs.setPromptType(msg.promptType());
        rs.setStatus(msg.getStatus());
        return rs;
    }

    public static Result buildResult(String code, String msg) {
        Result rs = new Result();
        rs.setCode(code);
        rs.setMsg(msg);
        rs.setEnMsg(null);
        rs.setPromptType(null);
        rs.setStatus(null);
        rs.setData(null);
        return rs;
    }

	public static Result ERROR(String message) {
		// TODO Auto-generated method stub
		return null;
	}

}
