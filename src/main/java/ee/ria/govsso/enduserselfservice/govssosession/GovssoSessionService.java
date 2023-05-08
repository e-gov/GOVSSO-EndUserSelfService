package ee.ria.govsso.enduserselfservice.govssosession;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GovssoSessionService {

    private final WebClient sessionRestClient;

    public Mono<List<GovssoSession>> getSubjectSessions(String subject) {
        return sessionRestClient
                .get()
                .uri("/admin/sessions/{subject}", subject)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});
    }

    public Mono<Void> endSession(String subject, String sessionId) {
        return sessionRestClient
                .delete()
                .uri("/admin/sessions/{subject}/{sessionId}", subject, sessionId)
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Mono<Void> endSubjectSessions(String subject) {
        return sessionRestClient
                .delete()
                .uri("/admin/sessions/{subject}", subject)
                .retrieve()
                .bodyToMono(Void.class);
    }

}
