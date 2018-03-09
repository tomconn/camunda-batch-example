#!/usr/bin/env bash

set -o errexit
set -o nounset
set -o pipefail

# run the eps tool to retrieve secrets. making them available to the rade application
# The SHARED_RESOURCES_NAMESPACE is an Environment Variable passed in on the Container Definition
# eval "$(sb_eps export --namespace ${SHARED_RESOURCES_NAMESPACE})"

# Run Camunda
exec java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar offers-batch-settlement.jar