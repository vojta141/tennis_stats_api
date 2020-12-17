1. Install IntelliJ if you haven't already
2. Open terminal na cd to project folder with docker-compose.yml
3. Run "docker volume create --name=postgres_data"
4. Run "docker-compose up"
5. In IntelliJ, create a new database with user, password and name identical to docker-compose.yml
6. Paste SQL code from /src/main/resources/db_init.sql to query console and run it
7. Uncomment code with "Add admin account" annotation in Application class to add admin account
8. Run application
9. After first run, comment out admin creation method
10. You can now add some sample data if you want from /src/main/resources/sample_data.sql
