#!/bin/bash

echo "Starting ASSETS"

cd $WORKSPACE

sm --stop ALL
sm --cleanlogs

ASSETS_FRONTEND_VERSION=$(grep -e 'frontend.assets.version = "' conf/application.conf | cut -d '"' -f 2)

echo "Using ASSETS_FRONTEND_VERSION as $ASSETS_FRONTEND_VERSION"

sm --start ASSETS_FRONTEND -r $ASSETS_FRONTEND_VERSION --wait 60 --noprogress

if [ $? -ne 0 ]
  then
    echo "Assets front end startup failed...Exiting..."
    exit $?
fi

echo "Running functional test for help-frontend..."

cd $WORKSPACE

echo "creating TMPDIR as $TMPDIR"
mkdir -p ${TMPDIR}

echo "Start functional tests..."

if [ "$1" == "--with-publish" ]
  then
    sbt -Djava.io.tmpdir=${TMPDIR} clean validate test fun:test dist-tgz publish -Dbrowser=chrome -Dwebdriver.chrome.driver=/usr/local/bin/chromedriver
  else
    sbt -Djava.io.tmpdir=${TMPDIR} clean validate test fun:test -Dbrowser=chrome -Dwebdriver.chrome.driver=/usr/local/bin/chromedriver
fi

SBT_EXIT_CODE=`echo $?`

echo "Gracefully shutdown server..."

sm --stop ALL

exit $SBT_EXIT_CODE
