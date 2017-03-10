'use strict';

let path = require('path');

var googleMapsClient = require('@google/maps').createClient({
    key: process.env.GOOGLE_MAP_API_KEY
});

function test(req, res) {
    var lat = req.query.lat;
    var lng = req.query.lng;

    if (!lat || !lng) {
        res.send('Not enough params');
        return;
    }

    googleMapsClient.placesNearby({
        location: [lat, lng],
        radius: 100
    }, function(err, response) {
        if (!err) {
            res.send(response.json.results);
        }
    });
}

module.exports.test = test;