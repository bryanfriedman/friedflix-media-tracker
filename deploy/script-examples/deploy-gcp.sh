#!/bin/bash
# Assumes the `gcloud` command has been installed and configured: https://cloud.google.com/sdk/gcloud/

# If not already installed
sudo gcloud components install app-engine-java
# Now create the app
gcloud app create --region us-central

# If not already installed
sudo gcloud components install beta
# Create database instance
gcloud sql instances create friedflix-media-tracker --tier=db-n1-standard-1 --region=us-central1
# Create database
gcloud beta sql databases create friedflix --instance=friedflix-media-tracker
# Get connection info
gcloud beta sql instances describe friedflix-media-tracker | grep connectionName

# Before moving on, replace SPRING_DATASOURCE_URL in src/main/appengine/app.yaml with the output of the previous command:
#    SPRING_DATASOURCE_URL: 'jdbc:mysql://google/friedflix?cloudSqlInstance=[REPLACE_HERE]&socketFactory=com.google.cloud.sql.mysql.SocketFactory'

# Deploy app
./mvnw -DskipTests appengine:deploy