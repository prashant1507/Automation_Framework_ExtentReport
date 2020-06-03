set fusionOrStation="ws"
set vmrunPath="C:\Program Files (x86)\VMware\VMware VIX\vmrun.exe"
set vmPath="E:\Virtual Machines\Windows 10 x64\Windows 10 x64.vmx"
set launchBrowserBatch="C:\Users\Shibu\Desktop\launchBrowser.bat"
set targetFile="C:\Users\Shibu\Downloads\calc.exe"
REM set targetURL="file://C:/Windows/System32/calc.exe"
set targetURL=%~1
REM set powerShell="C:\Windows\System32\WindowsPowerShell\v1.0\powershell.exe"
set snapShot="snapshotvm7"
set user="Shibu"
set password="password"
REM revert to snapshot
echo 'hello'
%vmrunPath% revertToSnapshot %vmPath% %snapShot% || echo "Failed to revert the snapshot" && exit 1
REM start the snapshot
%vmrunPath% start %vmPath% || echo "failed to start the vm" && exit 1
REM checking the ip of the machine
%vmrunPath% getGuestIPAddress %vmPath% -wait || echo "unable to fetch vm IP" && exit 1
REM verifying existence browser launch bat file 
%vmrunPath% -T %fusionOrStation% -gu %user% -gp %password% fileExistsInGuest %vmPath% %launchBrowserBatch% || exit 1
REM launch browser from batch file and passing targetURL
%vmrunPath% -T %fusionOrStation% -gu %user% -gp %password% runProgramInGuest %vmPath% -interactive -activeWindow %launchBrowserBatch% %targetURL% || echo "File to execute file" || exit 1
REM %vmrunPath% -T %fusionOrStation% -gu %user% -gp %password% runProgramInGuest %vmPath% %powerShell% "-command" "(New-Object System.Net.WebClient).DownloadFile('file://C:/Windows/System32/calc.exe','C:\Users\Shibu\Downloads\calctt.exe')" || echo "Failed to download target file" && exit 1
REM saving file exists result in a file to validate if file exists or not. If not will terminate the test case
%vmrunPath% -T %fusionOrStation% -gu %user% -gp %password% fileExistsInGuest %vmPath% %targetFile% || exit 1
REM running the file
%vmrunPath% -T %fusionOrStation% -gu %user% -gp %password% runProgramInGuest %vmPath% -interactive -activeWindow %targetFile% || echo "No successful execution" && exit 1