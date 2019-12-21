package com.ts.server.safe.common.id;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * 通过UUID生成ID
 * 
 * @author WangWei
 */
public class UUIDGenerator implements IdGenerator<String> {

	@Override
	public String generate() {
		return StringUtils.remove(UUID.randomUUID().toString(), "-");
	}
}
