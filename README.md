Aria Shell
===========================================
aria2 is a lightweight multi-protocol & multi-source command-line download utility.
It supports HTTP/HTTPS, FTP, BitTorrent and Metalink.
aria2 can be manipulated via built-in JSON-RPC and XML-RPC interfaces.
aria shell is command line tool to interact with running aria by XML-RPC

### aria xml-rpc daemon

      aria2c --enable-rpc --rpc-allow-origin-all --log-level debug -l /tmp/aria.log -d /tmp -c -D
