package ee.ria.govsso.enduserselfservice.controller;

import ee.ria.govsso.enduserselfservice.govssosession.GovssoSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import reactor.core.publisher.Mono;


// TODO: Return HTTP code instead of redirect
@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final GovssoSessionService govssoSessionService;

    @GetMapping("/sessions/{sessionId}") // TODO: DELETE
    public Mono<?> endSession(@AuthenticationPrincipal OidcUser oidcUser, @PathVariable String sessionId) {
        if (oidcUser == null) {
            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }
        return govssoSessionService.endSession(oidcUser.getSubject(), sessionId)
                .then(Mono.just(new RedirectView("/")));
    }

    @GetMapping("/sessions") // TODO: DELETE
    public Mono<?> endAllSessions(@AuthenticationPrincipal OidcUser oidcUser) {
        if (oidcUser == null) {
            return Mono.just(new ModelAndView("index"));
        }
        return govssoSessionService.endSubjectSessions(oidcUser.getSubject())
                .then(Mono.just(new RedirectView("/")));
    }

}
