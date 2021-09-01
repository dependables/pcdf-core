<!-- ReadMe for the PCDFCore Kotlin Library -->

## About The Project
**PCDFCore** is a Kotlin Multiplatform Library that allows to easily handle car related data obtained through the **OBD-II interface** of modern cars, GPS or external sensors.
PCDFCore offers easy (de-)serialization from (to) the event-based JSON format **PCDF** (Portable Car Data Format).

Raw data received from the car as bit strings can be directly parsed and interpreted, according to the OBD-II standard.

## Examples

In the first example, we have sent a RPM command (0x01 0x0C) to the vehicle and received a bit string in response. We create an **OBDEvent** with the UUID of the source (e.g., the ELM327 OBD adapter), a timestamp and the received bytes.

To interpret this raw data, the function **toIntermediate** can be called on OBDEvents, which will automatically detect the command the response was sent for and interpret it according to the OBD-II standard.

The resulting event is an **RPMEvent** containing the interpreted RPM.
```scala
    // Received Response 0x41 0x0C 0x1A 0xF8
    val event = OBDEvent("00001101-0000-1000-8000-00806f9b34fb", System.nanoTime(), "410C1AF8")

    val interpretedEvent = event.toIntermediate() as RPMEvent

    val measuredRPM = interpretedEvent.rpm
```



Every **PCDFEvent** can be serialized to the JSON-based PCDF format:

```scala
    val serializedEvent = interpretedEvent.serialize()
```
which returns:
```json
    {
        "source":"00001101-0000-1000-8000-00806f9b34fb",
        "type":"OBD_RESPONSE",
        "timestamp":313398744020470,
        "data": {
            "bytes":"410C1AF8",
            "mode":1,
            "pid":12,
            "rpm":1726.0
        }
    }
```
Deserialization can be done by calling the static **fromString** method:

```scala
    val event = PCDFEvent.fromString(serializedEvent)
```

## License
Distributed under the MIT License. See **LICENSE** for more information.

## Contact 

Yannik Schnitzer - s8yaschn@stud.uni-saarland.de

Project Link: TODO
