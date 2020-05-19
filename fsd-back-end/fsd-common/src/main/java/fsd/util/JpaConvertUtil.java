package fsd.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

public class JpaConvertUtil {

	public static <T> T convertResult(Map<String, Object> record, Class<T> clazz) {

		try {
			T bean = clazz.newInstance();
			BeanUtils.populate(bean, record);
			return bean;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> List<T> convertResult(List<Map<String, Object>> records, Class<T> clazz) {

		List<T> result = new ArrayList<>(records.size());
		records.forEach(record -> result.add(JpaConvertUtil.convertResult(record, clazz)));
		return result;
	}
}
