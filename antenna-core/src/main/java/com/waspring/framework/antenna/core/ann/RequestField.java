package com.waspring.framework.antenna.core.ann;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识请求字段，在客户端请求的时候，我们通过这个注解来标记需要设置成可转化为IRequest属性的字段
 * 
 * @author felly
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestField {
	public String name() default "";//// 参数为名字，如果为空，那么会默认使用当前字段名

	public String descrption() default "";//// 字段描述信息
}
