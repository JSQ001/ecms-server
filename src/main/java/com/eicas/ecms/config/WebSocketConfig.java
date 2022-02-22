package com.eicas.ecms.config;

import com.eicas.ecms.component.WebSocketServer;
import com.eicas.ecms.processor.XpathPageProcessor;
import com.eicas.ecms.service.IEcmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}