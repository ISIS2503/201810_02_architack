from flask import Flask, jsonify, request
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
import smtplib

app = Flask(__name__)

@app.route('/')
def index():
	return "Bienvenido al sistema de mensajeria"

@app.route('/alarm', methods=['POST'])
def testMethod():
	return jsonify({'msg': 'ok'})

if __name__ == '__main__':
	app.run(debug=False,host='0.0.0.0',port=8088)
