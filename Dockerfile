# JavaFX GUI application image.
# The window is drawn on the HOST's X server (XQuartz on macOS) through the
# DISPLAY variable passed at "docker run". Software rendering is forced because
# the container has no GPU.

FROM maven:3.9-eclipse-temurin-21

# Native libraries JavaFX needs to render on Linux
RUN apt-get update && apt-get install -y --no-install-recommends \
        libgl1 libglib2.0-0 libgtk-3-0 libxtst6 libxrender1 libxi6 libxxf86vm1 \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Copy the source and build the app.
# -U forces a fresh check so cached "not found" results for the JavaFX
# platform classifiers (linux-aarch64) are re-resolved instead of failing.
COPY . .
RUN mvn -q -B -U -DskipTests clean package

# Software rendering (no GPU) + reach the MariaDB running on the host
ENV _JAVA_OPTIONS="-Dprism.order=sw -Ddb.host=host.docker.internal"

# Launch the JavaFX application
CMD ["mvn", "-q", "-B", "javafx:run"]
