package org.example.cicd;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: danny
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private String greeting;
}
