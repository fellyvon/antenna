package com.waspring.framework.antenna.core.visitor;

import java.io.Serializable;

import com.waspring.framework.antenna.core.visitor.IRequest;

/**
 * ID产生器，可扩展
 * @author felly
 *
 */
public interface IdGenerator {

	Serializable generateId(IRequest request);
}
