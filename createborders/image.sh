#!/bin/bash

for i in $(ls); do

	./main $i
	echo $i
done


