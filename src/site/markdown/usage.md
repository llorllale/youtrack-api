
### Usage
#### Session
YouTrack's API supports several authentication strategies:

* Username/password
* Permanent tokens (aka *access tokens*)
* Anonymous sessions (aka *guest* access)
* OAuth2

`youtrack-api` supports all of these except OAuth2.

From the `org.llorllale.youtrack.api.session` package you have:

* `UsernamePasswordLogin`
* `PermanentTokenLogin` 
* `AnonymousLogin`

Each implements the `Login` interface and each produces a valid `Session` object 
that can be readily used by all other aspects of the API.

The use of *permanent tokens* is **recommended**:

    final Session session = new PermanentTokenLogin("your_token").login();

A generic `IOEXception` may be thrown if the YouTrack server is unreachable,
while a `AuthenticationException` - a more specific IOException - is thrown if
the credentials provided are invalid.

#### YouTrack (interface)
The `YouTrack` interface is the entry point to the rest of the API:

    final YouTrack youtrack = new DefaultYouTrack(session);

#### Projects
You can fetch projects with the `Projects` interface. If you have a project's ID
you can simply:

    final Optional<Project> project = youtrack.projects().get("project_id");

You can also access a regular Java 8 stream of all projects accessible by 
you using `Projects#stream()`:

    final List<Project> projects = youtrack.projects().stream()
        .filter(p -> p.name().contains("Example"))
        .collect(toList());

##### A note on Project Fields
Certain attributes that you would normally expect to be directly represented 
in a model for issue trackers (eg. an issue's *state* or its *priority*) are 
in fact **not present** in this API. This is because YouTrack *itself* does not
actually include these attributes as first class citizens in their model 
(incidentally, this is one area where most other Java YouTrack APIs get it 
wrong). Instead, most attributes are actually entirely *customizable* and 
*optional*. YouTrack does not require an issue to have either *State* or 
*Priority* although projects typically do.

*(Behind the scenes, YouTrack implements these as 'custom' fields, just like 
fields created by an admin user, and automatically configures them depending
on the template used to create the project.)*