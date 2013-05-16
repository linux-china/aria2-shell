Aria2 Shell
===========================================
Aria2 is a lightweight multi-protocol & multi-source command-line download utility.
It supports HTTP/HTTPS, FTP, BitTorrent and Metalink.
Aria2 can be manipulated via built-in JSON-RPC and XML-RPC interfaces.
Aria2 shell is command line tool to interact with running aria by XML-RPC

### Aria2 xml-rpc daemon
create a file named as aria2.conf with following code:

      enable-rpc=true
      rpc-allow-origin-all=true
      log-level=debug
      log=/tmp/aria.log
      dir=/tmp
      daemon=true
Then running aria2c --conf-path ./aria2.conf
You can running by command line:

      aria2c --enable-rpc --rpc-allow-origin-all --log-level debug -l /tmp/aria.log -d /tmp  -D

### xml-rpc
Aria2 shell use apache xml-rpc package to interact with aria xml-rpc service.

### How to debug app

    java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar target/aria2-shell-1.0.0-SNAPSHOT.jar

### RoadMap

*  display queue information: Waiting, Running and Done
*  print pretty gid status
*  start aria2c from java
*  Web Aria2 with bootstrap UI
