#! /bin/sh

DIRECTORY=build/libs
if [ -d "$DIRECTORY" ]; then
  echo "$DIRECTORY does exist."
  rm -rf $DIRECTORY
fi
docker rmi -f gateway-svr
gradle clean build