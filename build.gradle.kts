import java.net.URI

plugins {
  `java-library`
  signing
  `maven-publish`
}

group = "dev.turingcomplete"
version = "0.1.0"

repositories {
  mavenCentral()
}

dependencies {
  api("org.ow2.asm:asm:9.2")
  api("org.ow2.asm:asm-tree:9.2")
  api("org.ow2.asm:asm-util:9.2")

  api("org.hamcrest:hamcrest:2.2")
  api("org.assertj:assertj-core:3.22.0")

  testImplementation("org.jetbrains:annotations:22.0.0")
  testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
  testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.2")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.getByName<Test>("test") {
  useJUnitPlatform()
}

tasks {
  withType(Javadoc::class.java).configureEach {
    // The JavaDoc linter does not like Java code inside of
    // <pre>{@code ...}</pre> blocks.
    (options as StandardJavadocDocletOptions).addBooleanOption("Xdoclint:none", true)
  }

  val sourcesJar by creating(Jar::class) {
    group = "build"
    archiveClassifier.set("sources")
    from(sourceSets["main"].allSource)
  }

  val testsJar by creating(Jar::class) {
    dependsOn(JavaPlugin.TEST_CLASSES_TASK_NAME)
    group = "build"
    archiveClassifier.set("tests")
    from(sourceSets["test"].output)
  }

  val javaDocJar by creating(Jar::class) {
    dependsOn("javadoc")
    group = "build"
    archiveClassifier.set("javadoc")
    from(getByPath("javadoc").outputs)
  }

  artifacts {
    add("archives", sourcesJar)
    add("archives", testsJar)
    add("archives", javaDocJar)
  }
}

publishing {
  publications {
    create<MavenPublication>(project.name) {
      from(components["java"])
      setArtifacts(configurations.archives.get().allArtifacts)
    }
  }
}

/**
 * See https://docs.gradle.org/current/userguide/signing_plugin.html#sec:signatory_credentials
 *
 * The following Gradle properties must be set:
 * - signing.keyId (last 8 symbols of the key ID from 'gpg -K')
 * - signing.password
 * - signing.secretKeyRingFile ('gpg --keyring secring.gpg --export-secret-keys > ~/.gnupg/secring.gpg')
 */
signing {
  sign(publishing.publications[project.name])
}

configure<PublishingExtension> {
  publications {
    afterEvaluate {
      named<MavenPublication>(project.name) {
        pom {
          name.set("Asm Test Kit")
          description.set("A test kit to create fluent assertions for the ASM Java byte code modification framework, built on top of AssertJ.")
          url.set("https://github.com/marcelkliemannel/asm-testkit")
          developers {
            developer {
              name.set("Marcel Kliemannel")
              id.set("marcelkliemannel")
              email.set("dev@marcelkliemannel.com")
            }
          }
          licenses {
            license {
              name.set("The Apache Software License, Version 2.0")
              url.set("http://www.apache.org/licenses/LICENSE-2.0")
            }
          }
          issueManagement {
            system.set("Github")
            url.set("https://github.com/marcelkliemannel/asm-testkit/issues")
          }
          scm {
            connection.set("scm:git:git://github.com:marcelkliemannel/asm-testkit.git")
            developerConnection.set("scm:git:git://github.com:marcelkliemannel/asm-testkit.git")
            url.set("https://github.com/marcelkliemannel/asm-testkit")
          }
        }
      }
    }
  }
  repositories {
    maven {
      url = URI("https://oss.sonatype.org/service/local/staging/deploy/maven2")
      credentials {
        username = ""
        password = ""
      }
    }
  }
}