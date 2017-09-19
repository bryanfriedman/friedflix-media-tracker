#!/bin/bash
# Assumes the `aws` and `eb` commands have been installed and configured: http://docs.aws.amazon.com/elasticbeanstalk/latest/dg/eb-cli3.html

# Initialize current directory with .elasticbeanstalk directory and supporting files
eb init -p Java --region us-east-2

# Prepend the deploy info to the .elasticbeanstalk/config.yml file
echo '  artifact: target/media-tracker-0.0.1.jar' | cat - .elasticbeanstalk/config.yml > temp && mv temp .elasticbeanstalk/config.yml
echo 'deploy:' | cat - .elasticbeanstalk/config.yml > temp && mv temp .elasticbeanstalk/config.yml

# Create application with database instance
eb create friedflix-media-tracker -db -db.engine mysql -db.user dbuser -db.pass dbpassword

# Two things to do here before moving on:
# 1) Copy the RDS endpoint from the console and paste as part of the SPRING_DATASOURCE_URL value below. There might be a way to get this from the CLI, but I didn't figure it out.
# 2) Log in to the RDS instance and run `create database friedflix`. Once you have the endpoint from step 1, this could be scripted with CLI and mysql client.

# Set environment variables
eb setenv SPRING_DATASOURCE_URL=jdbc:mysql://[PASTE_ENDPOINT_HERE]:3306/friedflix SPRING_DATASOURCE_USERNAME=dbuser SPRING_DATASOURCE_PASSWORD=dbpassword PORT=8080