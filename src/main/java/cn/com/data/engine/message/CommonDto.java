/**  
 * All rights Reserved, Designed By www.cnfic.com.cn
 * @Title:  CommonDto.java   
 * @Package cn.com.cnfic.app.demo.domain   
 */
package cn.com.data.engine.message;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <p>
 * Title: CommonDto
 * </p>
 * <p>
 * Description: Data Transfer Object
 * </p>
 * 
 * @author caohaiwang
 * @date Jul 31, 2019
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "timestamp")
public class CommonDto implements Serializable {

	private static final long serialVersionUID = -7394479330778009409L;
	private int code;
	private DataType datatype;
	private String msg;
	private int datasize;
	private Object data;
	private Date timestamp = new Date();

	/**
	 * <p>
	 * Title: DataType
	 * </p>
	 * <p>
	 * Description: enum for datatype
	 * </p>
	 * 
	 * @author caohaiwang
	 * @date Aug 1, 2019
	 */
	public enum DataType {
		/**
		 * map
		 */
		map,
		/**
		 * array
		 */
		list,
		/**
		 * boolean
		 */
		bool,
		/**
		 * int
		 */
		integer,
		/**
		 * String
		 */
		string,
		/**
		 * null
		 */
		none,
		/** numbers */
		number,
	}

}
