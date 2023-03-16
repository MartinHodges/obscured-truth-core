#!/bin/sh

sed -i "s/image: martinhodges\/obscured-truth-$3:.*/image: martinhodges\/obscured-truth-$3:$2/g" $1 
