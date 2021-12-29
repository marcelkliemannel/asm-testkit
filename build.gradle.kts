plugins {
  java
}

group = "dev.turingcomplete"
version = "0.0.0-SNAPSHOT"

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.ow2.asm:asm:9.2")
  implementation("org.ow2.asm:asm-tree:9.2")
  implementation("org.ow2.asm:asm-util:9.2")

  implementation("org.hamcrest:hamcrest:2.2")
  implementation("org.assertj:assertj-core:3.21.0")

  testImplementation("org.jetbrains:annotations:22.0.0")
  testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
  testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.2")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.getByName<Test>("test") {
  useJUnitPlatform()
}