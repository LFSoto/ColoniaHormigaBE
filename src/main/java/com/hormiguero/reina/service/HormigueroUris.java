package com.hormiguero.reina.service;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HormigueroUris {

	private Logger log;
	private Properties props;
	private static HormigueroUris singleInstance;

	public enum SubSistemas {
		ENTORNO,
		COMIDA
	}

	private HormigueroUris() {
		this.log = LoggerFactory.getLogger(HormigueroUris.class);
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
			log.error("Se ha producido un error intentando cargar el application.properties file. Error message: {}", e.getMessage());
		}
		return properties;
	}
	
	private String getPropertyKeyName(SubSistemas sistema) {
		if (sistema == SubSistemas.COMIDA) {
			return "hormiguero.comida";
		}
		else {
			return "hormiguero.entorno";
		}
	}

	public String getUrl(SubSistemas sistema) {
		this.props = loadApplicationProps();
		return props.getProperty(getPropertyKeyName(sistema)).replaceAll("\"", "").replaceAll("\\\\", "");
	}
	
	public boolean setUrl(SubSistemas sistema, String newUrl) {
		this.props.setProperty(getPropertyKeyName(sistema), newUrl);
		java.io.OutputStream os = null;
		try {
			URL uri = Thread.currentThread().getContextClassLoader().getResource("application.properties");
			os = new java.io.FileOutputStream(uri.getPath());
			this.props.store(os,"");
			
			os.flush();
			return true;
		} catch (IOException ex) {
			return false;
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException ex) {
					
				} finally {
					os = null;
				}
			}
		}
	}

}
