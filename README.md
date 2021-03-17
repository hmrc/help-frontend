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
sbt test
```

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

To run the ZAP scan, you will need a copy of the ZAP proxy running locally: https://www.zaproxy.org/, with the 
following options configured:

* port configured to 11000
* under HUD, uncheck 'Enable when using the ZAP Desktop' (stops ZAP converting requests to HTTPS)
* under API, check 'Disable the API key'

```
./run_zap_tests.sh
```

More information on HMRC's ZAP scanning automation library can be found at https://github.com/hmrc/zap-automation

## Scalafmt

We are using scalafmt to auto-format all Scala and SBT code. This is enforced in CI. We recommend
 configuring your IDE to auto-format on save. Alternatively, run the following command before opening a PR:

```
sbt scalafmtAll scalafmtSbt
```

## License ##

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
 
