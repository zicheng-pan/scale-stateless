cd ./scale-single
mvn clean
cd ..
docker rmi defaultnamespace/my-openjdk-app:1.0 --force
docker-compose down