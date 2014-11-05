#!/usr/bin/python
import sys
data = sys.stdin.readline()
data = data.split("&")
first_name=data[0].split("=")[1]
last_name=data[1].split("=")[1]
print "Content-type:text/html\r\n\r\n"
print "<html>"
print "<head>"
print "<title>Hello - Second CGI Program</title>"
print "</head>"
print "<body>"
print "<h2>Hello %s %s</h2>" % (first_name, last_name)
print "</body>"
print "</html>"
