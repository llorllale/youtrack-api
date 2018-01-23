
# youtrack-api

## Guidelines for contributing to the project

### Feedback
Please direct any questions, feature requests or bugs to the 
[issue tracker](https://github.com/llorllale/youtrack-api/issues/).

### Requirements
To develop and run unit tests you'll need:

* Apache Maven 3+
* JDK 1.8+

To run the integration tests you'll additionally need a proper docker 
environment setup. The project is primarily developed in linux and hence
assumes the YouTrack instance running in the docker container is reachable
via `localhost`. If your OS differs you may need to temporarily play around
with the `youtrack.docker.baseUrl` property in the `pom.xml` in order to run
the integraion tests (just make sure not to commit any changes to this 
property!).

### How to contribute?
Fork the repository, code your changes, then submit a pull request. We ask 
several things:

* If your PR solves an issue, the first line on your commit message should be
a reference to the issue surrounded by parenthesis, followed by a space, 
followed by the issue's title. Example: `(#32) Fixing some bug`
* The rest of your commit message should clearly state what is new (NEW), 
what has merely changed (REF), what has been fixed (FIX), etc.
* First ensure your contribution meets the project's styleguides by running 
the following and making sure there are no errors: 
`mvn -P release-profile clean install -DskipTests`
* Your code contribution must include tests, whether its unit tests or 
integration-tests if applicable.

### Code Coverage
Coverage is reported via [CodeCov.io](https://codecov.io/gh/llorllale/youtrack-api)
and also the [project's site](https://llorllale.github.io/youtrack-api/cobertura/).

**The current minimum target coverage is 85%.**

#### Run the tests
* To run the unit tests: `mvn test`. 
* To run unit and integration tests: `mvn -P integration-tests clean verify` 
(make sure your docker environment is setup as mentioned before).
* To run all tests and verify coverage: `mvn -P integration-tests clean cobertura:check-integration-test`
