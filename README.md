Implementation for scanning of incoming files

docker
- contains compose files for DB, ClamAV & RabbitMQ

libs
- shared library for accessing DB

files-service
- simple upload, storing in DB & message is sent to inform about new file to be checked

checker-service
- checking files in DB using ClamAV & message is sent to inform about new file was checked