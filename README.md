# Enable HTTPS support for Spring boot server
1. generate pkcs12 keystore with selfsigned certificate

_keytool -genkeypair -alias mykey -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore R:\ranjit-intellij-ws\spring\role-base-access-with-jwt\src\main\resources\mykeystore.p12 -validity 3650 -storepass changeit -keypass changeit_

2. add _mykeystore.p12_ file to _maim/java/resources_
3. add below in application.properties
   _server.ssl.key-store=classpath:mykeystore.p12
   server.ssl.key-store-password=changeit
   server.ssl.key-alias=mykey
   server.ssl.key-store-type=PKCS12_