// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository 
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += Classpaths.typesafeResolver

// Use the Play sbt plugin for Play projects
addSbtPlugin("play" % "sbt-plugin" % "2.0.2")

addSbtPlugin("com.typesafe.startscript" % "xsbt-start-script-plugin" % "0.5.2")

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.0.0")

