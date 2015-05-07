#from BeautifulSoup import BeautifulSoup as Soup
from BeautifulSoup import BeautifulSoup as Soup
from urllib2 import urlopen
import re
import cookielib, urllib2
import sys

def openBrowser(url):
	try:
		cj = cookielib.CookieJar()
		opener = urllib2.build_opener(urllib2.HTTPCookieProcessor(cj))
		request = urllib2.Request(url)
		response = opener.open(request)
		return response
	except:
		print "Check internet connection / Probably blocked"
		pass

def find_stuff(reg, files, pos):
	pattern = re.compile(reg)
	objects = []
	for stuff in files:
		objs = re.findall(pattern, str(stuff))
		if objs != []:
			objects.append(objs[pos-1])
	return objects

def parsing_lines(arts, reg):
	
	lines = str(arts).split('\n')
	joined = ' '.join(lines)
	parsed = re.findall(reg, joined)
	return parsed

def Getting_articles(links, tags, baseUrl, iters, no_tags, iter2):
	bit = 0
	final_article = []
	final_header = []
	final_tags = []
	for i in range(iters):
		x = baseUrl+links[i]
		newu = openBrowser(x)
		z = Soup(newu.read())
		full_article = []
		bit = 0
		s = z.findAll("h1")
		nreg = re.compile('<h1>(.+?)</h1>')
		headers = re.findall(nreg, str(s))
		article = z.findAll("p")
		for article_lines in article:
			if '"articleLocation"' in str(article_lines):
				nreg = re.compile('</span>(.+?)</p>')
				parsedLines = parsing_lines(article_lines, nreg)
				bit = 1
				try:
						full_article.append(parsedLines[0])
				except:
						break
			elif bit == 1:
				nreg = re.compile('<p>(.+?)</p>')
				parsedLines = parsing_lines(article_lines, nreg)
				try:
					full_article.append(parsedLines[0])
				except:
					break
		if no_tags == False:
        		nreg = re.compile('>(.+?)</a>')
        		tag = re.findall(nreg, str(tags[i]))

        	if full_article[:-3] != []:
                	x = ''.join(full_article)
                	final_article.append(x)
			final_headers.append(headers)
                	if no_tags == False:
				final_tags.append(tag)
	return final_article, final_headers

def removePunct(line):
	x = re.findall(r"[\w']+", line.lower())
	return x

def CallExtractor1(links, baseUrl):
	final_article = []
	final_headers = []
	final_tags = []
	for i in range(len(links)-3):
		if '/video' in str(links[i]):
			continue
		if 'blogs.' in str(links[i]):
			continue
		x = baseUrl+links[i]	
		newu = openBrowser(x)
		if newu == None:
			continue
		z = Soup(newu.read())
		full_article = []
		bit = 0
		s = z.findAll("h1")
		nreg = re.compile('<h1>(.+?)</h1>')
		headers = re.findall(nreg, str(s))
		article = z.findAll("p")
		for arts in range(len(article)):
			if '"articleLocation"' in str(article[arts]):
				nreg = re.compile('</span>(.+?)</p>')
			elif '"articleLocatio&lt;/span&gt;n"' in str(article[arts]):
				nreg = re.compile('</span>(.+?)</p>')
			else:
				nreg = re.compile('<p>(.+?)</p>')
			full = re.findall(nreg, str(article[arts]))
			
			try:
				if '(Reporting' in str(full[0]):
					break
				elif '(Additional reporting' in str(full[0]):
                                        break
				elif '(Editing' in str(full[0]):
                                        break

				elif 'Back to top' in str(full[0]):
                                        break


				elif full[0] != [] and not full[0].isupper():

					full[0] = full[0].replace('    ', ' ')
					full_article.append(full[0].replace('  ', ' '))
			except:
				pass
	
		final_article.append(full_article)
	
		try:
			final_headers.append(headers[0])
		except:
			final_headers.append(headers)
			pass
	return final_article, final_headers


def main(m, y, dates, path):
	baseUrl = "http://www.reuters.com"
	terms  = [ 'GCA-Economy2010', 'businessNews', 'ousivMolt', 'mergerNews', 'marketNews']

	for d in dates:
		print d
		for term in terms:
				url = "http://www.reuters.com/news/archive/" + term + "?date=" + m +d + y
				print url
				u = openBrowser(url)
				try:
					f = Soup(u.read())
				except:
					continue
				regex = '<h2><a href="?\'?([^"\'>]*)'
				features = f.findAll("h2")
				tags = f.findAll("span", "relatedTopics")


				links = find_stuff(regex, features, 1)
				print links
				val = 0
				final_article = []
				final_headers = []
				final_tags = []
				print len(links)

				final_article, final_headers = CallExtractor1(links, baseUrl)
				

				for i in range(len(final_article)):
					if len(final_article) == len(final_headers):
						files = open(path+'NewsArticles/News2012/'+term +'_'+ m+d+y +'_'+str(i)+'.txt', 'wb')
						if len(final_headers[i]) != 0:
							files_header = open(path+'NewsHeaders/'+term +'_'+ m+d+y +'_'+str(i)+'.txt', 'wb')
							files_header.write(str(final_headers[i])+'\n')
							files_header.close()
						
						files.writelines("%s\n" % item.strip() for item in final_article[i])
						files.close()

import sys
'''
if len(sys.argv) >4:
	x = sys.argv[1]
	date = x.split(',')
	month = sys.argv[2]
	year = sys.argv[3]
	path = sys.argv[4]
elif len(sys.argv) > 3:
	date = sys.argv[1].split(',')
	month = sys.argv[2]
	year = '2014'
	path = '/home/suraj2991/News/Newfolder/'
else:
	print 'Please provide proper arguments in the order [Dates, Month, Year]'
	sys.exit()
'''
date=['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30', '31']
month=['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'] 
year=['2012']

m_31 = [ '03', '05', '07', '08', '10', '12']
feb = ['02']
path='/home/ss7359/News/'

for y in year:
    for m in month:
	if m in m_31:
		print '31 days' 
		main(m, y, date, path)
	elif m == '02':
	    if y in ['2012', '2008']:
		print 'Feb'
		main(m,y, date[:-2], path)
	    else:
		main(m,y, date[:-3], path)
	else:
		print '30 days'
		main(m,y, date[:-1], path)
		

