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



app = Flask(__name__)
app.secret_key = 'KgCeooHTX37S5LvbD-_FKhyFSr8GuzIcC0NANtB1sJtl8sMv3JcQP6ypCJtvezFH'

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

if __name__ == '__main__':
	app.run(debug=False,host='0.0.0.0',port=9160)
    
    
    
    
    
    
    
    
    
    
   