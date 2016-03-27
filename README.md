# S4Pubs

SyPubs is a modification of E4Mooc to support Synquid.

## Server setup

Set the following environment variables

* S4PUBS: the folder where the S4PUBS projects are stored

* S4PUBS_TMP: the folder where Synquid stored temporary files

* S4PUBS_EXEC_FILE: the fully qualified location of the Python file that used to to run Synquid (is internally called via "python S4PUBS_EXEC_FILE")

Make sure to have Python installed!


## URL structure

The base URL displays a list of all available projects.

Each project has it's own URL which is the base URL + */#project_name*

_Query parameters_

A URL of a particular project can have query parameters. Currently, the following parameters will be processed (and all other parameters are ignored):
* ```outputht``` an integer value defining the height of the output box
* ```bgcolor``` a hex-formated string that defines the background color

## Details

S4Pubs will run the following command on the server (example):

```
-- assume S4PUBS_EXEC_FILE is synquid.py

python synquid.py /tmp/randomid/bst_insert.sq
```

For now, only a single .sq file is supported; i.e. the generated command will only contain the first .sq file that was found to be part of the project.
