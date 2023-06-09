# bank
WEB:
-Se encuentra el archivo index.html en la ruta src/main/web/html
-Faltan los inputs para la busqueda por direcciones
-Se estan consumiendo el api de crear contacto cuando se presiona el boton Home de la eweb contact.html, creando siempre repetido el ultimo contacto que se creo.
Funcionalidades:

Pagina index.html:
  1. Contacts: muestra los Contactos en tabla que existen en la base de datos.
  2. Delete: la tabla tiene en cada fila al principio los checkbox que permite seleccionar los elementos a borrar de la base de datos
  3. Search: busca en la bd todos los contactos que coinciden con los inputs llenados
  
Pagina contacts.html
 1. Tiene los campos necesarios para crear un contacto: First Name, Second Name, Birthdate, Addresses, Phones, Photo
 2. El boton agregar para la seccion Phones agrega nuevos inputs para añadir mas phone
 3. El boton agregar para la seccion Addresses agrega nuevos inputs para añadir mas address
 4. En cada caso aparece el boton eliminar para eliminar el ultimo address o phone que se añadio
 5. Boton Crear contacto para crear el contacto
 6. Boton Home regresar a index.html
 
 
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

Imagen docker:
-Tiene el plugin de spotify-maven para la construccion de la imagen

Global:
-Faltan los test unitarios y los mock de pruebas
-Faltan validaciones del lado del Frontend y del Backend en cuanto a la fiabilidad de los datos ingresados por el usuario de la web

  
