var url_base = "http://localhost:8080"+ "/v1/api/contacts";
var addressCount = 0;
var addressContainer = document.getElementById('address-container');
var addAddressButton = document.getElementById('add-address');
var deleteAddressButton = document.getElementById("delete-address")
addAddressButton.addEventListener('click', function() {
    addressCount++;
    var newAddressDiv = document.createElement('div');
    newAddressDiv.classList.add('address-item');
    newAddressDiv.innerHTML = `
            <input type="text" name="addresses[${addressCount}].name" placeholder="Name">
            <input type="text" name="addresses[${addressCount}].country" placeholder="country">
            <input type="text" name="addresses[${addressCount}].state" placeholder="state">
            <input type="text" name="addresses[${addressCount}].city" placeholder="city">
            <input type="text" name="addresses[${addressCount}].street" placeholder="street">
            <input type="text" name="addresses[${addressCount}].zip" placeholder="zip">
        `;
    addressContainer.appendChild(newAddressDiv);
    deleteAddressButton.style.display = 'inline-block';
});

deleteAddressButton.addEventListener('click', function() {
    var addressItems = document.querySelectorAll('.address-item');
    var lastAddressItem = addressItems.item(addressItems.length - 1);
    lastAddressItem.remove();
    addressCount = addressItems.length - 1;
    if (addressCount === 0) {
        deleteAddressButton.style.display = 'none';
    }
});

var phoneCount = 0;
var phoneContainer = document.getElementById('phone-container');
var addPhoneButton = document.getElementById('add-phone');
var deletePhoneButton = document.getElementById("delete-phone");
addPhoneButton.addEventListener('click', function() {
    addressCount++;
    var newAddressDiv = document.createElement('div');
    newAddressDiv.classList.add('phone-item');
    newAddressDiv.innerHTML = `
        <div id="phone[${phoneCount}]">
            <input type="text" name="phone[${phoneCount}].name" placeholder="Name">
            <input type="number" name="phone[${phoneCount}].number" placeholder="Phone">
        </div>
        `;
    phoneContainer.appendChild(newAddressDiv);
    deletePhoneButton.style.display = 'inline-block';
});


deletePhoneButton.addEventListener('click', function() {
    var phoneItems = document.querySelectorAll('.phone-item');
    var lastPhoneItem = phoneItems.item(phoneItems.length - 1);
    lastPhoneItem.remove();
    phoneCount = phoneItems.length - 1;
    if (phoneCount === 0) {
        deletePhoneButton.style.display = 'none';
    }
});


var form = document.querySelector('form');
form.addEventListener('submit', async function (event) {
    event.preventDefault();
    var addressItems = document.querySelectorAll('.address-item');
    var addresses = [];
    addressItems.forEach(function (addressItem) {
        var name = addressItem.querySelector('input[name$=".name"]').value;
        var country = addressItem.querySelector('input[name$=".country"]').value;
        var state = addressItem.querySelector('input[name$=".state"]').value;
        var city = addressItem.querySelector('input[name$=".city"]').value;
        var street = addressItem.querySelector('input[name$=".street"]').value;
        var zip = addressItem.querySelector('input[name$=".zip"]').value;
        if (name && country && state && city && street && zip) {
            var address = {
                name: name,
                country: country,
                state: state,
                city: city,
                street: street,
                zip: zip
            };
            addresses.push(address);
        }
    });

    // Obtener los valores de los teléfonos
    var phoneItems = document.querySelectorAll('.phone-item');
    var phones = [];
    phoneItems.forEach(function (phoneItem) {
        var name = phoneItem.querySelector('input[name$=".name"]').value;
        var number = phoneItem.querySelector('input[name$=".number"]').value;
        if (name && number) {
            var phone = {
                name: name,
                number: number
            };
            phones.push(phone);
        }
    });

    var contact = {
        firstName: document.querySelector('input[name="firstName"]').value,
        secondName: document.querySelector('input[name="secondName"]').value,
        birthDate: document.querySelector('input[name="birthDate"]').value,
        addresses: addresses,
        phones: phones
    };
    var response_;
    await fetch(url_base, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(contact)
    })
        .then(function (response) {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            response_ = response;
        })
        .then(function (data) {
            const imageInput = document.getElementById("image-input");
            if (imageInput.files.length !== 0) {
                const file = imageInput.files[0];
                var id = data.id;
                uploadPic(id, file);
            } else {
                if (response_.ok) {
                    mostrarMensajeTemporal("La operación se realizó correctamente", "success");
                } else
                    mostrarMensajeTemporal("Se produjo un error al realizar la operación", "error");
            }
        })
        .catch(function (error) {
            console.error('Error:', error);
        });
});

function uploadPic(id, file){
    const formData = new FormData();
    formData.append("file", file);
    var url = url_base + "/upload/" + id.toString();
    fetch(url, {
        method: "POST",
        body: formData
    })
        .then(response => {
            if (response.ok) {
                mostrarMensajeTemporal("La operación se realizó correctamente", "success");
            }
            else
                mostrarMensajeTemporal("Se produjo un error al realizar la operación", "error");
        }
        )
        .then(data => console.log(data))
        .catch(error => console.error(error));
}

document.addEventListener('DOMContentLoaded', function() {
    const btnCreate = document.getElementById('go-back');
    btnCreate.addEventListener('click', function() {
        window.location.href = '../html/index.html';
    });
});


function mostrarMensajeTemporal(mensaje, tipo) {
    const mensajeTemporal = document.getElementById("mensaje-temporal");
    var message = mensajeTemporal.querySelector('p')
    message.textContent = mensaje;
    message.style.display = "inline-block"

    setTimeout(function() {
        message.style.display = "none";
        message.textContent = "";
    }, 1000); // elimina el mensaje temporal después de 2 segundos
}