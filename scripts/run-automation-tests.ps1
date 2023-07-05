$SUITES_LIST_PATH=$args[0]
$ENV=$args[1]
$DOMAIN=$args[2]
$BUCKET=$args[3]

$CURRENT_TIME = Get-Date -format "dd-MM-yyyy-HH-mm-ss-tt"

foreach($SUITE in Get-Content $SUITES_LIST_PATH) {

    # Setting time and archive name for report
    # $TIME = Get-Date -format "dd-MM-yyyy-HH-mm-ss-tt"
    $archive_name = "report-$ENV-$SUITE.zip"

    # Running tests
    Write-Output "Running Suite: $SUITE"
#     mvn clean verify -Dtest="$SUITE" -D"environments.default.env"=$ENV -D"environments.default.domain"="$DOMAIN"
    mvn clean verify -Dtest="$SUITE" -D"environments.default.domain"="$DOMAIN"

    # Creating results archive and uploading to S3
    Compress-Archive -Path "target/site/serenity" -DestinationPath "./$archive_name"
    aws s3 cp ./$archive_name s3://$BUCKET/web/$ENV/web-automation-report-$CURRENT_TIME/$archive_name --acl public-read
}
