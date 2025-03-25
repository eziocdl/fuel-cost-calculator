plugins {
    id("java")
    id("application") // Se você estiver criando uma aplicação executável
}

group = "io.github.eziocdl"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}


dependencies {
    // Dependências principais
    implementation("com.google.guava:guava:32.0.1-android")
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("org.slf4j:slf4j-api:2.0.9")
    implementation("ch.qos.logback:logback-core:1.5.13")

    // Lombok para reduzir código repetitivo
    implementation("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

    // Dependências de teste (JUnit 5)
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

// Configuração para rodar testes com JUnit Platform
tasks.test {
    useJUnitPlatform()
}

// Ativar o processador de anotações para Lombok
tasks.withType<JavaCompile> {
    options.compilerArgs.add("-parameters")
}
