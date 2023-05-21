plugins {
  `java-library`
  id("io.freefair.lombok") version "8.0.1"
}

repositories {
  mavenCentral()
}

dependencies {
  testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
}

tasks {
  test {
    useJUnitPlatform()
  }
}