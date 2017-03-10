'use strict';

let express = require('express');

let topic = require('../controllers/topic');

let router = express.Router();

router.get('/el/api/topic/list', topic.getTopics);

module.exports = router;