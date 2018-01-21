
### Usage
#### Session
YouTrack's API supports several authentication strategies:

* Username/password
* Permanent tokens (aka *access tokens*)
* Anonymous sessions (aka *guest* access)
* OAuth2

`youtrack-api` supports all of these except OAuth2.

From the `org.llorllale.youtrack.api.session` package you have:

* `UsernamePassword`
* `PermanentToken` 
* `Anonymous`

Each implements the `Login` interface and each produces a valid `Session` object 
that can be readily used by all other aspects of the API.

The use of *permanent tokens* is **recommended**:

    final Session session = new PermanentToken("your_token").login();

A generic `IOException` may be thrown if the YouTrack server is unreachable,
while an `AuthenticationException` - a more specific IOException - is thrown if
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

##### Project Fields
Certain attributes that you would normally expect to be directly represented 
in a model for issue trackers (eg. an issue's *state* or its *priority*) are 
in fact **not present** in this API. This is because YouTrack *itself* does not
actually include these attributes as first class citizens in their model.
This is one area where most other Java YouTrack APIs get it wrong: they always
assume that an `Issue` has `state` and `priority` fields, for instance.
In YouTrack, most attributes are actually entirely *customizable* and 
*optional*. An issue is not required to have either *State* or 
*Priority* attributes, although projects typically do.

*(Behind the scenes, YouTrack implements these as 'custom fields', just like 
fields created by a project's admin user, and automatically configures them 
depending on the template used to create the project.)*

Customizable fields for a project can thus be accessed with the `Fields` 
API retrieved via `Project#fields()`:

    project.fields()
        .stream()             //Stream<ProjectField>
        .filter(/* select your field */)
        .findFirst().get()
        .values()             //Stream<FieldValue>
        .collect(toSet());    //all valid values configured for this field

##### Time Tracking
Similar to project fields, time tracking is an *optional* feature that can 
be configured for projects by an admin. And, since the relevant fields can 
also be *optionally* named "Estimation" and "Spent Time", they are also
implemented as "custom fields".

To see if time tracking is enabled for a project:

    if (project.timetracking().enabled()) {
      ...
    }

You can then safely create a `TimeTrackEntry` on an `Issue`:

    if (issue.project().timetracking().enabled()) {
      issue.timetracking().create(Duration.ofMinutes(45));
    }

To collect all time track entries for the issue:

    issue.timetracking().stream().collect(toList());

#### Issues
##### Get an Issue for a Project

    final Optional<Issue> issue = project.issues().get("issue_id");

##### Collect all Issues for a Project

    final List<Issue> issues = project.issues().stream().collect(toList());

##### Create an Issue

    //with summary and description
    final Issue issue = project.issues()
        .create("summary", "description");

    //with summary, description, and state set to "Open"
    final Field state = /* use Project#fields() */
    final FieldValue open = /* use Project#fields()#stream()...#values() */
    final Map<Field, FieldValue> fields = new HashMap<>();
    fields.put(state, open);
    final Issue issue = project.issues()
        .create("summary", "description", fields);

##### Collect all comments for an Issue

    final List<Comment> comments = issue.comments().stream().collect(toList());

##### Post a new comment

    issue.comments().post("Hello World!");

##### Users of an Issue

    final User creator = issue.users().creator();
    final Optional<User> updater = issue.users().updater();
    final Optional<User> assignee = issue.users().assignee();

##### Assign an Issue to a User

    final Optional<User> joe = issue.project().users().assignees()
        .filter(u -> u.name().startsWith("Joe"))
        .findFirst();
    joe.ifPresent(u -> issue.users().assignTo(u));