Aria Shell
===========================================
aria2 is a lightweight multi-protocol & multi-source command-line download utility.
It supports HTTP/HTTPS, FTP, BitTorrent and Metalink.
aria2 can be manipulated via built-in JSON-RPC and XML-RPC interfaces.
aria shell is command line tool to interact with running aria by XML-RPC

### aria xml-rpc daemon

      aria2c --enable-rpc --rpc-allow-origin-all --log-level debug -l /tmp/aria.log -d /tmp -c -D

### xml-rpc
aria shell use apache xml-rpc package to interact with aria xml-rpc service.

### How to debug app

    java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar target/aria2-shell-1.0.0-SNAPSHOT.jar
