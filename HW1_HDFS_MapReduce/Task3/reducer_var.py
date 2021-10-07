#!/usr/bin/env python3

import sys

counter = 0
avg = 0
var = 0

while True:
    line = sys.stdin.readline().rstrip()
    if line == "":
        break
    new_counter, new_avg, new_var = line.split("\t")
    new_counter = float(new_counter)
    new_avg = float(new_avg)
    new_var = float(new_var)
    var = (counter * var + new_counter * new_var) / (counter + new_counter) + counter * new_counter * ((avg - new_avg) / (counter + new_counter)) ** 2
    avg = (counter * avg + new_counter * new_avg) / (counter + new_counter)
    counter += new_counter

print(var)