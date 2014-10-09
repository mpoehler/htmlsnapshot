#!/bin/bash

# this will create a running instance on google cloud
# this script assumes, that gcloud is installed

PROJECTID=data-concord-550
INSTANCENAME=htmlsnapshot-3
ZONE=europe-west1-a

# set default project
gcloud config set project $PROJECTID

# create instance
gcloud compute instances create $INSTANCENAME --image debian-7 --zone $ZONE --metadata-from-file startup-script=startupscript

# determine external IP
EXTERNALIP=`gcloud compute instances describe $INSTANCENAME --zone $ZONE | grep natIP`
EXTERNALIP=${EXTERNALIP:11}

echo "INFO: remote IP is $EXTERNALIP"

# add firewall rule to enable http
gcloud compute firewall-rules create allow-http --description "Incoming http allowed." --allow tcp:8080

# delete instance
echo "INFO: command to ssh into instance: 'gcloud compute ssh $INSTANCENAME --zone $ZONE'"
echo "INFO: command to delete instance: 'gcloud compute instances delete $INSTANCENAME --zone $ZONE'"
