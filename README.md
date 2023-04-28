<img src="doc/img/eu_regional_development_fund_horizontal.jpg" width="350" height="200" alt="European Union European Regional Development Fund"/>

# GovSSO End User Self-Service

GovSSO end user self-service provides TODO

## Prerequisites

* Java 17 JDK

## Building and Running Locally

1. Follow [GOVSSO-Session/README.md](https://github.com/e-gov/GOVSSO-Session/blob/master/README.md) to run dependent
   services.
2. If you have generated new TLS certificates (doable at project GOVSSO-Session) after the last copy, then:
    * copy-replace `GOVSSO-Session/local/tls/enduserselfservice/*.p12` files to `src/main/resources`;
    * copy-replace `GOVSSO-Session/local/tls/session/session.localhost.keystore.p12` to `src/test/resources`.
3. Add `127.0.0.1 session.localhost tara.localhost` line to `hosts` file. This is needed only for requests originating
   from GOVSSO-EndUserSelfService when it's running locally (not in Docker Compose). It's not needed for web browsers as
   popular browsers already have built-in support for resolving `*.localhost` subdomains.
4. Run
   ```shell 
   ./mvnw spring-boot:run
   ```

## Running in Docker

1. Build
    * Either build locally
      ```shell
      ./mvnw spring-boot:build-image
      ```
    * Or build in Docker
      ```shell
      docker run --pull always --rm \
                 -v /var/run/docker.sock:/var/run/docker.sock \
                 -v "$HOME/.m2:/root/.m2" \
                 -v "$PWD:/usr/src/project" \
                 -w /usr/src/project \
                 maven:3.8-openjdk-17 \
                 mvn spring-boot:build-image
      ```
      Git Bash users on Windows should add `MSYS_NO_PATHCONV=1` in front of the command.
2. Follow GOVSSO-Session/README.md to run GOVSSO-EndUserSelfService and dependent services inside Docker Compose

## Endpoints

* https://enduserselfservice.localhost:25443/ - UI
* https://enduserselfservice.localhost:25443/actuator - maintenance endpoints

## Configuration

### Integration with GovSSO Session

| Parameter | Mandatory | Description | Example |
| :-------- | :-------- | :---------- | :------ |
| `govsso-enduserselfservice.session.base-url` | Yes | GovSSO Session administrative API base URL. | `https://session.localhost:15443/` |
| `govsso-enduserselfservice.session.tls.trust-store` | Yes | Location of trust-store, containing trust anchors (CA or end-entity certificates) for verifying TLS connections to GovSSO Session. | `classpath:path/to/trust-store.p12` or `file:/path/to/trust-store.p12` |
| `govsso-enduserselfservice.session.tls.trust-store-password` | Yes | Trust-store password. | `changeit` |
| `govsso-enduserselfservice.session.tls.trust-store-type` | No | Trust-store type. If not provided, defaults to `PKCS12`. | `PKCS12` |

### Integration with TARA

| Parameter | Mandatory | Description | Example |
| :-------- | :-------- | :---------- | :------ |
| `tara.client-id` | Yes | TARA client identifier. The client ID is issued by [RIA](https://www.ria.ee/). | `id123` |
| `tara.client-secret` | Yes | TARA client password. The client password is issued by [RIA](https://www.ria.ee/). | `secret123` |
| `tara.issuer-uri` | Yes | TARA OIDC issuer URI where `${tara.issuer-uri}/.well-known/openid-configuration` must return OIDC well-known configuration. `tara.issuer-url` must exactly match `issuer` value published in OIDC well-known configuration. | `https://tara.localhost:16443/` |
| `tara.redirect-uri` | Yes | TARA client redirection URI. Publicly accessible URL of current application's OIDC authorization code flow redirection endpoint. The redirection URI is registered with [RIA](https://www.ria.ee/). | `https://enduserselfservice.localhost:25443/login/oauth2/code/tara` |
| `tara.trust-store` | Yes | Location of trust-store, containing trust anchors (CA or end-entity certificates) for verifying TLS connections to TARA. Trust anchors must be limited according to [TARA documentation](https://e-gov.github.io/TARA-Doku/TechnicalSpecification#512-verifying-the-tls-connection-to-endpoints).  | `classpath:path/to/trust-store.p12` or `file:/path/to/trust-store.p12` |
| `tara.trust-store-password` | Yes | Trust-store password. | `changeit` |

## Non-pom.xml Licenses

* [Maven Wrapper](https://maven.apache.org/wrapper/) - Apache 2.0 license