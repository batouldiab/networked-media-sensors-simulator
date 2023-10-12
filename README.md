# networked-media-sensors-simulator
The project idea revolves around the development of a distributed system for managing sensors and data transmission. The system is designed to handle communication protocols, data transmission, and supervision within a network of interconnected sensors, servers, and a supervisor. Here's a summary of the key components and functionalities described in the RFC files:

## 1. Communication Protocol (RFC0001.md)
### Abstract
Describes a protocol for exchanging data between sensors of different tiers and same tiers in the network.

### Architecture
- **Sink:** Main Request Handler
- **TIER 1 and TIER 2 sensors**

### Protocol Specification
Includes details on routing, request/response handling, and packet structure.

## 2. Data Transmission Protocol (RFC0002.md)
### Abstract
Regulates communication between a data server and sensors, specifying data format and transmission rules.

### Architecture
- **Data Server**
- **Tier 1 and Tier 2 Sensors**

### Protocol Specification
Covers operation, packet structures for streaming requests, data streaming, change requests, and "still alive" packets.

## 3. Supervision Protocol (RFC0003.md)
### Abstract
Describes a protocol for exchanging data between the supervisor server, sensors, and sink.

### Architecture
- **Supervisor:** Sensor Managing Server
- **Sink:** Base Request Managing Server
- **Sensors**

### Protocol Specification
Explains operations like sensor registration, handling unavailable zones, backup management, and packet structure for sink and sensor registration.

---

**Conclusion:**
The project aims to create a robust and flexible system for managing distributed sensors, enabling efficient communication, data transmission, and supervision. The protocols defined in the RFC files provide a foundation for implementing the communication and coordination aspects of the system. Key features include dynamic sensor allocation, data streaming, fault tolerance through backup mechanisms, and efficient request handling.
