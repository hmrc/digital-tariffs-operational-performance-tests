# digital-tariffs-operational-performance-tests

### Jenkins job
https://performance.tools.staging.tax.service.gov.uk/job/digital-tariffs-operational-performance-tests/

### Jenkins configurations
https://github.com/hmrc/performance-test-jobs/blob/master/jobs/live/digital_tariffs.groovy

---

### Performance testing documentation
https://confluence.tools.tax.service.gov.uk/display/DTRG/Performance+Testing

---

### License
This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").

### Local Environment

**To run this Service you will need:**

1. **Service Manager 2** installed
2. **SBT Version >=1.x** installed
3. **MongoDB version >=5.0** installed and running on **port: 27017**
4. **Localstack** installed and running on **port: 4572**
5. Create an S3 bucket in Localstack by using this command within the localstack container:
```
awslocal s3 mb s3://digital-tariffs-local
```

The easiest way to run MongoDB and Localstack for local development is to use Docker.

### Run Mongo

```
docker run --restart unless-stopped -d -p 27017-27019:27017-27019 --name mongodb mongo:5.0
```

---

### Localstack

Please ensure you have Docker installed before continuing with these steps.

#### Install Localstack:

``` 
docker run -d --restart unless-stopped --name localstack -e SERVICES=s3 -p4572:4566 -p8080:8080 localstack/localstack
```

```
pip install amazon_kclpy
```
#### Start localstack

Original main localstack port: `4572`

Possible port depending on configurations: `4566`

> Note: Starting with version 0.11.0, all APIs are exposed via a single edge service,
> which is accessible on http://localhost:4572 by default (customizable via EDGE_PORT, see further below).
> https://github.com/localstack/localstack#overview

#### Check the current port using

```
docker port localstack
```
---
### Whilst the **Localstack** container is running:

**Execute your Localstack container**

Open a new terminal session in the container (`localstack_main` by default) using:

```
docker exec -it localstack bash
```
---

### Configure AWS credentials

In your LocalStack terminal session run:
```
aws configure
```

Then enter these details for the configuration:

```
AWS Access Key ID [None]: test
AWS Secret Access Key [None]: dGVzdA==
Default region name [None]: eu-west-2
Default output format [None]:
```
---

### Create the bucket

```
awslocal s3 mb s3://digital-tariffs-local
```

Then check this URL is available in the browser:

> **http://localhost:4572/digital-tariffs-local**

**Note:** You should see some XML in your browser if successful

---

### Run required microservices
Run the following command:

`sm2 --start DIGITAL_TARIFFS`

`sm2 --start FEEDBACK_FRONTEND`

---

## Running the performance tests

### To clear down and delete all cases in LOCAL environment

In `services-local.conf` set

```
deleteAllCases = true
```

### To clear down and delete all cases in STAGING environment

In `services.conf` set

```
deleteAllCases = true
```

### Local

```
./run_local.sh
```

### Local Smoke

```
run_smoke_test_local.sh
```

### Staging

```
./run.sh
```

### Staging

```
run_smoke_test.sh
```
