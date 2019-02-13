# smitefixer

This fixes smite login issues by proxying the downloads of season and ui textures from the hwcdn.net server.

To run java 8 or later must be installed, and working from command line. (Java 8 works from release 1.1 and above)

(If you open a command prompt, and type <code>java -version</code> at least java 8 should be shown)

Then download the SmiteFixer.zip from the release tab, and unpack it somewhere and run the run.bat

If it complains that the hosts file does not contain the correct line, run it as administrator and it will add it itself.

When <code>Proxy running...</code> is showing in the console, its ready.

Run smite.

If you would rather add to the hosts file yourself, add the line <code>127.0.0.1 cds.q6u4m8x5.hwcdn.net</code> to the end of <code>C:\Windows\System32\drivers\etc\hosts</code> using an editor run as Administrator.
