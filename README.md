# jReader 2.0 on Google App Engine Standard with Java 11

## Prerequisites
Google Cloud SDK

## Build
```bash
mvn clean install
```

## Running locally
```bash
mvn spring-boot:run -Dspring.profiles.active=dev -DDATASTORE_EMULATOR_HOST=localhost:<port> -DDATASTORE_PROJECT_ID=uzonyib-jreader
```

### Starting the local datastore
```bash
gcloud beta emulators datastore start --data-dir=.
```

## Deployment
```bash
gcloud app deploy
```

To view the app, use:
```
gcloud app browse
```
or navigate to `https://<project-id>.appspot.com`.

## Google Cloud setup

### Deploying cron jobs
```bash
gcloud app deploy src/main/appengine/cron.yaml
```

### Creating tasks
```bash
gcloud tasks queues create refresh-all-queue
gcloud tasks queues create refresh-feed-queue
gcloud tasks queues create cleanup-posts-queue
gcloud tasks queues create cleanup-posts-for-user-queue
```

### Deploying Datastore indexes
```bash
gcloud datastore indexes create src/main/appengine/index.yaml
```

## Frontend

### Prerequisites
Node.js
Angular
```bash
npm install -g @angular/cli
```

### Build
```bash
npm run build
```
or
```bash
ng build --prod
```

### Running
or
```bash
ng serve --open
```