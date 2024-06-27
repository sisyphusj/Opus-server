package com.opus.config;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdditionalTomcatConfiguration {

	@Bean
	public WebServerFactoryCustomizer<TomcatServletWebServerFactory> additionalConnector() {
		return factory -> {
			Connector sslConnector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
			sslConnector.setPort(443);

			factory.addAdditionalTomcatConnectors(sslConnector);
		};
	}

}
