import json
import os
import sys
import toml
from os.path import split, splitext

file = sys.argv[1]
parsed_json = json.loads(file)

base_path, file_name = os.path.split(file)
toml_file_name = os.path.splitext(file_name)[0] + '.toml'

print("Writing build description to", toml_file_name)
with open(toml_file_name, 'w') as outfile:
   toml.dump(parsed_json, outfile)
