# smitefixer

Install Java 11.

Download the jar from release.

Put the jar in a folder somewhere.

Create a file "run.bat" next to the jar file.

@echo off
cd "PATH-TO-DIRECTORY-WHERE-JAR-IS-LOCATED"
"PATH TO JAVA\java.exe" -jar SmiteFixer-1.0-SNAPSHOT.jar

Now edit "C:\Windows\System32\drivers\etc\hosts"

And append the line:

127.0.0.1 cds.q6u4m8x5.hwcdn.net

to the end of hosts file and save. (Requires the editor to be opened as administrator)

Double click run.bat

In the terminal window should be:

"Proxy running..."

Now start smite and enjoy.

For systems other than windows, the jar should still work, but some other means of redirecting
cds.q6u4m8x5.hwcdn.net to 127.0.0.1 should be used, instead of the hosts file in windows dir.
