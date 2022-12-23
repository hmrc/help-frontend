help-frontend
=============

A frontend service which provides common help pages for services on tax.service.gov.uk,
including cookie details and terms & conditions.

## Adding details of new cookies
To comply with GDPR and related regulations, we are required to document all cookies which
could be set by services running on the platform, so that users can make an informed decision on consent.
If your service uses a cookie not already documented on the [cookie details page](https://www.tax.service.gov.uk/help/cookie-details),
you should raise a PR on this repo to document it and share it with us in #team-plat-ui on Slack.

If your cookie falls under one of the existing categories of cookie usage,
you should add its details to the relevant section in [`CookiesPage.scala.html`](app/uk/gov/hmrc/helpfrontend/views/CookiesPage.scala.html),
by adding
* a new cookie ID to the list of `cookieIds` for that section
* content for the following properties to [`messages`](conf/messages)
  * `help.cookies.how_used.<section>.table.<cookieId>.name` - cookie name as it is set in the response
  * `help.cookies.how_used.<section>.table.<cookieId>.purpose` - how the cookie is used
  * `help.cookies.how_used.<section>.table.<cookieId>.expires` - when the cookie expires
* corresponding Welsh content for these properties to [`messages.cy`](conf/messages.cy)

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

## Running accessibility tests

```
sbt a11y:test
```

The above tests are run via the
[sbt-accessibility-linter](https://www.github.com/hmrc/sbt-accessibility-linter)
plugin. This plugin requires Node.js v12 or above to be installed locally.

## Running the UI acceptance/integration tests

The UI and ZAP tests are based on the template at https://github.com/hmrc/platform-example-ui-journey-tests
with some modifications to allow them to be run as part of the microservice
repository.

To run the UI acceptance tests locally, you will need a copy of Chrome
and the Chrome browser driver installed at /usr/local/bin/chromedriver
```
./run_acceptance_tests.sh
```

The Chrome driver is available at https://chromedriver.chromium.org/

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
