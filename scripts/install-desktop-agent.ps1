 # Install Agent
 $CurrentDir = $PSScriptRoot;
 $Agent = Resolve-Path -Path "$CurrentDir\..\target\*.exe";
 Try {
     $InstallProcess = Start-Process -FilePath $Agent -ArgumentList "/S" -PassThru -Wait -Verb RunAs;
     $InstallProcess.WaitForExit();
     return $true;
 } Catch {return $false;}


