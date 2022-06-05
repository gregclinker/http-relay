****Http Relay****

A java application that runs on a cron timer, pulling data from a source http endpoint & pushing it to a sink http endpoint.

To build a runnable jar:

`mvn clean install`

To start the cron:

`java -jar http-relay-0.1.jar`

where config.yaml looks like:

```yaml
---
verbose: true
repeat: 1
interval: 1
timeout: 20
namespace: "test namespace"
source:
  name: "source"
  url: "http://source:5556/"
  method: "GET"
sink:
  name: "sink"
  url: "https://sink:8088/"
  method: "POST"
  headers:
    - name: "Authorization"
      value: "Splunk hggh-hghg-hghg-hghgh"

```