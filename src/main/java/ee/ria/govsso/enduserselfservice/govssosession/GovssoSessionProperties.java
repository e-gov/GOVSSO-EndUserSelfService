package ee.ria.govsso.enduserselfservice.govssosession;

import lombok.NonNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.core.io.Resource;

@ConstructorBinding
@ConfigurationProperties("govsso-enduserselfservice.session")
public record GovssoSessionProperties(
        @NonNull String baseUrl,
        @NonNull Tls tls
) {

    public record Tls(
        @NonNull Resource trustStore,
        @NonNull String trustStorePassword
    ) {}

}
