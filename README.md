[![CircleCI](https://circleci.com/gh/johnhunsley/ea-river-level-api-client.svg?style=svg)](https://circleci.com/gh/johnhunsley/ea-river-level-api-client)

[![](https://jitpack.io/v/johnhunsley/ea-river-level-api-client.svg)](https://jitpack.io/#johnhunsley/ea-river-level-api-client)

# ea-river-level-api-client
An Apache HTTP Client 4 implementation for the UK Gov EA River Level HTTP API

The EA provides an HTTP JSON API service for river level monitoring stations in the UK - 

https://environment.data.gov.uk/flood-monitoring/id/stations/

An example station river level data can be read from -

https://environment.data.gov.uk/flood-monitoring/id/stations/2077/readings?latest

The API docment can be found here - https://environment.data.gov.uk/flood-monitoring/doc/reference

This client api provides a serializable model for both request and response for data from a given station id using Apache HTTP 4 Client. The first release of this client will read the latest, today or values from a given date to now, of the defined station id. Where id is a path variable e.g. 2077 (Welsh Bridge - River Severn). 
