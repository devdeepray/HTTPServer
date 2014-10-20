@echo off

rem CGI headers
echo Content-Type: text/html
echo.

rem CGI Body
echo ^<HTML^>^<BODY^>
echo ^<PRE^>Your environment variables are:
set
echo ^</PRE^>^</BODY^>^</HTML^>