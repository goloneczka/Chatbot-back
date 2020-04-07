# pip2020-back
Test webhook


# Generowanie Schemy
Poniższa linijka służy do wygenerowania schemy bazy danych w jooq-u. Musisz podać następujące parametry: 
* url do bazy danych. Przykłdowe:
  * jdbc:postgresql:database
  * jdbc:postgresql://host/database
  * jdbc:postgresql://host:port/database
* user
* hasło

Dodatkowo należy przejsc do katalogu 'database'
````
mvn jooq-codegen:generate -Djooq.codegen.jdbc.url= -Djooq.codegen.jdbc.user= -Djooq.codegen.jdbc.password=
````

# Migracja schematu bazy danych

Parametry polecenia potrzebne do wykonania migracji schematu bazy danych za pomocą pluginu flyway:
* "-Dflyway.user" - użytkownik używany do połączenia z bazą danych
* "-Dflyway.password" - hasło używane do połączenia z baza danych
* "-Dflyway.url" - adres url do bazy danych

Pliki z kwerendami, które mają zostać wykonane znajduja się w katalogu: "database/src/main/resources/migration".

Znajdując się w katalogu "database" należy wpisać poniższą komendę z odpowiednio uzupełnionymi parametrami:
````
mvn -Dflyway.user= -Dflyway.password= -Dflyway.url= flyway:migrate 
````

