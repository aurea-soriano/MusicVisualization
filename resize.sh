#!/bin/bash

for i in $(ls /home/aurea/Dropbox/image-midi-Copia/); do

	convert -resize 200%x100%  "/home/aurea/Dropbox/image-midi-Copia/"$i "/home/aurea/Dropbox/image-midi-Copia/"$i
done


