
# youtrack-api

[![codecov](https://codecov.io/gh/llorllale/youtrack-api/branch/master/graph/badge.svg)](https://codecov.io/gh/llorllale/youtrack-api)
[![Build Status](https://travis-ci.org/llorllale/youtrack-api.svg?branch=master)](https://travis-ci.org/llorllale/youtrack-api)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.llorllale/youtrack-api/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.llorllale/youtrack-api)

`youtrack-api` is a fluent, object-oriented Java API for [YouTrack](https://www.jetbrains.com/youtrack/). Visit the [project's site](https://llorllale.github.io/youtrack-api) for more info.

Java 8 or above is required to run `youtrack-api`.

Here's a snippet of its usage:

```java
final YouTrack youtrack = new DefaultYouTrack(
    new PermanentTokenLogin("token").login()
);
youtrack.projects().get("project_id")
    .issues()
    .create(new IssueSpec("summary", "description"))    //creates issue
    .comments()
    .post("Hello World!");                              //posts comment to the issue
```

## Feedback
Please direct any questions, feature requests or bugs to the [issue tracker](https://github.com/llorllale/youtrack-api/issues/).

## How do I contribute?
Please view our guidelines for contributing [here](./CONTRIBUTING.md).
