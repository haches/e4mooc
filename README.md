#E4MOOC
E4MOOC is short for *Eiffel 4 Massive Open Online Courses*-. It's a web application that allows to display, modify, compile and run Eiffel programs in the browser.

## URL structure

The base URL displays a list of all available projects.

Each project has it's own URL which is the base URL + */#project_name*

_Query parameters_

A URL of a particular project can have query parameters. Currently, the following parameters will be processed (and all other parameters are ignored):
* ```id``` a string representing a user id
* ```outputht``` an integer value defining the height of the output box
* ```bgcolor``` a hex-formated string that defines the background color
