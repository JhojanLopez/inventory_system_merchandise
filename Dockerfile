FROM gradle:8-jdk17-alpine AS build

WORKDIR /app

COPY build.gradle settings.gradle ./

COPY src ./src
#compile without tests
RUN gradle bootJar

FROM amazoncorretto:17.0.0-alpine

WORKDIR /app

COPY --from=build /app/build/libs/*.jar merchandise.jar

EXPOSE 8080

CMD ["java", "-jar", "merchandise.jar"]
