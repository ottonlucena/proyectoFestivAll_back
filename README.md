## Favoritos

### Crear favoritos:

curl --location 'http://localhost:8080/api/favoritos' \
--header 'Content-Type: application/json' \
--data '{
    "juego_id": 5,
    "usuario_id": 2,
    "favorito": true
}
'

###  Borrar favoritos:

curl --location --request DELETE 'http://localhost:8080/api/favoritos' \
--header 'Content-Type: application/json' \
--data '{
    "juego_id": 4,
    "usuario_id": 2
}'

### Filtrar por usuario

curl --location 'http://localhost:8080/api/favoritos/usuario/2'

### Buscar favorito

curl --location 'http://localhost:8080/api/favoritos/usuario/2/juego/5'
