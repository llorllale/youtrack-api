# youtrack-api ![icon](/src/site/resources/images/icon_32.png)
[![EO principles respected here](http://www.elegantobjects.org/badge.svg)](http://www.elegantobjects.org)

[![codecov](https://codecov.io/gh/llorllale/youtrack-api/branch/master/graph/badge.svg)](https://codecov.io/gh/llorllale/youtrack-api)
[![Build Status](https://travis-ci.org/llorllale/youtrack-api.svg?branch=master)](https://travis-ci.org/llorllale/youtrack-api)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.llorllale/youtrack-api/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.llorllale/youtrack-api)
[![PDD status](http://www.0pdd.com/svg?name=llorllale/youtrack-api)](http://www.0pdd.com/p?name=llorllale/youtrack-api)
[![Javadocs](http://javadoc.io/badge/org.llorllale/youtrack-api.svg?color=blue)](http://javadoc.io/doc/org.llorllale/youtrack-api)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://llorllale.github.io/youtrack-api/license.html)

`youtrack-api` is a fluent, object-oriented Java API for [YouTrack](https://www.jetbrains.com/youtrack/). Visit the [project's site](https://llorllale.github.io/youtrack-api) for more info. It has just one dependency: Apache's [HttpClient](https://mvnrepository.com/artifact/org.apache.httpcomponents/httpclient) version `4.5.x`.

Java 8 or above is required.

Here's a snippet of its usage:

```java
final YouTrack youtrack = new DefaultYouTrack(
    new PermanentToken("token").login()
);
youtrack.projects().get("project_id").get()
    .issues()
    .create("summary", "description")       //creates issue
    .comments()
    .post("Hello World!");                  //posts comment to the issue
```

## Feedback
Please direct any questions, feature requests or bugs to the [issue tracker](https://github.com/llorllale/youtrack-api/issues/).

## How do I contribute?
Please view our guidelines for contributing [here](./CONTRIBUTING.md).

## License
`youtrack-api` is licensed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0). A copy of the license has been included
in [LICENSE](./LICENSE).

<br/>

<div>Icon made by <a href="http://www.freepik.com" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0" target="_blank">CC BY 3.0</a></div>
