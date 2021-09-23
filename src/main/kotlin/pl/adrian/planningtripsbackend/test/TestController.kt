package pl.adrian.planningtripsbackend.test

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.security.Principal

@RestController
@RequestMapping("/test")
class TestController {

    @GetMapping
    fun test() : Mono<String> = Mono.just("test")

    @GetMapping("/4")
    fun test4(principal: JwtAuthenticationToken) : Mono<String> =
        Mono.just(principal.toString() + "\n" + principal.tokenAttributes)
}