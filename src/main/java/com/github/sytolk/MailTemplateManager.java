package com.github.sytolk;

import java.util.Map;
import java.util.regex.Matcher;

/**
 * User: stanimir
 * Date: 7/21/16
 * developer STANIMIR MARINOV
 */

public class MailTemplateManager {

	static protected final String VAR_START = "\\$\\{";

	static protected final String VAR_END = "}";	

	static public String applyTemplate(String template, Map<String, String> parameterMap) {
		if (template == null) {
			throw new IllegalArgumentException("Unable to process a null mail template");
		}

		for (String paramName : parameterMap.keySet()) {
			String paramValue = parameterMap.get(paramName);
            // STANIMIR fix "Illegal group reference"
            paramValue = Matcher.quoteReplacement(paramValue);
			template = template.replaceAll(VAR_START + paramName + VAR_END, paramValue);			
		}

		return template;
	}
}
