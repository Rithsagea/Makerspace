const express = require('express');
const multer = require('multer');
const cors = require('cors');
const { MongoClient } = require("mongodb");

const { db_url } = require('./config.json');
const client = new MongoClient(db_url);
const db = client.db('makerspace');
const projects = db.collection('projects');

projects.find().forEach(doc => {
	console.log(doc);
});

const app = express();
const port = 8080;
const upload = multer({ dest: 'uploads/' });

app.use(cors());
app.use(express.static('public'));

app.post('/api/form', upload.any(), (req, res) => {
	const form = req.body;

	console.log(req.body);
	console.log(req.files);
	res.send('Received form!');
});

app.listen(port, () => {
	console.log(`Makerspace server listening on port ${port}`);
});