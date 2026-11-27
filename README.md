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
4. **Moto (MotoServer)** running on **port: 4566**
5. An S3 bucket called `digital-tariffs-local` created in Moto (see below)

The easiest way to run MongoDB and Moto for local development is to use Docker.

### Run Mongo

```
docker run --restart unless-stopped -d -p 27017-27019:27017-27019 --name mongodb mongo:5.0
```

---

### Moto (S3)

Please ensure you have Docker installed before continuing with these steps.

#### Start Moto:

[Moto](https://github.com/getmoto/moto) (MotoServer) listens on port 5000 inside the container, mapped to **4566** locally.

```
docker run -d --restart unless-stopped --name moto -p 4566:5000 motoserver/moto:5.1.18
```

#### Check the current port using

```
docker port moto
```
---

### Configure AWS credentials

On your machine run:
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
aws --endpoint-url=http://localhost:4566 s3 mb s3://digital-tariffs-local
```

Then check this URL is available in the browser:

> **http://localhost:4566/digital-tariffs-local**

**Note:** You should see some XML in your browser if successful

---

### Run required microservices
Run the following command:

`sm2 --start DIGITAL_TARIFFS`

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
