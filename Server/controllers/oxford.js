'use strict';

let path = require('path');
let request = require('request-promise');
let translate = require('google-translate-api');

function getNewWord(req, res) {
    var word = req.query.word;
    console.log(process.env.APP_ID);
    console.log(process.env.APP_KEY);

    word = 'hello';
    request({
        uri: 'https://od-api.oxforddictionaries.com:443/api/v1/entries/en/' + word,
        method: 'GET',
        headers: {
            "Accept": "application/json",
            "app_id": process.env.APP_ID,
            "app_key": process.env.APP_KEY
        },
        json: true
    }).then(function (response) {
        getWordData(word, response).then(newWord => {
            res.send(newWord);
        });
    });
}

function getWordData(word, rawData) {
    return new Promise((resolve, reject) => {
        var lexicalEntries = rawData.results[0].lexicalEntries[0];
        var senses = lexicalEntries.entries[0].senses[0];

        var title = word;
        var def = senses.definitions;
        var p = new Promise((resolve, reject) => {
            translate(word, {to: 'vi'}).then(function (res) {
                resolve(res.text);
            });}
        ).then(mean => {
            console.log(mean);

            var trans = lexicalEntries.pronunciations[0].phoneticSpelling;
            var sample = senses.examples[0].text;
            console.log(trans);
            resolve(createNewWord(title, def, mean, trans, sample));
        });
    });
}

function getVnMean(word) {
    return Promise((resolve, reject) => {
        translate(word, {to: 'vi'}).then(function (res) {
            resolve(res.text);
        });
    });
}
function createNewWord(title, def, mean, trans, sample) {
    return {
        title: title,
        def: def,
        mean: mean,
        trans: trans,
        sample: sample
    }
}

module.exports.getNewWord = getNewWord;