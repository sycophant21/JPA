# JPA

> Annotations on top of annotations on top of JDBC. You're welcome.

A **custom Java Persistence API** built from scratch using Java Reflection. Annotate your Java classes and let the library auto-create MySQL tables, map fields to columns, and execute CRUD queries — no Hibernate, no Spring Data.

## What it does

- **Auto-creates** MySQL tables from annotated Java classes via reflection
- **Maps** Java fields to SQL columns (type inference, nullable, primary keys)
- **Executes** standard CRUD operations through a generated query layer
- **Corrects** schema drift: `TableCorrector` detects mismatches between the class model and the live DB schema and applies `ALTER TABLE` patches
- Handles primary key management (`KeyManager`) and class-to-table name resolution (`ClassTableMapper`)

## How it works

`FilesManager` scans the project for classes annotated with `@Entity` (or equivalent). `ClassManager` loads them via reflection and extracts field metadata. `ClassTableMapper` maps class names to SQL table names. `QueryManager` generates parameterized SQL strings (INSERT, SELECT, UPDATE, DELETE) from the class metadata. `DBManager` executes them through a JDBC `ConnectionManager`. Before any operation, `TableExistenceChecker` verifies that the target table exists and is schema-correct; if not, `TableCorrector` patches it.

## Tech stack

- **Java** (standard library + Reflection API)
- **MySQL** (via `mysql-connector-java 8.0.22`)
- **JetBrains Annotations** (for nullability)
- **Reflections library** (`reflections-0.9.12`) for classpath scanning

## Getting started

### Prerequisites

- Java 8+
- A running MySQL instance
- IntelliJ IDEA (recommended — the lib/ folder contains bundled JARs)

### Configure

Open `JPA/src/com/Managers/ConnectionManager.java` and update the JDBC URL, username, and password to point at your MySQL database.

### Run

1. Open the project in IntelliJ IDEA.
2. Add all JARs in `JPA/lib/` to the project classpath.
3. Annotate your domain classes (see `Main.java` for examples).
4. Run `Main.java` — tables are created/corrected automatically, then queries run.

## Project structure

```
JPA/src/
├── Main.java                   # demo / entry point
└── com/
    └── Managers/
        ├── ClassManager.java        # reflection-based class loader
        ├── ClassTableMapper.java    # class → table name mapping
        ├── ConnectionManager.java   # singleton JDBC connection pool
        ├── DBManager.java           # query executor
        ├── FilesManager.java        # classpath scanner
        ├── KeyManager.java          # primary key resolution
        ├── QueryManager.java        # SQL generator
        └── TableCorrector.java      # schema drift patcher
```
