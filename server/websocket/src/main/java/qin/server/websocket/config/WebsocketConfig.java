package qin.server.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author qintingshuang
 * @create 2020-08-11 15:50
 * @description websocket配置文件
 **/
@Configuration
public class WebsocketConfig {


    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return  new ServerEndpointExporter();
    }

}
