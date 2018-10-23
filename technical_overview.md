- Test coverage: 99% line, 100% branch
- HTTPS
- Configurable input(`src/main/resources/application.yml`, `application-local.yml`) with sensible defaults
- Handles fuzzy logic using aliases(e.g. `members` and `member`)
- Easy maintenance and extensibility when fields change (single source of truth maintained in `enum`)
- Self documenting code. Used meaningful test names to document method intentions and expectations. Used meaningful method and variable names to convey assumptions and design logic.

To be completed:
- Checkstyle: comply with Google coding standard
- OAuth2

