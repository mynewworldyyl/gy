package com.gy.security.jaas;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

public class CallbackHandlerAdapter implements CallbackHandler {
	
	public void handle(Callback[] callbacks) throws IOException,
			UnsupportedCallbackException {
		Callback[] callbackArr = callbacks;
		int length = callbackArr.length;
		for (int i = 0; i < length; i++) {
			Callback callback = callbackArr[i];
			if (callback instanceof NameCallback) {
				((NameCallback) callback).setName(getName());
			} else if (callback instanceof PasswordCallback) {
				((PasswordCallback) callback).setPassword(getPassword());
			} else {
				throw new UnsupportedCallbackException(callback);
			}
		}
	}

	public String getName() {
		throw new UnsupportedOperationException();
	}

	public char[] getPassword() {
		throw new UnsupportedOperationException();
	}
}