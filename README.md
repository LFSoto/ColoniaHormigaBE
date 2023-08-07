# Sub sistema Reina
***
## Pasos para compilar
* Instalar [Apache Maven](https://maven.apache.org/install.html), usando las siguientes versiones como referencia:

```
mvn --version
Apache Maven 3.9.3 (21122926829f1ead511c958d89bd2f672198ae9f)
Maven home: C:\Users\DiegoCabezas\GIT_REPOSITORY\GIT_HUB\apache-maven-3.9.3
Java version: 20.0.2, vendor: Oracle Corporation, runtime: C:\Program Files\Java\jdk-20
Default locale: en_US, platform encoding: UTF-8
OS name: "windows 11", version: "10.0", arch: "amd64", family: "windows"
```
* Compilar el codigo desde la raiz del proyecto ejecutando `mvn package`
* El artefacto quedará en `/target/reina-0.0.1-SNAPSHOT.jar`

***
## Pasos para ejecutar la aplicación desde consola
* Ejecute el comando `java -jar <path_to_jar>`, por ejemplo `java -jar ColoniaHormigaBE/target/reina-0.0.1-SNAPSHOT.jar`

```

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.1.1)

2023-08-06T23:44:09.937-06:00  INFO 18692 --- [           main] com.hormiguero.reina.ReinaApplication    : Starting ReinaApplication v0.0.1-SNAPSHOT using Java 20.0.2 with PID 18692 (C:\Users\DiegoCabezas\GIT_REPOSITORY\GIT_HUB\ColoniaHormigaBE\target\reina-0.0.1-SNAPSHOT.jar started by DiegoCabezas in C:\Users\DiegoCabezas\GIT_REPOSITORY\GIT_HUB\ColoniaHormigaBE)
2023-08-06T23:44:09.941-06:00  INFO 18692 --- [           main] com.hormiguero.reina.ReinaApplication    : No active profile set, falling back to 1 default profile: "default"
2023-08-06T23:44:11.417-06:00  INFO 18692 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data MongoDB repositories in DEFAULT mode.
2023-08-06T23:44:11.524-06:00  INFO 18692 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 95 ms. Found 1 MongoDB repository interfaces.
2023-08-06T23:44:12.574-06:00  INFO 18692 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
2023-08-06T23:44:12.594-06:00  INFO 18692 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2023-08-06T23:44:12.595-06:00  INFO 18692 --- [           main] o.apache.catalina.core.StandardEngine    : Starting Servlet engine: [Apache Tomcat/10.1.10]
2023-08-06T23:44:12.757-06:00  INFO 18692 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2023-08-06T23:44:12.760-06:00  INFO 18692 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 2695 ms
2023-08-06T23:44:13.436-06:00  INFO 18692 --- [           main] org.mongodb.driver.client                : MongoClient with metadata {"driver": {"name": "mongo-java-driver|sync|spring-boot", "version": "4.9.1"}, "os": {"type": "Windows", "name": "Windows 11", "architecture": "amd64", "version": "10.0"}, "platform": "Java/Oracle Corporation/20.0.2+9-78"} created with settings MongoClientSettings{readPreference=primary, writeConcern=WriteConcern{w=majority, wTimeout=null ms, journal=null}, retryWrites=true, retryReads=true, readConcern=ReadConcern{level=null}, credential=MongoCredential{mechanism=null, userName='queen', source='admin', password=<hidden>, mechanismProperties=<hidden>}, streamFactoryFactory=null, commandListeners=[], codecRegistry=ProvidersCodecRegistry{codecProviders=[ValueCodecProvider{}, BsonValueCodecProvider{}, DBRefCodecProvider{}, DBObjectCodecProvider{}, DocumentCodecProvider{}, CollectionCodecProvider{}, IterableCodecProvider{}, MapCodecProvider{}, GeoJsonCodecProvider{}, GridFSFileCodecProvider{}, Jsr310CodecProvider{}, JsonObjectCodecProvider{}, BsonCodecProvider{}, EnumCodecProvider{}, com.mongodb.client.model.mql.ExpressionCodecProvider@7e3f95fe, com.mongodb.Jep395RecordCodecProvider@34625ccd]}, loggerSettings=LoggerSettings{maxDocumentLength=1000}, clusterSettings={hosts=[127.0.0.1:27017], srvHost=hormigareina.twxfm7c.mongodb.net, srvServiceName=mongodb, mode=MULTIPLE, requiredClusterType=REPLICA_SET, requiredReplicaSetName='atlas-cg3nk4-shard-0', serverSelector='null', clusterListeners='[]', serverSelectionTimeout='30000 ms', localThreshold='30000 ms'}, socketSettings=SocketSettings{connectTimeoutMS=10000, readTimeoutMS=0, receiveBufferSize=0, sendBufferSize=0}, heartbeatSocketSettings=SocketSettings{connectTimeoutMS=10000, readTimeoutMS=10000, receiveBufferSize=0, sendBufferSize=0}, connectionPoolSettings=ConnectionPoolSettings{maxSize=100, minSize=0, maxWaitTimeMS=120000, maxConnectionLifeTimeMS=0, maxConnectionIdleTimeMS=0, maintenanceInitialDelayMS=0, maintenanceFrequencyMS=60000, connectionPoolListeners=[], maxConnecting=2}, serverSettings=ServerSettings{heartbeatFrequencyMS=10000, minHeartbeatFrequencyMS=500, serverListeners='[]', serverMonitorListeners='[]'}, sslSettings=SslSettings{enabled=true, invalidHostNameAllowed=false, context=null}, applicationName='null', compressorList=[], uuidRepresentation=JAVA_LEGACY, serverApi=null, autoEncryptionSettings=null, contextProvider=null}
2023-08-06T23:44:13.720-06:00  INFO 18692 --- [m7c.mongodb.net] org.mongodb.driver.cluster               : Adding discovered server ac-4ey4qwb-shard-00-02.twxfm7c.mongodb.net:27017 to client view of cluster
2023-08-06T23:44:13.784-06:00  INFO 18692 --- [m7c.mongodb.net] org.mongodb.driver.cluster               : Adding discovered server ac-4ey4qwb-shard-00-00.twxfm7c.mongodb.net:27017 to client view of cluster
2023-08-06T23:44:13.786-06:00  INFO 18692 --- [m7c.mongodb.net] org.mongodb.driver.cluster               : Adding discovered server ac-4ey4qwb-shard-00-01.twxfm7c.mongodb.net:27017 to client view of cluster
2023-08-06T23:44:15.268-06:00  INFO 18692 --- [ngodb.net:27017] org.mongodb.driver.cluster               : Monitor thread successfully connected to server with description ServerDescription{address=ac-4ey4qwb-shard-00-00.twxfm7c.mongodb.net:27017, type=REPLICA_SET_SECONDARY, state=CONNECTED, ok=true, minWireVersion=0, maxWireVersion=17, maxDocumentSize=16777216, logicalSessionTimeoutMinutes=30, roundTripTimeNanos=794626300, setName='atlas-cg3nk4-shard-0', canonicalAddress=ac-4ey4qwb-shard-00-00.twxfm7c.mongodb.net:27017, hosts=[ac-4ey4qwb-shard-00-00.twxfm7c.mongodb.net:27017, ac-4ey4qwb-shard-00-01.twxfm7c.mongodb.net:27017, ac-4ey4qwb-shard-00-02.twxfm7c.mongodb.net:27017], passives=[], arbiters=[], primary='ac-4ey4qwb-shard-00-02.twxfm7c.mongodb.net:27017', tagSet=TagSet{[Tag{name='nodeType', value='ELECTABLE'}, Tag{name='provider', value='AWS'}, Tag{name='region', value='US_EAST_1'}, Tag{name='workloadType', value='OPERATIONAL'}]}, electionId=null, setVersion=7, topologyVersion=TopologyVersion{processId=64ce9f334d0d1117505b97a7, counter=4}, lastWriteDate=Sun Aug 06 23:44:14 CST 2023, lastUpdateTimeNanos=101813175137500}
2023-08-06T23:44:15.268-06:00  INFO 18692 --- [ngodb.net:27017] org.mongodb.driver.cluster               : Monitor thread successfully connected to server with description ServerDescription{address=ac-4ey4qwb-shard-00-02.twxfm7c.mongodb.net:27017, type=REPLICA_SET_PRIMARY, state=CONNECTED, ok=true, minWireVersion=0, maxWireVersion=17, maxDocumentSize=16777216, logicalSessionTimeoutMinutes=30, roundTripTimeNanos=794626300, setName='atlas-cg3nk4-shard-0', canonicalAddress=ac-4ey4qwb-shard-00-02.twxfm7c.mongodb.net:27017, hosts=[ac-4ey4qwb-shard-00-00.twxfm7c.mongodb.net:27017, ac-4ey4qwb-shard-00-01.twxfm7c.mongodb.net:27017, ac-4ey4qwb-shard-00-02.twxfm7c.mongodb.net:27017], passives=[], arbiters=[], primary='ac-4ey4qwb-shard-00-02.twxfm7c.mongodb.net:27017', tagSet=TagSet{[Tag{name='nodeType', value='ELECTABLE'}, Tag{name='provider', value='AWS'}, Tag{name='region', value='US_EAST_1'}, Tag{name='workloadType', value='OPERATIONAL'}]}, electionId=7fffffff00000000000000c0, setVersion=7, topologyVersion=TopologyVersion{processId=64cea025ccfb299e8ff1ff42, counter=6}, lastWriteDate=Sun Aug 06 23:44:14 CST 2023, lastUpdateTimeNanos=101813175137200}
2023-08-06T23:44:15.268-06:00  INFO 18692 --- [ngodb.net:27017] org.mongodb.driver.cluster               : Monitor thread successfully connected to server with description ServerDescription{address=ac-4ey4qwb-shard-00-01.twxfm7c.mongodb.net:27017, type=REPLICA_SET_SECONDARY, state=CONNECTED, ok=true, minWireVersion=0, maxWireVersion=17, maxDocumentSize=16777216, logicalSessionTimeoutMinutes=30, roundTripTimeNanos=794558500, setName='atlas-cg3nk4-shard-0', canonicalAddress=ac-4ey4qwb-shard-00-01.twxfm7c.mongodb.net:27017, hosts=[ac-4ey4qwb-shard-00-00.twxfm7c.mongodb.net:27017, ac-4ey4qwb-shard-00-01.twxfm7c.mongodb.net:27017, ac-4ey4qwb-shard-00-02.twxfm7c.mongodb.net:27017], passives=[], arbiters=[], primary='ac-4ey4qwb-shard-00-02.twxfm7c.mongodb.net:27017', tagSet=TagSet{[Tag{name='nodeType', value='ELECTABLE'}, Tag{name='provider', value='AWS'}, Tag{name='region', value='US_EAST_1'}, Tag{name='workloadType', value='OPERATIONAL'}]}, electionId=null, setVersion=7, topologyVersion=TopologyVersion{processId=64cea13768eeca759fd6d2d8, counter=3}, lastWriteDate=Sun Aug 06 23:44:14 CST 2023, lastUpdateTimeNanos=101813175137200}
2023-08-06T23:44:15.277-06:00  INFO 18692 --- [ngodb.net:27017] org.mongodb.driver.cluster               : Discovered replica set primary ac-4ey4qwb-shard-00-02.twxfm7c.mongodb.net:27017 with max election id 7fffffff00000000000000c0 and max set version 7
2023-08-06T23:44:15.608-06:00  INFO 18692 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2023-08-06T23:44:15.647-06:00  INFO 18692 --- [           main] com.hormiguero.reina.ReinaApplication    : Started ReinaApplication in 6.5 seconds (process running for 7.506)

```