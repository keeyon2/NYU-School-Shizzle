from flask import Flask
from flask import render_template
from gensim import models
import numpy as np
from operator import itemgetter
from flask import request, Response, jsonify
#from flask.ext.cors import CORS
app = Flask(__name__)
#cors = CORS(app)

#CORS(app, resources=r'/example_array', allow_headers='Content-Type')

def get_words(tups, w_list):
	try:
	    for keys, vals in tups:
		if keys not in w_list:
			w_list.append(keys)
	except:
		val = 1
	return w_list

def get_similarities(m1, m2, m3, m4, m5, m6, m7, word, w_n):
	mods = [m1, m2, m3, m4, m5, m6, m7]
	simis = []
	for w in word:
		simis_w = []
		for mos in mods:
			try:
			    val = mos.similarity(w_n, w)
			    simis_w.append(val+1)
			except:
			    simis_w.append(0)
		simis.append(simis_w)
	return simis
		    


@app.route('/')
def index():
    return render_template('index.html')

def get_array(word, vals):
	year_lists = ['2008', '2009', '2010', '2011', '2012', '2013', '2014']
	final_list = []
	for v in range(len(vals)):
		dict2 = {}
		dict2['date'] = year_lists[v]
		dict2['price'] = vals[v]
		dict2['symbol'] = word
		final_list.append(dict2)
	return final_list
		

def create_dict(tuples):
	lists = []
	for i,j,k in tuples:
		word_dict = {}
		word_dict['key'] = i
		word_dict['values'] = get_array(i,j)
		lists.append(word_dict)
	return lists

def get_wordsimilar(model, word):
	try: 
		w = tuple(model.most_similar(word))
	except: 
		w = []
	return w

@app.route("/HighVar/<word_name>", methods=['GET'])
def high_var(word_name):
    
    print 'Getting words'
    x1 = get_wordsimilar(mod08,word_name)
    x2 = get_wordsimilar(mod09,word_name)
    x3 = get_wordsimilar(mod10,word_name)
    x4 = get_wordsimilar(mod11,word_name)
    x5 = get_wordsimilar(mod12,word_name)
    x6 = get_wordsimilar(mod13,word_name)
    x7 = get_wordsimilar(mod14,word_name)
    words = []
    words = get_words(x1, words) 
    words = get_words(x2, words)
    words = get_words(x3, words)
    words = get_words(x4, words) 
    words = get_words(x5, words)
    words = get_words(x6, words)
    words = get_words(x7, words)
    print words
    similars = get_similarities(mod08, mod09, mod10, mod11, mod12, mod13, mod14, words, word_name)
    varians = map(np.var, similars)
    pairs = zip(words, similars, varians)	
    final_file = {}
    pairs.sort(key=itemgetter(2), reverse=True)
    final_file['Results'] = create_dict(pairs[:4])
    return jsonify(final_file)
    #return render_template('index.html', result=final_file)

@app.route("/LowVar/<word_name>", methods=['GET'])
def low_var(word_name):
    
    print 'Getting words'
    x1 = get_wordsimilar(mod08,word_name)
    x2 = get_wordsimilar(mod09,word_name)
    x3 = get_wordsimilar(mod10,word_name)
    x4 = get_wordsimilar(mod11,word_name)
    x5 = get_wordsimilar(mod12,word_name)
    x6 = get_wordsimilar(mod13,word_name)
    x7 = get_wordsimilar(mod14,word_name)
    words = []
    words = get_words(x1, words) 
    words = get_words(x2, words)
    words = get_words(x3, words)
    words = get_words(x4, words) 
    words = get_words(x5, words)
    words = get_words(x6, words)
    words = get_words(x7, words)
    print words
    similars = get_similarities(mod08, mod09, mod10, mod11, mod12, mod13, mod14, words, word_name)
    varians = map(np.var, similars)
    pairs = zip(words, similars, varians)	
    final_file = {}
    pairs.sort(key=itemgetter(2), reverse=True)
    final_file['Results'] = create_dict(pairs[-4:])
    return jsonify(final_file)
    #return render_template('index.html', result=final_file)

print "Loading Models 2008 - 2011"
mod08 = models.Word2Vec.load('model2008')
mod09 = models.Word2Vec.load('model2009')
mod10 = models.Word2Vec.load('model2010')
mod11 = models.Word2Vec.load('model2011')
print "2012 - 2014"
mod12 = models.Word2Vec.load('model2012')
mod13 = models.Word2Vec.load('model2013')
mod14 = models.Word2Vec.load('model2014')
app.run(host='0.0.0.0')
