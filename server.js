const express = require('express');
const { MongoClient } = require("mongodb");
const bodyparser = require('body-parser'); //add

const { db_url } = require('./config.json');
const client = new MongoClient(db_url);
const db = client.db('makerspace');
const projects = db.collection('projects');

async function addProject(project) {
	const res = await projects.insertOne(project);
}

const app = express();
const port = 8000;

app.use(bodyparser.json());
app.use(bodyparser.urlencoded({ extended: true })) //ad

app.set('view engine', 'ejs');
app.use(express.static('public'));
app.use('/views', express.static(__dirname + '/views'));

app.get('/', (req, res) => {
	res.render('pages/index');
});

app.get('/form', (req, res) => {
	res.render('pages/form');
});

app.post('/api/form', (req, res) => {
	console.log(req.body);

	res.send('Received form!');

	// const project = {
	// 	name: req.body['project-name'],
	// 	source: req.body['project-source'],
	// 	attributions: req.body['attributions'],
	// 	files: []
	// };

	// addProject(project);
});

app.listen(port, () => {
	console.log(`Makerspace server listening on port ${port}`);
});