#!/bin/bash          
#/Users/soma/Dropbox/dev/fadecandy/bin/fcserver-osx &
/Users/soma/Dropbox/dev/openpixelcontrol/bin/gl_server -l /Users/soma/Dropbox/dev/openpixelcontrol/layouts/tycho.json &
java -jar -Djava.library.path=/Users/soma/Dropbox/dev/Processing/libraries/Syphon/library Tycho_Test_Export.jar
