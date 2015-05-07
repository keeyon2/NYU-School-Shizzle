from gensim import models
import numpy as np
from sklearn.cluster import SpectralClustering, KMeans, MiniBatchKMeans


def get_array(word, mods): 
	feat_arrays = np.zeros(np.shape(mods.syn0))
	i = 0
	for w in word:
		feat_arrays[i] = mods[w]
		i = i+1
	return feat_arrays
		
		
word_mod13 = models.Word2Vec.load('model2013')
word_mod14 = models.Word2Vec.load('model2014')
word_mod12 = models.Word2Vec.load('model2012')



