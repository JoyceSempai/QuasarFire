# QuasarFire
Operación fuego Quasar topsecret y topsecret_split

El proyecto consta de clases Java para la aplicación topsecret y topsecret_split

Como jefe de comunicaciones rebelde, tu misión es crear un programa en Golang que retorne
la fuente y contenido del mensaje de auxilio. Para esto, cuentas con tres satélites que te
permitirán triangular la posición, ¡pero cuidado! el mensaje puede no llegar completo a cada
satélite debido al campo de asteroides frente a la nave.
Posición de los satélites actualmente en servicio
● Kenobi
● Skywalker
● Sato

Ejecutar la aplicación 

1. Abrir el proyecto con la estructura indicada. Package
2. Identificar la clase principal QuasarFireApplication.java en el paquete com.cruiser.quasarfire
3. Ejecutar la aplicación con la opcion Run As-> Java Application,  a traves de la clase principal QuasarFireApplication.java.
4. Se levanta el servicio y queda a la escucha de peticiones post y get.
5. Se incluye la colección de json para la prueba de los servicios topsecret y topsecret_split. Con ayuda de postman, se puede abrir la colección.

Json para pruebas QuasarFire.postman_collection.json

POST topsecret

1. seleccionar el json POST con nombre topsecret
2. Una vez abierto, dar click en el botón Send.
Tiene la siguiente formato:

POST → /topsecret/
{
   "satellites": [
                  {
                    “name”: "kenobi",
                    “distance”: 100.0,
                    “message”: ["este", "", "", "mensaje", ""]
                   },
                    {
                      “name”: "skywalker",
                      “distance”: 115.5
                      “message”: ["", "es", "", "", "secreto"]
                    },
                    {
                      “name”: "sato",
                      “distance”: 142.7
                      “message”: ["este", "", "un", "", ""]
                    }
                ]
}


En caso de ejecutarlo correctamente regresa un código 200. RESPONSE CODE: 200 y los soguientes datos
    {
        "position": {
        "x": posición_eje_X,
        "y": posición_eje_Y
        },
        "message": mensaje_reconstruido_enviado_origen
    }

En caso de error regresa un código 404. RESPONSE CODE: 404

POST topsecret_split

Caso de prueba 1. Happy path
1. seleccionar el json POST con nombre topsecret_split-kenobi y enviarlo
2. seleccionar el json POST con nombre topsecret_split-skywalker y enviarlo
3. seleccionar el json POST con nombre topsecret_split-sato y enviarlo
4. seleccionar el json GET topsecret_split 
5. Una vez abierto, dar click en el botón Send.

Caso de prueba 2. Envío en desorden de los satelites.
1. seleccionar el json POST con nombre topsecret_split-kenobi ó topsecret_split-skywalker ó topsecret_split-sato en cualquier orden y enviarlos uno a uno.
4. seleccionar el json GET topsecret_split 
5. Una vez abierto, dar click en el botón Send.

Caso de prueba 3. Envío de 4o más satelites.
1. seleccionar el json POST con nombre topsecret_split-kenobi y enviarlo
2. seleccionar el json POST con nombre topsecret_split-skywalker y enviarlo
3. seleccionar el json POST con nombre topsecret_split-sato y enviarlo
4. seleccionar el json POST con nombre topsecret_split-other y enviarlo
Nota. El envío de los satelites puede ser en este órden o uno aleatorio.
6. seleccionar el json GET topsecret_split. En este caso se espera un error ya que son más satelites que los esperado y utilizados para la triangulación.
7. Para continuar y lograr un resultado exitoso, se puede eliminar el dato adicional con el json DEL topsecret_split-other, posteriormente seleccionar el json GET topsecret_split.


Tiene la siguiente formato:

POST → /topsecret_split/{satellite_name}
  {
      "distance": 100.0,
      "message": ["este", "", "", "mensaje", ""]
  }

En caso de ejecutarlo correctamente regresa un código 200. RESPONSE CODE: 200 y los soguientes datos
    {
        "position": {
        "x": posición_eje_X,
        "y": posición_eje_Y
        },
        "message": mensaje_reconstruido_enviado_origen
    }

En caso de error regresa un código 404. RESPONSE CODE: 404

