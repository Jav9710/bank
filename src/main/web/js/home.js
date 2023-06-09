var url_base = "http://localhost:8080" + "/v1/api/contacts";
document.addEventListener('DOMContentLoaded', function() {
    var boton = document.getElementById('contacts');
    boton.addEventListener('click', function() {
        cargarTabla();
    });
});

var listChecked = [];
function cargarTabla() {
    // Llamada a la API para obtener los datos de la tabla
    fetch(url_base)
        .then(response => response.json())
        .then(data => {
            renderTable(data)
        })
        .catch(error => console.error(error));
}

function renderTable(data){

    // Obtener la referencia del elemento de la tabla
    var tabla = document.getElementById('tabla-dinamica');
    // Limpiar la tabla existente
    tabla.innerHTML = '';
    if (data.length !== 0) {
        // Crear una fila de encabezado
        var encabezado = tabla.insertRow();
        for (var key in data[0]) {
            if (data[0].hasOwnProperty(key)) {
                if(key !== 'id') {
                    var encabezadoCell = encabezado.insertCell();
                    encabezadoCell.innerHTML = key;
                }
                else {
                    var encabezadoCell = encabezado.insertCell();
                    encabezadoCell.innerHTML = ' ';
                }
            }
        }

        // Crear filas de datos
        data.forEach(function(item) {
            var fila = tabla.insertRow();
            var countItem = 1;
            for (var key in item) {
                if(key !== 'id') {
                    if (item.hasOwnProperty(key)) {
                        var cell = fila.insertCell();
                        if (key === 'addresses') { // Si la propiedad es "addresses"
                            var addresses = '';
                            item.addresses.forEach(function (address) { // Recorremos el array de direcciones
                                addresses += address.name + ': ' + address .country + ', ' + address.state + ', ' + address.city + ', ' + address.district + ', ' + address.street + ', ' + address.zip + '<br>'; // Concatenamos las propiedades de la direccion
                            });
                            cell.innerHTML = addresses; // Asignamos el valor concatenado a la celda
                        } else if (key === 'phones') { // Si la propiedad es "phones"
                            var phones = '';
                            item.phones.forEach(function (phone) { // Recorremos el array de teléfonos
                                phones += phone.name + ': ' + phone.number + '<br>'; // Concatenamos las propiedades del teléfono
                            });
                            cell.innerHTML = phones; // Asignamos el valor concatenado a la celda
                        } else if (key === 'photo'){
                            const photoPath = {
                                path: item.photo
                            };
                            fetch(url_base + "/download",{
                                method: 'POST',
                                headers: {
                                    'Content-Type': 'application/json'
                                },
                                body: JSON.stringify(photoPath)
                            })
                                .then(function(response) {
                                    return response.blob();
                                })
                                .then(function(blob) {
                                    const imagen = document.createElement("img")
                                    // crea una URL para la imagen
                                    imagen.width = 100;
                                    imagen.height = 100;
                                    imagen.src = URL.createObjectURL(blob);
                                    cell.appendChild(imagen);
                                })
                                .catch(function(error) {
                                    console.error('Error:', error);
                                });
                        }
                        else {
                            cell.innerHTML = item[key];
                        }
                    }
                }
                else {
                    const selectCheckbox = document.createElement('input');
                    selectCheckbox.setAttribute('type', 'checkbox')
                    selectCheckbox.setAttribute('data-id', item.id)
                    selectCheckbox.addEventListener('change', function(event) {
                        const isChecked = event.target.checked;
                        if (isChecked){
                            const contactId = event.target.getAttribute('data-id');
                            listChecked.push(contactId);
                        }
                        else {
                            const contactId = event.target.getAttribute('data-id');
                            listChecked.splice(listChecked.indexOf(contactId), 1)
                        }

                    });

                    var cell = fila.insertCell();
                    cell.appendChild(selectCheckbox);
                }
                countItem ++;
            }
        });
    }
}
document.addEventListener('DOMContentLoaded', function() {
    const btnCreate = document.getElementById('btn-create');
    btnCreate.addEventListener('click', function() {
        window.location.href = '../html/contact.html';
    });
});

document.addEventListener('DOMContentLoaded', function() {
    var boton = document.getElementById('delete');
    boton.addEventListener('click', function() {
        deleteContacts();
    });
});

function deleteContacts(){
    const contactsToDelete = {
        ids: listChecked
    };

    fetch(url_base, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(contactsToDelete)
    })
        .then(function(response) {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            cargarTabla();
            return response.json();
        })
        .then(function(data) {
            var result = document.getElementById('result');
            result.innerHTML = '<p>' + JSON.stringify(data, null, 2) + '</p>';
        })
        .catch(function(error) {
            console.error('Error:', error);
        })
    listChecked = [];
}

document.addEventListener('DOMContentLoaded', function() {
    var boton = document.getElementById('search');
    boton.addEventListener('click', function() {
        search();
    });
});

function search(){
    var firstName = document.querySelector('input[name="first-name"]').value;
    var secondName = document.querySelector('input[name="second-name"]').value;
    var from = document.querySelector('input[name="from"]').value;
    var to = document.querySelector('input[name="to"]').value;

    var url = url_base + "/search";
    var query = "?";
    if(firstName !== "")
        query = query + "firstName=" + firstName + '&';
    if(secondName !== "")
        query = query + "secondName=" + secondName + '&';
    if(from !== "")
        query = query + "from=" + from + '&';
    if(to !== "")
        query = query + "to=" + to + '&';

    if(query !== "") {
        if (query.endsWith('&')) {
            query = query.slice(0, -1);
        }
        url = url + query;
        fetch(url)
            .then(function (response) {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                renderTable(data);
            })
            .catch(function (error) {
                console.error('Error:', error);
            })
    }
}