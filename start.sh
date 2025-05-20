# 启动 Docker Compose
echo "Starting Docker Compose..."
docker-compose up -d
if [ $? -ne 0 ]; then
    echo "Docker Compose failed to start. Exiting."
    exit 1
fi

echo "Build and startup completed successfully."