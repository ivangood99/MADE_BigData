#!/usr/bin/env python3

import sys

counter = 0
avg = 0

while True:
    line = sys.stdin.readline().rstrip()
    if line == "":
        break
    new_counter, new_avg = line.split("\t")
    new_counter = float(new_counter)
    new_avg = float(new_avg)
    avg = (counter * avg + new_counter * new_avg) / (counter + new_counter)
    counter += new_counter

print(avg)