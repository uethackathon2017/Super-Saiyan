'use strict';

const EVENT_EXIT = 'exit';
const EVENT_SIGN_INTERRUPT = 'sigint'.toUpperCase();

let bodyParse = require('body-parser');
let express = require('express');
let http = require('http');
require('dotenv').config();

let app = express();
let server = http.createServer(app);

app.use(bodyParse.json());
app.use('/public', express.static(__dirname + '/public'));

startApplication();

process.on(EVENT_SIGN_INTERRUPT, () => {
    process.exit(0);
});

process.on(EVENT_EXIT, () => {
    console.log('Server was stopped.');
});

function startApplication() {
    server.listen(process.env.port || 8080, () => {
        console.log('Server was started.');

        app.use(require('./routes/topic'));
        app.use(require('./routes/map'));
    });
}