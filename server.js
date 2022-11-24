const path = require('path');
const express = require('express');
const multer = require('multer');
const cors = require('cors');
const { MongoClient } = require("mongodb");

const { db_url } = require('./config.json');
const client = new MongoClient(db_url);
const db = client.db('makerspace');
const projects = db.collection('projects');

const app = express();
const port = 8080;
const upload = multer();

app.use(cors());
app.use(express.static('public'));

app.get('/form', (req, res) => {
	res.sendFile(path.join(__dirname, '/form.html'));
});

app.post('/api/form', upload.any(), (req, res) => {
	console.log(req.body); // form info
	console.log(req.files); // form files
	res.send('Received form!');
});

app.listen(port, () => {
	console.log(`Makerspace server listening on port ${port}`);
});