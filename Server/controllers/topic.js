'use strict';

let path = require('path');

function getTopics(req, res) {
    res.sendFile(path.dirname(__dirname) + "/data/topic_db.json");
}

module.exports.getTopics = getTopics;