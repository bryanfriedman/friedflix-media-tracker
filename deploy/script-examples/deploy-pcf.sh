#!/bin/bash
# Assumes the `cf` CLI command has been installed and configured: https://docs.cloudfoundry.org/cf-cli/install-go-cli.html

cf create-service cleardb spark friedflix-db
cf push
