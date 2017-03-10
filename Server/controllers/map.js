'use strict';

let path = require('path');
let Place = require('../models/Place');
let Promise = require('promise');

var googleMapsClient = require('@google/maps').createClient({
    key: process.env.GOOGLE_MAP_API_KEY
});

const PLACE_TYPES = ['university', 'bank', 'hospital', 'church', 'airport'];

function test(req, res) {
    var lat = req.query.lat;
    var lng = req.query.lng;

    if (!lat || !lng) {
        res.send('Not enough params');
        return;
    }

    var places = [];

    PLACE_TYPES.forEach(function (place_type) {
        googleMapsClient.placesNearby({
            location: [lat, lng],
            radius: 100,
            type: place_type
        }, function (err, response) {
            if (!err) {
                var ps = getPlacesAround(response.json.results);
                ps.forEach(function (item) {
                    places.push(item);
                });
            }
        });
    })
}

function getPlacesAround(raw) {
    var places = [];
    raw.forEach(function (item) {
        var place = new Place(item.name, item.types[0]);
        places.push(place);
    });
    return places;
}

module.exports.test = test;