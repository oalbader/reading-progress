# Using H2 File as Database

## Setup

1. Add H2 dependency to your project:

   ```xml
   <dependency>
       <groupId>com.h2database</groupId>
       <artifactId>h2</artifactId>
       <version>2.1.214</version>
   </dependency>
   ```

2. Configure database connection in `application.properties`:

   ```properties
   spring.datasource.url=jdbc:h2:file:./data/projectdb
   spring.datasource.driverClassName=org.h2.Driver
   spring.datasource.username=sa
   spring.datasource.password=password
   spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
   ```

## Usage

- H2 file will be created at `./data/projectdb.mv.db`
- Access H2 console: `http://localhost:8080/h2-console`
- Use JDBC URL: `jdbc:h2:file:./data/projectdb`

## Best Practices

- Version control: Ignore H2 file in .gitignore
- Backup: Regularly backup the H2 file
- Performance: Consider indexing frequently queried columns

## Troubleshooting

- File locked: Ensure only one connection at a time
- Data persistence: Verify file path and permissions
