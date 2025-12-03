#!/bin/bash

# Usage: ./create_file.sh day00
if [ "$#" -ne 1 ]; then
    echo "Usage: $0 dayXX"
    exit 1
fi

day="$1"
if [[ ! "$day" =~ ^day[0-9]{2}$ ]]; then
    echo "Error: Argument must be in the format 'dayXX' (e.g., day04)"
    exit 1
fi

day_cap=$(echo "$day" | awk '{print toupper(substr($0,1,1)) substr($0,2)}')

main_dir="src/main/java/org/adventofcode/$day"
test_dir="src/test/java/org/adventofcode/$day"
main_file="$main_dir/$day_cap.java"
test_file="$test_dir/${day_cap}Test.java"
actual_file="src/main/resources/actual/$day.txt"
example_file="src/main/resources/example/$day.txt"

mkdir -p "$main_dir" "$test_dir" "src/main/resources/actual/adventofcode" "src/main/resources/example/adventofcode"

cp "src/main/java/org/adventofcode/utils/Day00.txt" "$main_file"

# Replace package, class, and constant names in the copied file
sed -i '' \
    -e "s|package org\.adventofcode\.utils;|package org.adventofcode.$day;|" \
    -e "s/Day00/$day_cap/g" \
    -e "s/day00/$day/g" \
    "$main_file"

touch "$actual_file" "$example_file" "$test_file"

echo "Created files for $day"
