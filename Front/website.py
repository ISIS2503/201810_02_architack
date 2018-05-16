from flask import Flask, jsonify, request

app = Flask(__name__)

@app.route('/')
def index():
	return "Bienvenido al sistema de Yale. Ruiz, haga el login!"
	
if __name__ == '__main__':
	app.run(debug=False,host='0.0.0.0',port=9160)