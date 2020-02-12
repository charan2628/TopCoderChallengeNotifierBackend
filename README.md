# TopCoder Challenge Notifier Backend

[![License](http://img.shields.io/:license-mit-blue.svg?style=flat-square)](http://badges.mit-license.org)

Sends notifications to users at their specified time through email, by filtering out previously notified challenges and filtering in by specified tags(Ex: NodeJs, Java) from TopCoder RSS Feed.

TRY IT OUT [https://custom-built.dev/app/tpcn](https://custom-built.dev/app/tpcn)

**Framework**: Spring Boot.<br>
**Databse**: MongoDB.

## How does it schedule

* On app start up, a [notification task](./src/main/java/com/app/scheduler/ChallengeNotificationScheduler.java#L61) is scheduled at a fixed rate, set through `schedule_rate` property.
* This will at each invocation divides the `schedule_rate` into equal sections, set through `schedule_sections` property.
* For each section it schedules an [task](./src/main/java/com/app/scheduler/ChallengeNotificationScheduler.java#L82) using `org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler`.
* These tasks wake up at their intervals and fetch all users scheduled in this interval from the database and send notifications to them through their emails.
### Note:
  * The variance between user scheduled time and actual notification time can be tuned by adjusting `schedule_sections` property.
  * For example: If a variance of +/- 10 min is acceptable, then you can set `schedule_rate` as 1800000(30 min in ms) and `schedule_sections` as 3.
  * `schedule_sections` property should be inline with `async_executor` max pool size in [AppConfig](./src/main/java/com/app/config/AppConfig.java).

## Authentication
* This app uses JWT access tokens to identify users between requests, signed using SHA256withECDSA.
* [Implementation](./src/main/java/com/app/service/AccessTokenService.java#L46) of JWT access token authentication is based on [RFC7519](https://tools.ietf.org/html/rfc7519).

## Admin Endpoint
* App has admin endpoint which can be accessed by admin users to get error log and summary of successful/un-successful messages.
* When exceptions are raised in the app, they are gracefully handled and details are asynchronously logged in db.   

## Setup
* Install and start mongodb, and update `db.host` and `db.port` properties in `application.properties` file.
* Create 256 bit EC key and store it in JKS format.
```bash
$ keytool -genkeypair -keyalg EC -keysize 256 -sigalg SHA256withECDSA -storetype JKS -keystore test.jks -alias test
```
* Update keystore alias, file and password in `JKS_KEYSTORE_ALIAS`, `JKS_KEYSTORE_FILE` and `JKS_KEYSTORE_PASSWORD` properties in `application.properties` file or as ENV variables.
* App uses Gmail STMP server to send mail, please provide Gmail credentials as `APP_SENDER_MAIL` and `APP_SENDER_MAIL_PASSWORD` ENV variables.
* Update `schedule_rate` in milliseconds and `schedule_sections` properties in `application.properties` to your choice.
* Set the app log level by `logging.level.com.app` property.
* Run the db init scripts in [build-scripts/mongodb](./build-scripts/mongodb) folder
```bash
$ cd build-scripts/mongodb
$ mongo db-colls-test-init.js
$ mongo db-colls-init.js
```  
* [db-colls-test-init.js](./build-scripts/mongodb/db-colls-test-init.js) creates collections for unit test cases.
* Build the app using `mvn install` command and run the jar file by providing `LOG_HOME` system property to store logs or run by AppRunner main method from your IDE.
* To run as docker container `cd` into build-scripts/server paste the jar previously built into this folder and from build folder run `build.sh` by providing three arguments:
  * path to store mongodb database data
  * path to store logs
  * app version

Example
```bash
$ .build.sh /var/app/tpcn/db /var/app/tpcn/logs 2.3
```
* Import postman collection and environment in `POST_MAN` folder to check and get info about API.

## Architecture
* App main root package is `com.app` which contains `AppRunner` which initializes the app.
* Sub packages `com.app.config` for app configuration, `com.app.controller` for app REST controllers, `com.app.controlleradvice` for controller advices, `com.app.converters` for http message converters, `com.app.dao` for app data access objects, `com.app.exception` for app exception classes, `com.app.interceptors` for app http interceptors, `com.app.model` for app models, `com.app.notifier` for app user notifier, `com.app.scheduler` for app scheduler, `com.app.service` for app services, `com.app.util` for app util classes.

## Implementation
* [AccessTokenService](./src/main/java/com/app/service/AccessTokenService.java) creates and verifies JWT access tokens.
* [StatusService](./src/main/java/com/app/service/StatusService.java) contains async methods to log successful/un-successful events.
* [ChallengeNotificationScheduler](./src/main/java/com/app/scheduler/ChallengeNotificationScheduler.java) schedules notifications at fixed rate.
* [ChallengeNotifier](./src/main/java/com/app/notifier/ChallengeNotifier.java) notifies new challenges by filtering out old challenges and filtering in by tags.
* [AuthInterceptor](./src/main/java/com/app/interceptors/AuthInterceptor.java) verifies the access token of incoming requests.

## TODO
* Add option to change challenge type to develop/design.
* Make build script more interactive and build script for windows.
