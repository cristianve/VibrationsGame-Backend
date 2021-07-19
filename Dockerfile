FROM adoptopenjdk:11.0.11_9-jre-hotspot
COPY build/libs/*.jar vibration-game.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "/vibration-game.jar"]
