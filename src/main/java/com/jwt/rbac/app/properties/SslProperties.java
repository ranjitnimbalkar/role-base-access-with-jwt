package com.jwt.rbac.app.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "server.ssl")
@Getter
@Setter
public class SslProperties {
    private String keyStore;
    private String keyStorePassword;
    private String keyAlias;
    private String keyStoreType;
}
