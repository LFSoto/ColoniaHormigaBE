package com.hormiguero.reina.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class HormigueroUris {

	private Properties props;
	private static HormigueroUris singleInstance;

	public enum SubSistemas {
		ENTORNO,
		COMIDA
	}

	private HormigueroUris() {
		this.props = loadApplicationProps();
	}
	
	public static HormigueroUris getInstance() {
		if (singleInstance == null) {
			singleInstance = new HormigueroUris();
		}
		return singleInstance;
	}

	private Properties loadApplicationProps() {
		Properties properties = new Properties();
		try (InputStream resourceStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties")) {
			properties.load(resourceStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}

	public String getUrl(SubSistemas sistema) {
		String value = "";
		switch(sistema) {
		
		case ENTORNO:
			value = props.getProperty("hormiguero.entorno");
			break;
		case COMIDA:
			value = props.getProperty("hormiguero.comida");
			break;
		}
		return value;
	}

}
