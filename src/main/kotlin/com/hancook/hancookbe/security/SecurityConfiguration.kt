package com.hancook.hancookbe.security

import com.nimbusds.jose.jwk.JWK
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.config.Customizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.security.web.SecurityFilterChain
import java.security.KeyPairGenerator
import java.security.Security
import java.security.interfaces.RSAKey
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey

@Configuration
class SecurityConfiguration(
    @Autowired
    private val customBasicAuthenticationEntryPoint: CustomBasicAuthenticationEntryPoint,

    @Autowired
    private val customBearerTokenAuthenticationEntryPoint: CustomBearerTokenAuthenticationEntryPoint,

    @Autowired
    private val customBearerTokenAccessDeniedHandler: CustomBearerTokenAccessDeniedHandler,

    @Autowired
    private val customNoAuthEntryPoint: CustomNoAuthEntryPoint
) {

    private lateinit var publicKey: RSAPublicKey
    private lateinit var privateKey: RSAPrivateKey

    @Value("\${api.endpoint.base-url}")
    lateinit var baseUrl: String


    init {
        val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
        keyPairGenerator.initialize(2048)
        val keyPair = keyPairGenerator.generateKeyPair()

        this.publicKey = keyPair.public as RSAPublicKey
        this.privateKey = keyPair.private as RSAPrivateKey
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http.authorizeHttpRequests {
            it
                .requestMatchers(HttpMethod.GET, "${this.baseUrl}/dishes/**").permitAll()
                .requestMatchers(HttpMethod.GET, "${this.baseUrl}/employees/**").hasAuthority("ADMIN")
                .anyRequest().authenticated()
        }
            .csrf { it.disable() }
            .cors(Customizer.withDefaults())
            .httpBasic { it.authenticationEntryPoint(customBasicAuthenticationEntryPoint) }
            .oauth2ResourceServer {
                it.jwt{}
                    .authenticationEntryPoint(customBearerTokenAuthenticationEntryPoint)
                    .accessDeniedHandler(customBearerTokenAccessDeniedHandler)
            }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .exceptionHandling { it.authenticationEntryPoint(customNoAuthEntryPoint) }
            .build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun jwtEncoder(): JwtEncoder {
        val jwk: JWK = com.nimbusds.jose.jwk.RSAKey
            .Builder(this.publicKey)
            .privateKey(this.privateKey)
            .build()

        val jwkSet: JWKSource<SecurityContext> = ImmutableJWKSet(JWKSet(jwk))
        return NimbusJwtEncoder(jwkSet)
    }

    @Bean
    fun jwtDecoder(): JwtDecoder {
        return NimbusJwtDecoder.withPublicKey(this.publicKey).build()
    }

    @Bean
    fun jwtAuthenticationConverter(): JwtAuthenticationConverter {
        val jwtGrantedAuthoritiesConverter = JwtGrantedAuthoritiesConverter()

        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("authorities")
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("")

        val jwtAuthenticationConverter = JwtAuthenticationConverter()
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter)

        return jwtAuthenticationConverter
    }
}