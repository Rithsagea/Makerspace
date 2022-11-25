const path = require('path');
const express = require('express');
const multer = require('multer');
const cors = require('cors');
const { MongoClient } = require("mongodb");

const { db_url } = require('./config.json');
const client = new MongoClient(db_url);
const db = client.db('makerspace');
const projects = db.collection('projects');

async function addProject(project) {
	const res = await projects.insertOne(project);
}

const app = express();
const port = 8000;
const upload = multer();

app.use(cors());
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
	console.log(req.body); // form info
	console.log(req.files); // form files
	res.send('Received form!');

	// const project = {
	// 	name: 'Lower GI',
	// 	category: 'Science',
	// 	attributions: ['cc', 'sharealike'],
	// 	designer: 'https://www.thingiverse.com/airforce/designs'
	// };

	// addProject(project);
});

app.listen(port, () => {
	console.log(`Makerspace server listening on port ${port}`);
});