'use strict';

let path = require('path');
let request = require('request-promise');
let translate = require('google-translate-api');

function getNewWord(req, res) {
    //noinspection JSUnresolvedVariable
    let word = req.query.word;

    request({
        uri: 'https://od-api.oxforddictionaries.com:443/api/v1/entries/en/' + word,
        method: 'GET',
        headers: {
            "Accept": "application/json",
            "app_id": process.env.OXFORD_APP_ID,
            "app_key": process.env.OXFORD_APP_KEY
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
        let lexicalEntries = rawData.results[0].lexicalEntries[0];
        let senses = lexicalEntries.entries[0].senses[0];

        let title = word;
        let def = senses.definitions[0];
        let p = new Promise((resolve, reject) => {
                translate(word, {to: 'vi'}).then(function (res) {
                    resolve(res.text);
                });
            }
        ).then(mean => {
            let trans = lexicalEntries.pronunciations[0].phoneticSpelling;
            trans = '/' + trans + '/';
            let sample = senses.examples[0].text;
            resolve(createNewWord(title, def, mean, trans, sample));
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