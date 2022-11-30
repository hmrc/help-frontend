# Add index redirect to "Help using GOV.UK"

* Status: accepted
* Date: 2022-11-31

Technical Story: PLATUI-2104

## Context and Problem Statement

Within the HMRC frontend libraries, when there is a need to link users to general site help, the standard footer links has
text "Help using GOV.UK" which links to https://www.gov.uk/help - for configuration of this in `play-frontend-hmrc`, see
[here](https://github.com/hmrc/play-frontend-hmrc/blob/main/src/main/scala/uk/gov/hmrc/hmrcfrontend/views/config/HmrcFooterItems.scala)
and [here](https://github.com/hmrc/play-frontend-hmrc/blob/main/src/main/resources/messages#L11-L12).

However, there are static HTML pages served from an S3 bucket in various circumstances, including when a page URL is 
invalid, i.e. 404 Not Found as seen here: https://www.tax.service.gov.uk/invalid-service/something-invalid. These are 
defined in the [assets-frontend](https://github.com/hmrc/assets-frontend/tree/main/assets/error_pages) repo, and are 
retrieved from S3 via the Platform routing rules.

These pages point to the following URL in their footer: `/help`. This then directs a user to https://www.tax.service.gov.uk/help,
which is currently not a valid URL.

The planned longer term fix for this would be to revisit the static error pages stored in S3 and sourced from
[assets-frontend](https://github.com/hmrc/assets-frontend). However, in the short-term, a quick fix for this would be a 
redirect from https://www.tax.service.gov.uk/help (which is currently routed to the undefined index of `help-frontend`) 
to the GOV.UK help page.

## Decision Drivers 

* Error pages on the www.tax.service.gov.uk point to a broken link when trying to access help, which is a very poor
  experience
* [assets-frontend](https://github.com/hmrc/assets-frontend) is a deprecated library that we are trying not to upgrade
* The current pattern for error pages (i.e. from [assets-frontend](https://github.com/hmrc/assets-frontend) via S3)
  may be one to revisit but that will be time-consuming and not a quick solution

## Considered Options

* Option 1: Do nothing
* Option 2: Redirect https://www.tax.service.gov.uk/help to https://www.gov.uk/help within Platform routing configuration
* Option 3: Redirect https://www.tax.service.gov.uk/help to https://www.gov.uk/help within `help-frontend`

## Decision Outcome

Chosen option: Option 3

## Pros and Cons of the Options 

* Good, because this is a quick fix that does not require changes to deprecated libraries
* Good, because it keeps logic within the `help-frontend` service for all endpoints, rather than mixing logic between
  service and Platform routing files
* Good, because there is already logic within service to send 303 redirects to gov.uk, so we already know this to be a
  feature of `help-frontend`
* Bad, because it does not fix the underlying issue with the error pages in [assets-frontend](https://github.com/hmrc/assets-frontend),
  although it does not block us from also doing that fix in the future
