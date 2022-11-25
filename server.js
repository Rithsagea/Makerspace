const express = require('express');
const multer = require('multer');
const { MongoClient } = require("mongodb");
const bodyparser = require('body-parser'); //add

const { db_url } = require('./config.json');
const client = new MongoClient(db_url);
const db = client.db('makerspace');
const projectCollection = db.collection('projects');
const fileCollection = db.collection('files');

const app = express();
const port = 8000;

const upload = multer();

app.use(bodyparser.json());
app.use(bodyparser.urlencoded({ extended: false }));

app.set('view engine', 'ejs');
app.use(express.static('public'));
app.use('/views', express.static(__dirname + '/views'));

app.get('/', (req, res) => {
	res.render('pages/index');
});

app.get('/form', (req, res) => {
	res.render('pages/form');
});

app.post('/api/form', upload.any(), (req, res) => {
	console.log(req.body);
	res.send('Received form!');
	projectCollection.insertOne(req.body);
	console.log('Uploaded Project');
});

app.post('/api/file', upload.any(), (req, res) => {
	let file = req.files[0];
	fileCollection.insertOne({
		name: file.originalname,
		data: file.buffer
	}).then(result => {
		res.send(result.insertedId.toString());
	});
});

app.listen(port, () => {
	console.log(`Makerspace server listening on port ${port}`);
});