# This dockerfile is designed to be as light as possible and have all the requirements for
# building any version of the project.
# Add DOCKER_DEFAULT_PLATFORM=linux/amd64 to the build command if you are on an M1/M2 mac and want to build for amd64
# To forcibly test the local image, you can change the pipeline to use image
#    image: advanced-portals-ci:dev
#    pull: false
# https://forgejo.org/docs/latest/user/packages/container/
# To build this locally for testing/use just run
# docker build -t codeberg.org/sekwah/advanced-portals/advanced-portals-ci:dev .
# docker push codeberg.org/sekwah/advanced-portals/advanced-portals-ci:dev

FROM alpine:3.23

# Make sure this matches gradle/wrapper/gradle-wrapper.properties
ARG GRADLE_VERSION=9.4.0

ARG JDK17_URL=https://api.adoptium.net/v3/binary/latest/17/ga/alpine-linux/x64/jdk/hotspot/normal/eclipse
ARG JDK25_URL=https://api.adoptium.net/v3/binary/latest/25/ga/alpine-linux/x64/jdk/hotspot/normal/eclipse

RUN apk add --no-cache bash ca-certificates curl unzip

RUN mkdir -p /usr/lib/jvm/temurin-17 /usr/lib/jvm/temurin-25 \
    && curl -fsSL "$JDK17_URL" | tar -xz -C /usr/lib/jvm/temurin-17 --strip-components=1 \
    && curl -fsSL "$JDK25_URL" | tar -xz -C /usr/lib/jvm/temurin-25 --strip-components=1

RUN curl -fsSL "https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip" -o /tmp/gradle.zip \
    && unzip -q /tmp/gradle.zip -d /opt \
    && rm /tmp/gradle.zip \
    && ln -s "/opt/gradle-${GRADLE_VERSION}/bin/gradle" /usr/local/bin/gradle

ENV JAVA_HOME=/usr/lib/jvm/temurin-25
ENV PATH="$JAVA_HOME/bin:$PATH"
