#!/bin/bash
# Assumes the `az` CLI command has been installed and configured: https://docs.microsoft.com/en-us/cli/azure/install-azure-cli?view=azure-cli-latest

# Create a resource group.
az group create --location westus --name friedflix-media-tracker

# Create an App Service plan in STANDARD tier.
az appservice plan create --name friedflix-media-tracker --resource-group friedflix-media-tracker --sku S1

# Create a web app.
az webapp create --name friedflix-media-tracker --resource-group friedflix-media-tracker --plan friedflix-media-tracker

# Turn on Java
az webapp config set --java-version 1.8 --java-container TOMCAT --java-container-version 8.5 --resource-group friedflix-media-tracker --name friedflix-media-tracker

# Do FTP 
hostname=`az webapp deployment list-publishing-profiles --resource-group friedflix-media-tracker --name friedflix-media-tracker | grep ftp:// | awk '{print $2}' | sed -e 's/[",]//g' | sed -e 's/ftp:\/\///g' | sed -e 's/\/site\/wwwroot//g'`
username=`az webapp deployment list-publishing-profiles --resource-group friedflix-media-tracker --name friedflix-media-tracker | grep userName | awk '{print $2}' | sed -e 's/[",$]//g' | head -1`
password=`az webapp deployment list-publishing-profiles --resource-group friedflix-media-tracker --name friedflix-media-tracker | grep userPWD | awk '{print $2}' | sed -e 's/[",]//g' | head -1`
ftp "ftp://${username}:${password}@${hostname}"
cd site/wwwroot
binary
lcd target
put media-tracker-0.0.1.jar
ascii
lcd ../deploy/manifests/azure
put web.config
quit

# Create database instance
az mysql server create -l westus -g friedflix-media-tracker -n friedflix-media-tracker -u dbuser -p DBpassword1 --performance-tier Basic --compute-units 100

# Firewall rules?
ips=`az webapp show --name friedflix-media-tracker --resource-group friedflix-media-tracker | grep outbound | awk '{print $2}' | sed -e 's/["]//g' | sed -e 's/,$//g'`
counter=0
for i in $(echo $ips | sed "s/,/ /g")
do
   counter=$[counter + 1]
   az mysql server firewall-rule create -g friedflix-media-tracker -s friedflix-media-tracker -n friedflix-allow-$counter --start-ip-address $i --end-ip-address $i
done
myip=`curl ipecho.net/plain ; echo`
az mysql server firewall-rule create -g friedflix-media-tracker -s friedflix-media-tracker -n myip --start-ip-address $myip --end-ip-address $myip

# Create database
dbhost=`az mysql server list -g friedflix-media-tracker | grep fullyQualifiedDomainName | awk '{print $2}' | sed -e 's/[",]//g'`
mysql -h $dbhost -u dbuser@friedflix-media-tracker --password=DBpassword1
create database friedflix;
quit

# Set environment variables
az webapp config appsettings set --resource-group friedflix-media-tracker --name friedflix-media-tracker --settings "SPRING_DATASOURCE_URL=jdbc:mysql://${dbhost}:3306/friedflix?verifyServerCertificate=true&useSSL=true&requireSSL=false" SPRING_DATASOURCE_USERNAME=dbuser@friedflix-media-tracker SPRING_DATASOURCE_PASSWORD=DBpassword1

# Restart app
az webapp restart --resource-group friedflix-media-tracker --name friedflix-media-tracker
