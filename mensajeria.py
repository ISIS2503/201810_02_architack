from flask import Flask, jsonify, request

app = Flask(__name__)

@app.route('/')
def index():
    return "Hello, World!"

@app.route('/postTest', methods=['POST'])
def testMethod():	content = request.json
	x = 'el correo se ha enviado existosamente a ' + content['correoReceptor'] + ' debido a que ' + content['cuerpo'] + '.'
	return jsonify({'msg': x})

if __name__ == '__main__':
    app.run(debug=False,host='0.0.0.0',port=8080)
