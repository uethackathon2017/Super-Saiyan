'use strict';

let express = require('express');

let map = require('../controllers/map');

let router = express.Router();

router.get('/el/api/map/test', map.test);

module.exports = router;