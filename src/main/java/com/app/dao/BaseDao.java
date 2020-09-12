package com.app.dao;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

abstract class BaseDao {
	
	@PersistenceContext
	protected EntityManager em;
	
	protected static String bBuilder(String... sqls) {
		StringBuilder build = new StringBuilder("");
		for(String sql : sqls) {
			build.append(sql);
		}
		return build.toString();
	}
	
	public static <T> List<T> bMapperList(List<Object[]> listMapping, Class<T> clazz, String... sql)
			throws Exception {

		List<T> listResult = new ArrayList<>();

		listMapping.forEach(valDb -> {
			try {
				T newClass = clazz.newInstance();
				Method[] methods = clazz.getDeclaredMethods();
				List<Method> listMethod = new ArrayList<>(Arrays.asList(methods));
				getSuperMethod(clazz, listMethod);

				for (int i = 0; i < valDb.length; i++) {
					Object value = valDb[i];
					String mapping = sql[i];

					invokeMethod(newClass, listMethod, value, mapping);

				}

				listResult.add(newClass);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		return listResult;
	}
	private static <T> void getSuperMethod(Class<T> clazz, List<Method> listMethod) {
		Class<? super T> superClazz = clazz.getSuperclass();
		while (superClazz != null) {
			Method[] methodSuper = superClazz.getDeclaredMethods();
			listMethod.addAll(Arrays.asList(methodSuper));
			superClazz = superClazz.getSuperclass();
		}
	}

	private static <T> void invokeMethod(T newClass, List<Method> listMethod, Object value, String mapping)
			throws Exception {
		for (int j = 0; j < listMethod.size(); j++) {
			boolean isFound = false;
			Method m = listMethod.get(j);

			if (m.getName().startsWith("set")) {

				for (Parameter p : m.getParameters()) {

					if (p.getName().equalsIgnoreCase(mapping)) {

						isFound = true;
						m.invoke(newClass, value);
						break;
					}

					break;
				}
			}

			if (isFound)
				break;
		}
	}
	
	public static List<String> mapping(String sql) throws Exception {
		List<String> resulLists = new ArrayList<>();

		// clear select keyword
		sql = sql.replaceFirst("\\s+", "");
		if (sql.length() < 6)
			throw new Exception("SQL length too short");
		sql = sql.substring(6, sql.length());

		// clear from until end of file
		int fromKey = sql.indexOf("FROM") != -1 ? sql.indexOf("FROM") : sql.indexOf("from");
		sql = sql.substring(0, fromKey);

		String[] sqls = sql.split(",");
		List<String> sqlList = new ArrayList<>(Arrays.asList(sqls));

		sqlList.forEach(val -> {

			if (!val.isEmpty()) {
				String result = val.substring(0, 1).equals(" ") ? val.replaceFirst("\\s+", "") : val;
				String[] sqls2 = result.split(" ");

				List<String> sqlList2 = new ArrayList<>(Arrays.asList(sqls2));

				int count = 0;
				boolean check = false;
				String temp = "";

				for (int i = 0; i < sqlList2.size(); i++) {
					if (!sqlList2.get(i).isEmpty()) {
						temp = sqlList2.get(i);
						count++;
						if (count == 2) {
							check = true;
							resulLists.add(temp);
							break;
						}
					}
				}

				if (!check)
					resulLists.add(temp);

			}

		});
		return resulLists;
	}
	
	public static <T> List<T> bMapper(List<Object> listMapping, Class<T> clazz, String... sql) throws Exception {
		List<T> listResult = new ArrayList<>();
		List<String> listObj = new ArrayList<>();
		for(int i= 0;i<sql.length;i++) {
			listObj.add(sql[i]);
		}

		listMapping.forEach(valDb -> {
			try {
				T newClass = clazz.newInstance();
				Method[] methods = clazz.getDeclaredMethods();
				List<Method> listMethod = new ArrayList<>(Arrays.asList(methods));
				getSuperMethod(clazz, listMethod);

				invokeMethod(newClass, listMethod, valDb, listObj.get(0));

				listResult.add(newClass);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		return listResult;
	}
}
