package com.example;

import java.io.IOException;
import java.net.InetAddress;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.sasl.RealmCallback;

import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.dmr.ModelNode;

public class Main {
	public static String result;

	public static String getResult() {
		return result;
	}

	public static void main(final String[] args) throws Exception {
		final String server = args[0];
		final String port = args[1];
		final String username = args[2];
		final String password = args[3];

		ModelControllerClient client = ModelControllerClient.Factory.create(InetAddress.getByName(server),
				Integer.valueOf(port), new CallbackHandler() {

					public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
						for (Callback current : callbacks) {
							if (current instanceof NameCallback) {
								NameCallback ncb = (NameCallback) current;
								ncb.setName(username);
							} else if (current instanceof PasswordCallback) {
								PasswordCallback pcb = (PasswordCallback) current;
								pcb.setPassword(password.toCharArray());
							} else if (current instanceof RealmCallback) {
								RealmCallback rcb = (RealmCallback) current;
								rcb.setText(rcb.getDefaultText());
							} else {
								throw new UnsupportedCallbackException(current);
							}
						}
					}
				});

		ModelNode operation = new ModelNode();
		operation.get("operation").set("read-attribute");
		operation.get("name").set("servlet-container");

		ModelNode address = operation.get("address");
		address.add("subsystem", "undertow");
		address.add("server", "default-server");

		System.out.println("ModelNode operation");
		System.out.println(operation.toJSONString(false));

		try {
			ModelNode returnVal = client.execute(operation);
			System.out.println("Result:");
			result = returnVal.toJSONString(false);
			System.out.println(result);
		} finally {
			client.close();
		}
	}
}
