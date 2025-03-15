#!/bin/zsh
lighthouse $1 --preset=desktop --port=9222 --output=json --output-path=target/lighthouse-report.json
