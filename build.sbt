name := "bonecp-timeout-reproduce"

version := "1.0"

libraryDependencies += "com.jolbox" % "bonecp" % "0.7.1.RELEASE" withSources()

libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.36"

libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.12"
