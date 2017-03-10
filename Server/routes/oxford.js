'use strict';

let express = require('express');

let topic = require('../controllers/oxford');

let router = express.Router();

router.get('/el/api/oxford', topic.getNewWord);

module.exports = router;