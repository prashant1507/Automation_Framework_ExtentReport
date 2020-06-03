Set ie = WScript.CreateObject("InternetExplorer.Application","IE_")
strWebSite = Wscript.Arguments(0)
ie.Visible = True
'Create WScript Shell Object to access filesystem.
Set WshShell = WScript.CreateObject("WScript.Shell")
WshShell.AppActivate("Internet Explorer")
ie.Navigate strWebSite
'Do While ie.Busy: Loop

'Wait for 3 seconds
WScript.Sleep 3000
WshShell.SendKeys "{F6}"
WshShell.SendKeys "{TAB}"
WshShell.SendKeys "{ENTER}"
WshShell.SendKeys "def"
