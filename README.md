help-frontend
=============


[ ![Download](https://api.bintray.com/packages/hmrc/releases/help-frontend/images/download.svg) ](https://bintray.com/hmrc/releases/help-frontend/_latestVersion)

A frontend service which provides help pages including cookies, T&amp;C's and privacy policy.

## Run the application

To run the application execute

```
sbt "run 9240"
```
or from a developer machine execute
```
sbt -Dplay.filters.headers.contentSecurityPolicy="default-src 'self' 'unsafe-inline' 'unsafe-url' localhost:9032 www.google-analytics.com data:" "run 9240"
```

and then access the application at

```
http://localhost:9240/help/cookies
```

## License ##

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").

