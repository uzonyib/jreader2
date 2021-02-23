# jReader 2.0 on Google App Engine Standard with Java 11

## Deployment
```bash
gcloud app deploy
```

To view the app, use this command:
```
gcloud app browse
```
Or navigate to `https://<project-id>.appspot.com`.

## Google Cloud setup

### Tasks
```bash
gcloud tasks queues create refresh-all-queue
gcloud tasks queues create refresh-feed-queue
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