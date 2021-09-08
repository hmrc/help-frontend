help-frontend
=============

A frontend service which provides help pages including cookies, T&amp;C's and privacy policy.

## Run the application

To run the application execute

```
sbt run
```

and then access the application at

```
http://localhost:9240/help/cookies
```

## Running the unit tests

```
sbt a11yTest
```

The above tests include accessibility checks via the
[sbt-accessibility-linter](https://www.github.com/hmrc/sbt-accessibility-linter)
plugin.

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
