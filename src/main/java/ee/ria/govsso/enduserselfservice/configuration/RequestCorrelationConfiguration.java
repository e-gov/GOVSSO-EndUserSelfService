package ee.ria.govsso.enduserselfservice.configuration;

import ee.ria.govsso.enduserselfservice.logging.RequestCorrelationFilter;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
public class RequestCorrelationConfiguration {

    @Bean
    public FilterRegistrationBean<RequestCorrelationFilter> requestCorrelationFilter(BuildProperties buildProperties, GitProperties gitProperties) {
        FilterRegistrationBean<RequestCorrelationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RequestCorrelationFilter(buildProperties, gitProperties));
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE); // Ensure that logging attributes are set as early as possible.
        return registrationBean;
    }

}
