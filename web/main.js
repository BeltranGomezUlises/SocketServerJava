var socket;
$('#btnEnviar').prop('disabled', true);
$('#btnEnviarTodos').prop('disabled', true);
$('#btnGetStatus').prop('disabled', true);


$(function() {

    $('#btnEnviar').click(function() {
      var arregloClientesId = $('#txtClientes').val().split(',');
      var arregloClientes = [];

      arregloClientesId.forEach( function(clientId, i) {
          var clientType = $('#cmbTipoClienteDestino').val();
          arregloClientes.push({clientId, clientType});
      });

      if (arregloClientes[0] !== '') {
        var texto = $('#txtEnviar').val();
        var clienteId = $('#txtClienteId').val();
        var socketMessage = {          
          'clientsToNotify': arregloClientes,
          'notificationType': 'NOTIFICATION',
          'data':texto
        };
        socket.send(JSON.stringify(socketMessage));
      }else{
        alert('Debe colocar numeros de clientes separados por \',\'');
      }

    });

    $('#btnEnviarTodos').click(function() {
      var texto = $('#txtEnviar').val();
      var socketMessage = {
        'notificationType': 'NOTIFICATION_ALL',
        'data':texto
      };
      socket.send(JSON.stringify(socketMessage));
    });

    $('#btnConectar').click(function() {
      var clienteId = $('#txtClienteId').val();
      if (clienteId === '') {
          alert('Debe ingresar un cliente id');
      }else{
        socket = new WebSocket("ws://192.168.10.10:8383/SocketServer/sockets");
        //socket = new WebSocket("ws://localhost:8084/socketServer/sockets");

        socket.onmessage = function(e){
          alert(e.data);
        }

        socket.onopen = function(){
          alert("socket conectado...")
          var tipoCliente = $('#cmbTipoCliente').val();
          socket.send(JSON.stringify({
            'key':{
                'clientId': clienteId,
                'clientType':tipoCliente
            },
            'notificationType':'REGISTER'
          }));
          $('#btnEnviar').prop('disabled', false);
          $('#btnEnviarTodos').prop('disabled', false);
          $('#btnGetStatus').prop('disabled', false);
        }

        socket.onerror = function(){
          console.log('error de socket', socket);
        }

        socket.onclosed = function(){
          console.log('closed');
        }
      }
    });

    $('#btnGetStatus').click(function() {
      var socketMessage = {
        'notificationType': 'GET_STATUS'
      };
      socket.send(JSON.stringify(socketMessage));
    });

});
