#Importados de websockets
import json
import threading
from threading import Lock
from flask import Flask, render_template
from flask_socketio import SocketIO, emit
from kafka import KafkaConsumer

#Importados de auth0
from functools import wraps
import json
from os import environ as env
from werkzeug.exceptions import HTTPException

from dotenv import load_dotenv, find_dotenv
from flask import Flask
from flask import jsonify
from flask import redirect
from flask import render_template
from flask import session
from flask import url_for
from authlib.flask.client import OAuth
from six.moves.urllib.parse import urlencode
import requests



from flask import Flask, jsonify, request

#"threading", "eventlet" or "gevent", eventlet is the best
#async_mode = eventlet 

app = Flask(__name__)
app.secret_key = 'KgCeooHTX37S5LvbD-_FKhyFSr8GuzIcC0NANtB1sJtl8sMv3JcQP6ypCJtvezFH'
# socketio = SocketIO(app, async_mode=async_mode)
# thread = None
# thread_lock = Lock()

oauth = OAuth(app)

auth0 = oauth.register(
    'auth0',
    client_id='Y_BZT-Tyb4Z09mgbRkkfZrR7GWN1wJ4y',
    client_secret='KgCeooHTX37S5LvbD-_FKhyFSr8GuzIcC0NANtB1sJtl8sMv3JcQP6ypCJtvezFH',
    api_base_url='https://isis2503-jagomez1.auth0.com',
    access_token_url='https://isis2503-jagomez1.auth0.com/oauth/token',
    authorize_url='https://isis2503-jagomez1.auth0.com/authorize',
    client_kwargs={
        'scope': 'openid profile',
    },
)

@app.route('/')
def index():
	return render_template('index.html')
    

# def background_thread_websocket():
    # consumer = KafkaConsumer('TOPICO DE MOSQUITTOOO', group_id='temperature', bootstrap_servers=['DONDE ESTA KAFKA? EJ:localhost:8090'])
    # for message in consumer:
        # json_data = json.loads(message.value.decode('utf-8'))
        # topicmqtt = json_data['topic']
        # messagemqtt = json_data['message']
        # residencia = topicmqtt.split('/')[1]
        
        # payload = {
            # 'residencia': residencia,
            # 'message': messagemqtt
        # }
        # socketio.emit('alarms', str(payload),
                      # namespace='/yale')
                      
              
#Cada vez que se conecta un cliente              
# @socketio.on('connect', namespace='/thermalcomfort')
# def test_connect():
    # global thread
    # with thread_lock:
        # if thread is None:
            # thread = socketio.start_background_task(target=background_thread_websocket)
    # emit('alarms', "Connected!!!")                      
                      
    
# Here we're using the /callback route.
@app.route('/callback')
def callback_handling():
    # Handles response from token endpoint
    resp = auth0.authorize_access_token()

    url = 'https://isis2503-jagomez1.auth0.com/userinfo'
    headers = {'authorization': 'Bearer ' + resp['access_token']}
    resp = requests.get(url, headers=headers)
    userinfo = resp.json()

    # Store the tue user information in flask session.
    session['jwt_payload'] = userinfo

    session['profile'] = {
        'user_id': userinfo['sub'],
        'name': userinfo['name'],
        'picture': userinfo['picture']
    }

    return redirect('/dashboard')
    
@app.route('/dashboard')
def dashboard():
    return render_template('dashboard.html',
                           userinfo=session['profile'],
                           userinfo_pretty=json.dumps(session['jwt_payload'], indent=4))


    
@app.route('/login')
def login():
    return auth0.authorize_redirect(redirect_uri='http://localhost:9160/callback', audience='https://isis2503-jagomez1.auth0.com/userinfo')

@app.route('/logout')
def logout():
    session.clear()
    params = {'returnTo': url_for('index', _external=True), 'client_id': 'Y_BZT-Tyb4Z09mgbRkkfZrR7GWN1wJ4y'}
    return redirect(auth0.api_base_url + '/v2/logout?' + urlencode(params))
    
if __name__ == '__main__':
	app.run(debug=False,host='0.0.0.0',port=9160)
    
    
    
    
    
    
    
    
    
    
   