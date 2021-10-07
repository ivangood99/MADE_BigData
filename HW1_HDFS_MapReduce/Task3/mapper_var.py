#!/usr/bin/env python3

import sys

counter = 0
sum = 0
square_sum = 0

while True:
    line = sys.stdin.readline()
    if line == "":
        break
    arr = line.strip().split(";")
    price = arr[1]
    if price == "" or price == "price":
        continue
    else:
        sum += float(price)
        square_sum += float(price) ** 2
        counter += 1
        
	
print(str(counter) + "\t" + str(sum / counter) + "\t" + str(square_sum / counter - (sum / counter) ** 2))