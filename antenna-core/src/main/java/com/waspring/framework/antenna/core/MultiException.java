package com.waspring.framework.antenna.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 多异常类型
 * 
 * @author felly
 *
 */
public class MultiException extends Exception {
	private List<Throwable> nested;

	/* ------------------------------------------------------------ */
	public MultiException() {
		super("Multiple exceptions");
	}

	/* ------------------------------------------------------------ */
	public void add(Throwable e) {
		if (e == null)
			throw new IllegalArgumentException();

		if (nested == null) {
			initCause(e);
			nested = new ArrayList();
		}  

		if (e instanceof MultiException) {
			MultiException me = (MultiException) e;
			nested.addAll(me.nested);
		} else
			nested.add(e);
	}

	/* ------------------------------------------------------------ */
	public int size() {
		return (nested == null) ? 0 : nested.size();
	}

	/* ------------------------------------------------------------ */
	public List<Throwable> getThrowables() {
		if (nested == null)
			return Collections.emptyList();
		return nested;
	}

	/* ------------------------------------------------------------ */
	public Throwable getThrowable(int i) {
		return nested.get(i);
	}

	public void ifExceptionThrow() throws Exception {
		if (nested == null)
			return;

		switch (nested.size()) {
		case 0:
			break;
		case 1:
			Throwable th = nested.get(0);
			if (th instanceof Error)
				throw (Error) th;
			if (th instanceof Exception)
				throw (Exception) th;
		default:
			throw this;
		}
	}

	public void ifExceptionThrowRuntime() throws Error {
		if (nested == null)
			return;

		switch (nested.size()) {
		case 0:
			break;
		case 1:
			Throwable th = nested.get(0);
			if (th instanceof Error)
				throw (Error) th;
			else if (th instanceof RuntimeException)
				throw (RuntimeException) th;
			else
				throw new RuntimeException(th);
		default:
			throw new RuntimeException(this);
		}
	}

	public void ifExceptionThrowMulti() throws MultiException {
		if (nested == null)
			return;

		if (nested.size() > 0)
			throw this;
	}

	/* ------------------------------------------------------------ */
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(MultiException.class.getSimpleName());
		if ((nested == null) || (nested.size() <= 0)) {
			str.append("[]");
		} else {
			str.append(nested);
		}
		return str.toString();
	}

}
