package com.ts.server.safe.common.id;

/**
 * 自动生成ID
 * 
 * @author WangWei
 */
public interface IdGenerator<T> {

	/**
	 * 生成ID
	 *
	 * @return id
	 */
	T generate();
	
}
