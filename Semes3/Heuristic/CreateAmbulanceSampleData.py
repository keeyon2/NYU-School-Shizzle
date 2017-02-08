import random

f = open('data', 'w')
for x in range (0, 300):
    x = random.randint(0, 500)
    y = random.randint(0, 500)
    time = random.randrange(100, 200)
    s = "%d,%d,%d\n" % (x, y, time)
    f.write(s)
