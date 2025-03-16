#!/bin/zsh
# Check if Lighthouse is installed
if ! command -v lighthouse &> /dev/null
then
    echo "❌ Error: Lighthouse is not installed. Install it using: npm install -g lighthouse"
    exit 1
fi

# Validate input URL
if [ -z "$1" ]; then
    echo "❌ Error: No URL provided. Usage: ./run_lighthouse.sh <URL> <CHANNEL>"
    exit 1
fi

# Assign variables
URL=$1
CHANNEL=$2

echo "📊 Running Lighthouse audit on $URL using Chrome on port 9222..."
lighthouse "$URL" --preset="$CHANNEL" --port=9222 --output=json --output-path=target/lighthouse-report.json

echo "✅ Lighthouse audit completed. Results saved to target/lighthouse-report.json"
