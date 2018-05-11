from flask import Flask, jsonify, request
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
import smtplib

app = Flask(__name__)

def sendMail(receptor, asunto, cuerpo):
	server = smtplib.SMTP('smtp.gmail.com', 587)
	server.starttls()
	server.login("jagv1998@gmail.com", "bvelmbnnqeytvyki")

	msg = MIMEMultipart()
	msg['Subject'] = asunto

	body = cuerpo

	msg.attach(MIMEText(body, 'plain'))
	server.sendmail("jagv1998@gmail.com", receptor, msg.as_string())
	print("Mail sent.")
	server.close()



@app.route('/')
def index():
	return "Bienvenido al sistema de mensajeria"

@app.route('/alarm', methods=['POST'])
def testMethod():
	return jsonify({'msg': 'ok'})

@app.route('/healthError', methods=['POST'])
def healthCheckEmail():
	content = request.json
	x = 'el correo se ha enviado existosamente a ' + content['correo'] + ' con el contenido: ' + content['cuerpo']
	sendMail(content['correo'],content['asunto'],content['cuerpo'])
	return jsonify({'msg': x})

if __name__ == '__main__':
	app.run(debug=False,host='0.0.0.0',port=8088)
