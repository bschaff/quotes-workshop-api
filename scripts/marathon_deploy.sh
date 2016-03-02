#!/bin/bash

PROJECT_VERSION=${1}
ENV=${2}
MARATHON_SERVER=${3}

PROJECT_NAME=quotes-workshop-api
MARATHON_URL="http://${MARATHON_SERVER}:8080/v2/apps"

if [ ${ENV} == "dev" ]
then
  INSTANCES=1
  MEM=128
  CONSTRAINTS=""
elif [ ${ENV} == "prod" ]
then
  INSTANCES=2
  MEM=128
  CONSTRAINTS="[\"hostname\", \"GROUP_BY\", \"2\"]"
elif [ ${ENV} == "preprod" ]
then
  INSTANCES=2
  MEM=128
  CONSTRAINTS="[\"hostname\", \"GROUP_BY\", \"2\"]"
else
  echo "Env ${ENV} not supported"
  exit 1
fi

JSON="{
  \"id\": \"${PROJECT_NAME}\",
  \"healthChecks\": [
    {
      \"protocol\": \"HTTP\",
      \"portIndex\": 0,
      \"path\": \"/health\",
      \"gracePeriodSeconds\": 15,
      \"intervalSeconds\": 10,
      \"maxConsecutiveFailures\": 3
    }
  ],
  \"constraints\": [${CONSTRAINTS}],
  \"ports\": [0],
  \"instances\": ${INSTANCES},
  \"cpus\": 0.1,
  \"mem\": ${MEM},
  \"uris\": [\"http://ec2-54-236-221-63.compute-1.amazonaws.com/artifactory/libs-release/reactivecore/${PROJECT_NAME}/${PROJECT_VERSION}/${PROJECT_NAME}-${PROJECT_VERSION}-assembly.jar\"],
  \"cmd\": \"java -Xmx${MEM}m -Dserver.port=\${PORT0} -Dconfig.resource=${ENV}.conf -jar ${PROJECT_NAME}-${PROJECT_VERSION}-assembly.jar\",
  \"env\": {},
  \"upgradeStrategy\": {
    \"minimumHealthCapacity\": 1.0,
    \"maximumOverCapacity\": 1.0
  }
}"

RESPONSE=$(curl -X PUT -H "Content-Type: application/json" ${MARATHON_URL}/${PROJECT_NAME} -d "${JSON}" -s)

echo ${RESPONSE}

[[ ${RESPONSE} =~ ^\{\"version\":\".*\",\"deploymentId\":\".*\"\}$ ]]
exit $?
