package ex03.core;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author JasonLiu
 */
public class StringManager {
	private static ConcurrentHashMap<String, StringManager> managers = new ConcurrentHashMap<String, StringManager>();

	private StringManager(String packageName) {

	}

	public static StringManager getManager(String packageName) {
		StringManager manager = managers.get(packageName);
		if (manager == null) {
			manager = new StringManager(packageName);
			managers.put(packageName, manager);
		}
		return manager;
	}
}
