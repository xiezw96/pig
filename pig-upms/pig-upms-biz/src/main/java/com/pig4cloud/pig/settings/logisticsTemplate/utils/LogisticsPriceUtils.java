package com.pig4cloud.pig.settings.logisticsTemplate.utils;

import com.pig4cloud.pig.settings.logisticsTemplate.entity.LogisticsTemplate;
import org.springframework.util.StringUtils;

import java.util.Arrays;

/**
 * <p>Title: LogisticsPriceUtils</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年07月04日
 * @since 1.8
 */
public class LogisticsPriceUtils {
	private static final String FARAWAYPROVINCES_SPERATOR = ",";

	public static LogisticsTemplate split(LogisticsTemplate t) {
		if (t != null && StringUtils.hasText(t.getFarawayProvinces()))
			t.setFarawayProvinceList(Arrays.asList(t.getFarawayProvinces().split(FARAWAYPROVINCES_SPERATOR)));
		return t;
	}

	public static LogisticsTemplate join(LogisticsTemplate t) {
		if (t != null && t.getFarawayProvinceList() != null)
			t.setFarawayProvinces(t.getFarawayProvinceList().stream().reduce((a, b) -> a + FARAWAYPROVINCES_SPERATOR + b).orElse(null));
		else
			t.setFarawayProvinces("");
		return t;
	}

}
