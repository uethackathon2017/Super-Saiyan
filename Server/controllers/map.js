'use strict';

const PLACE_TYPES = [
    'airport',
    'bank',
    'church',
    'doctor',
    'hospital',
    'university'
];

let googleMap = require('@google/maps');

let googleMapsClient = googleMap.createClient({key: process.env.GOOGLE_MAP_API_KEY});

function getSuggestionTopic(req, res) {
    let latitude = req.body.latitude;
    let longitude = req.body.longitude;
    if (latitude == null || longitude == null) {
        res.status(400).json({success: false});
        return;
    }

    getSuggestionTopicAsync(latitude, longitude, function (topics) {
        if (topics.length > 0) {
            res.status(200).json({topic: topics[0]});
        } else {
            res.status(200).json({topic: null});
        }
    })
}

function getSuggestionTopicAsync(latitude, longitude, callback) {
    let result = [];
    let count = 0;
    PLACE_TYPES.forEach(function (item) {
        googleMapsClient.placesNearby({
            location: [latitude, longitude],
            radius: 100,
            type: item
        }, function (err, response) {
            if (!err) {
                //noinspection JSUnresolvedVariable
                parseTopicData(response.json.results).then(topics => {
                    topics.forEach(function (item) {
                        result.push(item);
                    });
                    count++;
                    if (count === PLACE_TYPES.length) {
                        callback(result);
                    }
                });
            }
        });
    })
}

function parseTopicData(data) {
    return new Promise((reslove, reject) => {
        let topics = [];
        data.forEach(function (item) {
            topics.push(item.types[0]);
        });
        reslove(topics);
    });
}

module.exports.getSuggestionTopic = getSuggestionTopic;