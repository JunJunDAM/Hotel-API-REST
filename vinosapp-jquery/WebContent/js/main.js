// La URL raiz para los servicios RESTful
var rootURL = "http://localhost:8080/vinosapp-jquery/rest/vinos";

var vinoActual;

// Recuperar la lista de vinos cuando la aplicacion inicia 
findAll();

// Ocultar el botón de eliminar al iniciarse la aplicacion
$('#btnDelete').hide();

// Registrar los listeners
$('.btnBusqueda').click(function() {
	//$('#campoBusqueda').trigger('keyup');
	search($('#campoBusqueda').val());
	return false;
});

// Lanzar la operacion de busqueda al presionar 'Return' en el campo de busqueda
$('#campoBusqueda').keypress(function(e){
	if(e.which == 13) {
		search($('#campoBusqueda').val());
		e.preventDefault();
		return false;
    }
});

$('.new').click(function() {
	newHotel();
	return false;
});

$('#btnSave').click(function() {
	if ($('#hotel_id').val() == '')
		addHotel();
	else
		updateHotel();
	return false;
});

$('#btnDelete').click(function() {
	deleteHotel();
	return false;
});

$('#listaHoteles').delegate('a', 'click', function() {
	findById($(this).data('identity'));
});

function search(searchKey) {
	if (searchKey == '') 
		findAll();
	else
		findByName(searchKey);
}

function newHotel() {
	$('#btnDelete').hide();
	renderDetails(vinoActual); // Mostrar formulario vacio
	$('#hotel_name').focus();
}

function findAll() {
	console.log('findAll');
	$.ajax({
		type: 'GET',
		url: rootURL,
		dataType: "json", // tipo de la respuesta
		success: renderList
	});
}

function findByName(searchKey) {
	console.log('findByName: ' + searchKey);
	$.ajax({
		type: 'GET',
		url: rootURL + '/search/' + searchKey,
		dataType: "json",
		success: renderList 
	});
}

function findById(id) {
	console.log('findById: ' + id);
	$.ajax({
		type: 'GET',
		url: rootURL + '/' + id,
		dataType: "json",
		success: function(data){
			$('#btnDelete').show();
			console.log('findById success: ' + data.name);
			hotel = data;
			renderDetails(hotel);
		}
	});
}

function addHotel() {
	console.log('addHotel');
	$.ajax({
		type: 'POST',
		contentType: 'application/json',
		url: rootURL,
		dataType: "json",
		data: formToJSON(),
		success: function(data, textStatus, jqXHR){
			findAll();
			alert('Hotel created');
			$('#btnDelete').show();
			$('#hotel_id').val(data.id);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('Error on addHotel: ' + textStatus);
		}
	});
}

function updateHotel() {
	console.log('updateHotel');
	$.ajax({
		type: 'PUT',
		contentType: 'application/json',
		url: rootURL + '/' + $('#hotel_id').val(),
		dataType: "json",
		data: formToJSON(),
		success: function(data, textStatus, jqXHR){			
			alert('Hotel updated');
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('Error on updateHotel: ' + textStatus);
		}
	});
}

function deleteHotel() {
	console.log('deleteHotel');
	$.ajax({
		type: 'DELETE',
		url: rootURL + '/' + $('#hotel_id').val(),
		success: function(data, textStatus, jqXHR){
			findAll();
			renderDetails({});
			alert('Hotel deleted');
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('Error on deleteHotel');
		}
	});
}

function renderList(data) {
	// JAX-RS serializa una lista vacia como null, y una coleccion de un único elemento en lugar de un array de uno)
	var list = data == null ? [] : (data instanceof Array ? data : [data]);

	$('#listaHoteles li').remove();
	$.each(list, function(index, vino) {
		$('#listaHoteles').append('<li><a href="#" data-identity="' + hotel.id + '">'+hotel.hotel_name+'</a></li>');
	});
}

function renderDetails(hotel) {
	$('#hotel_id').val(hotel.id);
	$('#hotel_name').val(hotel.hotel_name);
	$('#stars').val(hotel.stars);
	$('#city').val(hotel.city);
	$('#description').val(hotel.description);
	$('#price').val(hotel.price);
}

// Funcion de ayuda para serializar todos los campos del formulario a strings de JSON
function formToJSON() {
	var vinoId = $('#vinoId').val();
	return JSON.stringify({
		"id": vinoId == "" ? null : vinoId, 
		"hotel_name": $('#hotel_name').val(), 
		"stars": $('#stars').val(),
		"city": $('#city').val(),
		"description": $('#description').val(),
		"price": $('#price').val(),
		});
}
