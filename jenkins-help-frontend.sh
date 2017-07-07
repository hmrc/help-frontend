#!/bin/bash

cd $WORKSPACE
rm -rf service-manager-config
git clone git@github.tools.tax.service.gov.uk:HMRC/service-manager-config.git

echo "Starting ASSETS"

sm --stop ALL
sm --cleanlogs

ASSETS_FRONTEND_VERSION=$(grep -e 'frontend.assets.version = "' conf/application.conf | cut -d '"' -f 2)

echo "Using ASSETS_FRONTEND_VERSION as $ASSETS_FRONTEND_VERSION"

sm --start ASSETS_FRONTEND -r $ASSETS_FRONTEND_VERSION --wait 60 --noprogress

echo "Running functional test for help-frontend..."

cd $WORKSPACE

echo "creating TMPDIR as $TMPDIR"
mkdir -p ${TMPDIR}

echo "Start functional tests..."

sbt -Djava.io.tmpdir=${TMPDIR} -Dbrowser=chrome clean validate test fun:test dist-tgz publish -Dwebdriver.chrome.driver=/usr/local/bin/chromedriver

SBT_EXIT_CODE=`echo $?`

echo "Gracefully shutdown server..."

sm --stop ALL

exit $SBT_EXIT_CODE
