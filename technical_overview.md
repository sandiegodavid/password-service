This solution has the following technical characteristics:

- Test coverage: 99% line, 100% branch
- Configurable input(`src/main/resources/application.yml`, `application-local.yml`) with sensible defaults
- Handles fuzzy logic using aliases(e.g. `members` and `member`)
- Easy maintenance and extensibility when fields change (single source of truth maintained in `enum`)
- Self documenting code. Used meaningful test names to document method intentions and expectations. Used meaningful method and variable names to convey assumptions and design logic.

Other branches of this repository have the following features:
- HTTPS
- Checkstyle: comply with Google coding standard
- OAuth2
- Actuator for runtime health check

