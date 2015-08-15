name := "bonecp-timeout-reproduce"

version := "1.0"

libraryDependencies += "com.jolbox" % "bonecp" % "0.7.1.RELEASE" withSources()

//libraryDependencies += "com.jolbox" % "bonecp" % "0.8.0.RELEASE" withSources()

libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.12"

libraryDependencies += "com.h2database" % "h2" % "1.4.188"
