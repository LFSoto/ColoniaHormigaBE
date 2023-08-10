package com.hormiguero.reina.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class HormigueroUris {

	private Properties _props;
	private static HormigueroUris _singleInstance;

	public enum SubSistemas {
		ENTORNO,
		COMIDA
	}

	private HormigueroUris() {
		this._props = loadApplicationProps();
	}
	
	public static HormigueroUris getInstance() {
		if (_singleInstance == null) {
			_singleInstance = new HormigueroUris();
		}
		return _singleInstance;
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
			value = _props.getProperty("hormiguero.entorno");

		case COMIDA:
			value = _props.getProperty("hormiguero.comida");
		}
		return value;
	}

}
