package com.waspring.framework.antenna.access.id;

import java.io.Serializable;
import java.util.Random;

import org.apache.log4j.Logger;

import com.waspring.framework.antenna.core.visitor.IRequest;
import com.waspring.framework.antenna.core.visitor.IdGenerator;

/**
 * 随机数产生的id
 * @author felly
 *
 */
public class RandomGenerator implements IdGenerator {

	private static final Logger log = Logger.getLogger(RandomGenerator.class);

	private static final String RANDOM_NUM_GENERATOR_ALGORITHM_NAME = "SHA1PRNG";
	private Random random;

	
	/**
	 * 初始化产生数据，所以需要通过new来产生新值
	 */
	public RandomGenerator() {
		try {
			this.random = java.security.SecureRandom.getInstance(RANDOM_NUM_GENERATOR_ALGORITHM_NAME);
		} catch (java.security.NoSuchAlgorithmException e) {

			this.random = new java.security.SecureRandom();
		}
	}

	public Serializable generateId(IRequest session) {
		return Long.toString(random.nextLong());
	}

}
