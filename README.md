# networked-media-sensors-simulator
The project idea revolves around the development of a distributed system for managing sensors and data transmission where sensors are organized into different zones and managed by supervisors and sinks. The system is designed to handle communication protocols, data transmission, and supervision within a network of interconnected sensors, servers, and a supervisor. The system is designed to operate over a LAN or WIFI network. Here's a summary of the key components and functionalities described in the RFC files and a basic guide to setting up and running the project:

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

## Prerequisites

- Java Development Kit (JDK) installed on all machines.
- RMI (Remote Method Invocation) configured and enabled.
- Network connectivity between the Supervisor, Sink, and Sensors.

## Getting Started

1. **Clone the Repository:**

```shell
git clone <repository-url>
cd distributed-sensor-network/src
```


2. **Compile the Code:**

```shell
javac *.java
```

## Running the Components

### 1. Start the Supervisor

- Run the Supervisor on a machine that will coordinate the sensor network.

```shell
java Supervisor
```

The Supervisor will bind to the RMI registry and wait for Sink and Sensor registrations.

### 2. Start the Sink

- Run the Sink on a machine that will collect and process data from sensors.
```shell
java Sink
```

The Sink will register itself with the Supervisor and wait for zone requests.

### 3. Start the Sensors

- Run the Sensor(s) on individual machines, specifying the Supervisor's IP address and port as arguments.

```shell
java Sensor <supervisor-ip> <supervisor-port>
```

Replace `<supervisor-ip>` and `<supervisor-port>` with the IP address and port where the Supervisor is running.

## Usage

- Once all components are running, the sensors will automatically register with the Supervisor.
- The Sink can request data from specific zones, and sensors covering those zones will respond with data.

## Additional Notes

- Ensure proper network configuration to allow communication between Supervisor, Sink, and Sensors.
- Adjust the IP addresses and ports in the code if necessary to match your network setup.



---
**Conclusion:**
The project aims to create a robust and flexible system for managing distributed sensors, enabling efficient communication, data transmission, and supervision. The protocols defined in the RFC files provide a foundation for implementing the communication and coordination aspects of the system. Key features include dynamic sensor allocation, data streaming, fault tolerance through backup mechanisms, and efficient request handling.
