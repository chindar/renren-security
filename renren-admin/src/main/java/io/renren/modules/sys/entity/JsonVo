//package io.renren.modules.sys.entity;
//
//import java.io.Serializable;
//import java.util.HashMap;
//import java.util.List;
//import com.github.pagehelper.Page;
//
//
//public class JsonVo implements Serializable{
//
//	/**
//	* @Fields serialVersionUID
//	*/
//
//	private static final long serialVersionUID = 1L;
//	/**
//	 * 系统时间
//	 */
//	private long servertime;
//
//	/**
//	 * 结果
//	 */
//	private int code=200;
//
//	/**
//	 * 成功的消息
//	 */
//	private String msg="ok";
//
//	/**
//	 * 具体每个输入错误的消息
//	 */
//	private HashMap<String, String> errors = new HashMap<String, String>();
//
//	/**
//	 * 数据总数
//	 */
//	private Long totalrows=0l;
//	/**
//	 * 版本号
//	 */
//	private String version="1.0";
//	/**
//	 * 返回的数据
//	 */
//	private Object data;
//	/**
//	 * 接口最后更新时间
//	 */
//	private String updatedate;
//
//	public String getUpdatedate() {
//		return updatedate;
//	}
//
//	public void setUpdateDate(String updatedate) {
//		this.updatedate = updatedate;
//	}
//
//	/**
//	 * 作者
//	 */
//	private String author;
//
//	public String getAuthor() {
//		return author;
//	}
//
//	public void setAuthor(String author) {
//		this.author = author;
//	}
//
//	public Long getTotalrows() {
//		return totalrows;
//	}
//
//	public void setTotalrows(Long totalrows) {
//		this.totalrows = totalrows;
//	}
//
//	public JsonVo() {}
//
//	public JsonVo(String author,String version,String updatedate) {
//		this.author=author;
//		this.version=version;
//		this.servertime=System.currentTimeMillis();
//		this.updatedate=updatedate;
//	}
//
//	public String getVersion() {
//		return version;
//	}
//
//	public void setVersion(String version) {
//		this.version = version;
//	}
//
//	public long getServertime() {
//		return servertime;
//	}
//
//	public void setServertime(long servertime) {
//		this.servertime = servertime;
//	}
//
//	public int getCode() {
//		return code;
//	}
//
//	public void setCode(int code) {
//		this.code = code;
//	}
//
//
//	public HashMap<String, String> getErrors() {
//		return errors;
//	}
//
//	public void setErrors(HashMap<String, String> errors) {
//		this.errors = errors;
//	}
//
//	public String getMsg() {
//		return msg;
//	}
//
//	public void setMsg(String msg) {
//		this.msg = msg;
//	}
//
//	public Object getData() {
//		return data;
//	}
//
//	public void setData(Object data) {
//		 if (data instanceof Page) {
//			 Page page = (Page) data;
//			this.totalrows=page.getTotal();
//		}else if(data instanceof List){
//			List list=(List)data;
//			this.totalrows=list.size()+0l;
//		}
//		this.data = data;
//	}
//}
