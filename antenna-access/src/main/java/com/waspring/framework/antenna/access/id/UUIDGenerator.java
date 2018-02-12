package com.waspring.framework.antenna.access.id;

import java.io.Serializable;
import java.util.UUID;

import com.waspring.framework.antenna.core.visitor.IRequest;
import com.waspring.framework.antenna.core.visitor.IdGenerator;



/**
 * UUID  
 * @author felly
 *
 */
public class UUIDGenerator implements IdGenerator {

	/**
	 * 方法级产生ID,不用重新new
	 */
	@Override
	public Serializable generateId(IRequest request) {
		return UUID.randomUUID().toString();
	}

}
