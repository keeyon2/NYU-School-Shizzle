import sys
import re

def printTabCount(name_of_file):
    input = open(name_of_file, 'r')
    number_of_tabs = 0
    for line in input:
        line = re.split(r'\t+', line)
        for sep_by_tab in line:
            number_of_tabs += len(line) - 1

    print ("File %s has %d tabs" % (name_of_file, number_of_tabs))

if __name__ == '__main__':
    for file in sys.argv[1:]:
        printTabCount(file)
