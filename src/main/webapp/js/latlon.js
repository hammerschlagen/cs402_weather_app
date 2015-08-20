var locs = [];
var userId;

$( document ).ready(function() {
    userId = getParameterByName("user");
    $.ajax({
        url: '/latlon/users/' + userId + '/locations',
        type: 'get',
        dataType: 'json',
        success: function(response) {
      	  //console.log(response);
            locs = response;
            toHtml(locs);
        },
        error: function(response) {
            alert(JSON.stringify(response));
        }
    });
});

function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}

getLocations = function() {
    $.ajax({
        url: '/latlon/users/' + userId + '/locations',
        type: 'get',
        dataType: 'json',
        success: function(response) {
      	  //console.log(response);
            locs = response;
            toHtml(locs);
        },
        error: function(response) {
            alert(JSON.stringify(response));
        }
    });
}

deleteLocation = function(locId) {
	  console.log('deleting a user: ' + locId);
	  $.ajax({
        url: '/latlon/users/' + userId + '/locations/' + locId,
        type: 'delete',
        dataType: 'json',
        success: function(response) {        	  
      	  locs = response;
      	  toHtml(locs);
        },
        error: function(response) {
            alert("Could not load the locations list");
        }
    });
}

addLocation = function(){
	  console.log('adding location');
	  var latitude = $('#latitudeField').val();
	  if (latitude == ""){ latitude = '43.81'};
	  var longitude = $('#longitudeField').val();
	  if (longitude == ""){ longitude = '-91.23'}
	  
	  if (latitude > 90 || latitude < -90 || longitude > 180 || longitude < -180){
		  alert("Latitude must be between -90 and 90. Longitude must be between -180 and 180");
		  return false;
	  }

	  $.ajax({
          url: '/latlon/users/' + userId + '/locations',
		  type: 'post',
          data: { "lat": latitude, "lon": longitude},
          dataType: 'json',
          beforeSend: function(xhr) {
              xhr.setRequestHeader("Accept", "application/json");
              xhr.setRequestHeader("Content-Type", "application/json");
          },
          success: function(response) {
              locs = response;
              //console.log(newLoc);
              toHtml(locs);
          },
          error: function(response) {
              alert(JSON.stringify(response));
          }
      });
}

toHtml = function(locs) {
    var locNode = document.getElementById("locations");
    // remove any existing locations
    while (locNode.firstChild) {
    	locNode.removeChild(locNode.firstChild);
    }

    // add users
    locs.forEach(function(val, i) {
        var div = document.createElement('div');
        div.className = "location";
        
        var addrPara = document.createElement('p');
        var delSpan = document.createElement('span');
        var coordPara = document.createElement('p');
        var timePara = document.createElement('p');
        var windPara = document.createElement('p');
        var tempPara = document.createElement('p');
        var humidPara = document.createElement('p');
        var barPara = document.createElement('p');

        div.appendChild(addrPara);
        addrPara.appendChild(delSpan);
        div.appendChild(coordPara);
        div.appendChild(timePara);
        div.appendChild(windPara);
        div.appendChild(tempPara);
        div.appendChild(humidPara);
        div.appendChild(barPara);
        
        addrPara.appendChild(document.createTextNode(val.address));
        coordPara.appendChild(document.createTextNode("Coordinates: " + val.coordinates));
        timePara.appendChild(document.createTextNode("Time: " + new Date(val.reqTime).toLocaleString()));
        windPara.appendChild(document.createTextNode("Wind Speed: " + val.windSpeed));
        tempPara.appendChild(document.createTextNode("Temperature: " + val.temperature));
        humidPara.appendChild(document.createTextNode("Humidity: " + val.humidity));
        barPara.appendChild(document.createTextNode("Barometric Pressure: " + val.barPressure));
        
        
        delSpan.className="btn btn-xs btn-danger glyphicon glyphicon-trash pull-right";
        delSpan.addEventListener('click', function() { deleteLocation(val.id); } );
        
        locNode.appendChild(div);
    });
}


//login = function() {
//	  console.log('Logging in');
//	  var username = $('#usernameField').val();
//	  var password = $('#passField').val();
//	  var phone = $('#phoneNumberField').val();
//	  var fn = $('#firstField').val();
//	  var ln = $('#lastField').val();
//	  var em = $('#emField').val();
//	  $.ajax({
//          url: '/latlon/users',
//          type: 'post',
//          data: { firstName : fn, lastName : ln, emails : em, username : username, password : password, phoneNumbers : phone},
//          dataType: 'json',
//          success: function(response) {
//              newUser = response;
//              console.log(newUser);
//              getUsers();
//          },
//          error: function(resonse) {
//              alert("Could not load the user list");
//          }
//      });
//}