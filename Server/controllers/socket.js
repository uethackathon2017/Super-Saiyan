'use strict';

let io;

function setup(server) {
    io = require('socket.io')(server);
    //noinspection JSUnresolvedFunction
    io.set('heartbeat interval', 3000);
    //noinspection JSUnresolvedFunction
    io.set('heartbeat timeout', 3000);
    //noinspection JSUnresolvedFunction
    io.set('origins', "*:*");
}

function Socket(server) {
    setup(server);

    io.on('connection', function (socket) {
        //noinspection JSUnresolvedVariable
        let id = socket.request._query['id'];
        console.log('Connection...', id);

        socket.on('disconnect', function (reason) {
            console.log('Disconnection...', reason);
        })
    })
}

function sendSpecificClient(event, socketId, data) {
    io.to(socketId).emit(event, data);
}

module.exports = Socket;