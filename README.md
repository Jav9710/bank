# bank
WEB:
-Se encuentra el archivo index.html en la ruta src/main/web/html
-Faltan los inputs para la busqueda por direcciones
-Se estan consumiendo el api de crear contacto cuando se presiona el boton Home de la eweb contact.html, creando siempre repetido el ultimo contacto que se creo.
 
Backend:
-Falta la parte de busqueda por addresses
Endpoints:
- GET /v1/api/contacts Devuelve todos los contactos 
- POST /v1/api/contacts (JSON) Crea un contacto 
- PUT /v1/api/contacts (JSON) Actualiza un contacto
- DELETE /v1/api/contacts (JSON) Elimina los contactos correspondientes a la lista de ids que se le pasa por JSON
- GET /v1/api/contacts/search (path dinamic query: firestName, secondName, from, to) Devuelve todos los elementos que cumplen con el path query
- POST /v1/api/contacts/upload/{id} (Multipart file) Sube una imagen y la asocia al Contacto con id {id} 
- GET /v1/api/contacts/download (JSON) Devuelve la imagen correspondiente al path provisto en el json

Global:
-Faltan los test unitarios y los mock de pruebas

  
