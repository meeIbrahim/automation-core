# Installing latest available google chrome
md -Path $env:temp\chromeinstall -erroraction SilentlyContinue | Out-Null
$Download = join-path $env:temp\chromeinstall chrome_installer.exe
Invoke-WebRequest 'http://dl.google.com/chrome/install/latest/chrome_installer.exe'  -OutFile $Download
Invoke-Expression "$Download"

# Get chrome version
$INSTALLED = Get-ItemProperty HKLM:\Software\Microsoft\Windows\CurrentVersion\Uninstall\* |  Select-Object DisplayName, DisplayVersion, Publisher, InstallDate
$INSTALLED += Get-ItemProperty HKLM:\Software\Wow6432Node\Microsoft\Windows\CurrentVersion\Uninstall\* | Select-Object DisplayName, DisplayVersion, Publisher, InstallDate
$CHROME_VERSION = $INSTALLED | ?{ $_.DisplayName -match 'chrome' } | Select-Object "DisplayVersion" | Format-Table -HideTableHeaders | Out-String
$CHROME_VERSION = $CHROME_VERSION.Trim()

# Extracting latest chrome driver version based on chrome version
$VERSION_0=$CHROME_VERSION.split(".")[0]
$VERSION_1=$CHROME_VERSION.split(".")[1]
$VERSION_2=$CHROME_VERSION.split(".")[2]
$CHROME_DRIVER_VERSION=$(Invoke-RestMethod "https://chromedriver.storage.googleapis.com/LATEST_RELEASE_${VERSION_0}.${VERSION_1}.${VERSION_2}")

# Printing out versions
Write-Host "Chrome Version:" $CHROME_VERSION
Write-Host "Chrome Driver Version:" $CHROME_DRIVER_VERSION

# Downloading and installing chrome driver
Invoke-WebRequest https://chromedriver.storage.googleapis.com/$CHROME_DRIVER_VERSION/chromedriver_win32.zip -OutFile chromedriver_win32.zip
Expand-Archive -LiteralPath chromedriver_win32.zip -DestinationPath .\src\test\resources\webdriver\windows -Force
