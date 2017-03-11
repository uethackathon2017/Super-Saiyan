'use strict';

let express = require('express');

let map = require('../controllers/map');
let topic = require('../controllers/topic');

let router = express.Router();

router.get('/el/api/topic/list', topic.getTopics);
router.get('/el/api/words/common', topic.getCommonWords);
router.post('/el/api/topic/suggestion', map.getSuggestionTopic);

module.exports = router;