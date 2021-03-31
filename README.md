# digital-tariffs-operational-performance-tests

## Jenkins

#### Jenkins job
https://performance.tools.staging.tax.service.gov.uk/job/digital-tariffs-operational-performance-tests/

#### Jenkins configurations
https://github.com/hmrc/performance-test-jobs/blob/master/jobs/live/digital_tariffs.groovy
---

#### Performance testing documentation 
https://confluence.tools.tax.service.gov.uk/display/DTRG/Performance+Testing
=======
# digital-tariffs-operational-performance-tests

### License
This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").

## Run the tests locally

#### Service Manager
Install Service Manager:
https://github.com/hmrc/service-manager

Clone this project:
https://github.com/hmrc/service-manager-config/

#### Mongo
Run an instance of Mongo database. Version 3.6.1 is recommended.

#### Localstack
Please ensure you have Docker installed before continuing with these steps.

You might need to modify your `~/.bash_profile` file by adding `export PATH="/Users/<USR>/Library/Python/2.7/bin:$PATH"`
(Run `source ~/.bash_profile` for reloading it without restarting your machine) (one time only)

Install localstack:

`pip install localstack --user --upgrade --ignore-installed six` (one time only)

`pip install amazon_kclpy` (one time only)

`localstack start` (one time only)

Start localstack

`SERVICES=s3 localstack start` (we need only the S3 service)

Set up aws cli credentials by running `aws configure` (one time only)
```
AWS Access Key ID [None]: test
AWS Secret Access Key [None]: dGVzdA==
Default region name [None]: eu-west-2
Default output format [None]:
```

Create the bucket

`aws --endpoint-url=http://localhost:4572 s3 mb s3://digital-tariffs-local`

Then check this URL is available in the browser http://localhost:4572/digital-tariffs-local

#### Run required microservices
Run the following command:
```
sm --start DIGITAL_TARIFFS -r
sm --stop PDF_GENERATOR_SERVICE
sm --start PDF_GENERATOR_SERVICE -r 1\.20\.0
sm --start FEEDBACK_FRONTEND -f

Stop any relevant services that you will be running locally through the console:
eg. sm --stop BINDING_TARIFF_CLASSIFICATION
```

####

For all tests to run the `sm -s` command should result in something like the table below:

```
+----------------------------------+------+------+-------------+--------+-------+---------+-----------------+----------+-------------+
| name                             | ppid |  pid |      uptime |    mem |  port | test id | run from        | features | healthcheck |
+----------------------------------+------+------+-------------+--------+-------+---------+-----------------+----------+-------------+
| ACCESSIBILITY_STATEMENT_FRONTEND |    1 | 1654 |    14:43:57 |   7712 | 12346 |         | 0.432.0-RELEASE |          |        PASS |
| ASSETS_FRONTEND                  |    1 | 2068 |    14:42:34 |    580 |  9032 |         |                 |          |        PASS |
| AUTH                             |    1 |  979 |    14:46:02 |  72180 |  8500 |         | 6.236.0-RELEASE |          |        PASS |
| AUTH_LOGIN_API                   |    1 | 1299 |    14:45:28 |   8344 |  8585 |         | 1.48.0-RELEASE  |          |        PASS |
| AUTH_LOGIN_STUB                  |    1 | 1031 |    14:45:58 |   8364 |  9949 |         | 1.72.0-RELEASE  |          |        PASS |
| BANK_HOLIDAYS                    |    1 |  711 |    14:46:35 |  51848 |  9587 |         | 0.28.0-RELEASE  |          |        PASS |
| BINDING_TARIFF_ADVICE_FRONTEND   |    1 | 1952 |    14:42:46 |   7668 |  9585 |         | 0.76.0-RELEASE  |          |        PASS |
| BINDING_TARIFF_CLASSIFICATION    |    1 | 1758 |    14:43:46 |  69172 |  9580 |         | 0.400.0-RELEASE |          |        PASS |
| BINDING_TARIFF_FILESTORE         |    1 | 1706 |    14:43:53 |  57128 |  9583 |         | 0.195.0-RELEASE |          |        PASS |
| BINDING_TARIFF_RULING_FRONTEND   |    1 | 2002 |    14:42:40 |  58872 |  9586 |         | 0.101.0-RELEASE |          |        PASS |
| BINDING_TARIFF_TRADER_FRONTEND   |    1 | 1900 |    14:42:51 |  80420 |  9582 |         | 0.472.0-RELEASE |          |        PASS |
| CONTACT_FRONTEND                 |    1 | 1485 |    14:44:31 |  40384 |  9250 |         | 3.236.0-RELEASE |          |        PASS |
| EMAIL                            |    1 |  768 |    14:46:32 | 173252 |  8300 |         | 10.21.0-RELEASE |          |        PASS |
| FEEDBACK_FRONTEND                |    1 | 1538 |    14:44:24 |   8828 |  9514 |         | 0.77.0-RELEASE  |          |        PASS |
| HMRC_EMAIL_RENDERER              |    1 |  822 |    14:46:17 |   7764 |  8950 |         | 2.447.0-RELEASE |          |        PASS |
| IDENTITY_VERIFICATION            |    1 | 1083 |    14:45:50 |  50996 |  9927 |         | 1.269.0-RELEASE |          |        PASS |
| MAILGUN_STUB                     |    1 |  874 |    14:46:15 |  81212 |  8086 |         | 6.7.0-RELEASE   |          |        PASS |
| MONGO                            |    1 |  553 | 29-01:13:26 |  72196 |       |         |                 |          |        NONE |
| PDF_GENERATOR_SERVICE            |    1 | 2191 |    14:41:49 |   7492 |  9852 |         | 1.20.0-RELEASE  |          |        PASS |
| STRIDE_AUTH                      |    1 | 1191 |    14:45:39 |  16472 |  9042 |         | 1.40.0-RELEASE  |          |        PASS |
| STRIDE_AUTH_FRONTEND             |    1 | 1244 |    14:45:34 |  11828 |  9041 |         | 1.23.0-RELEASE  |          |        PASS |
| STRIDE_IDP_STUB                  |    1 | 1135 |    14:45:45 |  44396 |  9043 |         | 1.24.0-RELEASE  |          |        PASS |
| TARIFF_CLASSIFICATION_FRONTEND   |    1 | 1841 |    14:43:13 |  63148 |  9581 |         | 0.863.0-RELEASE |          |        PASS |
| TRACKING_CONSENT_FRONTEND        |    1 | 1590 |    14:44:18 |   7760 | 12345 |         | 0.147.0-RELEASE |          |        PASS |
| UPSCAN_STUB                      |    1 |  926 |    14:46:11 |   7792 |  9570 |         | 0.36.0-RELEASE  |          |        PASS |
| USER_DETAILS                     |    1 | 1351 |    14:45:22 |  78524 |  9978 |         | 0.60.0-RELEASE  |          |        PASS |
+----------------------------------+------+------+-------------+--------+-------+---------+-----------------+----------+-------------+
```
##### command for running all Gatling simulations in Localhost
```
sbt -DrunLocal=true gatling:test or

To run smoke test
sbt -Dperftest.runSmokeTest=true -DrunLocal=true gatling:test

```