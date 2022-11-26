#!/usr/bin/env sh

export CHARTDIR=$(dirname $(readlink -f "$0"))
export UID=$(id -u)
export GID=$(id -g)

echo "CHARTDIR=$CHARTDIR;UID=$UID;GID=$GID" 1>&2
