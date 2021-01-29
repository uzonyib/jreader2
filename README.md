# jReader 2.0 on Google App Engine Standard with Java 11

## Deploying

```bash
gcloud app deploy
```

To view the app, use this command:
```
gcloud app browse
```
Or navigate to `https://<project-id>.appspot.com`.

## Setup

```bash
gcloud tasks queues create refresh-all-queue
```
