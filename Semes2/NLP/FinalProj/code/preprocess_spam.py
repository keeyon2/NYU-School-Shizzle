import pandas as pd
import numpy as np
import os, glob, sys, re

def preprocess_data(dir, labels, ids):
    for root,dirNames,fname in os.walk(dir):
	files = glob.glob(os.path.join(root, '*.txt'))
			
	
	final_dataset = []
	for f in files:	
		sents = ''
		ids += 1	
        	for line in open(f, 'r'):
			x = re.sub("[^a-zA-Z]", " ",line).lower().split()
			sents +=  ' '.join(x)
			sents += ' '
		final_dataset.append([str(ids), labels, sents])

	dat = pd.DataFrame(final_dataset)
        return dat, ids
dir_list = ['enron1', 'enron2', 'enron3', 'enron4', 'enron5', 'enron6']
data_lists = []
final_data = pd.DataFrame()
id = 0
for dirs in dir_list:
	print 'Directory: ', dirs
	ham_dat, id = preprocess_data(dirs+'/ham', 1, id)
	spam_dat, id = preprocess_data(dirs+'/spam', 0, id)	

	new_dat = spam_dat.append(ham_dat)
	new_dat.index = range(len(new_dat))
	final_dat = new_dat.reindex(np.random.permutation(new_dat.index))
	print 'Merging with previous data'
	final_data = final_data.append(final_dat) 
	print 'Data contains ', len(final_data), ' mails'

print 'Collected all data'
print 'Shuffling data'
final_data.index = range(len(final_data))
Data = final_data.reindex(np.random.permutation(final_data.index))
print 'Storing Train and Test Data'

Train_data = Data.iloc[:25000]
Test_data = Data.iloc[25000:]

Train_data.to_csv('Spam_train.tsv', sep = '\t', index = False)
Test_data.to_csv('Spam_test.tsv', sep = '\t', index = False)

