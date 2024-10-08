help-frontend
=============

A frontend service which provides common help pages for services on tax.service.gov.uk,
including cookie details and terms & conditions.

## Adding details of new cookies
To comply with GDPR and related regulations, we are required to document all cookies which
could be set by services running on the platform, so that users can make an informed decision on consent.
If your service uses a cookie not already documented on the [cookie details page](https://www.tax.service.gov.uk/help/cookie-details),
you should **fork this repo and raise a PR** to document it and share it with us in #team-plat-ui on Slack.

If your cookie falls under one of the existing categories of cookie usage,
you should add its details to the relevant section in [`CookiesPage.scala.html`](app/uk/gov/hmrc/helpfrontend/views/CookiesPage.scala.html),
by adding
* a new cookie ID to the list of `cookieIds` for that section
* content for the following properties to [`messages`](conf/messages)
  * `help.cookies.how_used.<section>.table.<cookieId>.name` - cookie name as it is set in the response
  * `help.cookies.how_used.<section>.table.<cookieId>.purpose` - how the cookie is used
  * `help.cookies.how_used.<section>.table.<cookieId>.expires` - when the cookie expires
* corresponding Welsh content (which you must request from the Welsh Language Unit, do not use automated translation tools) for these properties to [`messages.cy`](conf/messages.cy)

If your cookie does not fit one of these existing categories, please contact the PlatUI team to discuss adding a new section.

## Running the application

To run the application execute
```
sbt run
```

and then access the application at (for example)
```
http://localhost:9240/help/cookie-details
```

## Running unit tests

```
sbt test
```

## Integration Tests

```
sbt it/test
```

## Accessibility

Accessibility tests are run in Jenkins or locally via the journey tests.

## UI Journey Tests

UI Journey Tests can be found at https://github.com/hmrc/help-frontend-ui-tests

## Running ZAP scan locally

To run the ZAP scan, use the Docker helper supplied by `dast-config-manager` (https://github.com/hmrc/dast-config-manager#running-zap-locally)

Follow the following steps:
1. Clone the repo at: https://github.com/hmrc/dast-config-manager
2. Enable port forwarding: `export ZAP_FORWARD_ENABLE="true"`
3. Configure port forwarding: `export ZAP_FORWARD_PORTS=6001`
4. In the `dast-config-manager` directory, start the ZAP docker container: `make local-zap-running`
5. In the `help-frontend` directory, run the acceptance tests with ZAP proxying: `sbt -Dbrowser=chrome -Dzap.proxy=true acceptance:test`
6. In the `dast-config-manager` directory, stop the ZAP docker container: `make local-zap-stop`

Information about the local ZAP test output can be found at https://github.com/hmrc/dast-config-manager#running-zap-locally.

## Scalafmt

We are using scalafmt to auto-format all Scala and SBT code. This is enforced in CI. We recommend
 configuring your IDE to auto-format on save. Alternatively, run the following command before opening a PR:
```
sbt scalafmtAll scalafmtSbt
```

## License ##

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
