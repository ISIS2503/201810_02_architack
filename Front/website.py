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


#Constantes
path = "http://172.24.42.30:8080/api/"
#Fin constantes

#Declaracion APP
app = Flask(__name__)
app.secret_key = 'KgCeooHTX37S5LvbD-_FKhyFSr8GuzIcC0NANtB1sJtl8sMv3JcQP6ypCJtvezFH'
oauth = OAuth(app)


#Declaracion AUTH0
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
    
@app.route('/callback')
def callback_handling():
    # Handles response from token endpoint
    resp = auth0.authorize_access_token()
    id_token = resp['id_token']
    url = 'https://isis2503-jagomez1.auth0.com/userinfo'
    headers = {'authorization': 'Bearer ' + resp['access_token']}
    resp = requests.get(url, headers=headers)
    userinfo = resp.json()

    # Store the tue user information in flask session.
    session['jwt_payload'] = userinfo
    
    session['id_token'] = id_token 
    
    session['profile'] = {
        'user_id': userinfo['sub'],
        'name': userinfo['name'],
        'picture': userinfo['picture']
    }
    return redirect('/dashboard')
    
@app.route('/dashboard')
def dashboard():
    unidadId = request.args.get('idUnidad')
    headers = {'Authorization': 'Bearer ' + session['id_token']}
    data = requests.get(path + 'unidadresidencial/' + unidadId + "/residencias", headers = headers).json()
    
    return render_template('dashboard.html',
                           residenciasUnidad = data,
                           token = session['id_token'])
                           
                           
                           
@app.route('/unidades')
def unidades():
    headers = {'Authorization': 'Bearer ' + session['id_token']}
    data = requests.get(path + 'unidadresidencial/', headers = headers).json()
    
    return render_template('unidades.html',
                           unidades = data)
    
@app.route('/login')
def login():
    return auth0.authorize_redirect(redirect_uri='http://localhost:9080/callback', audience='https://isis2503-jagomez1.auth0.com/userinfo')

@app.route('/logout')
def logout():
    session.clear()
    params = {'returnTo': url_for('index', _external=True), 'client_id': 'Y_BZT-Tyb4Z09mgbRkkfZrR7GWN1wJ4y'}
    return redirect(auth0.api_base_url + '/v2/logout?' + urlencode(params))
    
if __name__ == '__main__':
	app.run(debug=False,host='0.0.0.0',port=9080)
    
    
    
    
    
    
    
    
    
    
   